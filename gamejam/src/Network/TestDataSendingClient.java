package Network;

import Logic.GameLoop;
import Translater.Sender;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Lukas on 31.01.19.
 */
public class TestDataSendingClient {
    public static void main(String[] args) {
        Network network = new Network();
        Client client;
        String ip = "192.168.178.20";
        int port = 42069;
        String username = args[0];

        new GameLoop().start();

        try {
            client = network.connect(ip, port, username);

            Sender.setClient(client);

            // app functionality here
            String message;
            while (true) {
                // Input
                Scanner scanner = new Scanner(System.in);
                message = scanner.nextLine();

                if (message.equals("exit")) {
                    break;
                }

                client.send(message);
            }

            // try worldstate, message
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
