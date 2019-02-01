package Network;

import Translater.Parser;
import Translater.Sender;

import java.io.*;
import java.net.Socket;

/**
 * Created by Lukas on 31.01.19.
 */
public class ServerClient extends ClientModel {
    private Server server;
    private static int counter = 0;
    private int connectionId;

    public ServerClient(Socket _socket, Server _server, String _userName) {
        socket = _socket;
        client = new Thread(this);
        client.start();
        server = _server;
        name = _userName;
        try {
            send("GIBMENAME");
            send("CONID: " + connectionId);
        } catch (IOException e) {
            System.out.println("failed to request name");
            e.printStackTrace();
        }
        connectionId = (++counter);
    }


    @Override
    public void run() {
        try {
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

                if (message.startsWith("GAME: ")) {
                    String change =  message.substring(message.indexOf(" ") + 1);
                    Parser.parse(connectionId, change);

                    System.out.println(message);
                } else if (message.equals("GUESSIDIE")) {
                    server.getClients().remove(this);
                    send("GUESSYOUDIE");
                    server.broadcast("<server> " + getRemoteName() + " left the game");
                    break;
                } else if (message.equals("GIBMENAME")) {
                    send("HEREISNAME: " + name);
                } else if (message.startsWith("HEREISNAME: ")) {
                    remoteName = message.split(" ")[1];
                    if (!remoteName.equals("SCAN")) {
                        Parser.parse(connectionId, "PLAYER|" + remoteName); // creates new player
                        server.broadcast("<server> " + getRemoteName() + " joined the game");
                    }
                    continue;
                } else if (message.equals("CANIHAZPLAYERLIST")) {
                    String players = "PLAYER|PING:\n";
                    for (ClientModel client: server.getClients()) {
                        players += client.getRemoteName() + " | " + client.getPing() + "\n";
                    }
                    send(players);
                    continue;
                } else if (message.equals("PING")) {
                    send("PONG");
                    continue;
                } else if (message.equals("PONG")) {
                    setLastPong(System.currentTimeMillis());
                    Parser.parse(connectionId, "PING|" + getPing());
                    continue;
                } else if (message.equals("HEADER")) {
                    send("LUSCHTIGIGAMEJAM: " + name);
                    break;
                }

                server.broadcast("<"+getRemoteName()+"> " + message);
            }
        } catch (IOException e) {
            // create error log
            System.out.println("Client" + socket.getInetAddress() + " crashed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            clean();
        }
    }

    public void clean() {
        Sender.sendDisconnect(connectionId);
        try {
            server.getClients().remove(this);
            Thread.sleep(1000);
            socket.close();
        } catch (IOException e) {
            System.out.println("couldn't close socket");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Insomnia");
        }
    }
}
