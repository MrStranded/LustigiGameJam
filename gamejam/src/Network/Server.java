package Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by Lukas on 31.01.19.
 */
public class Server implements Runnable {
    private Thread server;
    private ServerSocket serverSocket;
    private int port;
    private Queue<ClientModel> clients;
    private Ping ping;


    public Server(int _port) throws IOException {
        port = _port;
        serverSocket = new ServerSocket(port);
        server = new Thread(this);
        server.start();
        clients = new ConcurrentLinkedDeque<ClientModel>();
        ping = new Ping(getClients());
    }

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("New connection from " + socket.getInetAddress() + ":" + socket.getPort());

                    ServerClient client = new ServerClient(socket, this);
                    clients.add(client);

                } catch (IOException e) {
                    System.out.println("failed to accept socket");
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clean();
        }
    }



    public void broadcast(String message) throws IOException {
        for (ClientModel client: clients) {
            client.send(message);
        }
    }

    public void broadcastGameState(String message) throws IOException {
        message = "GAME: " + message;
        broadcast(message);
    }

    public Queue<ClientModel> getClients() {
        return clients;
    }

    public void clean() {
        try {
            ping.stop();
            try {
                ping.getPing().join();
            } catch (InterruptedException e) {
                System.out.println("Failed ping join");
            }

            serverSocket.close();
        } catch (IOException e) {
            System.out.println("failed to close server");
        }
    }
}