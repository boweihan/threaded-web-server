import java.io.*;
import java.net.*;

public final class Server {

    public static void main(String[] args) throws IOException {
        // get the port number and message from the command line
        int port = Integer.parseInt(args[0]);
        String messageToClient = args[1];

        while (true) {
            // open a socket on the correct port
            ServerSocket listenSocket = new ServerSocket(port);
            Socket connectionSocket = listenSocket.accept();

            // get a reference to the socket's InputStream and OutputStream
            InputStream is = connectionSocket.getInputStream();
            OutputStream os = connectionSocket.getOutputStream();

            // setup input stream filters to simplify read operations
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            // setup output stream filters to simplify write operations
            OutputStreamWriter ows = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(ows);

            // get and display message from client
            String messageFromClient = br.readLine();
            System.out.println(messageFromClient);

            try {
                // send message to client
                bw.write(messageToClient + "\n");

                // flush the output stream because we added a filter to buffer writes into the stream.
                // only when the buffer is full are the bytes actually sent to the output stream of the socket.
                bw.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // cleanup resources
            bw.close();
            br.close();
            connectionSocket.close();
            listenSocket.close();
        }
    }
}
