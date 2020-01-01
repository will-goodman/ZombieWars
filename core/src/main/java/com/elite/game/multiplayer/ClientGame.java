package com.elite.game.multiplayer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.elite.audio.AudioAccessor;
import com.elite.audio.AudioSettings;
import com.elite.game.entities.characters.Zombie;
import com.elite.game.entities.pickups.AmmoCrate;
import com.elite.game.entities.pickups.Crate;
import com.elite.game.entities.pickups.HealthCrate;
import com.elite.game.entities.weapons.Bone;
import com.elite.game.GameType;
import com.elite.network.client.Client;
import com.elite.game.hud.EnergyBar;
import com.elite.ui.multiplayer.ServerListing;
import com.elite.game.logic.EnergyCost;
import com.elite.game.world.RenderWorld;
import com.elite.game.world.WorldAttributes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

/**
 * The game object on the client-side
 *
 * @author Will Goodman
 */
public class ClientGame extends RenderWorld implements GameType {

    private static final double maxEnergy = 100;
    private static long previousTime = System.nanoTime();
    private static final float TIME_STEP = 1.0f / 60.0f;

    private static int currentPlayer;
    private ArrayList<Integer> clientTeam = new ArrayList<>();
    private ArrayList<Integer> enemyTeam = new ArrayList<>();
    private double energy = 100;
    private World world;
    private Hashtable<Integer, Integer> footContacts = new Hashtable<>();
    private final Client CLIENT;
    private Hashtable<String, Zombie> zombies = new Hashtable<>();
    private Hashtable<String, Zombie> deadZombies = new Hashtable<>();
    private Hashtable<Integer, Crate> crates = new Hashtable<>();
    private HashMap<String, String> commands = new HashMap<>();
    private HashMap<String, GameObject> newZombies = new HashMap<>();
    private boolean addZombie;
    private HashMap<String, GameObject> newCrates = new HashMap<>();
    private boolean addCrate;
    private boolean clientTurn;
    private boolean bothPlayersJoined = false;
    private boolean editZombie = false;
    private String editZombieUserData;
    private float editZombieX;
    private float editZombieY;
    private ArrayList<Zombie> deadPlayers = new ArrayList<>();
    private ArrayList<Crate> usedCrates = new ArrayList<>();
    private boolean gameIsOver = false;
    private String zombieName;
    private EnergyBar energyBar = new EnergyBar();
    private boolean quitLobby = false;
    private boolean endMusicPlayed = false;

    private BitmapFont timeText = new BitmapFont();
    DecimalFormat value = new DecimalFormat("#.#");
    Vector3 mouse_position = new Vector3(0, 0, 0);

    private AudioSettings audioSettings;

