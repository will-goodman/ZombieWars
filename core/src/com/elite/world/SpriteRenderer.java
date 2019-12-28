package com.elite.world;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.elite.audio.AudioAccessor;
import com.elite.entities.*;
import com.elite.game.GameType;
import com.elite.ui.EnergyBar;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;


/**
 * @author Jacob Wheale
 * @author Katie Potts
 * @author Minhal Khan
 */
public class SpriteRenderer extends RenderWorld implements GameType {
	private SpriteRenderer spriteRenderer;
	private Game game;

    /*Character attributes*/
    private static World world;
    private ContactListener myContactListener;

    private EnergyBar energyBar = new EnergyBar();
    private static double energy = 100;

    private final long turnTime = 60000000000L;
    private final long aiShotWait = 1000000000L;
    private static final double maxEnergy = 100;

    private final float TIME_STEP = 1.0f / 60.0f;

    private static long previousTime = System.nanoTime();
    private long currentTime = System.nanoTime();
    private static int currentPlayer = 0;

    private static boolean allowMove = true;

    private String showString;

    private static ArrayList<Zombie> team1 = new ArrayList<>();
    private static ArrayList<Zombie> team2 = new ArrayList<>();
    private Hashtable<Integer, Crate> crates = new Hashtable<>();


    private BitmapFont timeText = new BitmapFont();
    private DecimalFormat value = new DecimalFormat("#.#");
    private Vector3 mouse_position = new Vector3(0, 0, 0);


    private static ArrayList<Zombie> players;
    private static ArrayList<Zombie> deadPlayers;
    private static ArrayList<Crate> usedCrates;

    private boolean gameIsOver = false;
    private static boolean letsGoAI = false;
    private int targettedPlayer = 0;
    private static boolean targetFound = false;
    private static int closestDistance = 1000000000;
    private static boolean aiMoved = false;
    private long lastShotTime;
    private long startWalkTime;

    private boolean endMusicPlayed = false;

    /**
     * Constructor which initalises variables and adds 3 Zombies to each team.
     * Also spawns in ammo and health crates.
     */
    public SpriteRenderer() {
        players = new ArrayList<>();
        deadPlayers = new ArrayList<>();
        usedCrates = new ArrayList<>();
        myContactListener = new MyContactListener();

        // Create physics world simulation with gravity value
        world = new World(new Vector2(0, -200f), true);
        world.setContactListener(myContactListener);


        for (int i = 0, j = 5; j < 8; j++, i++) {
            team1.add(new Zombie(world, 775f + 50 * i, 700f, this, j, true));
            team2.add(new Zombie(world, 225f + (50 * i), 700f, this, j + 100, false));
            players.add(team1.get(i));
            players.add(team2.get(i));

        }

        Random randPosition = new Random();

        HealthCrate healthCrate;
        AmmoCrate ammoCrate;
        for (int i = 8; i < 11; i += 2) {
            healthCrate = new HealthCrate(world, randPosition.nextInt(1500), 700f, i);
            ammoCrate = new AmmoCrate(world, randPosition.nextInt(1500), 700f, i + 1);
            crates.put(i, healthCrate);
            crates.put(i + 1, ammoCrate);
        }

    }


