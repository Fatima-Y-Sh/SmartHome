package com.smarthome.accessories;

import com.smarthome.operating.Operation;
import com.smarthome.operating.OperationRoom;
import com.smarthome.users.UserAuthentication;

public class SecuritySafety extends Accessory {
    private int motionDetectionCounter;

    public SecuritySafety(String name, String manufacturer, int room) {
        super(name, manufacturer, room);
        type = "security and safety";
        motionDetectionCounter = 0;
    }

    @Override
    public String toString() {
        return "Accessory: id-" + this.id + " type-" + this.type +
                " manufacturer-" + this.manufacturer +
                " name-"+ this.name + " room-"+ this.room +
                " motionCount-" + this.motionDetectionCounter + "\n";
    }

    public void motionDetect() {
        motionDetectionCounter++;
        String operator = UserAuthentication.getActiveUser().getUsername();
        System.out.println("motion detected at room " + room + "\n");
        OperationRoom.addOperation(new Operation(operator, room, System.currentTimeMillis(), type,
                new String[]{"motion detection triggered"}));
    }

    @Override
    public void turnON() {
        String operator = UserAuthentication.getActiveUser().getUsername();
        System.out.println(type + " in room " + room + " turned on \n");
        OperationRoom.addOperation(new Operation(operator, room, System.currentTimeMillis(), type,
                new String[]{"motion detection turned on"}));
    }

    @Override
    public void turnOFF() {
        String operator = UserAuthentication.getActiveUser().getUsername();
        System.out.println(type + " in room " + room + " turned on \n");
        OperationRoom.addOperation(new Operation(operator, room, System.currentTimeMillis(), type,
                new String[]{"motion detection turned off"}));
    }
}
