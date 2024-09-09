import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.util.List;

/**
 * Class that stores all the databases
 *
 * @author Alexander Siladie, David Liansi, Mridula Naikawadi, Isaac Wang
 * @version 2024-04-15
 */
public class Database implements DatabaseInterface {
    ArrayList<String> user;
    ArrayList<String> friends;
    ArrayList<String> blocked;
    ArrayList<String> messages;
    ArrayList<String> bio;
    String profileFile;
    String friendsFile;
    String blockedFile;
    String messagingFile;
    String bioFile;

    public Database() {
        user = new ArrayList<>();
        friends = new ArrayList<>();
        blocked = new ArrayList<>();
        messages = new ArrayList<>();
        profileFile = "profilesDatabase.txt";
        friendsFile = "friendsDatabase.txt";
        blockedFile = "blockedDatabase.txt";
        messagingFile = "messagingDatabase.txt";
        bioFile = "bioDatabase.txt";
    }

    //Database that stores each user as "username, passwword" with each user on a new line
    public void storeUsers(Profile profile) {
        try {
            user = new ArrayList<>();
            BufferedReader bfr = new BufferedReader(new FileReader(profileFile));
            String line = "";
            while ((line = bfr.readLine()) != null) {
                user.add(line);
            }
            if (!user.contains(profile.getName() + ", " + profile.getPassword())) {
                user.add(profile.getName() + ", " + profile.getPassword());
            }
            BufferedWriter bfw = new BufferedWriter(new FileWriter(profileFile));

            for (String users : user) {
                bfw.write(users);
                bfw.newLine();
            }

            bfr.close();
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Database that stores each user's friends as  "username: friend1, friend2 ...", with each user on a new line
    public void storeFriends(Profile profile, String friendUsername) {
        String username = profile.getName();
        try {
            friends = new ArrayList<>();
            BufferedReader bfr = new BufferedReader(new FileReader(friendsFile));
            String line = "";
            Boolean userPresent = false;
            while ((line = bfr.readLine()) != null) {
                String userName = line.split(": ")[0];
                if (line.split(":")[0].equals(username)) { //Checks if the current line database is the username
                    userPresent = true;
                    String[] friend = line.split(": ")[1].split(", ");
                    //Checks to make sure the friend isn't in the database
                    if (!(new ArrayList<String>(List.of(friend)).contains(friendUsername))) {
                        line += ", " + friendUsername;
                        profile.setFriends(line);
                    }
                }
                friends.add(line);
            }
            if (!userPresent) {    //If this is the users first friend
                friends.add(username + ": " + friendUsername);
                profile.setFriends(username + ": " + friendUsername);

            }
            BufferedWriter bfw = new BufferedWriter(new FileWriter(friendsFile));
            for (String friend : friends) {
                bfw.write(friend);
                bfw.newLine();
            }
            bfr.close();
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Database that stores each user's blocked users as  "username: blocked1, blocked2 ..." with each user on a new line
    public void storeBlocked(Profile profile, String blockedUsername) {
        String username = profile.getName();
        try {
            blocked = new ArrayList<>();
            BufferedReader bfr = new BufferedReader(new FileReader(blockedFile));
            String line = "";
            Boolean userPresent = false;
            while ((line = bfr.readLine()) != null) {
                String userName = line.split(": ")[0];
                if (line.split(":")[0].equals(username)) { //Checks if the current line database is the username
                    userPresent = true;
                    String[] friend = line.split(": ")[1].split(", ");
                    //Checks to make sure the blocked name isn't in the database
                    if (!(new ArrayList<String>(List.of(friend)).contains(blockedUsername))) {
                        line += ", " + blockedUsername;
                        profile.setBlocked(line);
                    }
                    if (blockedUsername.isEmpty()) {
                        line = line.substring(0, line.length() - 2);
                    }
                }
                blocked.add(line);
                removeFriend(profile, blockedUsername);
            }
            if (!userPresent && !blockedUsername.isEmpty()) {    //If this is the users first blocked person
                blocked.add(username + ": " + blockedUsername);
                profile.setBlocked(username + ": " + blockedUsername);
            }

            BufferedWriter bfw = new BufferedWriter(new FileWriter(blockedFile));
            for (String blockedFriend : blocked) {
                bfw.write(blockedFriend);
                bfw.newLine();
            }
            bfr.close();
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeFriend(Profile profile, String friendUsername) {
        String username = profile.getName();
        try {
            friends = new ArrayList<>();
            BufferedReader bfr = new BufferedReader(new FileReader(friendsFile));
            String line = "";
            Boolean userPresent = false;
            while ((line = bfr.readLine()) != null) {
                String userName = line.split(": ")[0];
                if (line.split(":")[0].equals(username)) { //Checks if the current line database is the username
                    String friends = line.split(": ")[1];
                    String[] friendsList = friends.split(", "); //Gets all the friends
                    String friendsString = userName + ": ";
                    for (String name : friendsList) {
                        if (!name.equals(friendUsername)) {
                            friendsString += name + ", ";
                        }
                    }
                    friendsString = friendsString.substring(0, friendsString.length() - 2);
                    profile.setFriends(friendsString);
                    line = friendsString;
                }
                friends.add(line);
            }

            BufferedWriter bfw = new BufferedWriter(new FileWriter(friendsFile));
            for (String friend : friends) {
                bfw.write(friend);
                bfw.newLine();
            }
            bfr.close();
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Database that stores conversation between two users as "user1, user2: ... 1\| ... 2" with two users per line
    //Using \| as separators between messages because commas are used in text
    //The " 1" is added at the end to indicate user1, and the order of the arraylist is the order the messages were sent
    public void storeMessages(String user1, String user2, String message, String sentBy) {
        try {
            messages = new ArrayList<>();
            BufferedReader bfr = new BufferedReader(new FileReader(messagingFile));
            String line = "";
            boolean messagesExist = false;
            while ((line = bfr.readLine()) != null) {
                String userName1 = line.split(": ")[0].split(", ")[0];
                String userName2 = line.split(": ")[0].split(", ")[1];

                if (user1.equals(userName1) && userName2.equals(user2)) {
                    messagesExist = true;
                    if (sentBy.equals(userName1)) {
                        line += "\\|  " + message + " 1";
                    } else {
                        line += "\\|  " + message + " 2";
                    }
                } else if (user1.equals(userName2) && userName2.equals(user1)) {
                    messagesExist = true;
                    if (sentBy.equals(userName2)) {
                        line += "\\|  " + message + " 2";
                    } else {
                        line += "\\|  " + message + " 1";
                    }
                }
                messages.add(line);
            }
            if (!messagesExist) {
                if (sentBy.equals(user1)) {
                    String newLine = user1 + ", " + user2 + ": " + message + " 1";
                    messages.add(newLine);
                } else if (sentBy.equals(user2)) {
                    String newLine = user1 + ", " + user2 + ": " + message + " 2";
                    messages.add(newLine);
                }
            }
            BufferedWriter bfw = new BufferedWriter(new FileWriter(messagingFile));
            for (String eachMessage : messages) {
                bfw.write(eachMessage);
                bfw.newLine();
            }
            bfr.close();
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Database that stores user's bio as "user: bio..."
    public void storeBio(Profile profile, String userBio) {
        String username = profile.getName();
        try {
            bio = new ArrayList<>();
            BufferedReader bfr = new BufferedReader(new FileReader(bioFile));
            String line = "";
            Boolean userPresent = false;
            while ((line = bfr.readLine()) != null) {
                String userName = line.split(": ")[0];
                if (line.split(":")[0].equals(username)) { //Checks if the current line database is the username
                    userPresent = true;
                    line = username + ": " + userBio;
                }
                bio.add(line);
            }
            if (!userPresent) {    //If this is the users first biography
                bio.add(username + ": " + userBio);
            }
            BufferedWriter bfw = new BufferedWriter(new FileWriter(bioFile));
            for (String profileBio : bio) {
                bfw.write(profileBio);
                bfw.newLine();
            }
            bfr.close();
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