    /**
     * Creates all of the floor and walls of the game.
     */
    @Override
    public void show() {
        super.show(); //renders world


        // These blocks of code are creating the wall and floor bodies in the world.

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
     * This method is the main render loop of the game.
     * This includes updating all of the players and dealing with AI code.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {

        super.render(delta);

        mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0); //fetch mouse position from frame

        currentTime = System.nanoTime();
        if (currentTime - previousTime >= turnTime) {
            //System.out.println("player change");
            switchPlayer();
        }

        super.spriteBatch.begin();

        this.energyBar.renderEnergy(energy, super.spriteBatch); //draw energy bar

        if (gameIsOver) {
            allowMove = false;
            if (team1.isEmpty()) {
                showString = "Game Over! AI wins!";
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

        } else if (allowMove) {
            showString = "Time Left: " + value.format(60 - (currentTime - previousTime) / 1_000_000_000);
        } else {
            showString = "Next Turn Starts In: " + value.format(60 - (currentTime - previousTime) / 1_000_000_000);
        }
        timeText.draw(super.spriteBatch, showString, (WorldAttributes.WORLD_WIDTH / 3f) - 120, 780);
        super.spriteBatch.end();

        for (int i = 0; i < players.size(); i++) {
            players.get(i).updatePlayer();
        }

        for (int key : crates.keySet()) {
            crates.get(key).updateCrate();
        }

        //Show aim just for users
        try {
            getAllPlayers().get(currentPlayer).setMousePosition(mouse_position); //set mouse position for current zombie to add aim
        } catch (Exception e) {
            //do nothing, the game will be over
        }


        // if statements for moving left or right, if neither then stop
        if (allowMove) {
            try {
                getAllPlayers().get(currentPlayer);
            } catch (Exception e) {
                switchPlayer();
            }
            if (getAllPlayers().get(currentPlayer).getIsPlayerControlled()) {
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    players.get(currentPlayer).moveLeft();
                    energy -= EnergyCost.getWalkCost();
                } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    players.get(currentPlayer).moveRight();
                    energy -= EnergyCost.getWalkCost();
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    players.get(currentPlayer).jump();
                } else {
                    players.get(currentPlayer).noMovement();
                }

                if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
                    if (players.get(currentPlayer).getNumGrenades() > 0) {
                        players.get(currentPlayer).throwGrenade();
                        reduceEnergy(EnergyCost.getGrenadeCost());
                    }
                }

            } else {
                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).getIsPlayerControlled()) {
                        if (Math.abs(players.get(currentPlayer).getX() - players.get(i).getX()) < closestDistance && Math.abs(players.get(currentPlayer).getY() - players.get(i).getY()) < 10) {
                            closestDistance = (int) Math.abs(players.get(currentPlayer).getX() - players.get(i).getX());
                            targettedPlayer = i;
                            targetFound = true;
                        }
                    }
                }
                if (targetFound) {
                    //System.out.println("AI method 1");
                    if (players.get(targettedPlayer).getX() > players.get(currentPlayer).getX()) {
                        if (!aiMoved) {
                            players.get(currentPlayer).moveRight();
                            aiMoved = true;
                            reduceEnergy(EnergyCost.getWalkCost());
                        }
                    } else {
                        if (!aiMoved) {
                            players.get(currentPlayer).moveLeft();
                            aiMoved = true;
                            reduceEnergy(EnergyCost.getWalkCost());
                        }
                    }
                    if (System.nanoTime() - lastShotTime >= aiShotWait) {
                        players.get(currentPlayer).shoot();
                        //System.out.println(players.get(currentPlayer).getUserData());
                        lastShotTime = System.nanoTime();
                    }
                    players.get(currentPlayer).noMovement();
                } else {
                    for (int i = 0; i < players.size(); i++) {
                        if (Math.abs(players.get(currentPlayer).getY()) > Math.abs(players.get(i).getY()) && players.get(i).getIsPlayerControlled()) {
                            letsGoAI = true;
                            startWalkTime = System.nanoTime();
                        }

                    }
                    if (letsGoAI) {
                        //System.out.println("AI method 2");
                        if (players.get(currentPlayer).getX() < 1215 - players.get(currentPlayer).getX()) {
                            players.get(currentPlayer).moveRight();
                            reduceEnergy(EnergyCost.getWalkCost());
                        } else {
                            players.get(currentPlayer).moveLeft();
                            reduceEnergy(EnergyCost.getWalkCost());
                        }

                        if (System.nanoTime() - startWalkTime >= 200000000L) {
                            players.get(currentPlayer).noMovement();
                            letsGoAI = false;
                        }


                    } else {
                        if (players.get(currentPlayer).getX() < 1215 - players.get(currentPlayer).getX()) {
                            if (!aiMoved) {
                                players.get(currentPlayer).moveRight();
                                aiMoved = true;
                                reduceEnergy(EnergyCost.getWalkCost());
                            }
                        } else {
                            if (!aiMoved) {
                                players.get(currentPlayer).moveLeft();
                                aiMoved = true;
                                reduceEnergy(EnergyCost.getWalkCost());
                            }
                        }
                        if (System.nanoTime() - (lastShotTime + 10000000000L) >= aiShotWait) {
                            players.get(currentPlayer).throwGrenade();
                            System.out.println(players.get(currentPlayer).getUserData());
                            lastShotTime = System.nanoTime();
                            reduceEnergy(EnergyCost.getGrenadeCost());
                        }
                        players.get(currentPlayer).noMovement();
                    }
                }
            }
        }
        checkEnergy();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ALT_RIGHT)) {
            AudioAccessor.switchMusic("testMusic");
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ALT_LEFT)) {
            AudioAccessor.allowSoundsSwitch();
        }


        world.step(TIME_STEP, 6, 2);

        if (usedCrates.size() > 0) {
            for (int i = 0; i < usedCrates.size(); i++) {
                world.destroyBody(usedCrates.get(i).getBody());
            }
            usedCrates.clear();
        }

        if (deadPlayers.size() > 0) {
            for (int i = 0; i < deadPlayers.size(); i++) {
                world.destroyBody(deadPlayers.get(i).getBody());
            }
            deadPlayers.clear();
        }

    }

    /**
     * Method to check if the player has no energy left, if so we stop them from moving anymore.
     */
    private static void checkEnergy() {
        if (energy <= 0) {
            previousTime = System.nanoTime() - 55000000000L;
            energy = 999;
            allowMove = false;
            players.get(currentPlayer).noMovement();
        }
    }

    /**
     * Method to deal with switching players at the end of each turn.
     */
    private static void switchPlayer() {
        try {
            players.get(currentPlayer).noMovement();
            players.get(currentPlayer).removeAim();     //removes aim line from current player
        } catch (Exception e) {
            //do nothing
        }
        energy = maxEnergy;
        if (currentPlayer == players.size() - 1) {
            currentPlayer = 0;
        } else {
            currentPlayer += 1;
        }
        previousTime = System.nanoTime();
        aiMoved = false;
        letsGoAI = false;
        closestDistance = 100000000;
        targetFound = false;
        allowMove = true;
        try {
            players.get(currentPlayer);
        } catch (Exception e) {
            currentPlayer = 0;
        }
    }

    /**
     * Method for when grenades explode, checking for nearby enemies to damage.
     *
     * @param grenadeThrower The Zombie which threw the grenade.
     * @param x              The x coordinate of the grenade.
     * @param y              The y coordinate of the grenade.
     */
    public void checkGrenade(Zombie grenadeThrower, float x, float y) {

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) != grenadeThrower) {
                Zombie zombie = players.get(i);

                if ((zombie.getPlayerX() < x + 100 && zombie.getPlayerX() > x - 100) && (zombie.getPlayerY() < y + 100 && zombie.getPlayerY() > y - 100)) {
                    if ((team1.contains(zombie) && team2.contains(grenadeThrower)) || (team2.contains(zombie) && team1.contains(grenadeThrower))) {
                        zombie.getHit(35);
                        System.out.println("Grenade hit");
                        checkAlive(zombie);
                    }

                }
            }
        }

    }

    /**
     * Code for dealing with dead zombies.
     * If the zombie was the last on the team, then the game is over.
     *
     * @param zombie The zombie that has just took damage and could be dead.
     */
    private void checkAlive(Zombie zombie) {
        if (zombie.getHealth() <= 0) {
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i) == zombie) {
                    deadPlayers.add(players.get(i));
                    players.remove(i);
                    targetFound = false;
                }
            }
            int j;

            if (zombie.getIsPlayerControlled()) {
                for (j = 0; j < team1.size(); j++) {
                    if (team1.get(j) == zombie) {
                        team1.remove(j);
                    }
                }
                if (team1.size() == 0) {
                    System.out.println("AI wins");
                    allowMove = false;
                    showGameOver("AI");
                    

                }


            } else {
                for (j = 0; j < team2.size(); j++) {
                    if (team2.get(j) == zombie) {

                        team2.remove(j);
                    }
                }
                if (team2.size() == 0) {
                    System.out.println("Player wins");
                    allowMove = false;
                    showGameOver("Player");
                   
                    
                    
                   
                    


                }
            }
            System.out.println(zombie.getUserData() + " died!");
        }
    }
  

    /**
     * Method for when the game is over.
     *
     * @param winner Who won the game.
     */
    private void showGameOver(String winner) {
        gameIsOver = true;
    }

    /**
     * Method for reducing the players energy levels after they perform an action.
     *
     * @param energyReduction The amount of energy to take away, depends on the action.
     */
    public void reduceEnergy(double energyReduction) {
        energy = energy - energyReduction;
        checkEnergy();
    }

    /**
     * Getter method for 'currentPlayer'
     *
     * @return The current player index.
     */
    public int getPlayerNow() {
        return currentPlayer;
    }

    /**
     * Getter method for the Arraylist 'players'
     *
     * @return The list of all players in the game currently.
     */
    public ArrayList<Zombie> getAllPlayers() {
        return players;
    }

    /**
     * Getter method for the boolean value 'allowMove'.
     *
     * @return Whether the current Zombie is allowed to move or not.
     */
    public static boolean getAllowMove() {
        return allowMove;
    }

    /**
     * Class to deal with contact events between two bodies in the physics world.
     * Such as contact with floors and walls, or bullet contacts.
     */
    class MyContactListener implements ContactListener {

        @Override
        public void beginContact(Contact contact) {

            Object fixtureUserData = contact.getFixtureA().getUserData();
            Object fixtureUserData2 = contact.getFixtureB().getUserData();

            System.out.println("contact: " + fixtureUserData + " " + fixtureUserData2);

            for (Zombie player1 : players) {
                if (((int) fixtureUserData == player1.getUserData() && (int) fixtureUserData2 == 1) || (int)
                        fixtureUserData == 1 && (int) fixtureUserData2 == player1.getUserData()) {
                    player1.setFootContacts(1);
                }
            }

            if ((int) fixtureUserData2 == 50 || (int) fixtureUserData == 50) {
                Body body;
                if ((int) fixtureUserData == 50) {
                    body = contact.getFixtureA().getBody();
                } else {
                    body = contact.getFixtureB().getBody();
                }
                for (Zombie player : new ArrayList<>(players)) {
                    for (Bullet bullet : player.getBullets()) {
                        if (bullet.getBody() == body) {
                            bullet.setRemove(true);
                            for (Zombie victim : new ArrayList<>(players)) {
                                if (victim.getUserData() == (int) fixtureUserData || victim.getUserData() == (int) fixtureUserData2) {
                                    if (((team1.contains(player) && team1.contains(victim)) || team2.contains(player) && team2.contains(victim))) {
                                        System.out.println("friendly fire");
                                    } else {
                                        victim.getHit(30);
                                        System.out.println(victim.getUserData() + "'s Health: " + victim.getHealth());
                                        System.out.println("hit " + victim.getUserData());
                                        checkAlive(victim);


                                    }
                                }

                            }
                        }
                    }
                }
            } else if (((int) fixtureUserData >= 5 && (int) fixtureUserData <= 10) && crates.containsKey((int) fixtureUserData2)) {
                System.out.println("DELETE THE CRATE " + (int) fixtureUserData);
                Crate crate = crates.get((int) fixtureUserData2);
                Zombie zombie = players.get((int) fixtureUserData - 5);

                if (crate.getType().equals("HEALTH")) {
                    zombie.pickupHealth();
                } else {
                    zombie.pickupGrenades();
                }
                crates.remove((int) fixtureUserData2);
                usedCrates.add(crate);
            }

        }

        @Override
        public void endContact(Contact contact) {

            Object fixtureUserData = contact.getFixtureA().getUserData();
            Object fixtureUserData2 = contact.getFixtureB().getUserData();

            for (Zombie player : players) {
                if (((int) fixtureUserData == player.getUserData() && (int) fixtureUserData2 == 1) || (int)
                        fixtureUserData == 1 && (int) fixtureUserData2 == player.getUserData()) {
                    player.setFootContacts(-1);
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

}
