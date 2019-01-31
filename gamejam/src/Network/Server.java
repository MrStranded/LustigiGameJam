package Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Lukas on 31.01.19.
 */
public class Server implements Runnable {
    private Thread server;
    private ServerSocket serverSocket;
    private int port;


    public Server(int _port) throws IOException {
        port = _port;
        serverSocket = new ServerSocket(port);
        server = new Thread(this);
        server.start();
    }

    @Override
    public void run() {
        final int BUFSIZE = 1024;
        char[] buffer = new char[BUFSIZE];
        String message;
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New connection from " + socket.getInetAddress() + ":" + socket.getPort());

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                int count = bufferedReader.read(buffer, 0, BUFSIZE); // blocking function
                try {
                    message = new String(buffer, 0, count);
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("Lost connection to " + socket.getInetAddress());
                    continue;
                }

                if (message.equals("bye")) {
                    break;
                }


                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                printWriter.print("JAHUI: " + message);
                printWriter.flush();

                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


}