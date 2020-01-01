package com.elite.network.server;

import com.elite.network.Keys;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.net.SocketException;
import java.util.Hashtable;

/**
 * The main server object, which listens for new clients connecting
 *
 * @author Will Goodman
 */
public class Server {

    private volatile Hashtable<String, Lobby> lobbies = new Hashtable<>();
    private ServerSocket serverSocket;

    /**
     * Starts the server
     */
    public void startServer() {
        try {
            serverSocket = new ServerSocket(Keys.MAIN_PORT_NUMBER, 0, InetAddress.getLocalHost());

            Socket socket;

            while (true) {
                try {
                    socket = serverSocket.accept();

                    new ServerThread(socket, lobbies).start();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
