package Network;

import java.io.IOException;
import java.util.Scanner;

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

            // app functionality here
            String message;
            while (true) {
                // input
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
