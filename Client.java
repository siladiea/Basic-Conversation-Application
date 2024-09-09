import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Interface for the Client class
 *
 * @author Alexander Siladie, David Liansi, Mridula Naikawadi, Isaac Wang
 * @version 2024-04-15
 */
public class Client implements ClientInterface {
    //    private Socket s;
//    private ObjectOutputStream output;
//    private ObjectInputStream input;
    private static String text = "";
    boolean stillChatting;
    String chosenFriend;
    String username;
    String chatFriend;
    JTextField usernameInput;
    JPasswordField passwordInput;
    JButton signUpButton;
    JButton logInButton;
    Socket socket;
    JFrame frame;
    JFrame mainFrame;
    JFrame chatBox;
    Container content;
    JTextField generalInput;
    JTextField message;
    JButton bioButton;
    JButton addButton;
    JButton removeButton;
    JButton blockButton;
    JButton chatButton;
    JButton searchButton;
    JButton exitButton;
    JButton sendButton;
    JTextArea friendsList;
    JTextArea chatTextArea;
    JTextArea displayPanel;
    //    public Client(String address, int port) {
//        try {
//            s = new Socket(address, port);
//            System.out.println("Connection established");
//
//            output = new ObjectOutputStream(s.getOutputStream());
//            input = new ObjectInputStream(s.getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public Client() {
        frontPage();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater((Runnable) new Client());
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == signUpButton) {
                try {
                    setSignUpButton();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (e.getSource() == logInButton) {
                try {
                    setLogInButton();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (e.getSource() == bioButton) {
                try {
                    setBioButton();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (e.getSource() == addButton) {
                try {
                    setAddButton();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (e.getSource() == removeButton) {
                try {
                    setRemoveButton();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (e.getSource() == blockButton) {
                try {
                    setBlockButton();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (e.getSource() == chatButton) {
                try {
                    setChatButton();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (e.getSource() == searchButton) {
                try {
                    setSearchButton();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (e.getSource() == exitButton) {
                setExitButton();
            } else if (e.getSource() == sendButton) {
                try {
                    setSendButton();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    };

    public void run() {
        Scanner scanner = new Scanner(System.in);
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    socket = new Socket("localhost", 1234);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(socket.getOutputStream());

                    System.out.println("Please enter 'username, password' replacing 'username' and " +
                            "'password' with the appropriate fields\n" +
                            "Then enter whether you would like to 'Sign-Up' or 'Sign-In");
                    String username = scanner.nextLine();
                    writer.write(username);
                    writer.println();
                    writer.flush();
                    writer.write(text = scanner.nextLine());
                    writer.println();
                    writer.flush();

                    boolean canExit = false;
                    do {
                        System.out.println("Please choose one of the following options:\n" +
                                "Enter 'add' if you want to add a friend\n" +
                                "Enter 'remove' if you want to remove a friend\n" +
                                "Enter 'block' if you want to block a friend\n" +
                                "Enter 'search' if you want to search for a friend\n" +
                                "Enter 'chat' if you want to message a friend\n" +
                                "Enter 'exit' if you want to sign out");
                        writer.write(text = scanner.nextLine());
                        writer.println();
                        writer.flush();

                        String line = "";
                        switch (text) {
                            case "add":
                                // writer.write(text);
                                // writer.println();
                                // writer.flush();
                                System.out.println("Enter the username of the profile you want to friend");
                                writer.write(scanner.nextLine());
                                writer.println();
                                writer.flush();
                                // while((line = reader.readLine()) != null) {
                                //     System.out.println(line);
                                // }
                                break;
                            case "remove":
                                // writer.write(text);
                                // writer.println();
                                // writer.flush();
                                System.out.println("Enter the username of the profile you want to remove as a friend");
                                writer.write(scanner.nextLine());
                                writer.println();
                                writer.flush();
                                // while((line = reader.readLine()) != null) {
                                //     System.out.println(line);
                                // }
                                break;
                            case "block":
                                // writer.write(text);
                                // writer.println();
                                // writer.flush();
                                System.out.println("Enter the username of the profile you want to block");
                                writer.write(scanner.nextLine());
                                writer.println();
                                writer.flush();
                                // while((line = reader.readLine()) != null) {
                                //     System.out.println(line);
                                // }
                                break;
                            case "search":
                                boolean isSearch = false;
                                do {
                                    // writer.write(text);
                                    // writer.println();
                                    // writer.flush();
                                    System.out.println("Enter the username of the profile you want to search");
                                    writer.write(scanner.nextLine());
                                    writer.println();
                                    writer.flush();
                                    System.out.println("Do you want to search again? (1 or 0)");
                                    String again = scanner.nextLine();
                                    writer.write(again);
                                    writer.println();
                                    writer.flush();
                                    if (again.equals("0")) {
                                        isSearch = true;
                                    }
                                    // while((line = reader.readLine()) != null) {
                                    //     Systmridem.out.println(line);
                                    // }
                                } while (!isSearch);
                                break;
                            case "chat": // move gui to a separate method
                                stillChatting = true;
                                System.out.println("chatting");
                                chooseFriend(username.split(",")[0], writer);
                                break;
                            case "exit":
                                writer.write(text);
                                writer.println();
                                writer.flush();
                                canExit = true;
                                break;
                        }
                    } while (!canExit);

                    reader.close();
                    writer.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        worker.execute();
        return;
    }

    public void setSignUpButton() throws IOException {
        PrintWriter writer;
        BufferedReader reader;
        try {
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        if (!usernameInput.getText().isEmpty() && !passwordInput.getText().isEmpty()) {
            writer.write(usernameInput.getText() + "," + passwordInput.getText());
            writer.println();
            writer.write("Sign-Up");
            writer.println();
            writer.flush();

            String input = reader.readLine();
            if (input.equals("exists")) {
                JOptionPane.showMessageDialog(null, "Sorry, that username already exists",
                        "Error", JOptionPane.INFORMATION_MESSAGE);
            } else if (input.equals("added")) {
                JOptionPane.showMessageDialog(null, "Success",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                username = usernameInput.getText();
                frame.dispose();
                menuPage();
            }
            usernameInput.setText("");
            passwordInput.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a username and password",
                    "Error", JOptionPane.INFORMATION_MESSAGE);
        }

        //writer.close();
        //reader.close();
    }

    public void setLogInButton() throws IOException {
        PrintWriter writer;
        BufferedReader reader;
        try {
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        if (!usernameInput.getText().isEmpty() && !passwordInput.getText().isEmpty()) {
            writer.write(usernameInput.getText() + "," + passwordInput.getText());
            writer.println();
            writer.write("Log-In");
            writer.println();
            writer.flush();

            String input = reader.readLine();
            if (input.equals("Failed")) {
                JOptionPane.showMessageDialog(null, "Sorry, this account doesn't exist",
                        "Error", JOptionPane.INFORMATION_MESSAGE);
            } else if (input.equals("Success")) {
                JOptionPane.showMessageDialog(null, "Success",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                username = usernameInput.getText();
                frame.dispose();
                menuPage();
                input = reader.readLine();
                updateFriendsList(input);
                input = reader.readLine();
                updateBlockedList(input);
            }
            usernameInput.setText("");
            passwordInput.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a username and password",
                    "Error", JOptionPane.INFORMATION_MESSAGE);
        }

        //writer.close();
        //reader.close();
    }

    public void setBioButton() throws IOException {
        PrintWriter writer;
        BufferedReader reader;
        try {
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        if (!generalInput.getText().isEmpty()) {
            writer.write("bio\n" + generalInput.getText());
            writer.println();
            writer.flush();

            String input = reader.readLine();
            if (input.equals("added")) {
                JOptionPane.showMessageDialog(null, "Success",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a bio into the text field",
                    "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void setAddButton() throws IOException {
        PrintWriter writer;
        BufferedReader reader;
        try {
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        if (!generalInput.getText().isEmpty()) {
            writer.write("add\n" + generalInput.getText());
            writer.println();
            writer.flush();

            String input = reader.readLine();
            if (input.equals("added")) {
                input = reader.readLine();
                updateFriendsList(input);
                input = reader.readLine();
                updateBlockedList(input);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a username into the text field",
                    "Error", JOptionPane.INFORMATION_MESSAGE);
        }

        //writer.close();
        //reader.close();
    }

    public void setRemoveButton() throws IOException {
        PrintWriter writer;
        BufferedReader reader;
        try {
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        if (!generalInput.getText().isEmpty()) {
            writer.write("remove\n" + generalInput.getText());
            writer.println();
            writer.flush();

            String input = reader.readLine();
            if (input.equals("removed")) {
                input = reader.readLine();
                updateFriendsList(input);
                input = reader.readLine();
                updateBlockedList(input);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a username into the text field",
                    "Error", JOptionPane.INFORMATION_MESSAGE);
        }

        //writer.close();
        //reader.close();
    }

    public void setBlockButton() throws IOException {
        PrintWriter writer;
        BufferedReader reader;
        try {
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        if (!generalInput.getText().isEmpty()) {
            writer.write("block\n" + generalInput.getText());
            writer.println();
            writer.flush();

            String input = reader.readLine();
            if (input.equals("blocked")) {
                input = reader.readLine();
                updateFriendsList(input);
                input = reader.readLine();
                updateBlockedList(input);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a username into the text field",
                    "Error", JOptionPane.INFORMATION_MESSAGE);
        }

        //writer.close();
        //reader.close();
    }

    public void setChatButton() throws IOException {
        PrintWriter writer;
        BufferedReader reader;
        try {
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        if (!generalInput.getText().isEmpty()) {
            messagingGUI(username, generalInput.getText(), writer);
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a username into the text field",
                    "Error", JOptionPane.INFORMATION_MESSAGE);
        }

        //writer.close();
        //reader.close();
    }

    public void setSearchButton() throws IOException {
        PrintWriter writer;
        BufferedReader reader;

        try {
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        if (!generalInput.getText().isEmpty()) {
            writer.write("search");
            writer.println();
            writer.write(generalInput.getText());
            writer.println();
            writer.flush();
        }

        //receives search results from friends
        String input = reader.readLine();
        if (!input.equals("empty")) {
            String[] results = input.split(";");
            String userSelection;
            String bioSelection = "User does not have bio.";
            for (int i = 0; i < results.length; i++) {
                results[i] = results[i].substring(0, results[i].indexOf(':'));
            }
            String selection = (String) JOptionPane.showInputDialog(null,
                    "Select a user:",
                    "Select results",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    results,
                    results[0]);

            userSelection = selection;
            results = input.split(";");
            for (int i = 0; i < results.length; i++) {
                if (results[i].contains(userSelection)
                        && !results[i].substring(results[i].indexOf(":") + 1).equals(" ")) {
                    bioSelection = results[i].substring(results[i].indexOf(":") + 1);
                    break;
                }
            }

            displayPanel.setText(userSelection + "\n\n" + bioSelection);
        }
    }

    public void setExitButton() {
        mainFrame.dispose();
    }

    public void setSendButton() throws IOException {
        PrintWriter writer;
        BufferedReader reader;
        try {
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        if (!message.getText().isEmpty()) {
            writer.write("chat");
            writer.println();
            writer.write(chatFriend);
            writer.println();
            writer.write(message.getText());
            writer.println();
            writer.flush();
            chatTextArea.append(username + ": " + message.getText() + "\n");

            String input = reader.readLine();
            if (input.equals("sent")) {

            }
        }
    }

    public void frontPage() {
        frame = new JFrame("Find-A-Friend");
        content = frame.getContentPane();
        content.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();

        JLabel title = new JLabel("Welcome to Find-A-Friend");
        centerPanel.add(title);
        JPanel usernamePanel = new JPanel();
        JLabel usernameLabel = new JLabel("Username:");
        usernameInput = new JTextField(10);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameInput);
        centerPanel.add(usernamePanel);

        JPanel passwordPanel = new JPanel();
        JLabel passwordLabel = new JLabel("Password:");
        passwordInput = new JPasswordField(10);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordInput);
        centerPanel.add(passwordPanel);

        JPanel buttonsPanel = new JPanel();
        signUpButton = new JButton("Sign-Up");
        signUpButton.addActionListener(actionListener);
        buttonsPanel.add(signUpButton);
        logInButton = new JButton("Log-In");
        logInButton.addActionListener(actionListener);
        buttonsPanel.add(logInButton);
        centerPanel.add(buttonsPanel);

        content.add(centerPanel, BorderLayout.CENTER);

        frame.setSize(300, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void menuPage() {
        mainFrame = new JFrame("Find-A-Friend Home Page");
        Container content = mainFrame.getContentPane();
        content.setLayout(new BorderLayout());

        //JPanel mainPanel = new JPanel();

        JLabel title = new JLabel("Find-A-Friend presented by MAID Inc.");
        mainFrame.add(title, BorderLayout.NORTH);
        //mainPanel.add(title, BorderLayout.NORTH);

        displayPanel = new JTextArea();
        displayPanel.setFont(displayPanel.getFont().deriveFont(20f));
        //add functionality to display panel
        Color color = new Color(98, 204, 107);
        displayPanel.setBackground(color);
        displayPanel.setEditable(false);
        displayPanel.setColumns(10);
        displayPanel.setRows(30);
        JScrollPane displayListPanel = new JScrollPane(displayPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        displayListPanel.setBounds(0, 10, 10, 20);
        mainFrame.add(displayListPanel, BorderLayout.CENTER);
        //mainPanel.add(displayPanel, BorderLayout.CENTER);

        JPanel textPanel = new JPanel();
        generalInput = new JTextField("Enter a username",20);
        generalInput.setText("");
        textPanel.add(generalInput);
        //mainPanel.add(textPanel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel();
        bioButton = new JButton("Bio");
        bioButton.addActionListener(actionListener);
        optionsPanel.add(bioButton);
        addButton = new JButton("Add");
        addButton.addActionListener(actionListener);
        optionsPanel.add(addButton);
        removeButton = new JButton("Remove");
        removeButton.addActionListener(actionListener);
        optionsPanel.add(removeButton);
        blockButton = new JButton("Block");
        blockButton.addActionListener(actionListener);
        optionsPanel.add(blockButton);
        chatButton = new JButton("Chat");
        chatButton.addActionListener(actionListener);
        optionsPanel.add(chatButton);
        searchButton = new JButton("Search");
        searchButton.addActionListener(actionListener);
        optionsPanel.add(searchButton);
        exitButton = new JButton("Exit");
        exitButton.addActionListener(actionListener);
        optionsPanel.add(exitButton);
        textPanel.add(optionsPanel, BorderLayout.SOUTH);

        mainFrame.add(textPanel, BorderLayout.SOUTH);
        // mainPanel.add(textPanel, BorderLayout.SOUTH);

        friendsList = new JTextArea(35, 30);
        friendsList.setFont(friendsList.getFont().deriveFont(14f));
        color = new Color(175, 119, 199);
        friendsList.setBackground(color);
        friendsList.setEditable(false);
        friendsList.setColumns(10);
        friendsList.setRows(30);
        JScrollPane listPanel = new JScrollPane(friendsList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        listPanel.setBounds(0, 100, 100, 200);
        mainFrame.add(listPanel, BorderLayout.WEST);

        //mainFrame.add(mainPanel, BorderLayout.CENTER);

        mainFrame.setSize(750, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void chooseFriend(String username, PrintWriter writer) throws FileNotFoundException, IOException {
        JFrame chooseFriend = new JFrame("Direct Messaging");
        chooseFriend.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                try {
                    messagingGUI(username, chosenFriend.strip(), writer);
                } catch (FileNotFoundException f) {
                    f.printStackTrace();
                } catch (IOException f) {
                    f.printStackTrace();
                }
            }
        });
        BufferedReader bfr = new BufferedReader(new FileReader("friendsDatabase.txt"));
        String line = "";
        String userFriends = "";
        while ((line = bfr.readLine()) != null) {
            if (line.split(":")[0].equals(username)) {
                userFriends = line.split(":")[1];
                break;
            }
        }
        bfr.close();
        if (userFriends.equals("")) {
            JOptionPane.showMessageDialog(null, "You have no friends:(",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] choices = userFriends.split(",");
        JComboBox<String> friends = new JComboBox<>(choices);
        friends.setMaximumSize(friends.getPreferredSize());
        JButton choiceFriend = new JButton("OK");
        choiceFriend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chosenFriend = (String)friends.getSelectedItem();
                stillChatting = false;
                writer.write(chosenFriend.strip());
                writer.println();
                writer.flush();
                chooseFriend.dispose();
            }
        });

        chooseFriend.add(friends);
        chooseFriend.add(choiceFriend, BorderLayout.EAST);
        chooseFriend.setSize(350, 350);
        chooseFriend.setLocationRelativeTo(null);
        chooseFriend.setVisible(true);
    }

    public void messagingGUI(String username, String friend, PrintWriter writer) throws FileNotFoundException, IOException {
        chatFriend = friend;
        chatBox = new JFrame(friend);
        Container content = chatBox.getContentPane();
        content.setLayout(new BorderLayout());

//        JPanel topPanel = new JPanel();
//        topPanel.setSize(350, 100);
//        topPanel.setBackground(Color.BLACK);
//        // JLabel friendName = new JLabel(friend, 10);
//        exitChatButton = new JButton("Exit");
//        exitChatButton.addActionListener(actionListener);
////        exitChatButton.addActionListener(new ActionListener() {
////            public void actionPerformed(ActionEvent e) {
////                stillChatting = false;
////                writer.write("false");
////                writer.println();
////                writer.flush();
////                chatBox.dispose();
////            }
////        });
//        // topPanel.add(friendName, BorderLayout.WEST);
//        topPanel.add(exitButton, BorderLayout.EAST);
//        chatBox.add(topPanel, BorderLayout.NORTH);

        chatTextArea = new JTextArea(350, 300);
        Color color = new Color(116, 165, 214);
        chatTextArea.setBackground(color);
        chatTextArea.setEditable(false);
        chatTextArea.setColumns(350);
        chatTextArea.setRows(300);
        getChatHistory(username, friend, chatTextArea);
        JScrollPane scrollPane = new JScrollPane(chatTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, 100, 350, 300);
        chatBox.add(scrollPane, BorderLayout.CENTER);

        JPanel messagePanel = new JPanel();
        messagePanel.setSize(350, 200);
        messagePanel.setBackground(Color.GRAY);
        message = new JTextField("", 20);
        sendButton = new JButton("Send");
        sendButton.addActionListener(actionListener);
        messagePanel.add(message, BorderLayout.WEST);
        messagePanel.add(sendButton);
        chatBox.add(messagePanel, BorderLayout.SOUTH);
//        chatBox.add(scrollPane, BorderLayout.CENTER);
//        chatBox.add(topPanel, BorderLayout.NORTH);
        chatBox.setSize(350, 600);
        chatBox.setLocationRelativeTo(null);
        chatBox.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chatBox.setLocationRelativeTo(null);
        chatBox.setVisible(true);

        //        sendButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                writer.write("true");
//                writer.println();
//                writer.flush();
//                String text = message.getText();
//                chatTextArea.append(username + ": " + text + "\n");
//                writer.write(message.getText());
//                writer.println();
//                writer.flush();
//                message.setText("");
//            }
//        });
    }

    public void getChatHistory(String username, String friend, JTextArea chatTextArea) throws FileNotFoundException, IOException {
        BufferedReader bfr = new BufferedReader(new FileReader("messagingDatabase.txt"));
        String responses = "";
        while ((responses = bfr.readLine()) != null) {
            if (responses.split(":")[0].equals(username + ", " + friend)) {
                if (responses.split(":")[1].contains("|")) {
                    String[] history = responses.split(":")[1].split("\\|");
                    for (String text : history) {
                        if (text.contains("1")) {
                            chatTextArea.append(username + ": " + text.substring(0, text.indexOf("1")).strip() + "\n");
                        } else {
                            chatTextArea.append(friend + ": " + text.substring(0, text.indexOf("2")).strip() + "\n");
                        }
                    }
                } else {
                    String history = responses.split(":")[1].strip();
                    if (history.substring(history.length() - 1).equals("1")) {
                        chatTextArea.append(username + ": " + history.substring(0, history.length() - 1) + "\n");
                    } else {
                        chatTextArea.append(friend + ": " + history.substring(0, history.length() - 1) + "\n");
                    }
                }
            } else if (responses.split(":")[0].equals(friend + ", " + username)) {
                if (responses.split(":")[1].contains("|")) {
                    String[] history = responses.split(":")[1].split("\\|");
                    for (String text : history) {
                        if (text.contains("1")) {
                            chatTextArea.append(friend + ": " + text.substring(0, text.indexOf("1")).strip() + "\n");
                        } else {
                            chatTextArea.append(username + ": " + text.substring(0, text.indexOf("2")).strip() + "\n");
                        }
                    }
                } else {
                    String history = responses.split(":")[1].strip();
                    if (history.substring(history.length() - 1).equals("1")) {
                        chatTextArea.append(friend + ": " + history.substring(0, history.length() - 1) + "\n");
                    } else {
                        chatTextArea.append(username + ": " + history.substring(0, history.length() - 1) + "\n");
                    }
                }
            }
        }
        bfr.close();
    }

    public void updateFriendsList(String input) {
        friendsList.setText("Friends:");
        String[] friends = input.split(",");
        for (String i : friends) {
            friendsList.append("\n" + i);
        }
    }

    public void updateBlockedList(String input) {
        friendsList.append("\n\nBlocked:");
        String[] blocked = input.split(",");
        for (String i : blocked) {
            friendsList.append("\n" + i);
        }
    }
}



// import java.awt.*;
// import java.awt.event.ActionListener;
// import java.io.*;
// import java.net.*;
// import java.util.Scanner;
// import javax.swing.*;

// /**
//  * Interface for the Client class
//  *
//  * @author Alexander Siladie, David Liansi, Mridula Naikawadi, Isaac Wang
//  * @version 2024-04-15
//  */
// public class Client implements ClientInterface {
//     //    private Socket s;
// //    private ObjectOutputStream output;
// //    private ObjectInputStream input;
//     private static String text = "";
//     private static boolean stillChatting;


//     //    public Client(String address, int port) {
// //        try {
// //            s = new Socket(address, port);
// //            System.out.println("Connection established");
// //
// //            output = new ObjectOutputStream(s.getOutputStream());
// //            input = new ObjectInputStream(s.getInputStream());
// //        } catch (IOException e) {
// //            e.printStackTrace();
// //        }
// //    }
//     public void run() {
//         JFrame frame = new JFrame("Find-A-Friend");
//         frame.setLocationRelativeTo(null);
//         Container content = frame.getContentPane();
//         content.setLayout(new BorderLayout());

//         JPanel centerPanel = new JPanel();

//         JLabel title = new JLabel("Welcome to Find-A-Friend");
//         centerPanel.add(title);

//         JPanel usernamePanel = new JPanel();
//         JLabel usernameLabel = new JLabel("Username:");
//         JTextField usernameInput = new JTextField(10);
//         usernamePanel.add(usernameLabel);
//         usernamePanel.add(usernameInput);
//         centerPanel.add(usernamePanel);

//         JPanel passwordPanel = new JPanel();
//         JLabel passwordLabel = new JLabel("Password:");
//         JTextField passwordInput = new JTextField(10);
//         passwordPanel.add(passwordLabel);
//         passwordPanel.add(passwordInput);
//         centerPanel.add(passwordPanel);

//         JPanel buttonsPanel = new JPanel();
//         JButton signUpButton = new JButton("Sign-Up");
//         buttonsPanel.add(signUpButton);
//         JButton logInButton = new JButton("Log-In");
//         buttonsPanel.add(logInButton);
//         centerPanel.add(buttonsPanel);


//         content.add(centerPanel, BorderLayout.CENTER);

//         frame.setSize(350, 600);
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setVisible(true);
//     }

//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);
//         Client client = new Client();

//         try {
//             Socket socket = new Socket("localhost", 1234);

//             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//             PrintWriter writer = new PrintWriter(socket.getOutputStream());

//             client.run();
//             System.out.println("Please enter 'username, password' replacing 'username' and " +
//                     "'password' with the appropriate fields\n" +
//                     "Then enter whether you would like to 'Sign-Up' or 'Sign-In");
//             writer.write(scanner.nextLine());
//             writer.println();
//             writer.flush();
//             writer.write(text = scanner.nextLine());
//             writer.println();
//             writer.flush();

//             boolean canExit = false;
//             do {
//                 System.out.println("Please choose one of the following options:\n" +
//                         "Enter 'add' if you want to add a friend\n" +
//                         "Enter 'remove' if you want to remove a friend\n" +
//                         "Enter 'block' if you want to block a friend\n" +
//                         "Enter 'search' if you want to search for a friend\n" +
//                         "Enter 'chat' if you want to message a friend\n" +
//                         "Enter 'exit' if you want to sign out");
//                 writer.write(scanner.nextLine());
//                 writer.println();
//                 writer.flush();

//                 String line = "";
//                 switch (text) {
//                     case "add":
//                         writer.write(text);
//                         writer.println();
//                         writer.flush();
//                         System.out.println("Enter the username of the profile you want to friend");
//                         writer.write(scanner.nextLine());
//                         writer.println();
//                         writer.flush();
//                         // while((line = reader.readLine()) != null) {
//                         //     System.out.println(line);
//                         // }
//                         break;
//                     case "remove":
//                         writer.write(text);
//                         writer.println();
//                         writer.flush();
//                         System.out.println("Enter the username of the profile you want to remove as a friend");
//                         writer.write(scanner.nextLine());
//                         writer.println();
//                         writer.flush();
//                         // while((line = reader.readLine()) != null) {
//                         //     System.out.println(line);
//                         // }
//                         break;
//                     case "block":
//                         writer.write(text);
//                         writer.println();
//                         writer.flush();
//                         System.out.println("Enter the username of the profile you want to block");
//                         writer.write(scanner.nextLine());
//                         writer.println();
//                         writer.flush();
//                         // while((line = reader.readLine()) != null) {
//                         //     System.out.println(line);
//                         // }
//                         break;
//                     case "search":
//                         boolean isSearch = false;
//                         do {
//                             writer.write(text);
//                             writer.println();
//                             writer.flush();
//                             System.out.println("Enter the username of the profile you want to search");
//                             writer.write(scanner.nextLine());
//                             writer.println();
//                             writer.flush();
//                             System.out.println("Do you want to search again? (1 or 0)");
//                             String again = scanner.nextLine();
//                             writer.write(again);
//                             writer.println();
//                             writer.flush();
//                             if (again.equals("0")) {
//                                 isSearch = true;
//                             }
//                             // while((line = reader.readLine()) != null) {
//                             //     System.out.println(line);
//                             // }
//                         } while (!isSearch);
//                         break;
//                     case "chat":
//                         stillChatting = true;
//                         // chatBox();
//                         System.out.println("chatting");
//                         // writer.write(text);
//                         // writer.println();
//                         // writer.flush();mm
//                         System.out.println("Enter the username of the profile you want to message");
//                         String friend = scanner.nextLine();
//                         writer.write(friend);
//                         writer.println();
//                         writer.flush();
//                         JFrame chatBox = new JFrame("Chat");
//                         JPanel topPanel = new JPanel();
//                         topPanel.setSize(500, 100);
//                         topPanel.setBackground(Color.BLACK);
//                         JLabel friendName = new JLabel(friend);
//                         JButton exitButton = new JButton("Exit");
//                         exitButton.addActionListener(new ActionListener() {
//                             public void actionPerformed(ActionEvent e) {
//                                 stillChatting = false;
//                                 chatBox.dispose();
//                             }
//                         });
//                         topPanel.add(friendName, BorderLayout.WEST);
//                         topPanel.add(exitButton, BorderLayout.EAST);

//                         JTextArea chatTextArea = new JTextArea(500, 300);
//                         chatTextArea.setBackground(Color.MAGENTA);
//                         chatTextArea.setEditable(false);
//                         chatTextArea.setColumns(500);
//                         chatTextArea.setRows(300);
//                         JScrollPane scrollPane = new JScrollPane(chatTextArea);
//                         scrollPane.setBounds(0, 100, 500, 300);

//                         JPanel messagePanel = new JPanel();
//                         messagePanel.setSize(500, 200);
//                         messagePanel.setBackground(Color.GRAY);
//                         JTextField message = new JTextField("", 40);
//                         JButton sendButton = new JButton("Send");
//                         messagePanel.add(message, BorderLayout.WEST);
//                         messagePanel.add(sendButton);

//                         // System.out.println("Enter your message");
//                         // String sentMessage = "";
//                         // while((line = reader.readLine()) != null) {
//                         //     System.out.println(line);
//                         // }
//                         chatBox.add(messagePanel, BorderLayout.SOUTH);
//                         chatBox.add(scrollPane);
//                         chatBox.add(topPanel, BorderLayout.NORTH);
//                         chatBox.setSize(500, 600);
//                         chatBox.setLocationRelativeTo(null);
//                         // chatBox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                         chatBox.setVisible(true);
//                         sendButton.addActionListener(new ActionListener() {
//                             public void actionPerformed(ActionEvent e) {
//                                 String sentMessage = message.getText();
//                                 chatTextArea.append(sentMessage + "\n");
//                                 writer.write(sentMessage); //TODO need to fix the send message part
//                                 writer.println();
//                                 writer.flush();
//                             }
//                         });
//                         break;
//                     case "exit":
//                         writer.write(text);
//                         writer.println();
//                         writer.flush();
//                         // while((line = reader.readLine()) != null) {
//                         //     System.out.println(line);
//                         // }
//                         canExit = true;
//                         break;
//                 }
//             } while (!canExit);

//             reader.close();
//             writer.close();
//         } catch (UnknownHostException e) {
//             e.printStackTrace();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     public void chatBox() {
//         JFrame chatBox = new JFrame("Chat");
//         chatBox.setVisible(true);
//     }
// }
