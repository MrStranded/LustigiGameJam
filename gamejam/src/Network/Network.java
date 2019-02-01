package Network;

import Translater.Parser;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lukas on 31.01.19.
 */
public class Network {
    private final int port = 42069;
    public Server startServer(int port) throws IOException {
        Server server = new Server(port);
        return server;
    }

    public Client connect(String ip, int port, String name) throws IOException {
        Socket socket = new Socket();
        Client client = new Client(socket, name, ip, port);
        return client;
    }

    public void scan() {
        String localIp = Utility.getOwnIp();
        String subNet = localIp.substring(0, localIp.lastIndexOf(".") + 1);
        Map<String, Client> servers = new HashMap<String, Client>();
        for (int i = 0; i < 255; i++) {
            String ip = subNet + i;
            System.out.println(ip);
            try {
                Socket socket = new Socket();
                Client client = new Client(socket, "SCAN", ip, port);
                client.send("HEADER");
                servers.put(ip, client);
            } catch (IOException e) {
                System.out.println("scan fail " + ip);
            }
        }
        for (Client server: servers.values()) {
            try {
                server.getClient().join();
            } catch (InterruptedException e) {}
        }

        Map<String, String> gameServers = new HashMap<>();
        for (Map.Entry<String, Client> entry: servers.entrySet()) {
            if (entry.getValue().isGameServer()) {
                String ip = entry.getKey();
                String name = entry.getValue().getName();
                gameServers.put(ip, name);
                System.out.println(ip + ": " + name);
            }
        }

        String gameList = "";
        for (Map.Entry<String, String> entry: gameServers.entrySet()) {
            String ip = entry.getKey();
            String name = entry.getValue();
            String game = ip + "" + name;
            gameList += game + ",";
            System.out.println(game);
        }

        //Parser.parse(gameServers);
    }
}
