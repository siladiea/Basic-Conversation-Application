import java.util.ArrayList;

/**
 * Interface for the Profile Class
 *
 * @author Alexander Siladie, David Liansi, Mridula Naikawadi, Isaac Wang
 * @version 2024-04-01
 */
public interface ProfileInterface {

    public boolean equals(Object o);

    public String getName();

    public String getPassword();

    public ArrayList<String> getFriends();

    public ArrayList<String> getBlocked();

    public void setName(String name);

    public void setPassword(String password);

    public void setFriends(String friendsList);

    public void setBlocked(String blockedList);

    public void addFriend(String friend);

    public void addBlocked(String blockedUser);

    public void removingAFriend(String friend);

    public void setBio(String userBio);

    public String getBio();

    public String toString();
}
