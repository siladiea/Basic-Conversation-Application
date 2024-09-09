import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Interface as a template for the ClientHandler
 *
 * @author Alexander Siladie, David Liansi, Mridula Naikawadi, Isaac Wang
 * @version 2024-04-15
 */
public interface ClientHandlerInterface {
    public void messagingFeature(Profile user, BufferedReader reader, PrintWriter writer) throws IOException;

    public void searchFeature(Profile user, int listNum, BufferedReader reader, PrintWriter writer)
            throws IOException;
}
