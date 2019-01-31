package Network;

import java.io.*;
import java.net.Socket;

/**
 * Created by Lukas on 31.01.19.
 */
public class Client extends ClientModel {

    public Client(Socket _socket, String _name) {
        super(_socket);
        name = _name;
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


                if (message.startsWith("MSG")) {

                } else if (message.equals("GIBMENAME")) {
                    send("HEREISNAME: " + name);
                }


                System.out.println(message);


            }


            socket.close();

        } catch (IOException e) {
            // create error log
            System.out.println("Client" + socket.getInetAddress() + " crashed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void getPlayers() throws IOException {
        send("CANIHAZPLAYERLIST");
    }
}
