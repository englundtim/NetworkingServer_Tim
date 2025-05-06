/* SERVER - may be enhanced to work for multiple clients */
import java.net.*;
import java.io.*;

public class NetworkingServer {

    public static void main(String [] args) {
        ServerSocket server = null;
        Socket client;

        //Default port number we are going to use
        int portnumber = 8080;
        if (args.length >= 1){
            portnumber = Integer.parseInt(args[0]);
        }

        // Create server side socket
        try {
            server = new ServerSocket(portnumber);
        } catch (IOException ie) {
            System.out.println("Cannot open socket. " + ie);
            System.exit(1);
        }
        System.out.println("ServerSocket is created = " + server);

        // Wait for the data from the client and reply
        while(true) {
            try {
                // Listens for a connection to be made to 
                // this socket and accepts it. The method blocks until 
                // a connection is made
                System.out.println("Waiting for connect request...");
                client = server.accept();
                System.out.println("Connect request is accepted...");

                // Storing client host 
                System.out.println("Client host = " + client.getInetAddress().getHostName());
                System.out.println("Client port = " + client.getPort());
                String clientHost = client.getInetAddress().getHostName();
                String clientPort = "" + client.getPort();
                System.out.println("Client host = " + clientHost + " Client port = " + clientPort);

                // Read data from the client
                InputStream clientIn = client.getInputStream();
                InputStreamReader isr = new InputStreamReader(clientIn);
                BufferedReader in = new BufferedReader(isr);
                String msgFromClient = in.readLine();
                System.out.println("Message received from client = " + msgFromClient);

                // Send response to the client
                if (msgFromClient != null && !msgFromClient.equalsIgnoreCase("bye")) {
                    OutputStream clientOut = client.getOutputStream();
                    PrintWriter pw = new PrintWriter(clientOut, true);
                    String ansMsg = "Hello, " + msgFromClient;
                    pw.println(ansMsg);
                }

                // Close sockets
                if (msgFromClient != null && msgFromClient.equalsIgnoreCase("bye")) {
                    server.close();
                    client.close();
                    break;
                }

            } catch (IOException ie) {
                // Skriv ett lämpligt error meddelande
            }
        }
    }
}
