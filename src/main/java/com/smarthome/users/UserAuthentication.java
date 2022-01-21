package com.smarthome.users;

import com.smarthome.fileinputoutput.FileIO;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UserAuthentication {
    private static final String usersFilePath = "main/resources/usersCSV.txt";
    private static ArrayList<User> usersCredentials = new ArrayList<>();
    private static User activeUser;

    public UserAuthentication(){
        try { usersCredentials = getUsersFromFile(); } catch (IOException e) { e.printStackTrace(); }
    }

    @NotNull
    private ArrayList<User> getUsersFromFile() throws IOException {
        ArrayList<User> users = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(usersFilePath));
        String temp;
        String[] split;
        while( (temp = bufferedReader.readLine()) != null ){
            split = temp.split(",");
            users.add( new User(split[0], split[1], (split[2].equals("1"))) );
        }
        bufferedReader.close();
        return users;
    }

    public static void userSignUp(String username, String password, boolean admin) throws IOException {
        String userNameLineCSV = "\n" + username + "," + password + "," + (admin ? "1" : "0");
        usersCredentials.add(new User(username, password, admin));
        activeUser = usersCredentials.get(usersCredentials.size()-1);   //since we added him as the last user
        FileIO.writeToFile(usersFilePath, userNameLineCSV, true );
    }

    public ArrayList<User> getUsersCredentials() {
        return usersCredentials;
    }

    public static User getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(User activeUser) {
        UserAuthentication.activeUser = activeUser;
    }

    public static boolean isActiveUserAdmin(){
        return activeUser.privileges;
    }
}
