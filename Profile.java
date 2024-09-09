import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that creates each user's profile
 *
 * @author Alexander Siladie, David Liansi, Mridula Naikawadi, Isaac Wang
 * @version 2024-04-15
 */
public class Profile extends Database implements ProfileInterface {
    private String name;
    private String password;
    public ArrayList<String> friends;
    private ArrayList<String> blocked;
    private String bio;

    public Profile(String name, String password) {
        this.name = name;
        this.password = password;
        this.friends = new ArrayList<>();
        this.blocked = new ArrayList<>();
        this.bio = null;
    }

    public Profile() {
        this.name = null;
        this.password = null;
    }


    public boolean equals(Object o) {
        if (o instanceof Profile) {
            Profile p = (Profile) o;
            if (this.name.equals(p.name) && this.password == p.password &&
                    this.friends.equals(p.friends) && this.blocked.equals(p.blocked)) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public ArrayList<String> getBlocked() {
        return blocked;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //friendsList is in the form of, "username: friend1, friend2 ..."
    //Adds each friend to the 'friends' arrayList
    public void setFriends(String friendsList) {
        String friendsSplit = friendsList.split(": ")[1];
        String[] friend = friendsSplit.split(", ");
        ArrayList<String> listFriends = new ArrayList<String>(List.of(friend));
        this.friends = listFriends;
    }

    //blockedList is in the form of, "username: blocked1, blocked2 ..."
    //Adds each blocked user to the 'blocked' arrayList
    public void setBlocked(String blockedList) {
        String blockedSplit = blockedList.split(": ")[1];
        String[] block = blockedSplit.split(", ");
        ArrayList<String> listBlocked = new ArrayList<String>(List.of(block));
        this.blocked = listBlocked;
    }

    public void addFriend(String friend) {
        storeFriends(this, friend);
    }

    public void addBlocked(String blockedUser) {
        storeBlocked(this, blockedUser);
    }

    public void removingAFriend(String friend) {
        removeFriend(this, friend);
    }

    //Changes the users bio, or adds one if they don't have one
    public void setBio(String userBio) {
        this.bio = userBio;
        storeBio(this, userBio);
    }

    public String getBio() {
        return bio;
    }

    public String toString() {
        return name + ":\n" + bio;
    }
}
