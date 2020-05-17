import java.io.*;
import java.net.Socket;

public class HttpRequest implements Runnable {

    final static String CRLF = "\r\n";
    Socket socket;

    public HttpRequest(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            processRequest();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void processRequest() throws Exception {
        // get a reference to the socket's InputStream and OutputStream
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        // setup input stream filters to simplify read operations
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        // setup output stream filters to simplify write operations
        OutputStreamWriter ows = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(ows);

        // get and display message from client
        String requestLine = br.readLine();
        System.out.println(requestLine);

        String headerLine = null;
        while ((headerLine = br.readLine()).length() != 0) {
            System.out.println(headerLine);
        }

        // cleanup resources
        bw.close();
        br.close();
        socket.close();
    }

}
