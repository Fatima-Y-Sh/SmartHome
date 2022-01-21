package com.smarthome.operating;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class Operation implements Comparable<Operation>{

    private static int incrementID = 1;

    private int id;
    private String operator;
    private int room;
    private long operationDateMillis;
    private String accessory;
    private String[] statuses;

    public Operation(){
    }

    public Operation(String operator, int room, long operationDateMillis, String accessory, String[] statuses) {
        this.operator = operator;
        this.room = room;
        this.operationDateMillis = operationDateMillis;
        this.accessory = accessory;
        this.statuses = statuses;
        this.id = incrementID;
        incrementID++;
    }

    @Override
    public int compareTo(@NotNull Operation o){
        return Long.compare(this.operationDateMillis, o.operationDateMillis);
    }

    public String toString(){
        Date time = new Date(operationDateMillis);
        String str = "=======Operation:"+ id +"=======\n" +
                "Operator: "+ operator +"\n" +
                "Room: " + room +"\n" +
                "Time: " + time +"\n" +
                "Accessory Type: " + accessory + "\n" +
                "Statuses: [ ";
        for(String st : statuses) str = str + "\"" + st + "\", ";
        str = str + "]\n";
        return str;
    }

    public String toBriefString(){
        Date time = new Date(operationDateMillis);
        return "=======Operation:"+ id +"=======\n" +
                "Time: " + time +"\n" +
                "Accessory Type: " + accessory + "\n";
    }

    public static void setIncrementID(int incrementID) {
        Operation.incrementID = incrementID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperator() {
        return operator;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public long getOperationDateMillis() {
        return operationDateMillis;
    }

    public String getAccessory() {
        return accessory;
    }

    public String[] getStatuses() {
        return statuses;
    }

}
