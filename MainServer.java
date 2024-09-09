import java.io.*;
import java.net.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.SocketHandler;

/**
 * Class that will run the main server
 *
 * @author Alexander Siladie, David Liansix, Mridula Naikawadi, Isaac Wang
 * @version 2024-04-15
 */
public class MainServer extends Profile implements MainServerInterface {

    public static void main(String[] args) {
        MainServer mainServer = new MainServer();
        try {
            mainServer.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void run() throws UnknownHostException, IOException, ClassNotFoundException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(1234);
            serverSocket.setReuseAddress(true);
            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                ClientHandler clientHandler = new ClientHandler(socket, reader, writer);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
