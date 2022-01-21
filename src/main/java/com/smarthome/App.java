package com.smarthome;

import com.smarthome.accessories.AccessoryPool;
import com.smarthome.operating.OperationRoom;
import com.smarthome.users.LoginGUI;
import com.smarthome.users.UserAuthentication;

import java.io.IOException;

public class App {
    public static void main(String[] args) {

        try { OperationRoom.fillOperationsFromFile(); } catch (IOException e) { e.printStackTrace(); }
        try { AccessoryPool.fillAccessoriesPool(); } catch (IOException e) { e.printStackTrace(); }

        UserAuthentication userAuthentication = new UserAuthentication();

        new LoginGUI(userAuthentication.getUsersCredentials());
    }
}