    /**
     * The Constructor for the Client side of the Game. It contains information about the client name, the zombie name,
     * the floor collisions, and whether the zombie has been created
     *
     * @param client     The client object which will be used to connect to the server
     * @param zombieName The name of the player
     */
    public ClientGame(Client client, String zombieName, AudioSettings audioSettings) {
        this.CLIENT = client;
        this.zombieName = zombieName;
        this.audioSettings = audioSettings;
        ContactListener myContactListener = new MyContactListener();
        // Create physics world simulation with gravity value
        world = new World(new Vector2(0, -200f), true);
        world.setContactListener(myContactListener);

        //If the player is the second player to join the server, we must also generate the opponent's zombies, and get the crate entities from the opponent
        Zombie newZombie;
        if (zombieName.equals("Player2")) {

            for (int i = 0, j = 101; j < 104; j++, i++) {
                newZombie = new Zombie(world, 150f + 200 * i, 700f, this, j, false);
                enemyTeam.add(j);
                zombies.put(Integer.toString(j), newZombie);
                commands.put(Integer.toString(j), "NO-MOVEMENT");
                footContacts.put(j, 0);
            }

            for (int i = 0, j = 5; j < 8; j++, i++) {
                newZombie = new Zombie(world, 750f + 200 * i, 700f, this, j, true);
                System.out.println("Zombie X: " + (750f + 200 * i));
                clientTeam.add(j);
                zombies.put(Integer.toString(j), newZombie);
                commands.put(Integer.toString(j), "NO-MOVEMENT");
                footContacts.put(j, 0);

            }
            currentPlayer = 5;
            clientTurn = false;
            bothPlayersJoined = true;

            previousTime = System.nanoTime();
        } else {
            //Otherwise we can just generate the client's zombies and the crates ourselves
            for (int i = 0, j = 101; j < 104; j++, i++) {
                newZombie = new Zombie(world, 150f + 200 * i, 700f, this, j, true);
                clientTeam.add(j);
                zombies.put(Integer.toString(j), newZombie);
                commands.put(Integer.toString(j), "NO-MOVEMENT");
                footContacts.put(j, 0);
            }
            currentPlayer = 101;

            Random randPosition = new Random();

            HealthCrate healthCrate;
            AmmoCrate ammoCrate;
            for (int i = 8; i < 11; i += 2) {
                healthCrate = new HealthCrate(world, randPosition.nextInt(1500), 700f, i);
                ammoCrate = new AmmoCrate(world, randPosition.nextInt(1500), 700f, i + 1);
                crates.put(i, healthCrate);
                crates.put(i + 1, ammoCrate);
            }

            clientTurn = true;

        }


        addZombie = false;
        addCrate = false;
    }

    /**
     * Getter method for the List of Zombies in the game
     *
     * @return An ArrayList of all the Zombies in the game
     */
    public ArrayList<Zombie> getAllPlayers() {
        return new ArrayList<>(zombies.values());
    }

    /**
     * Getter method for the List of Players
     *
     * @return A HashTable of Zombies with their UserData as the key
     */
    public Hashtable<String, Zombie> getPlayers() {
        return this.zombies;
    }

    /**
     * Getter method for the UserData of the currentPlayer
     *
     * @return The UserData of the currentPlayer
     */
    public int getPlayerNow() {
        return currentPlayer;
    }

    /**
     * Getter method for the crates in the game
     *
     * @return All Ammo and Health crates in the game
     */
    public Hashtable<Integer, Crate> getCrates() {
        return this.crates;
    }


    /**
     * Getter method for the currentPlayer's zombie object
     *
     * @return The currentPlayer's zombie object
     */
    public Zombie getZombie() {
        return this.zombies.get(Integer.toString(currentPlayer));
    }


    /**
     * The method which updates the zombie depending on the commands it has been given, or creates a zombie in case
     * one has not been created yet. The commands are: move left, move right, stop, and jump
     *
     * @param owner   The owner/name of the zombie
     * @param x       The zombie's x coordinate
     * @param y       The zombie's y coordinate
     * @param command The zombie's new command
     */
    public void updateZombie(String owner, float x, float y, String command) {
        if (command != null) {
            commands.put(owner, command);
        }

        //If the zombie on the local client has de-synced by a set amount of pixels, then we re-sync it
        Zombie zombie = zombies.get(owner);
        if (zombie != null && command != null) {
            if ((Math.abs(zombie.getPlayerX() - x) > 25 || Math.abs(zombie.getPlayerY() - y) > 25) && (!command.equals("NO-MOVEMENT"))) {
                editZombie = true;
                editZombieUserData = owner;
                editZombieX = x;
                editZombieY = y;
                deadPlayers.add(zombie);
            }
        }
    }

