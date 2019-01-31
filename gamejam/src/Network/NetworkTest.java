package Network;

import java.io.IOException;

/**
 * Created by Lukas on 31.01.19.
 */
public class NetworkTest {
    public static void main(String[] args) {
        Network network = new Network();
        Server server;
        Client client;
        String ip = "";
        int port = 42069;

        try {
            server = network.startServer(port);
            client = network.connect(ip, port);

            network.sendMessage(client.getSocket(), "swehuia");
            network.sendMessage(client.getSocket(), "bye");
            network.sendMessage(client.getSocket(), "swehuia");


            // try worldstate, message
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
