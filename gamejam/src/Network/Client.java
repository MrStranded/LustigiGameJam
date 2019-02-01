package Network;

import Translater.Parser;

import java.io.*;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by Lukas on 31.01.19.
 */
public class Client extends ClientModel {
    private static Queue<ClientModel> clients;
    private Ping ping;

    public Client(Socket _socket, String _name) {
        super(_socket);
        name = _name;
        clients = new ConcurrentLinkedDeque<ClientModel>();
        clients.add(this);
        ping = new Ping(clients);
    }


    @Override
    public void run() {
        try {
            Boolean proceed = true;
            while(proceed) {
                // Input
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                int count = bufferedReader.read(buffer, 0, BUFSIZE); // blocking function
                System.out.println("count: " + count);
                try {
                    message = new String(buffer, 0, count);
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("Lost connection to " + socket.getInetAddress());
                    break;
                }


                if (message.startsWith("GAME: ")) {
                    String change = message.substring(message.indexOf(" ") + 1);
                    Parser.parse(0, change);
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
                } else {
                    System.out.println(message);
                }
            }


            socket.close();

        } catch (IOException e) {
            // create error log
            System.out.println("Client" + socket.getInetAddress() + " crashed: " + e.getMessage());
            e.printStackTrace();
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
            socket.close();
            ping.stop();
        } catch (IOException e) {
            System.out.println("couldn't close socket");
            e.printStackTrace();
        }
    }
}