    /**
     * The method which displays the environmental world
     */
    @Override
    public void show() {
        super.show(); //renders world

        // Create floor object
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(new Vector2(0, 632));


        Body groundBody1 = world.createBody(groundBodyDef);
        PolygonShape groundBox1 = new PolygonShape();
        groundBox1.setAsBox(512 / 2f, 1f);
        FixtureDef groundFixtureDef1 = new FixtureDef();
        groundFixtureDef1.density = 0f;
        groundFixtureDef1.shape = groundBox1;
        Fixture groundFixture1 = groundBody1.createFixture(groundFixtureDef1);
        groundFixture1.setUserData(1);

        groundBodyDef.position.set(new Vector2(460, 470));
        Body groundBody2 = world.createBody(groundBodyDef);
        PolygonShape groundBox2 = new PolygonShape();
        groundBox2.setAsBox(100f, 1f);
        groundFixtureDef1.shape = groundBox2;
        Fixture groundFixture2 = groundBody2.createFixture(groundFixtureDef1);
        groundFixture2.setUserData(1);

        groundBodyDef.position.set(new Vector2(460, 430));
        Body groundBody2_1 = world.createBody(groundBodyDef);
        PolygonShape groundBox2_1 = new PolygonShape();
        groundBox2_1.setAsBox(100f, 1f);
        groundFixtureDef1.shape = groundBox2_1;
        Fixture groundFixture2_1 = groundBody2_1.createFixture(groundFixtureDef1);
        groundFixture2_1.setUserData(1);

        groundBodyDef.position.set(new Vector2(0, 385));
        Body groundBody3 = world.createBody(groundBodyDef);
        PolygonShape groundBox3 = new PolygonShape();
        groundBox3.setAsBox(260f, 1f);
        groundFixtureDef1.shape = groundBox3;
        Fixture groundFixture3 = groundBody3.createFixture(groundFixtureDef1);
        groundFixture3.setUserData(1);

        groundBodyDef.position.set(new Vector2(510f, 230f));
        Body groundBody4 = world.createBody(groundBodyDef);
        PolygonShape groundBox4 = new PolygonShape();
        groundBox4.setAsBox(260f, 1f);
        groundFixtureDef1.shape = groundBox4;
        Fixture groundFixture4 = groundBody4.createFixture(groundFixtureDef1);
        groundFixture4.setUserData(1);

        groundBodyDef.position.set(new Vector2(260f, 305f));
        Body wallBody1 = world.createBody(groundBodyDef);
        PolygonShape wallBox1 = new PolygonShape();
        wallBox1.setAsBox(1f, 80f);
        groundFixtureDef1.shape = wallBox1;
        Fixture wallFixture1 = wallBody1.createFixture(groundFixtureDef1);
        wallFixture1.setUserData(4);

        groundBodyDef.position.set(new Vector2(760f, 390f));
        Body wallBody2 = world.createBody(groundBodyDef);
        PolygonShape wallBox2 = new PolygonShape();
        wallBox2.setAsBox(1f, 160f);
        groundFixtureDef1.shape = wallBox2;
        Fixture wallFixture2 = wallBody2.createFixture(groundFixtureDef1);
        wallFixture2.setUserData(4);


        groundBodyDef.position.set(new Vector2(985, 550));
        Body groundBody5 = world.createBody(groundBodyDef);
        PolygonShape groundBox5 = new PolygonShape();
        groundBox5.setAsBox(225, 1f);
        groundFixtureDef1.shape = groundBox5;
        Fixture groundFixture5 = groundBody5.createFixture(groundFixtureDef1);
        groundFixture5.setUserData(1);

        groundBodyDef.position.set(new Vector2(1400, 385));
        Body groundBody6 = world.createBody(groundBodyDef);
        PolygonShape groundBox6 = new PolygonShape();
        groundBox6.setAsBox(200, 1f);
        groundFixtureDef1.shape = groundBox6;
        Fixture groundFixture6 = groundBody6.createFixture(groundFixtureDef1);
        groundFixture6.setUserData(1);

        groundBodyDef.position.set(new Vector2(1215f, 465f));
        Body wallBody3 = world.createBody(groundBodyDef);
        PolygonShape wallBox3 = new PolygonShape();
        wallBox3.setAsBox(1f, 85f);
        groundFixtureDef1.shape = wallBox3;
        Fixture wallFixture3 = wallBody3.createFixture(groundFixtureDef1);
        wallFixture3.setUserData(4);

        groundBodyDef.position.set(new Vector2(0, Gdx.graphics.getHeight()));
        Body groundBody7 = world.createBody(groundBodyDef);
        PolygonShape groundBox7 = new PolygonShape();
        groundBox7.setAsBox(Gdx.graphics.getWidth(), 1f);
        groundFixtureDef1.shape = groundBox7;
        groundFixtureDef1.filter.groupIndex = -1;
        Fixture groundFixture7 = groundBody7.createFixture(groundFixtureDef1);
        groundFixture7.setUserData(1);


        // free up memory
        groundBox1.dispose();
        groundBox2.dispose();
        groundBox3.dispose();
        groundBox4.dispose();
        groundBox5.dispose();
        groundBox6.dispose();
        wallBox1.dispose();
        wallBox2.dispose();
        wallBox3.dispose();

    }

