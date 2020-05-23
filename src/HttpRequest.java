import mapper.MimeMapper;

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
            e.printStackTrace();
            fileExists = false;
        }

        // construct the response message
        String statusLine = null;
        String contentTypeLine = null;
        String contentLengthLine = null;
        String entityBody = null;

        if (fileExists) {
            statusLine = "HTTP/1.0 200 OK" + CRLF;
            contentTypeLine = "Content-type: " + MimeMapper.getContentTypeForFileName(getFileExtension(fileName)) + CRLF;
            contentLengthLine = "Content-length: " + fis.getChannel().size() + CRLF;
        } else {
            statusLine = "HTTP/1.0 404 Not Found" + CRLF;
            contentTypeLine = "Content-type: text/html; charset=UTF-8";
            entityBody = "<HTML>" +
                "<HEAD><TITLE>Not Found</TITLE></HEAD>" +
                "<BODY>Not Found</BODY></HTML>" ;
            contentLengthLine = "Content-length: " + entityBody.getBytes().length;
        }

        os.write(statusLine.getBytes());
        os.write(contentTypeLine.getBytes());
        os.write(contentLengthLine.getBytes());
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

    private String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return fileName.substring(lastIndexOf);
    }

}
