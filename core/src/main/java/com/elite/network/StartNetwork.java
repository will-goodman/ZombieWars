package com.elite.network;

import com.elite.network.server.Server;

/**
 * Starts the server
 *
 * @author Will Goodman
 */
public class StartNetwork {

    /**
     * Main method which starts the server.
     *
     * @param args A required parameter of a main Java method, although here no data is passed.
     */
    public static void main(String[] args) {
        Server server = new Server();

        server.startServer();
    }

}