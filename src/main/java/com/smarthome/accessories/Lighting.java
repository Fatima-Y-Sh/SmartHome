package com.smarthome.accessories;

import com.smarthome.operating.Operation;
import com.smarthome.operating.OperationRoom;
import com.smarthome.users.UserAuthentication;

public class Lighting extends Accessory {

    private boolean lightsOn;

    public Lighting(String name, String manufacturer, int room) {
        super(name, manufacturer, room);
        type = "lighting";
        lightsOn = false;
    }

    @Override
    public String toString() {
        return "Accessory: id-" + this.id + " type-" + this.type +
                " manufacturer-" + this.manufacturer +
                " name-"+ this.name + " room-"+ this.room +
                " lightsOn?-" + this.lightsOn + "\n";
    }

    @Override
    public void turnON() {
        lightsOn = true;
        String operator = UserAuthentication.getActiveUser().getUsername();
        System.out.println(type + " in room " + room + " turned on \n");
        OperationRoom.addOperation(new Operation(operator, room, System.currentTimeMillis(), type,
                new String[]{"turned on"}));
    }

    @Override
    public void turnOFF() {
        lightsOn = false;
        String operator = UserAuthentication.getActiveUser().getUsername();
        System.out.println(type + " in room " + room + " turned off \n");
        OperationRoom.addOperation(new Operation(operator, room, System.currentTimeMillis(), type,
                new String[]{"turned off"}));
    }
}
