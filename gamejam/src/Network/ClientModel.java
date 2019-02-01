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
    Socket socket;
    BufferedReader bufferedReader;
    final int BUFSIZE = 4096;
    char[] buffer = new char[BUFSIZE];
    String message;
    String name;
    private Long lastPing = 0l;
    private Long lastPong = 0l;
    private Long pingValue = 0l;
    ReadWriteLock lock = new ReentrantReadWriteLock();

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

    public Thread getClient() {
        return client;
    }
}
