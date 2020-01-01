package com.elite.network.client;

import com.elite.game.multiplayer.ClientGame;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Client.class)
public class TestClient {

    final Socket socket = Mockito.mock(Socket.class);

    @Before
    public void init() throws Exception {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("test_data".getBytes());
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Mockito.when(socket.getInputStream()).thenReturn(byteArrayInputStream);
        Mockito.when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
        PowerMockito.whenNew(Socket.class).withAnyArguments().thenReturn(socket);
    }

    @Test
    public void constructorTest() {
        Client testClient = new Client();

        assertTrue(testClient.getConnectionStatus());
    }

    @Test
    public void getConnectionStatusTest() {
        Client testClient = new Client();

        assertTrue(testClient.getConnectionStatus());
    }

    @Test
    public void getGameTest() {
        Client testClient = new Client();

        assertNull(testClient.getGame());
    }

    @Test
    public void setGameTest() {
        Client testClient = new Client();
        ClientGame testClientGame = Mockito.mock(ClientGame.class);

        testClient.setGame(testClientGame);

        assertEquals(testClient.getGame(), testClientGame);
    }
}
