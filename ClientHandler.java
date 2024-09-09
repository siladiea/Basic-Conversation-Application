import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class that runs each new client
 *
 * @author Alexander Siladie, David Liansi, Mridula Naikawadi, Isaac Wang
 * @version 2024-04-15
 */
public class ClientHandler extends Thread implements ClientHandlerInterface {
    Socket socket;
    BufferedReader reader;
    PrintWriter writer;

    public ClientHandler(Socket socket, BufferedReader reader, PrintWriter writer) {
        this.socket = socket;
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void run() {
        try {
            boolean entrySuccess = false;
            Profile user = new Profile();

            do {
                //The opening page will allow the user to enter a username and password, and then they can click on either
                //the Sign-Up button, which will add their name to the database, or the Log-In button,
                //which will make sure their name is in the database, and then log them in
                String usernamePassword = reader.readLine();
                String logInSignUp = reader.readLine();

                if (logInSignUp.equals("Sign-Up")) {
                    BufferedReader fileReader = new BufferedReader(new FileReader("profilesDatabase.txt"));

                    boolean userPresent = false;   //Determines if the user already has an account
                    boolean exists = false;
                    String line;
                    while ((line = fileReader.readLine()) != null) {
                        //Checks if the username exists
                        if (line.split(", ")[0].equals(usernamePassword.split(",")[0])) {
                            exists = true;
                            break;
                        }
                        if (line.equals(usernamePassword)) {
                            userPresent = true;     //This means the user already exists in the user database
                        }
                    }
                    if (!exists) {
                        //Adds the user to the database, and tells the client "added"
                        String userName = usernamePassword.split(",")[0];
                        String password = usernamePassword.split(",")[1];
                        user = new Profile(userName, password);
                        Database database = new Database();
                        database.storeUsers(user);
                        writer.write("added");
                        writer.println();
                        writer.flush();
                        entrySuccess = true;
                    } else {
                        writer.write("exists");
                        writer.println();
                        writer.flush();
                        entrySuccess = false;
                    }
                } else {
                    BufferedReader fileReader = new BufferedReader(new FileReader("profilesDatabase.txt"));

                    boolean userPresent = false;   //Determines if the user already has an account
                    String line;
                    while ((line = fileReader.readLine()) != null) {
                        if (line.split(", ")[0].equals(usernamePassword.split(",")[0]) &&
                                line.split(", ")[1].equals(usernamePassword.split(",")[1])) {
                            userPresent = true;     //This means the user already exists in the user database
                            user = new Profile(usernamePassword.split(",")[0], usernamePassword.split(",")[1]);
                        }
                    }
                    if (userPresent) {        //Successfully logged the user in
                        writer.write("Success");
                        writer.println();
                        user.addFriend("EASTER EGG");
                        user.removingAFriend("EASTER EGG");
                        user.addBlocked("");
                        ArrayList<String> friends = user.getFriends();
                        String friendString = "";
                        for (String i : friends) {
                            friendString += i + ",";
                        }
                        writer.write(friendString);
                        writer.println();
                        ArrayList<String> blocked = user.getBlocked();
                        String blockedString = "";
                        for (String i : blocked) {
                            blockedString += i + ",";
                        }
                        writer.write(blockedString);
                        writer.println();
                        writer.flush();
                        entrySuccess = true;
                    } else {                  //User either doesn't have an account, or entered the wrong username or password
                        writer.write("Failed");
                        writer.println();
                        writer.flush();
                    }
                }
            } while (!entrySuccess);


            boolean exit = false;
            do {
                //////////////////////////////This section will add, remove, block, search user////////////////////////////
                String message = reader.readLine();
                if (message.equals("bio")) {
                    String bio = reader.readLine();
                    user.setBio(bio);
                    writer.write("added");
                    writer.println();
                    writer.flush();
                } else if (message.equals("add")) {
                    String account = reader.readLine();
                    user.addFriend(account);
                    writer.write("added");
                    writer.println();
                    ArrayList<String> friends = user.getFriends();
                    String friendString = "";
                    for (String i : friends) {
                        friendString += i + ",";
                    }
                    writer.write(friendString);
                    writer.println();
                    ArrayList<String> blocked = user.getBlocked();
                    String blockedString = "";
                    for (String i : blocked) {
                        blockedString += i + ",";
                    }
                    writer.write(blockedString);
                    writer.println();
                    writer.flush();
                } else if (message.equals("block")) {
                    String account = reader.readLine();
                    user.addBlocked(account);
                    writer.write("blocked");
                    writer.println();
                    ArrayList<String> friends = user.getFriends();
                    String friendString = "";
                    for (String i : friends) {
                        friendString += i + ",";
                    }
                    writer.write(friendString);
                    writer.println();
                    ArrayList<String> blocked = user.getBlocked();
                    String blockedString = "";
                    for (String i : blocked) {
                        blockedString += i + ",";
                    }
                    writer.write(blockedString);
                    writer.println();
                    writer.flush();
                } else if (message.equals("remove")) {
                    String account = reader.readLine();
                    user.removingAFriend(account);
                    writer.write("removed");
                    writer.println();
                    ArrayList<String> friends = user.getFriends();
                    String friendString = "";
                    for (String i : friends) {
                        friendString += i + ",";
                    }
                    writer.write(friendString);
                    writer.println();
                    ArrayList<String> blocked = user.getBlocked();
                    String blockedString = "";
                    for (String i : blocked) {
                        blockedString += i + ",";
                    }
                    writer.write(blockedString);
                    writer.println();
                    writer.flush();
                } else if (message.equals("search")) {
                    searchFeature(user, 0, reader, writer);
//                    searchFeature(user, -1, reader, writer);
//                    searchFeature(user, 0, reader, writer);
                } else if (message.equals("chat")) {
                    messagingFeature(user, reader, writer);
                } else if (message.equals("exit")) {
                    exit = true;
                }
            } while (!exit);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public synchronized void messagingFeature(Profile user, BufferedReader reader, PrintWriter writer)
            throws IOException {
        String messagedFriend = reader.readLine();
        String messageSent = reader.readLine();
        Database database = new Database();
        database.storeMessages(user.getName(), messagedFriend, messageSent, user.getName());

        BufferedReader bfr = new BufferedReader(new FileReader("messagingDatabase.txt"));
        String line = "";
        while ((line = bfr.readLine()) != null) {
            if (line.split(": ")[0].equals(user.getName() + ", " + messagedFriend)
                    || line.split(": ")[0].equals(messagedFriend + ", " + user.getName())) {
                writer.write(line);
                writer.println();
                break;
            }
        }
        writer.flush();
        bfr.close();
    }

    @Override
    public synchronized void searchFeature(Profile user, int listNum, BufferedReader reader, PrintWriter writer)
            throws IOException {
        // Each number corresponds to a list
        // 1 for friends, -1 for blocked list, and 0 for regular search
        //reads database files and adds them to arraylists.
        //will probably need to change how this works with threads
        if (listNum == 0) {
            File f = new File("profilesDatabase.txt");
            ArrayList<String> searchData = new ArrayList<>();
            ArrayList<String> searchName = new ArrayList<>();
            ArrayList<String> searchNameLower = new ArrayList<>();
            ArrayList<String> searchBio = new ArrayList<>(); //stores corresponding bios
            String outputTitle = "";
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);

                String line = br.readLine();
                while (line != null) {
                    searchData.add(line);
                    line = br.readLine();
                }
                br.close();

                for (String searchDatum : searchData) {
                    if (searchDatum.contains(",") && searchDatum.split(",").length > 1) {
                        searchName.add(searchDatum.split(",")[0]);
                        searchNameLower.add(searchDatum.split(",")[0].toLowerCase());
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            File fb = new File("bioDatabase.txt");
            try {
                FileReader fr = new FileReader(fb);
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                while (line != null) {
                    searchBio.add(line);
                    line = br.readLine();
                }
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //the core search loop, only stops when something is selected, does not have live listening yet (GUI phase)
            boolean searchDone = false;
            do {
                String search = reader.readLine();
                //System.out.println("received search query");
                ArrayList<String> matching = new ArrayList<>();
                for (int i = 0; i < searchName.size(); i++) {
                    if (searchName.get(i).contains(search)
                            || searchNameLower.get(i).contains(search.toLowerCase())) {
                        matching.add(searchName.get(i));
                    }
                }
                if (matching.isEmpty()) {
                    outputTitle = "empty";
                } else {
                    outputTitle = "";
                    for (String s : matching) {
                        boolean added = false;
                        for (String string : searchBio) {
                            if (s.equals(string.split(":")[0])) {
                                outputTitle += s + ":" + string + ";";
                                added = true;
                            }
                        }
                        if (!added) {
                            outputTitle += s + ": ;";
                        }
                    }
                    if (outputTitle.isEmpty()) {
                        outputTitle = "empty";
                    }
                }
                writer.write(outputTitle);
                writer.println();
                writer.flush();
//                if (matching.size() != 1) {
//                    if (!(outputTitle.equals("empty"))) {
//                        outputTitle = "";
//                        try {
//                            int index = Integer.parseInt(reader.readLine());
//                            for (String string : searchBio) {
//                                if (matching.get(index).equals(string.split(":")[0])) {
//                                    outputTitle += string;
//                                    break;
//                                }
//                            }
//                            writer.write(outputTitle);
//                            writer.println();
//                            writer.flush();
//
//                        } catch (NumberFormatException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                String reply = reader.readLine();
//                System.out.println("repeat search or not request receieved");
//                if (reply.equals("-1")) {
//                    searchDone = true;
//                }
                searchDone = true;
            } while (!searchDone);
        } else if (listNum == -1) {
            File f = new File("blockedDatabase.txt");
            ArrayList<String> searchData = new ArrayList<>();
            ArrayList<String> searchName = new ArrayList<>();
            ArrayList<String> searchNameLower = new ArrayList<>();
            ArrayList<String> searchBio = new ArrayList<>(); //stores corresponding bios
            String outputTitle = "";
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);

                String line = br.readLine();
                while (line != null) {
                    searchData.add(line);
                    line = br.readLine();
                }
                br.close();

                for (String searchDatum : searchData) {
                    String[] splitData = searchDatum.split(":");
                    if (splitData[0].equals(user.getName())) {
                        if (splitData[1].contains(",") && splitData[1].split(",").length > 1) {
                            String[] blocked = splitData[1].split(",");
                            for (int j = 0; j < blocked.length; j++) {
                                searchName.add(splitData[1].split(",")[j]);
                                searchNameLower.add(searchName.get(j).toLowerCase());
                            }
                        } else {
                            searchName.add(splitData[1]);
                            searchNameLower.add(splitData[1].toLowerCase());
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            File fb = new File("bioDatabase.txt");
            try {
                FileReader fr = new FileReader(fb);
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                while (line != null) {
                    searchBio.add(line);
                    line = br.readLine();
                }
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //the core search loop, only stops when something is selected, does not have live listening yet (GUI phase)
            boolean searchDone = false;
            do {
                String search = reader.readLine();
                //System.out.println("received search query");
                ArrayList<String> matching = new ArrayList<>();
                for (int i = 0; i < searchName.size(); i++) {
                    if (searchName.get(i).contains(search)
                            || searchNameLower.get(i).contains(search.toLowerCase())) {
                        matching.add(searchName.get(i));
                    }
                }
                if (matching.isEmpty()) {
                    outputTitle = "empty";
                } else {
                    outputTitle = "";
                    for (String s : matching) {
                        for (String string : searchBio) {
                            if (s.equals(string.split(":")[0])) {
                                outputTitle += s + ":" + string + ";";
                            }
                        }
                    }
                    if (outputTitle.isEmpty()) {
                        outputTitle = "empty";
                    }
                }

                writer.write(outputTitle);
                writer.println();
                writer.flush();
                if (matching.size() != 1) {
                    if (!(outputTitle.equals("empty"))) {
                        outputTitle = "";
                        try {
                            int index = Integer.parseInt(reader.readLine());
                            for (String string : searchBio) {
                                if (matching.get(index).equals(string.split(":")[0])) {
                                    outputTitle += string;
                                    break;
                                }
                            }
                            writer.write(outputTitle);
                            writer.println();
                            writer.flush();

                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }

                String reply = reader.readLine();
                System.out.println("repeat search or not request receieved");
                if (reply.equals("-1")) {
                    searchDone = true;
                }
            } while (!searchDone);
        } else {
            File f = new File("friendsDatabase.txt");
            ArrayList<String> searchData = new ArrayList<>();
            ArrayList<String> searchName = new ArrayList<>();
            ArrayList<String> searchNameLower = new ArrayList<>();
            ArrayList<String> searchBio = new ArrayList<>(); //stores corresponding bios
            String outputTitle = "";
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);

                String line = br.readLine();
                while (line != null) {
                    searchData.add(line);
                    line = br.readLine();
                }
                br.close();

                for (String searchDatum : searchData) {
                    String[] splitData = searchDatum.split(":");
                    if (splitData[0].equals(user.getName())) {
                        if (splitData[1].contains(",") && splitData[1].split(",").length > 1) {
                            String[] friends = splitData[1].split(",");
                            for (int j = 0; j < friends.length; j++) {
                                searchName.add(splitData[1].split(",")[j]);
                                searchNameLower.add(searchName.get(j).toLowerCase());
                            }
                        } else {
                            searchName.add(splitData[1]);
                            searchNameLower.add(splitData[1].toLowerCase());
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            File fb = new File("bioDatabase.txt");
            try {
                FileReader fr = new FileReader(fb);
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                while (line != null) {
                    searchBio.add(line);
                    line = br.readLine();
                }
                br.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //the core search loop, only stops when something is selected, does not have live listening yet (GUI phase)
            boolean searchDone = false;
            do {
                String search = reader.readLine();
                //System.out.println("received search query");
                ArrayList<String> matching = new ArrayList<>();
                for (int i = 0; i < searchName.size(); i++) {
                    if (searchName.get(i).contains(search)
                            || searchNameLower.get(i).contains(search.toLowerCase())) {
                        matching.add(searchName.get(i).substring(1));
                    }
                }
                if (matching.isEmpty()) {
                    outputTitle = "empty";
                } else {
                    outputTitle = "";
                    for (String s : matching) {
                        boolean added = false;
                        for (String string : searchBio) {
                            if (s.equals(string.split(":")[0])) {
                                outputTitle += s + ":" + string + ";";
                                added = true;
                            }
                        }
                        if (!added) {
                            outputTitle += s + ": ;";
                        }
                    }
                    if (outputTitle.isEmpty()) {
                        outputTitle = "empty";
                    }
                }
                writer.write(outputTitle);
                writer.println();
                writer.flush();
//                if (matching.size() != 1) {
//                    if (!(outputTitle.equals("empty"))) {
//                        outputTitle = "";
//                        try {
//                            int index = Integer.parseInt(reader.readLine());
//                            for (String string : searchBio) {
//                                if (matching.get(index).equals(string.split(":")[0])) {
//                                    outputTitle += string;
//                                    break;
//                                }
//                            }
//                            writer.write(outputTitle);
//                            writer.println();
//                            writer.flush();
//
//                        } catch (NumberFormatException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                String reply = reader.readLine();
//                System.out.println("repeat search or not request receieved");
//                if (reply.equals("-1")) {
//                    searchDone = true;
//                }
                searchDone = true;
            } while (!searchDone);
        }
    }
}
