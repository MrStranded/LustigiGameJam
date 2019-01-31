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
    private Queue<ServerClient> clients;


    public Server(int _port) throws IOException {
        port = _port;
        serverSocket = new ServerSocket(port);
        server = new Thread(this);
        server.start();
        clients = new ConcurrentLinkedDeque<ServerClient>();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New connection from " + socket.getInetAddress() + ":" + socket.getPort());

                ServerClient client = new ServerClient(socket, this);
                clients.add(client);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }



    public void broadcast(String message) throws IOException {
        for (ServerClient client: clients) {
            client.send(message);
        }
    }

    public Queue<ServerClient> getClients() {
        return clients;
    }
}