    /**
     * The method which manages the rendered world. It creates and handles the zombies and the crates. It checks what key
     * is pressed and calls the appropriate behaviour. The behaviours include: move left, move right, stop, jump,
     * switch music, full volume, mute volume.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0); //fetch mouse position from frame

        //Set the mouse aim on the currentPlayer
        if (zombies.get(currentPlayer) != null) {
            zombies.get(Integer.toString(currentPlayer)).setMousePosition(mouse_position);
        }

        //If a Zombie has de-synced, then re-render it in the right position
        if (editZombie) {
            Zombie newZombie = new Zombie(world, editZombieX, editZombieY, this, Integer.parseInt(editZombieUserData), zombies.get(editZombieUserData).getIsPlayerControlled());
            zombies.put(editZombieUserData, newZombie);
            editZombie = false;
        }

        //Add the host player's zombies
        if (addZombie && !zombieName.equals("Player2")) {
            Zombie newZombie;
            for (int i = 0, j = 5; j < 8; j++, i++) {
                newZombie = new Zombie(world, 750f + 200 * i, 700f, this, j, false);
                System.out.println("Zombie X: " + (750f + 200 * i));
                enemyTeam.add(j);
                zombies.put(Integer.toString(j), newZombie);
                commands.put(Integer.toString(j), "NO-MOVEMENT");
                footContacts.put(j, 0);
            }
            if (zombies.size() == 6) {
                bothPlayersJoined = true;
            }
            previousTime = System.nanoTime();
            addZombie = false;
        }

        //Add the crates from the host player to the game
        if (addCrate) {
            GameObject currentCrate;
            Crate newCrate;
            for (String key : newCrates.keySet()) {
                currentCrate = newCrates.get(key);
                if (currentCrate.getCommand().equals("HEALTH")) {
                    newCrate = new HealthCrate(world, currentCrate.getX(), currentCrate.getY(), Integer.parseInt(key));
                } else {
                    newCrate = new AmmoCrate(world, currentCrate.getX(), currentCrate.getY(), Integer.parseInt(key));
                }
                crates.put(Integer.parseInt(key), newCrate);
            }
            addCrate = false;
        }

        //Only allow input and start the timer once both players are in the game
        if (bothPlayersJoined) {
            long currentTime = System.nanoTime();
            long turnTime = 60000000000L;
            if (currentTime - previousTime >= turnTime) {
                clientTurn = !clientTurn;
                CLIENT.keyPress("NO-MOVEMENT", currentPlayer);
                previousTime = System.nanoTime();
            }


            if (clientTurn) {
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    CLIENT.keyPress("LEFT", currentPlayer);
                    energy -= EnergyCost.getWalkCost();
                } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    CLIENT.keyPress("RIGHT", currentPlayer);
                    energy -= EnergyCost.getWalkCost();
                } else if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                    CLIENT.keyPress("SHOOT", currentPlayer);
                } else if (Gdx.input.isKeyPressed(Input.Keys.G)) {
                    CLIENT.keyPress("G", currentPlayer);
                } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                    CLIENT.keyPress("SPACE", currentPlayer);
                } else {
                    CLIENT.keyPress("NO-MOVEMENT", currentPlayer);
                }
            }

            super.spriteBatch.begin();
            if (gameIsOver) {
                String showString;
                if (clientTeam.isEmpty()) {
                    showString = "Game Over! Opponent wins!";
                    if (!endMusicPlayed) {
                        AudioAccessor.playSound("loss");
                        endMusicPlayed = true;
                    }
                } else {
                    showString = "Game Over! You win!";
                    if (!endMusicPlayed) {
                        AudioAccessor.playSound("win");
                        endMusicPlayed = true;
                    }
                }
                timeText.draw(super.spriteBatch, showString, (WorldAttributes.WORLD_WIDTH / 3f) - 120, 780);

            } else {
                timeText.draw(super.spriteBatch, "Time Left: " + value.format(60 - (currentTime - previousTime) / 1_000_000_000), (int) (((double) WorldAttributes.WORLD_WIDTH / (double) 3) - 120), 780);
                if (clientTurn) {
                    this.energyBar.renderEnergy(energy, super.spriteBatch); //draw energy bar
                }
            }
            super.spriteBatch.end();
        } else {
            CLIENT.keyPress("NO-MOVEMENT", currentPlayer);
        }

        //Apply commands to all the Zombies and render them
        Zombie currentZombie;
        String command;
        for (String key : zombies.keySet()) {
            currentZombie = zombies.get(key);
            command = commands.get(key);

            switch (command) {
                case "LEFT":
                    currentZombie.moveLeft();
                    break;
                case "RIGHT":
                    currentZombie.moveRight();
                    break;
                case "NO-MOVEMENT":
                    currentZombie.noMovement();
                    break;
            }

            // code for jumping
            if (command.equals("SPACE")) {
                currentZombie.jump();
                System.out.println("JUMP!");
            }

            if (command.equals("SHOOT")) {
                currentZombie.shoot();
                commands.put(key, "NO-MOVEMENT");
            }

            if (command.equals("G")) {
                currentZombie.throwGrenade();
                commands.put(key, "NO-MOVEMENT");
            }


            currentZombie.updatePlayer();
        }

        //Render all the crates
        Crate currentCrate;
        for (int key : crates.keySet()) {
            currentCrate = crates.get(key);
            currentCrate.updateCrate();
        }


        //Inputs which can be handled locally
        if (Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT)) {
            AudioAccessor.switchMusic("testMusic");
        }

        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            AudioAccessor.changeMusicVolume(100.0f, "testMusic");
            System.out.println(AudioAccessor.getMusicVolume("testMusic"));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            AudioAccessor.changeMusicVolume(-100.0f, "testMusic");
            System.out.println(AudioAccessor.getMusicVolume("testMusic"));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) || quitLobby) {
            CLIENT.quitLobby();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new ServerListing(new Client(), audioSettings));
        }

        checkEnergy();

        world.step(TIME_STEP, 6, 2);

        //Delete bodies of dead zombies and used crates
        if (usedCrates.size() > 0) {
            for (Crate usedCrate : usedCrates) {
                world.destroyBody(usedCrate.getBody());
            }
            usedCrates.clear();
        }

        if (deadPlayers.size() > 0) {
            for (Zombie deadPlayer : deadPlayers) {
                world.destroyBody(deadPlayer.getBody());
            }
            deadPlayers.clear();
        }

    }

    //TODO Look at removing reduceEnergy
    public void reduceEnergy(double value) {

    }

    /**
     * Sets the game to render the second player's zombies
     */
    public void addZombies() {
        addZombie = true;
    }

