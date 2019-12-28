package com.elite.network;

/**
 * Starts the server
 * @author Will Goodman
 */
public class StartNetwork {

    public static void main(String[] args) {
        Server server = new Server();

        server.startServer();
    }

}