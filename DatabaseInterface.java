/**
 * Interface for the database class
 *
 * @author Alexander Siladie, David Liansi, Mridula Naikawadi, Isaac Wang
 * @version 2024-04-01
 */
public interface DatabaseInterface {
    public void storeUsers(Profile profile);

    public void storeFriends(Profile profile, String friendUsername);

    public void storeBlocked(Profile profile, String blockedUsername);

    public void removeFriend(Profile profile, String friendUsername);

    public void storeMessages(String user1, String user2, String message, String sentBy);

    public void storeBio(Profile profile, String userBio);
}