    /**
     * Sets the game to render the crates from the host player
     *
     * @param crates The information about which crates to render and where
     */
    public void addCrates(HashMap<String, GameObject> crates) {
        addCrate = true;
        newCrates = crates;
    }

    /**
     * Checks if a zombie object is present in the current game
     *
     * @param key The name of the zombie
     * @return Whether or not the zombie is present
     */
    public boolean isZombiePresent(String key) {
        return zombies.get(key) != null || deadZombies.get(key) != null || newZombies.get(key) != null;
    }

    /**
     * Checks whether any zombies are hit by damage from a grenade and if so, reduces their health
     *
     * @param grenadeThrower The Zombie which threw the grenade
     * @param x              The x coordinate of the grenade
     * @param y              The y coordinate of the grenade
     */
    public void checkGrenade(Zombie grenadeThrower, float x, float y) {

        for (String i : zombies.keySet()) {
            if (zombies.get(i).getUserData() != grenadeThrower.getUserData()) {
                Zombie zombie = zombies.get(i);
                if ((zombie.getPlayerX() < x + 100 && zombie.getPlayerX() > x - 100) && (zombie.getPlayerY() < y + 100 && zombie.getPlayerY() > y - 100)) {
                    if ((clientTeam.contains(zombie.getUserData()) && enemyTeam.contains(grenadeThrower.getUserData())) || (enemyTeam.contains(zombie.getUserData()) && clientTeam.contains(grenadeThrower.getUserData()))) {
                        zombie.getHit(35);
                        System.out.println("Grenade hit");
                        checkAlive(zombie);
                    }

                }
            }
        }

    }

