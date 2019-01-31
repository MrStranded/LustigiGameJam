package Network;

import java.io.*;
import java.net.Socket;

/**
 * Created by Lukas on 31.01.19.
 */
public class Client implements Runnable {
    private Thread client;
    private Socket socket;

    public Client(Socket _socket) {
        socket = _socket;
        client = new Thread(this);
        client.start();

    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader;
        final int BUFSIZE = 1024;
        char[] buffer = new char[BUFSIZE];
        String message;

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

                System.out.println(message);


            }


            socket.close();

        } catch (IOException e) {
            // create error log
            System.out.println("Client" + socket.getInetAddress() + " crashed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
