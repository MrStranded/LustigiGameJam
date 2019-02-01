package Network;

import Logic.WorldState;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Lukas on 31.01.19.
 */
public class Network {
    private WorldState worldState;




    public Server startServer(int port) throws IOException {
        Server server = new Server(port);
        return server;
    }



    public Client connect(String ip, int port, String name) throws IOException {
        Socket socket = new Socket(ip, port);
        Client client = new Client(socket, name);

        return client;
    }



    public WorldState getWorldState() {
        return worldState;
    }

    public void sendWorldState() {

    }
}
