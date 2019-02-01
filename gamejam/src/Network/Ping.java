package Network;

import java.io.IOException;
import java.util.Queue;

/**
 * Created by Lukas on 31.01.19.
 */
public class Ping implements Runnable {
    private Thread ping;
    private Queue<ClientModel> clients;
    private Boolean proceed;

    public Ping(Queue<ClientModel> _clients) {
        clients = _clients;
        ping = new Thread(this);
        ping.start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Insomnia");
        }
        proceed = true;
        while (proceed) {
            Long start = System.currentTimeMillis();
            for (ClientModel client: clients) {
                try {
                    pingClient(client);
                } catch (IOException e) {
                    System.out.println("Failed to ping " + client.getName());
                    e.printStackTrace();
                }
            }
            Long end = System.currentTimeMillis();
            try {
                Thread.sleep(1000 - (end-start));
            } catch (InterruptedException e) {
                System.out.println("Insomnia");
            }
        }
    }

    public void pingClient(ClientModel client) throws IOException {
        client.send("PING");
        client.setLastPing(System.currentTimeMillis());
    }

    public void stop() {
        proceed = false;
    }
}
