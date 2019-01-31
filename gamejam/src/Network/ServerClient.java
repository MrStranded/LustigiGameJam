package Network;

import java.io.*;
import java.net.Socket;

/**
 * Created by Lukas on 31.01.19.
 */
public class ServerClient extends ClientModel {
    private Server server;

    public ServerClient(Socket _socket, Server _server) {
        super(_socket);
        server = _server;
        try {
            send("GIBMENAME");
        } catch (IOException e) {
            System.out.println("failed to receive name");
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        try {
            Boolean proceed = true;
            while(proceed) {
                // input
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                int count = bufferedReader.read(buffer, 0, BUFSIZE); // blocking function
                try {
                    message = new String(buffer, 0, count);
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("Lost connection to " + socket.getInetAddress());
                    break;
                }

                if (message.equals("bye")) {
                    break;
                } else if (message.startsWith("HEREISNAME: ")) {
                    name = message.split(" ")[1];
                    server.broadcast("<server>: " + getName() + " joined the game");
                } else if (message.equals("CANIHAZPLAYERLIST")) {
                    String players = "PLAYERS:\n";
                    for (ServerClient client: server.getClients()) {
                        players += client.getName() + "\n";
                    }
                    send(players);
                }

                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                printWriter.print("JAHUI: " + message);
                printWriter.flush();
            }


            socket.close();

        } catch (IOException e) {
            // create error log
            System.out.println("Client" + socket.getInetAddress() + " crashed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }
}
