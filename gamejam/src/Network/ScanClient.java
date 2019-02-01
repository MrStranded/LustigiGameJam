package Network;

import Translater.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by Lukas on 31.01.19.
 */
public class ScanClient extends ClientModel {
    private static Queue<ClientModel> clients;
    private Ping ping;
    private boolean gameServer = false;
    private String ip;
    private int port;

    public ScanClient(Socket _socket, String _name, String _ip, int _port) {
        socket = _socket;
        name = _name;
        ip = _ip;
        port = _port;
        client = new Thread(this);
        client.start();
        clients = new ConcurrentLinkedDeque<ClientModel>();
        clients.add(this);
    }


    @Override
    public void run() {
        try {
            socket.connect(new InetSocketAddress(ip, port), 1000);
            Boolean proceed = true;
            while(proceed) {
                // Input
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                int count = bufferedReader.read(buffer, 0, BUFSIZE); // blocking function
                try {
                    message = new String(buffer, 0, count);
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("Lost connection to " + socket.getInetAddress());
                    break;
                }


                if (message.startsWith("MSG")) {

                } else if (message.equals("GIBMENAME")) {
                    send("HEREISNAME: " + name);
                } else if (message.equals("GUESSYOUDIE")) {
                    System.out.println("ok bye");
                    break;
                } else if (message.equals("PING")) {
                    send("PONG");
                } else if (message.equals("PONG")) {
                    setLastPong(System.currentTimeMillis());
                } else if (message.equals("LUSCHTIGIGAMEJAM")) {
                    gameServer = true;
                    break;
                } else {
                    System.out.println(message);
                }
            }
        } catch (IOException e) {
            // create error log
            System.out.println("Client " + socket.getInetAddress() + " crashed: " + e.getMessage());
            //e.printStackTrace();
        } finally {
            clean();
        }
    }

    public void getPlayers() throws IOException {
        send("CANIHAZPLAYERLIST");
    }

    public void disconnect() throws IOException {
        send("GUESSIDIE");
    }

    public void clean() {
        try {
            clients.remove(this);
            if (ping != null) {
                ping.stop();
                try {
                    ping.getPing().join();
                } catch (InterruptedException e) {
                    System.out.println("Failed ping join");
                }
            }
            socket.close();
        } catch (IOException e) {
            System.out.println("couldn't close socket");
            //e.printStackTrace();
        }
    }

    public boolean isGameServer() {
        return gameServer;
    }
}
