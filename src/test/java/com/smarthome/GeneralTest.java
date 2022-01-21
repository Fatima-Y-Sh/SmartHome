package com.smarthome;

import com.smarthome.operating.Operation;
import com.smarthome.operating.OperationRoom;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneralTest {

    @Test
    void millisToDate(){
        long millis = System.currentTimeMillis();
        System.out.println(millis);
        Date date = new Date(millis);
        System.out.println(date);
    }

    @Test
    void fillOperations() throws IOException {
        OperationRoom.fillOperationsFromFile();
    }

    @Test
    void printOperation(){
        Operation o = new Operation("william", 1, 156874984, "security",
                new String[]{"s1","s2","s3"});
        System.out.println(o);
    }

    @Test
    void autoIncrement(){
        Operation o1 = new Operation("william", 1, 156874984, "security",
                new String[]{"s1","s2","s3"});
        Operation o2 = new Operation("william", 1, 156874984, "security",
                new String[]{"s1","s2","s3"});
        Operation o3 = new Operation("william", 1, 156874984, "security",
                new String[]{"s1","s2","s3"});
        System.out.println(o1);
        System.out.println(o2);
        System.out.println(o3);
    }

    @Test
    void toLowerCase(){
        String s = "12 abc ABC wdWD * / - TRUE";
        System.out.println(s.toLowerCase());
    }

    @Test
    void stringInt() throws NumberFormatException{
        String s = "128199";
        int i = Integer.parseInt(s);
        System.out.println(i);
    }

    @Test
    void stringDate() throws ParseException {
        String s = "12/5/2001";
        Date d = new SimpleDateFormat("dd/MM/yyyy").parse(s);
        System.out.println(d);
    }

    @Test
    void fillOperationRoom() throws IOException {
        OperationRoom.fillOperationsFromFile();
        for (int i = 0; i < OperationRoom.getOperationsSize(); i++) System.out.println(OperationRoom.getOperation(i).toString());
    }

    @Test
    void fillOperationSortRoom() throws IOException {
        OperationRoom.fillOperationsFromFile();
        for (int i = 0; i < OperationRoom.getOperationsSize(); i++) System.out.println(OperationRoom.getOperation(i).toString());
        OperationRoom.sortOperationsByDate();
        for (int i = 0; i < OperationRoom.getOperationsSize(); i++) System.out.println(OperationRoom.getOperation(i).toString());
    }

    @Test
    void printOperations() throws IOException {
        OperationRoom.fillOperationsFromFile();
        OperationRoom.sortOperationsByDate();
        OperationRoom.writeOperationsToFile();
    }

    @Test
    void printLastN() throws IOException {
        OperationRoom.fillOperationsFromFile();
        OperationRoom.printLastNOperations(3, true);
    }

    @Test
    void printAll() throws IOException {
        OperationRoom.fillOperationsFromFile();
        OperationRoom.printAllOperations();
    }

    @Test
    void printByOperator() throws IOException {
        OperationRoom.fillOperationsFromFile();
        OperationRoom.printOperationByOperator("william", true);
    }

    @Test
    void printByRoom() throws IOException {
        OperationRoom.fillOperationsFromFile();
        OperationRoom.printOperationByRoom(1, false);
    }

    @Test
    void printByDate() throws IOException, ParseException {
        OperationRoom.fillOperationsFromFile();
        OperationRoom.printBetweenTimestamp(new SimpleDateFormat("dd/MM/yyyy").parse("11/7/1970"),
                new SimpleDateFormat("dd/MM/yyyy").parse("14/7/1970"));
    }

    //String operator, int room, long operationDateMillis, String accessory, String[] statuses
    @Test
    void copyArray(){
        try {
            OperationRoom.fillOperationsFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] statuses;
        statuses = new String[]{"s1", "s2", "s3"};
        Operation operation = new Operation("william", 1, System.currentTimeMillis(), "lighting", statuses);
        OperationRoom.addOperation(operation);
    }
}
