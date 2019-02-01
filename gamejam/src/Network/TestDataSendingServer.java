package Network;

import Globals.MasterSwitch;
import Logic.GameLoop;
import Translater.Sender;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Lukas on 31.01.19.
 */
public class TestDataSendingServer {
    public static void main(String[] args) {
        Network network = new Network();
        Server server;
        Client client;
        String ip = "";
        int port = 42069;

        new GameLoop().start();

        try {
            server = network.startServer(port);
            client = network.connect(ip, port, "gundar");

            Sender.setClient(client);
            Sender.setServer(server);
            MasterSwitch.isServer = true;

            // app functionality here
            String message;
            while (true) {
                // Input
                Scanner scanner = new Scanner(System.in);
                message = scanner.nextLine();

                if (message.equals("exit")) {
                    break;
                } else if (message.equals("sendmap")) {
                    Sender.sendMap();
                }

                client.send(message);
            }

            // try worldstate, message
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
