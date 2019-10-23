package com.elite.network;

import com.elite.entities.Crate;
import com.elite.entities.Zombie;
import com.elite.game.ClientGame;
import com.elite.game.GameObject;
import com.elite.game.NetworkGame;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.Hashtable;

/**
 * Connects to and sends/receives messages from the server
 * @author Will Goodman
 */
public class Client extends Thread implements Serializable{

    private Socket SOCKET;
    private BufferedReader INPUT;
    private PrintWriter OUTPUT;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ClientGame game;
    private boolean inGame = false;
    private boolean connectionStatus = false;

    /**
     * Constructor method for a new Client object
     */
    public Client() {
        try {
            SOCKET = new Socket(Keys.SERVER_ADDRESS, Keys.MAIN_PORT_NUMBER);
            INPUT = new BufferedReader(new InputStreamReader(SOCKET.getInputStream()));
            OUTPUT = new PrintWriter(SOCKET.getOutputStream(), true);
            connectionStatus = true;

            System.out.println(INPUT.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Whether a successful connection to the server has been made
     */
    public boolean getConnectionStatus() { return this.connectionStatus; }

    /**
     * @return The ClientGame for this client
     */
    public ClientGame getGame() { return this.game; }

    /**
     * Sets the client-side game object which must communicate with the server
     * @param game The game object on the client's side
     */
    public void setGame(ClientGame game) {
        this.game = game;
    }

    /**
     * The thread which continuously waits for messages from the server and then updates the game
     */
    public void run() {
        try {
            out = new ObjectOutputStream(SOCKET.getOutputStream());
            out.flush();

            if (in == null) {
                in = new ObjectInputStream(SOCKET.getInputStream());
            }

            while (inGame) {
                NetworkGame netGame = (NetworkGame) in.readObject();
                HashMap<String, GameObject> objects = netGame.getObjects();
                for (String key : objects.keySet()) {
                    GameObject object = objects.get(key);
                    if(object.getCommand().equals("QUIT")) {
                        System.out.println("Quitting lobby");
                        game.quit();
                    } else if (!object.getCommand().equals("HEALTH") && !object.getCommand().equals("AMMO")) {
                        if (game.isZombiePresent(key)) {
                            game.updateZombie(key, object.getX(), object.getY(), object.getCommand());
                        } else {
                            game.addZombies();
                            System.out.println("NEW ZOMBIE: " + key + " " + object.getX());
                        }
                    }
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all the servers available to join from the server
     * @return The list of servers being hosted on the server
     */
    public ArrayList<String> getServers() {
        ArrayList<String> servers = new ArrayList<>();
        try {
            OUTPUT.println("SEND_SERVERS");
            int numLobbies = Integer.parseInt(INPUT.readLine());
            for (int i = numLobbies; i > 0; i--) {
                String lobbyName = INPUT.readLine();
                String numPlayers = INPUT.readLine();
                String privacy = INPUT.readLine();
                servers.add(lobbyName + " " + numPlayers + " " + privacy);
                //servers.add(INPUT.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return servers;
    }


    /**
     * Sends commands from the client-game to the server
     * @param key The key/button which the player has pressed
     */
    public void keyPress(String key, int currentPlayer) {
        try {
            if (out == null) {
                out = new ObjectOutputStream(SOCKET.getOutputStream());
            }
            OUTPUT.println(key);
            OUTPUT.println(currentPlayer);
            OUTPUT.println(game.getZombie().getPlayerX());
            OUTPUT.println(game.getZombie().getPlayerY());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Host a public lobby
     * @param lobbyName The name of the lobby
     * @param host The name of the host player (the player who own's this client object)
     * @param gameMode The gamemode being played
     * @return Whether or not the lobby-creation was a success
     */
    public boolean hostLobby(String lobbyName, String host, String gameMode) {
        OUTPUT.println("HOST");
        OUTPUT.println(lobbyName);
        OUTPUT.println(host);
        //not a private lobby
        OUTPUT.println(false);

        try {
            out = new ObjectOutputStream(SOCKET.getOutputStream());
            String msg = INPUT.readLine();
            if (msg.equals("Lobby created")) {
                Hashtable<String, Zombie> zombies = game.getPlayers();
                HashMap<String, GameObject> objects = new HashMap<>();
                for(String key : zombies.keySet()) {
                    Zombie currentZombie = zombies.get(key);
                    System.out.println("ZOMBIE: " + key);
                    GameObject object = new GameObject(key, currentZombie.getX(), currentZombie.getY(), "NO-MOVEMENT");
                    objects.put(key, object);
                    //out.writeObject(object);
                }

                Hashtable<Integer, Crate> crates = game.getCrates();
                for (int key : crates.keySet()) {
                    Crate currentCrate = crates.get(key);
                    GameObject object = new GameObject(Integer.toString(key), currentCrate.getX(), currentCrate.getY(), currentCrate.getType());
                    objects.put(String.valueOf(key), object);
                    //out.writeObject(object);
                }
                out.writeObject(objects);
                /*Zombie human = game.getZombie();
                System.out.println("HUMAN: " + human);
                GameObject object = new GameObject(Integer.toString(game.getPlayerNow()), human.getX(), human.getY(), "NO-MOVEMENT");
                out.writeObject(object);*/
                inGame = true;
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }


    public boolean hostLobby(String lobbyName, String host, String gameMode, String password) {
        OUTPUT.println("HOST");
        OUTPUT.println(lobbyName);
        OUTPUT.println(host);
        //private lobby
        OUTPUT.println(true);
        byte[] salt = new byte[Keys.SALT_LENGTH];
        SecureRandom saltGen = new SecureRandom();
        saltGen.nextBytes(salt);
        String hashedPassword = hashPassword(password, salt.toString());
        OUTPUT.println(hashedPassword);
        OUTPUT.println(salt.toString());
        try {
            out = new ObjectOutputStream(SOCKET.getOutputStream());
            String msg = INPUT.readLine();
            if (msg.equals("Lobby created")) {


                inGame = true;

                Hashtable<String, Zombie> zombies = game.getPlayers();
                HashMap<String, GameObject> objects = new HashMap<>();
                for(String key : zombies.keySet()) {
                    Zombie currentZombie = zombies.get(key);
                    System.out.println("ZOMBIE: " + key);
                    GameObject object = new GameObject(key, currentZombie.getX(), currentZombie.getY(), "NO-MOVEMENT");
                    objects.put(key, object);
                    //out.writeObject(object);
                }

                Hashtable<Integer, Crate> crates = game.getCrates();
                for (int key : crates.keySet()) {
                    Crate currentCrate = crates.get(key);
                    GameObject object = new GameObject(Integer.toString(key), currentCrate.getX(), currentCrate.getY(), currentCrate.getType());
                    objects.put(String.valueOf(key), object);
                    //out.writeObject(object);
                }
                out.writeObject(objects);
                /*Zombie human = game.getZombie();
                System.out.println("HUMAN: " + human);
                GameObject object = new GameObject(Integer.toString(game.getPlayerNow()), human.getX(), human.getY(), "NO-MOVEMENT");
                out.writeObject(object);*/
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Connect to a public lobby
     * @param lobbyName The name of the lobby to connect to
     * @param player The name of the player who's joining
     * @return Whether or not the connection was a success
     */
    public boolean connectLobby(String lobbyName, String player) {
        OUTPUT.println("CONNECT");
        OUTPUT.println(lobbyName);
        OUTPUT.println(player);
        try {

            String msg = INPUT.readLine();
            if (msg.contains("Joined")) {
                ArrayList<Zombie> zombies = game.getAllPlayers();
                HashMap<String, GameObject> objects = new HashMap<>();
                GameObject zombie;
                for(int i=0; i<3; i++) {
                    zombie = new GameObject(Integer.toString(i + 5), zombies.get(i).getX(), zombies.get(i).getY(), "NO-MOVEMENT");
                    System.out.println("SENDING: " + i + 5 + " " + zombie.getX());

                    //out.writeObject(zombie);
                    //out.flush();
                    objects.put(Integer.toString(i + 5), zombie);
                }
                out = new ObjectOutputStream(SOCKET.getOutputStream());
                out.writeObject(objects);
                out.flush();

                //GameObject object = new GameObject("test", human.getX(), human.getY(), "NO-MOVEMENT");

                //out.writeObject(object);
                if (in == null) {
                    in = new ObjectInputStream(SOCKET.getInputStream());
                }

                objects = (HashMap<String, GameObject>) in.readObject();
                System.out.println(objects);

                HashMap<String, GameObject> newZombies = new HashMap<>();
                HashMap<String, GameObject> newCrates = new HashMap<>();
                for (String owner : objects.keySet()) {
                    if (objects.get(owner).getCommand().equals("HEALTH") || objects.get(owner).getCommand().equals("AMMO")) {
                        newCrates.put(owner, objects.get(owner));
                    } else {
                        newZombies.put(owner, objects.get(owner));
                    }
                }

                game.addZombies();
                game.addCrates(newCrates);
                inGame = true;
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyPassword(String lobbyName, String player, String password) {
        OUTPUT.println("CONNECT");
        OUTPUT.println(lobbyName);
        OUTPUT.println(player);

        try {
            String salt = INPUT.readLine();
            System.out.println("Got salt");
            String hashedAndSaltedPassword = hashPassword(password, salt);
            OUTPUT.println(hashedAndSaltedPassword);

            if (INPUT.readLine().equals("VERIFIED")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean connectLobby(String lobbyName, String player, String password) {
        OUTPUT.println("CONNECT");
        OUTPUT.println(lobbyName);
        OUTPUT.println(player);

        try {
            String salt = INPUT.readLine();
            System.out.println("Got salt");
            String hashedAndSaltedPassword = hashPassword(password, salt);
            OUTPUT.println(hashedAndSaltedPassword);

            if(INPUT.readLine().equals("VERIFIED")) {

                out = new ObjectOutputStream(SOCKET.getOutputStream());

                String msg = INPUT.readLine();
                if (msg.contains("Joined")) {
                    ArrayList<Zombie> zombies = game.getAllPlayers();
                    HashMap<String, GameObject> objects = new HashMap<>();
                    GameObject zombie;
                    for (int i = 0; i < 3; i++) {
                        zombie = new GameObject(Integer.toString(i + 5), zombies.get(i).getX(), zombies.get(i).getY(), "NO-MOVEMENT");
                        System.out.println("SENDING: " + i + 5 + " " + zombie.getX());

                        //out.writeObject(zombie);
                        //out.flush();
                        objects.put(Integer.toString(i + 5), zombie);
                    }
                    out.writeObject(objects);
                    out.flush();

                    //GameObject object = new GameObject("test", human.getX(), human.getY(), "NO-MOVEMENT");

                    //out.writeObject(object);
                    if (in == null) {
                        in = new ObjectInputStream(SOCKET.getInputStream());
                    }

                    objects = (HashMap<String, GameObject>) in.readObject();

                    HashMap<String, GameObject> newZombies = new HashMap<>();
                    System.out.println(objects);
                    HashMap<String, GameObject> newCrates = new HashMap<>();
                    for (String owner : objects.keySet()) {
                        if (objects.get(owner).getCommand().equals("HEALTH") || objects.get(owner).getCommand().equals("AMMO")) {
                            newCrates.put(owner, objects.get(owner));
                            //game.addCrate(owner, objects.get(owner).getX(), objects.get(owner).getY(), objects.get(owner).getCommand());
                        } else {
                            newZombies.put(owner, objects.get(owner));
                            //game.addZombie(owner, objects.get(owner));
                        }
                    }

                    game.addZombies();
                    game.addCrates(newCrates);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Quit the current game lobby
     */
    public void quitLobby() {
        if(inGame) {
            inGame = false;
            System.out.println("quitting");
            this.keyPress("QUIT", 0);
        }
    }

    /**
     * Salts and hashes a password before sending it over the network for joining a private lobby
     * @param password The password to be hashed and salted
     * @param salt The salt to be used
     * @return The salted and hashed password
     */
    private static String hashPassword(String password, String salt) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance(Keys.HASH_ALGORITHM);
            byte[] saltBytes = salt.getBytes();
            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
            byte[] hashedAndSalted = new byte[saltBytes.length + passwordBytes.length];
            System.arraycopy(saltBytes, 0, hashedAndSalted, 0, saltBytes.length);
            System.arraycopy(passwordBytes, 0, hashedAndSalted, saltBytes.length, passwordBytes.length);
            byte[] hashedPassword = algorithm.digest(hashedAndSalted);
            BigInteger hashInt = new BigInteger(1, hashedPassword);

            return hashInt.toString();
        } catch (NoSuchAlgorithmException e) {
            return e.getMessage();
        }
    }

}