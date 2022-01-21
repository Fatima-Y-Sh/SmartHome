package com.smarthome.commandlineinterface;

import com.smarthome.accessories.AccessoryPool;
import com.smarthome.operating.OperationRoom;
import com.smarthome.users.UserAuthentication;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CLI {

    private static final Scanner scanner = new Scanner(System.in);
    private static final boolean isAdmin = UserAuthentication.isActiveUserAdmin();
    private static boolean isRunning = true;

    public static void runCLI() throws ParseException {
        printMenu();
        while(isRunning){
            try { doCMD(getInputSplitLowercase()); } catch (IOException e) { e.printStackTrace(); }
        }
    }

    private static void printMenu(){
        System.out.println();
        System.out.println("Welcome to Smart Home " + UserAuthentication.getActiveUser().getUsername());
        System.out.println("this is a CLI application. Enter ? to show help.\n");
    }

    private static void printHelp(){
        System.out.println();
        System.out.println("commands are not case sensitive");
        System.out.println("Command | What it does");
        System.out.println();
        System.out.println("show operation brief all | shows brief of all operations latest to oldest");
        System.out.println("show operation brief operator ((string) operator name) | shows brief of all operations performed by (operator name)");
        System.out.println("show operation brief room ((int) room number) | shows brief of all operations that happened in room (room number)");
        System.out.println("show operation brief between ((dd/MM/yyyy) start date) ((dd/MM/yyyy) end date) | shows brief of all operation between (start date) and (end date)");
        System.out.println("show operation brief id ((int) id) | shows brief of operation with id (id)");
        System.out.println("show operation brief last ((int) number) | shows brief of the last (number) of operations");
        System.out.println("show operation brief oldest | shows brief of all operations from oldest to latest");
        System.out.println("show operation brief states ((int) id) | shows a brief of all statuses of an operation");
        System.out.println("show operation brief prepost ((int) id) | shows the change of statuses before and after the operation");
        System.out.println();
        System.out.println("show operation detailed all | shows detailed of all operations latest to oldest");
        System.out.println("show operation detailed operator ((string) operator name) | shows detailed of all operations performed by (operator name)");
        System.out.println("show operation detailed room ((int) room number) | shows detailed of all operations that happened in room (room number)");
        System.out.println("show operation detailed between ((dd/MM/yyyy) start date) ((dd/MM/yyyy) end date) | shows detailed of all operation between (start date) and (end date)");
        System.out.println("show operation detailed id ((int) id) | shows detailed of operation with id (id)");
        System.out.println("show operation detailed last ((int) number) | shows detailed of the last (number) of operations");
        System.out.println("show operation detailed oldest | shows detailed of all operations from oldest to latest");
        System.out.println("show operation detailed states ((int) id) | shows detailed of all statuses of an operation");
        System.out.println("show operation detailed prepost ((int) id) | shows the change of statuses before and after the operation");

        System.out.println();
        System.out.println("(admin)show accessory all | shows all accessories");
        System.out.println("(admin)show accessory id ((int) id) | shows accessory with id (id)");
        System.out.println("(admin)show accessory room ((int) room number) | shows all accessories in room (room number)");
        System.out.println();
        System.out.println("set temperature ((int) accessory id) ((double) temperature) ((double) fan speed) | sets temperature (temperature) and fan speed (fan speed) to accessory with id (id) if its climate accessory");
        System.out.println("set motion ((int) id) | trigger motion on accessory with id (id) ");
        System.out.println("set on ((int) id) | turn on motion on accessory with id (id) ");
        System.out.println("set off ((int) id) | turn off motion on accessory with id (id) ");
        System.out.println();
        System.out.println("add accessory light ((string) name) ((string) manufacturer) ((int) room number) | add accessory of certain (type) with (name) (manufacturer) and (room number)");
        System.out.println();
        System.out.println("? | display help");
        System.out.println("exit | save and exit the program");
        System.out.println();
        System.out.println("This program is created by William Baaklini and Fatima Shalhoub as a university project");
        System.out.println();
    }

    public static void doCMD(@NotNull String[] userInput) throws ParseException, IOException {
        //layer1
        switch (userInput[0]){
            case "show":
                //layer2
                switch (userInput[1]){
                    case "operation":
                        //layer3
                        switch(userInput[2]){
                            case "brief":
                                //layer4.1
                                switch (userInput[3]) {
                                    case "all" -> OperationRoom.printAllOperations(true);
                                    case "operator" -> OperationRoom.printOperationByOperator(userInput[4], true);
                                    case "room" -> {
                                        if (stringIsNotNumeric(userInput[4])) {
                                            printInputError();
                                            break;
                                        }
                                        OperationRoom.printOperationByRoom(stringToInt(userInput[4]), true);
                                    }
                                    case "between" -> OperationRoom.printBetweenTimestamp(stringToDate(userInput[4]), stringToDate(userInput[5]), true);
                                    case "id" -> {
                                        if (stringIsNotNumeric(userInput[4])) {
                                            printInputError();
                                            break;
                                        }
                                        OperationRoom.printOperationByID(stringToInt(userInput[4]), true);
                                    }
                                    case "last" -> {
                                        if (stringIsNotNumeric(userInput[4])) {
                                            printInputError();
                                            break;
                                        }
                                        OperationRoom.printLastNOperations(stringToInt(userInput[4]), true);
                                    }
                                    case "oldest" -> OperationRoom.printLastNOperations(true);
                                    case "states" -> {
                                        if (stringIsNotNumeric(userInput[4])) {
                                            printInputError();
                                            break;
                                        }
                                        OperationRoom.printStates(stringToInt(userInput[4]), true);
                                    }
                                    case "prepost" -> {
                                        if (stringIsNotNumeric(userInput[4])) {
                                            printInputError();
                                            break;
                                        }
                                        OperationRoom.printStatesBeforeAfter(stringToInt(userInput[4]),true);
                                    }

                                    default -> System.out.println("wrong command enter ? to show help\n");
                                }//end layer4.1
                                break;

                            case "detailed":
                                //layer4.2
                                switch (userInput[3]) {
                                    case "all" -> OperationRoom.printAllOperations();
                                    case "operator" -> OperationRoom.printOperationByOperator(userInput[4]);
                                    case "room" -> {
                                        if (stringIsNotNumeric(userInput[4])) {
                                            printInputError();
                                            break;
                                        }
                                        OperationRoom.printOperationByRoom(stringToInt(userInput[4]));
                                    }
                                    case "between" -> OperationRoom.printBetweenTimestamp(stringToDate(userInput[4]), stringToDate(userInput[5]));
                                    case "id" -> {
                                        if (stringIsNotNumeric(userInput[4])) {
                                            printInputError();
                                            break;
                                        }
                                        OperationRoom.printOperationByID(stringToInt(userInput[4]));
                                    }
                                    case "last" -> {
                                        if (stringIsNotNumeric(userInput[4])) {
                                            printInputError();
                                            break;
                                        }
                                        OperationRoom.printLastNOperations(stringToInt(userInput[4]));
                                    }
                                    case "oldest" -> OperationRoom.printLastNOperations();
                                    case "states" -> {
                                        if (stringIsNotNumeric(userInput[4])) {
                                            printInputError();
                                            break;
                                        }
                                        OperationRoom.printStates(stringToInt(userInput[4]));
                                    }
                                    case "prepost" -> {
                                        if (stringIsNotNumeric(userInput[4])) {
                                            printInputError();
                                            break;
                                        }
                                        OperationRoom.printStatesBeforeAfter(stringToInt(userInput[4]));
                                    }
                                    default -> printInputError();
                                }//end layer4.2
                                break;
                            default:
                                printInputError();
                        }//end layer 3;
                        break;
                    case "accessory":
                        if(!isAdmin){
                            printAdminError();
                            break;
                        }
                        switch (userInput[2]) {
                            case "all" -> AccessoryPool.printAllAccessories();
                            case "id" -> {
                                if (stringIsNotNumeric(userInput[3])) {
                                    printInputError();
                                    break;
                                }
                                AccessoryPool.printAccessoryByID(stringToInt(userInput[3]));
                            }
                            case "room" -> {
                                if (stringIsNotNumeric(userInput[3])) {
                                    printInputError();
                                    break;
                                }
                                AccessoryPool.printAccessoryByRoom(stringToInt(userInput[3]));
                            }
                            default -> printInputError();
                        }
                        break;
                    default:
                        printInputError();
                }//end layer2
                break;
            case "set":
                switch (userInput[1]) {
                    case "temperature" -> {
                        if (stringIsNotNumeric(userInput[2]) || stringIsNotNumeric(userInput[3]) || stringIsNotNumeric(userInput[4])) {
                            printInputError();
                            break;
                        }
                        AccessoryPool.setTemperature(
                                stringToInt(userInput[2]),
                                stringToDouble(userInput[3]),
                                stringToDouble(userInput[4])
                        );
                    }
                    case "motion" -> {
                        if (stringIsNotNumeric(userInput[2])) {
                            printInputError();
                            break;
                        }
                        AccessoryPool.detectMotion(stringToInt(userInput[2]));
                    }
                    case "on" -> {
                        if (stringIsNotNumeric(userInput[2])) {
                            printInputError();
                            break;
                        }
                        AccessoryPool.turnOn(stringToInt(userInput[2]));
                    }
                    case "off" -> {
                        if (stringIsNotNumeric(userInput[2])) {
                            printInputError();
                            break;
                        }
                        AccessoryPool.turnOff(stringToInt(userInput[2]));
                    }
                    default -> printInputError();
                }
                break;
            case "add":
                if(!isAdmin){
                    printAdminError();
                    break;
                }
                if(!userInput[1].equals("accessory") || stringIsNotNumeric(userInput[5])){
                    printInputError();
                    break;
                }
                switch (userInput[2]) {
                    case "climate", "security", "lighting" -> AccessoryPool.addAccessory(userInput[2], userInput[3], userInput[4], stringToInt(userInput[5]));
                    default -> printInputError();
                }
                break;
            case "?":
                printHelp();
                break;
            case "exit":
                OperationRoom.writeOperationsToFile();
                AccessoryPool.saveAccessoriesToFile();
                scanner.close();
                isRunning = false;
                System.exit(0);
                return;
            default:
                printInputError();
        }//end layer1
    }

    private static String getInputFromUser(){
        return scanner.nextLine();
    }

    @NotNull
    public static String[] getInputSplitLowercase(){
        String a = getInputFromUser();
        a = a.toLowerCase();
        String[] temp = a.split("\\s+");
        String[] result =  new String[6];
        System.arraycopy(temp, 0, result, 0, temp.length);
        for (int i = 0; i < 6; i++) {
            if(result[i]==null) result[i] = "";
        }
        return result;
    }

    private static int stringToInt(String str) {
        return Integer.parseInt(str);
    }

    private static Double stringToDouble(String str) {
        return Double.parseDouble(str);
    }

    private static boolean stringIsNotNumeric(String str){
        return str == null || !str.matches("[0-9.]+");
    }

    private static Date stringToDate(String str) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(str);
    }

    private static void printInputError(){
        System.out.println("wrong command enter ? to show help\n");
    }
    private static void printAdminError(){ System.out.println("you need to be admin in order to execute this command\n"); }
}
