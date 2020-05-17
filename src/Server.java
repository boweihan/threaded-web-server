import java.io.*;
import java.net.*;

public final class Server {

    public static void main(String[] args) throws IOException {
        // get the port number and message from the command line
        int port = Integer.parseInt(args[0]);

        // open a socket on the correct port
        ServerSocket listenSocket = new ServerSocket(port);

        while (true) {
            // listen for requests
            Socket connectionSocket = listenSocket.accept();

            HttpRequest request = new HttpRequest(connectionSocket);
            Thread thread = new Thread(request);
            thread.start();
        }
    }
}
