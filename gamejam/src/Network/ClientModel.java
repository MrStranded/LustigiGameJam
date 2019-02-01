package Network;

import java.io.*;
import java.net.Socket;

/**
 * Created by Lukas on 31.01.19.
 */
public class ClientModel implements Runnable {
    Thread client;
    final Socket socket;
    BufferedReader bufferedReader;
    final int BUFSIZE = 1024;
    char[] buffer;
    String message;
    String name;
    Long lastPing = 0l;
    Long lastPong = 0l;


    public ClientModel(Socket _socket) {
        buffer = new char[BUFSIZE];
        socket = _socket;
        client = new Thread(this);
        client.start();
    }


    public void send(String message) throws IOException {
        synchronized (socket) {
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            printWriter.print(message);
            printWriter.flush();
        }
    }

    @Override
    public void run() {}

    public void setLastPing(Long time) {
        lastPing = time;
    }

    public void setLastPong(Long time) {
        lastPong = time;
    }


    public String getName() {
        return name;
    }

    public Long getPing() {
        Long pingValue = lastPong - lastPing;
        return pingValue;
    }
}