    /**
     * Sets the game to quit
     */
    public void quit() {
        quitLobby = true;
    }

    /**
     * Checks if a Zombie has been killed, and performs checks to see whether the game has ended, and which side
     * has won.
     *
     * @param zombie The Zombie to check
     */
    private void checkAlive(Zombie zombie) {
        if (zombie.getHealth() <= 0) {
            String zombieToRemove = "-1";
            for (String i : zombies.keySet()) {
                if (zombies.get(i) == zombie) {
                    zombieToRemove = i;
                    break;
                }
            }
            if (!zombieToRemove.equals("-1")) {
                zombies.remove(zombieToRemove);
            }
            int j;
            boolean allZombiesDead = false;
            if (zombie.getIsPlayerControlled()) {
                for (j = 0; j < clientTeam.size(); j++) {
                    if (clientTeam.get(j) == zombie.getUserData()) {
                        clientTeam.remove(j);
                    }
                }
                if (clientTeam.size() == 0) {
                    System.out.println("Opponent wins");
                    gameIsOver = true;
                    allZombiesDead = true;
                }
            } else {
                for (j = 0; j < enemyTeam.size(); j++) {
                    if (enemyTeam.get(j) == zombie.getUserData()) {
                        enemyTeam.remove(j);
                    }
                }
                if (enemyTeam.size() == 0) {
                    System.out.println("Player wins");
                    gameIsOver = true;
                    allZombiesDead = true;
                }
            }
            System.out.println(zombie.getUserData() + " died!");
            if (!allZombiesDead) {
                if (zombie.getUserData() == currentPlayer) {
                    switchPlayer();
                }
            }
            zombies.remove(zombie);
            deadZombies.put(Integer.toString(zombie.getUserData()), zombie);
            deadPlayers.add(zombie);
        }
    }

    /**
     * Checks if the player has ran out of energy, if so then we switch player
     */
    private void checkEnergy() {
        if (energy <= 0) {
            System.out.println("Next player's turn");
            energy = 100;
            clientTurn = !clientTurn;
            CLIENT.keyPress("NO-MOVEMENT", currentPlayer);
        }
    }

    /**
     * Switches the currentPlayer to a different Zombie
     */
    private void switchPlayer() {
        try {
            zombies.get(Integer.toString(currentPlayer)).noMovement();
            zombies.get(Integer.toString(currentPlayer)).removeAim();     //removes aim line from current player
        } catch (Exception e) {
            //do nothing
        }
        energy = maxEnergy;
        if (currentPlayer == 8) {
            currentPlayer = 5;
        } else if (currentPlayer == 103) {
            currentPlayer = 101;
        } else {
            currentPlayer += 1;
        }
        previousTime = System.nanoTime();

        if (zombies.get(Integer.toString(currentPlayer)) == null) {
            switchPlayer();
        } else {
            previousTime = System.nanoTime();
            System.out.println(currentPlayer);
        }
    }


