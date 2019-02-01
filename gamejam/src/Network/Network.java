package Network;

import Translater.Encoder;
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
        Client client = new Client(socket, name, ip, port, false);
        return client;
    }

    public void scan() {
        String localIp = Utility.getOwnIp();
        String subNet = localIp.substring(0, localIp.lastIndexOf(".") + 1);
        Map<String, Client> servers = new HashMap<String, Client>();
        for (int i = 0; i < 255; i++) {
            String ip = subNet + i;
            Socket socket = new Socket();
            Client client = new Client(socket, "SCAN", ip, port, true);
            servers.put(ip, client);
        }
        for (Client server: servers.values()) {
            try {
                server.getHeader();
            } catch (IOException e) {}
            try {
                server.getClient().join();
            } catch (InterruptedException e) {}
        }

        HashMap<String, String> gameServers = new HashMap<>();
        for (Map.Entry<String, Client> entry: servers.entrySet()) {
            if (entry.getValue().isGameServer()) {
                String ip = entry.getKey();
                String name = entry.getValue().getName();
                gameServers.put(ip, name);
            }
        }

        Parser.parse(0 ,Encoder.createGameList(gameServers));
    }
}
