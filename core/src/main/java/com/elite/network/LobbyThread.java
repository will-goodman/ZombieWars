package com.elite.network;

import com.elite.game.NetworkGame;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Continuously sends the current game state to a client in the game lobby
 *
 * @author Will Goodman
 */
public class LobbyThread extends Thread {

    private static final int TICK_RATE = 200;
    private NetworkGame game;
    private ObjectOutputStream out;
    private boolean inLobby;

    /**
     * Creates a new LobbyThread object
     *
     * @param game The game state
     * @param out  The output stream to the client
     */
    public LobbyThread(NetworkGame game, ObjectOutputStream out) {
        this.game = game;
        this.out = out;
        this.inLobby = true;
    }

    /**
     * Sends updates to the client, a certain number of times a second based on the tick rate
     */
    public void run() {
        try {
            while (inLobby) {
                out.reset();
                out.writeObject(game);

                Thread.sleep(TICK_RATE);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops sending data to the client if the lobby has ended.
     */
    public void endLobby() {
        inLobby = false;
    }

}

