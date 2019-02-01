package Network;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Lukas on 31.01.19.
 */
public class ClientModel implements Runnable {
    Thread client;
    final Socket socket;
    BufferedReader bufferedReader;
    final int BUFSIZE = 4096;
    char[] buffer;
    String message;
    String name;
    Long lastPing = 0l;
    Long lastPong = 0l;
    Long pingValue = 0l;
    ReadWriteLock lock = new ReentrantReadWriteLock();


    public ClientModel(Socket _socket) {
        buffer = new char[BUFSIZE];
        socket = _socket;
        client = new Thread(this);
        client.start();
    }


    public synchronized void send(String message) throws IOException {
        synchronized (socket) {
            lock.writeLock().lock();
            try {
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                printWriter.print(message);
                printWriter.flush();
            } finally {
                lock.writeLock().unlock();
            }

        }
    }

    @Override
    public void run() {}

    public void setLastPing(Long time) {
        lastPing = time;
    }

    public void setLastPong(Long time) {
        lastPong = time;
        setPing();
    }


    public String getName() {
        return name;
    }

    public Long getPing() {
        return pingValue;
    }

    private void setPing() {
        pingValue = lastPong - lastPing;
    }
}
