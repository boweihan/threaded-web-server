import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

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

        // get and display message from client
        String requestLine = br.readLine();

        // extract filename from request line
        StringTokenizer tokens = new StringTokenizer(requestLine);
        tokens.nextToken(); // skip over method - "GET"
        String fileName = ".." + tokens.nextToken();

        // open the requested file
        FileInputStream fis = null;
        boolean fileExists = true;
        try {
            fis = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            fileExists = false;
        }

        // construct the response message
        String statusLine = null;
        String contentTypeLine = null;
        String entityBody = null;

        if (fileExists) {
            statusLine = "HTTP/1.0 200 OK" + CRLF;
            contentTypeLine = "Content-type: " + contentType( fileName ) + CRLF;
        } else {
            statusLine = "HTTP/1.0 404 Not Found" + CRLF;
            contentTypeLine = "Content-type: text/html; charset=UTF-8";
            entityBody = "<HTML>" +
                "<HEAD><TITLE>Not Found</TITLE></HEAD>" +
                "<BODY>Not Found</BODY></HTML>" ;
        }

        os.write(statusLine.getBytes());
        os.write(contentTypeLine.getBytes());
        os.write(CRLF.getBytes());

        if (fileExists) {
            sendBytes(fis, os);
            fis.close();
        } else {
            os.write(entityBody.getBytes());
        }

        // cleanup resources
        is.close();
        os.close();
        socket.close();
    }

    private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception {
        // construct a 1K buffer to hld bytes on their way to the socket
        byte[] buffer = new byte[1024];
        int bytes = 0;

        // copy requested file into the socket's output stream
        while ((bytes = fis.read(buffer)) != -1) { // -1 indicates the end of the buffer has been reached
            os.write(buffer, 0, bytes);
        }
    }

    private static String contentType(String fileName) {
        if (fileName.endsWith(".htm") || fileName.endsWith(".html")) {
            return "text/html";
        }
        if (fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        if (fileName.endsWith("jpg")) {
            return "image/jpg";
        }
        return "";
    }

}
