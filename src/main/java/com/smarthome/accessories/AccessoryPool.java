package com.smarthome.accessories;

import com.smarthome.fileinputoutput.FileIO;
import com.smarthome.operating.Operation;
import com.smarthome.operating.OperationRoom;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class AccessoryPool {
    private static final String filePath = "main/resources/AccessoriesCSV";
    private static ArrayList<Accessory> accessories = new ArrayList<>();

    public static void fillAccessoriesPool() throws IOException { //we are filling the array containing accessories from the file
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath)); //reads and fills char array one after another
        String temp;
        String[] split;
        while((temp = bufferedReader.readLine()) != null && !temp.isBlank()){
            split = temp.split(",");
            switch (split[1]) {
                case "climate control" -> accessories.add(new ClimateControl(split[2], split[3], Integer.parseInt(split[4])));//converting str to int
                case "lighting" -> accessories.add(new Lighting(split[2], split[3], Integer.parseInt(split[4])));
                case "security and safety" -> accessories.add(new SecuritySafety(split[2], split[3], Integer.parseInt(split[4])));
            }
        }
        if(!accessories.isEmpty()) System.out.println("Accessories Loaded from file \n");
        else System.out.println("No Saved Accessories found \n");
        bufferedReader.close();
    }

    public static void addAccessory(String type, String name, String manufacturer, int roomID){//adding accessories into the array based on user desire
        switch (type) {
            case "climate" -> accessories.add(new ClimateControl(name, manufacturer, roomID));
            case "lighting" -> accessories.add(new Lighting(name, manufacturer, roomID));
            case "security" -> accessories.add(new SecuritySafety(name, manufacturer, roomID));
            default -> {
                System.out.println("Adding Accessory failed \n");
                return;
            }
        }
        System.out.println("Accessory added successfully \n");
    }

    public static void saveAccessoriesToFile() throws IOException {
        sortAccessoriesById();
        String str = "";
        for(Accessory temp : accessories)
            str = str + temp.id + "," + temp.type + "," + temp.name + "," + temp.manufacturer + "," + temp.room + "\n";
        FileIO.writeToFile(filePath, str);
    }

    public static void setTemperature(int id, double temp, double fan){
        Accessory a = getAccessoryByID(id);
        if(a == null) {
            System.out.println("accessory with such id does not exist \n");
            return;
        }
        if(a instanceof ClimateControl) {
            ((ClimateControl) a).setTemperature(temp);
            ((ClimateControl) a).setFanSpeed(fan);
            System.out.println("Temperature and fan speed set \n");
        }
        else
            System.out.println("accessory with such id is not climate control \n");
    }

    public static void detectMotion(int id){
        Accessory a = getAccessoryByID(id);
        if(a == null) {
            System.out.println("accessory with such id does not exist \n");
            return;
        }
        if(a instanceof SecuritySafety) {
            ((SecuritySafety) a).motionDetect();
        }
        else
            System.out.println("accessory with such id is not security and safety \n");
    }

    public static void turnOn(int id){
        Accessory a = getAccessoryByID(id);
        if(a == null) {
            System.out.println("accessory with such id does not exist \n");
            return;
        }
        a.turnON();
    }

    public static void turnOff(int id){
        Accessory a = getAccessoryByID(id);
        if(a == null) {
            System.out.println("accessory with such id does not exist \n");
            return;
        }
        a.turnOFF();
    }

    public static void printAllAccessories(){
        sortAccessoriesById();
        for (Accessory a : accessories)
            System.out.println(a);
    }

    public static void printAccessoryByID(int id){
        Accessory a = getAccessoryByID(id);
        if(a == null) {
            System.out.println("accessory with such id does not exist \n");
            return;
        }

        System.out.println(a);
    }

    public static void printAccessoryByRoom(int roomID){
        boolean found = false;
        for(Accessory a : accessories) {
            if (a.room == roomID) {
                System.out.println(a);
                found = true;
            }
        }
        if(!found) System.out.println("no accessories in room " + roomID + "\n");
    }

    private static Accessory getAccessoryByID(int id){
        for (Accessory a : accessories) {
            if (a.id == id) return a;
        }
        return null;
    }

    private static void sortAccessoriesById(){
        Collections.sort(accessories);
    }
}
