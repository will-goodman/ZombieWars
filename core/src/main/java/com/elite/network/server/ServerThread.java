package com.elite.network.server;

import com.elite.game.GameObject;
import com.elite.game.NetworkGame;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Set;

/**
 * A specific thread which connects to a specific client
 *
 * @author Will Goodman
 */
public class ServerThread extends Thread implements Serializable {

    private final Socket SOCKET;
    private volatile Hashtable<String, Lobby> lobbies;

    /**
     * Makes a new ServerThread object
     *
     * @param socket  The socket connected to the client
     * @param lobbies The list of lobbies on the server
     */
    public ServerThread(Socket socket, Hashtable<String, Lobby> lobbies) {
        this.SOCKET = socket;
        this.lobbies = lobbies;
    }

    /**
     * Continuously listens to messages from the client
     */
    public void run() {
        try {
            final PrintWriter OUTPUT = new PrintWriter(SOCKET.getOutputStream(), true);
            final BufferedReader INPUT = new BufferedReader(new InputStreamReader(SOCKET.getInputStream()));

            OUTPUT.println("Connected!");

            boolean connected = true;
            boolean inLobby = false;
            boolean hostOfLobby = false;
            String playerName = "";
            Lobby currentLobby = null;
            boolean verified = false;

            while (connected) {
                while (!inLobby) {
                    String command = INPUT.readLine();

                    String lobbyName;
                    boolean isPrivate;
                    String password;
                    switch (command) {
                        //send all open lobbies
                        case "SEND_SERVERS":
                            Set<String> keys = lobbies.keySet();
                            OUTPUT.println(lobbies.size());
                            for (String key : keys) {
                                Lobby lobby = lobbies.get(key);
                                OUTPUT.println(key);
                                OUTPUT.println(lobby.getPlayers().size());
                                OUTPUT.println(lobby.getPrivacy());
                            }
                            break;
                        //connect a client to a lobby
                        case "CONNECT":
                            lobbyName = INPUT.readLine();
                            playerName = INPUT.readLine();
                            try {
                                Lobby lobby = lobbies.get(lobbyName.substring(0, lobbyName.length() - 1));

                                if (lobby.getPrivacy() && !verified) {
                                    String salt = lobby.getSalt();
                                    OUTPUT.println(salt);
                                    password = INPUT.readLine();
                                    if (!lobby.verifyPassword(password)) {
                                        OUTPUT.println("WRONG-PASSWORD");
                                        verified = false;
                                    } else {
                                        OUTPUT.println("VERIFIED");
                                        verified = true;
                                    }
                                } else {
                                    Player newPlayer = new Player(playerName, SOCKET.getRemoteSocketAddress().toString());
                                    lobby.addPlayer(newPlayer);
                                    lobbies.put(lobbyName, lobby);
                                    OUTPUT.println("Joined " + lobby.getName());
                                    inLobby = true;
                                    currentLobby = lobby;
                                }

                            } catch (NullPointerException e) {
                                OUTPUT.println("Lobby doesn't exist");
                            }
                            break;
                        //make a new lobby
                        case "HOST":
                            lobbyName = INPUT.readLine();
                            playerName = INPUT.readLine();
                            isPrivate = Boolean.parseBoolean(INPUT.readLine());
                            if (!lobbies.contains(lobbyName)) {
                                Player host = new Player(playerName, SOCKET.getRemoteSocketAddress().toString());
                                Lobby newLobby;
                                if (isPrivate) {
                                    password = INPUT.readLine();
                                    String salt = INPUT.readLine();
                                    newLobby = new Lobby(lobbyName, host, password, salt);
                                } else {
                                    newLobby = new Lobby(lobbyName, host);
                                }

                                lobbies.put(lobbyName, newLobby);
                                inLobby = true;
                                currentLobby = newLobby;
                                hostOfLobby = true;
                                OUTPUT.println("Lobby created");

                            } else {
                                OUTPUT.println("Lobby already exists");
                            }
                            break;
                        //disconnect a client from a lobby
                        case "DISCONNECT-LOBBY":
                            lobbyName = INPUT.readLine();
                            playerName = INPUT.readLine();
                            try {
                                Lobby lobby = lobbies.get(lobbyName);
                                Player player = new Player(playerName, SOCKET.getRemoteSocketAddress().toString());
                                lobby.removePlayer(player);
                                ArrayList<Player> players = lobby.getPlayers();

                                if (players.size() == 0) {
                                    lobbies.remove(lobbyName);
                                } else {
                                    lobby.setPlayers(players);
                                    lobbies.put(lobbyName, lobby);
                                }
                                OUTPUT.println("Disconnected from " + lobbyName);
                            } catch (NullPointerException e) {
                                OUTPUT.println("Failed to disconnect");
                            }
                            break;
                        //disconnect a client from a server
                        case "DISCONNECT-SERVER":
                            SOCKET.close();
                            connected = false;
                    }
                }


                ObjectInputStream in = new ObjectInputStream(SOCKET.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(SOCKET.getOutputStream());
                out.flush();

                NetworkGame game;
                //if the lobby is being created, then we must set the game state
                if (hostOfLobby) {
                    game = new NetworkGame();
                    currentLobby.setGame(game);
                    HashMap<String, GameObject> objects = (HashMap<String, GameObject>) in.readObject();

                    GameObject currentObject;
                    for (String key : objects.keySet()) {
                        currentObject = objects.get(key);
                        game.addObject(currentObject, key);
                    }

                    game.playerName = playerName;
                } else {
                    game = currentLobby.getGame();
                    GameObject currentObject;
                    HashMap<String, GameObject> objects = (HashMap<String, GameObject>) in.readObject();
                    for (String key : objects.keySet()) {
                        currentObject = objects.get(key);
                        game.addObject(currentObject, key);
                    }
                    out.writeObject(game.getObjects());
                    out.flush();
                    game.playerName = playerName;
                    playerName = game.playerName;

                }

                //start sending updates back to the client
                LobbyThread lobbyThread = new LobbyThread(game, out);
                lobbyThread.start();

                float x, y;
                //Continuously wait for commands from the client
                while (inLobby) {
                    String msg = INPUT.readLine();

                    if (msg.equals("ZOMBIE-DIED")) {
                        String player = INPUT.readLine();
                        game.removePlayer(Integer.parseInt(player));
                    } else if (msg.equals("QUIT")) {
                        lobbyThread.endLobby();
                        if (lobbies.get(currentLobby.getName()).getPlayers().size() > 1) {
                            lobbies.get(currentLobby.getName()).removePlayer(lobbies.get(currentLobby.getName()).getPlayers().get(0));
                            game.endGame();
                        } else {
                            lobbies.remove(currentLobby.getName());
                        }

                        inLobby = false;
                    } else {
                        String player = INPUT.readLine();
                        x = Float.parseFloat(INPUT.readLine());
                        y = Float.parseFloat(INPUT.readLine());
                        game.updateGame(player, msg, x, y);
                    }
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}