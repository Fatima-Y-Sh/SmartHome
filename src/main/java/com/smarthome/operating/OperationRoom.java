package com.smarthome.operating;

import com.fasterxml.jackson.databind.JsonNode;
import com.smarthome.fileinputoutput.FileIO;
import com.smarthome.jsonparsing.Json;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class OperationRoom {
    private static final String filePath = "main/resources/operations.json";
    private static ArrayList<Operation> operations;

    public static void fillOperationsFromFile() throws IOException {
        String src = FileIO.readAllFromFile(filePath);
        if(src.isBlank()) {
            System.out.println("no saved Operations try loading the app again and make sure the json is not empty");
            System.exit(-1);
        }
        else {
            operations = new ArrayList<>(Arrays.asList(Json.jsonFromFile(filePath, Operation[].class)));
            Operation.setIncrementID(getOperationsSize()+1);
            System.out.println("Operations Loaded from file");
        }
    }

    public static void writeOperationsToFile() throws IOException {
        String str = "[ ";
        int listSize = operations.size();
        for (int i = 0; i < listSize; i++) {
            JsonNode node = Json.toJson(operations.get(i));
            str = str + Json.prettyPrint(node);
            if(i < listSize - 1) str = str + ", ";
        }
        str = str + " ]";
        FileIO.writeToFile(filePath, str);
    }

    public static void sortOperationsByDate(){
        Collections.sort(operations);
    }

    public static Operation getOperation(int index){//index of the list
        return operations.get(index);
    }

    @Nullable
    private static Operation searchOperationByID(int id){//index defined by the user
        for(Operation operation : operations){
            if(operation.getId() == id) return operation;
        }
        return null;
    }

    public static void printAllOperations(){
        if(operations.isEmpty()){
            System.out.println("!!!No Operations!!!");
        }
        for (Operation operation : operations) {
            System.out.println(operation);
        }
    }

    public static void printAllOperations(boolean briefed){
        if(operations.isEmpty()){
            System.out.println("!!!No Operations!!!");
        }
        for (Operation operation : operations) {
            if(briefed)
                System.out.println(operation.toBriefString());
            else
                System.out.println(operation);
        }
    }

    public static void printOperationByID(int id){
        Operation operation = searchOperationByID(id);
        if(operation ==null)
            System.out.println("Operation with such ID not found");
        else
            System.out.println(operation);
    }

    public static void printOperationByID(int id, boolean briefed){
        Operation operation = searchOperationByID(id);
        if(operation ==null)
            System.out.println("!!!Operation with such ID not found!!!");
        else {
            if(briefed)
                System.out.println(operation.toBriefString());
            else
                System.out.println(operation);
        }
    }

    public static void printOperationByRoom(int roomId){
        boolean found = false;
        for (Operation operation : operations) {
            if(operation.getRoom() == roomId) {
                System.out.println(operation);
                found = true;
            }
        }
        if(!found) System.out.println("Operation with such Room ID not found");
    }

    public static void printOperationByRoom(int roomId, boolean briefed){
        boolean found = false;
        for (Operation operation : operations) {
            if(operation.getRoom() == roomId) {
                if(briefed)
                    System.out.println(operation.toBriefString());
                else
                    System.out.println(operation);
                found = true;
            }
        }
        if(!found) System.out.println("Operation with such Room ID not found");
    }

    public static void printOperationByOperator(String operator){
        boolean found = false;
        for (Operation operation : operations) {
            if(operation.getOperator().equals(operator)) {
                System.out.println(operation);
                found = true;
            }
        }
        if(!found) System.out.println("Operation with such Operator not found");
    }

    public static void printOperationByOperator(String operator, boolean briefed){
        boolean found = false;
        for (Operation operation : operations) {
            if(operation.getOperator().equals(operator)) {
                if(briefed)
                    System.out.println(operation.toBriefString());
                else
                    System.out.println(operation);
                found = true;
            }
        }
        if(!found) System.out.println("Operation with such Operator not found");
    }

    public static void printLastNOperations(int n){
        if(n>operations.size()){
            System.out.println("N > Operations");
            return;
        }
        sortOperationsByDate();
        for (int i = operations.size() - 1; n > 0 ; i--, n--) {
            System.out.println(operations.get(i));
        }
    }

    public static void printLastNOperations(int n, boolean briefed){
        if(n>operations.size()){
            System.out.println("N > Operations");
            return;
        }
        sortOperationsByDate();
        for (int i = operations.size() - 1 ; n > 0 ; i--, n--) {
            if(briefed)
                System.out.println(operations.get(i).toBriefString());
            else
                System.out.println(operations.get(i));
        }
    }

    public static void printLastNOperations(){
        sortOperationsByDate();
        for (int i = operations.size() - 1 ; i >= 0; i--) {
            System.out.println(operations.get(i));
        }
    }
    public static void printLastNOperations(boolean briefed){
        sortOperationsByDate();
        for (int i = operations.size() - 1 ; i >= 0; i--) {
            if(briefed)
                System.out.println(operations.get(i).toBriefString());
            else
                System.out.println(operations.get(i));
        }
    }

    public static void printBetweenTimestamp(@NotNull Date startDate, @NotNull Date endDate){
        long d1 = startDate.getTime(), d2 = endDate.getTime();
        boolean found = false;
        for (Operation operation : operations) {
            long operationDate = operation.getOperationDateMillis();
            if(operationDate >= d1 && operationDate <= d2){
                System.out.println(operation);
                found = true;
            }
        }
        if(!found) System.out.println("No Operation Between " + startDate + " and " + endDate);
    }

    public static void printBetweenTimestamp(@NotNull Date startDate, @NotNull Date endDate, boolean briefed){
        long d1 = startDate.getTime(), d2 = endDate.getTime();
        boolean found = false;
        for (Operation operation : operations) {
            long operationDate = operation.getOperationDateMillis();
            if(operationDate >= d1 && operationDate <= d2){
                if(briefed)
                    System.out.println(operation.toBriefString());
                else
                    System.out.println(operation);
                found = true;
            }
        }
        if(!found) System.out.println("No Operation Between " + startDate + " and " + endDate);
    }

    public static void printStatesBeforeAfter(int id){
        Operation operation = searchOperationByID(id);

        String [] statusesArray;

        if(operation == null)
            System.out.println("no operation with such id");

        else {

                  statusesArray=operation.getStatuses();

                        if(statusesArray[0].equals("turned on")){
                            System.out.println("The before status is: " + "turned off" + ".\n");
                            System.out.println("The after status is: " + statusesArray[0] + ".\n");
                        }
                        else{
                            System.out.println("The before status is: " + "turned on" + ".\n");
                            System.out.println("The after status is: " + statusesArray[0] + ".\n");
                }
            System.out.println("The operation's current details are: \n");
            System.out.println(operation);
            }
        }

    public static void printStatesBeforeAfter(int id, boolean briefed){
        Operation operation = searchOperationByID(id);

        String [] statusesArray;

        if(operation == null)
            System.out.println("no operation with such id");

        else {

                statusesArray=operation.getStatuses();

            if(statusesArray[0].equals("turned on")){
                System.out.println("The before status is: " + "turned off" + ".\n");
                System.out.println("The after status is: " + statusesArray[0] + ".\n");
            }
            else{
                System.out.println("The before status is: " + "turned on" + ".\n");
                System.out.println("The after status is: " + statusesArray[0] + ".\n");
            }

            if (briefed){
                System.out.println("The operation's current information (brief)");
                System.out.println(operation.toBriefString());
            }
        }
    }


    public static void printStates(int id, boolean briefed){
        Operation operation = searchOperationByID(id);
        if(operation == null)
            System.out.println("no operation with such id");
        else {
            if (briefed)
                System.out.println(operation.toBriefString());
            else
                System.out.println(operation);
        }
    }

    public static void printStates(int id){
        Operation operation = searchOperationByID(id);
        if(operation == null)
            System.out.println("no operation with such id");
        else {

                System.out.println(operation);
        }
    }

    public static int getOperationsSize(){
        return operations.size();
    }

    public static void addOperation(Operation operation) {
        operations.add(operation);
    }
}
