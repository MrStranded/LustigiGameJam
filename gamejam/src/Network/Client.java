package Network;

import Translater.Encoder;
import Translater.Parser;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by Lukas on 31.01.19.
 */
public class Client extends ClientModel {
    private static Queue<ClientModel> clients;
    private Ping ping;
    private boolean gameServer = false;
    private String ip;
    private int port;
    private boolean scan;
    private int connectionId;

    public Client(Socket _socket, String _name, String _ip, int _port, boolean _scan) {
        socket = _socket;
        name = _name;
        ip = _ip;
        port = _port;
        scan = _scan;
        client = new Thread(this);
        client.start();
        clients = new ConcurrentLinkedDeque<ClientModel>();
        clients.add(this);
    }


    @Override
    public void run() {
        try {
            socket.connect(new InetSocketAddress(ip, port), 1000);
            ping = new Ping(clients);
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

                if (scan) {
                    if (message.equals("LUSCHTIGIGAMEJAM")) {
                        gameServer = true;
                        break;
                    } else {
                        getHeader();
                    }
                } else if (message.startsWith("GAME: ")) {
                    String change = message.substring(message.indexOf(" ") + 1);
                    Parser.parse(0, change);

                    System.out.println(message);
                } else if (message.startsWith("MSG")) {

                } else if (message.equals("GIBMENAME")) {
                    send("HEREISNAME: " + name);
                } else if (message.equals("GUESSYOUDIE")) {
                    System.out.println("ok bye");
                    break;
                } else if (message.equals("PING")) {
                    send("PONG");
                } else if (message.equals("PONG")) {
                    setLastPong(System.currentTimeMillis());
                } else if (message.startsWith("CONID: ")) {
                    connectionId = Integer.parseInt(message.split(" ")[1]);
                    Parser.parse(0, Encoder.createPlayerIdMsg(connectionId));
                } else {
                    System.out.println(message);
                }
            }
        } catch (IOException e) {
            // socket closed
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

    public void getHeader() throws IOException {
        send("HEADER");
    }
}
