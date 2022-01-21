package com.smarthome.accessories;

import com.smarthome.operating.Operation;
import com.smarthome.operating.OperationRoom;
import com.smarthome.users.UserAuthentication;

public class ClimateControl extends Accessory{

    private double temperature;
    private double fanSpeed;

    public ClimateControl(String name, String manufacturer, int room) {
        super(name, manufacturer, room);
        type = "climate control";
        temperature = 0;
        fanSpeed = 0;
    }

    @Override
    public String toString() {
        return "Accessory: id-" + this.id + " type-" + this.type +
                " manufacturer-" + this.manufacturer +
                " name-"+ this.name + " room-"+ this.room +
                " temperature-"+ this.temperature + " fanSpeed-"+ this.fanSpeed + "\n";
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setFanSpeed(double fanSpeed) {

        this.fanSpeed = fanSpeed;
    }

    @Override
    public void turnON() {
        String operator = UserAuthentication.getActiveUser().getUsername();
        System.out.println(type + " in room " + room + " turned on \n");
        OperationRoom.addOperation(new Operation(operator, room, System.currentTimeMillis(), type,
                new String[]{"turned On","temperature: " + temperature,"fan speed: " + fanSpeed}));
    }

    @Override
    public void turnOFF() {
        String operator = UserAuthentication.getActiveUser().getUsername();
        System.out.println(type + " in room " + room + " turned off \n");
        OperationRoom.addOperation(new Operation(operator, room, System.currentTimeMillis(), type,
                new String[]{"turned Off","temperature: " + temperature,"fan speed: " + fanSpeed}));
    }
}
