package Network;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Lukas on 31.01.19.
 */
public class TestClient {
    public static void main(String[] args) {
        Network network = new Network();
        Client client;
        String ip = "";
        int port = 42069;
        String username = args[0];

        try {
            client = network.connect(ip, port, username);

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