    /**
     * A contact listener for contact between objects
     */
    private class MyContactListener implements ContactListener {

        @Override
        public void beginContact(Contact contact) {

            Object fixtureUserData = contact.getFixtureA().getUserData();
            Object fixtureUserData2 = contact.getFixtureB().getUserData();

            if (((int) fixtureUserData == zombies.get(Integer.toString(currentPlayer)).getUserData() && (int) fixtureUserData2 == 1) || (int)
                    fixtureUserData == 1 && (int) fixtureUserData2 == zombies.get(Integer.toString(currentPlayer)).getUserData()) {
                zombies.get(String.valueOf(currentPlayer)).setFootContacts(1);
            } else if ((zombies.containsKey(Integer.toString((int) fixtureUserData)) && crates.containsKey((int) fixtureUserData2))) {
                Crate crate = crates.get((int) fixtureUserData2);
                Zombie zombie = zombies.get(Integer.toString((int) fixtureUserData));

                if (crate.getType().equals("HEALTH")) {
                    zombie.pickupHealth();
                } else {
                    zombie.pickupGrenades();
                }
                crates.remove((int) fixtureUserData2);
                usedCrates.add(crate);
            } else if ((zombies.containsKey(Integer.toString((int) fixtureUserData2)) && crates.containsKey((int) fixtureUserData))) {
                Crate crate = crates.get((int) fixtureUserData);
                Zombie zombie = zombies.get(Integer.toString((int) fixtureUserData2));

                if (crate.getType().equals("HEALTH")) {
                    zombie.pickupHealth();
                } else {
                    zombie.pickupGrenades();
                }
                crates.remove((int) fixtureUserData);
                usedCrates.add(crate);
            }

            if ((int) fixtureUserData2 == 50) {
                Body body = contact.getFixtureB().getBody();
                for (Zombie player : new ArrayList<>(zombies.values())) {
                    for (Bone bone : player.getBones()) {
                        if (bone.getBody() == body) {
                            bone.setRemove(true);
                            for (Zombie victim : new ArrayList<>(zombies.values())) {
                                if (victim.getUserData() == (int) fixtureUserData) {
                                    if (!((clientTeam.contains(player.getUserData()) && clientTeam.contains(victim.getUserData())) || (enemyTeam.contains(player.getUserData()) && enemyTeam.contains(victim.getUserData())))) {
                                        victim.getHit(30);
                                        checkAlive(victim);
                                    }
                                }

                            }
                        }
                    }
                }
            } else if ((int) fixtureUserData == 50) {
                Body body = contact.getFixtureA().getBody();
                for (Zombie player : new ArrayList<>(zombies.values())) {
                    for (Bone bone : player.getBones()) {
                        if (bone.getBody() == body) {
                            bone.setRemove(true);
                            for (Zombie victim : new ArrayList<>(zombies.values())) {
                                if (victim.getUserData() == (int) fixtureUserData2) {
                                    if (!((clientTeam.contains(player.getUserData()) && clientTeam.contains(victim.getUserData())) || (enemyTeam.contains(player.getUserData()) && enemyTeam.contains(victim.getUserData())))) {
                                        victim.getHit(30);
                                        checkAlive(victim);
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }

        @Override
        public void endContact(Contact contact) {

            Object fixtureUserData = contact.getFixtureA().getUserData();
            Object fixtureUserData2 = contact.getFixtureB().getUserData();

            if (zombies.get(Integer.toString(currentPlayer)) != null) {
                if (((int) fixtureUserData == zombies.get(Integer.toString(currentPlayer)).getUserData() && (int) fixtureUserData2 == 1) || (int)
                        fixtureUserData == 1 && (int) fixtureUserData2 == zombies.get(Integer.toString(currentPlayer)).getUserData()) {
                    zombies.get(String.valueOf(currentPlayer)).setFootContacts(-1);
                }
            }
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
