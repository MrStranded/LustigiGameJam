package Network;

import java.io.*;
import java.net.Socket;

/**
 * Created by Lukas on 31.01.19.
 */
public class ClientModel implements Runnable {
    Thread client;
    Socket socket;
    BufferedReader bufferedReader;
    final int BUFSIZE = 1024;
    char[] buffer = new char[BUFSIZE];
    String message;


    public ClientModel(Socket _socket) {
        socket = _socket;
        client = new Thread(this);
        client.start();

    }


    public void send(String message) throws IOException {
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        printWriter.print(message);
        printWriter.flush();
    }

    @Override
    public void run() {}
}
