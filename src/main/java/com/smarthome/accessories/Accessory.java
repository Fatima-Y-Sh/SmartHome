package com.smarthome.accessories;

import org.jetbrains.annotations.NotNull;

public abstract class Accessory implements IControllable, Comparable<Accessory> {
    private static int increment = 0;
    protected int id;
    protected String name;
    protected String manufacturer;
    protected String type;
    protected int room;

    public Accessory(String name, String manufacturer, int room) {
        this.id = increment;
        this.name = name;
        this.manufacturer = manufacturer;
        this.room = room;
        increment++;
    }

    public abstract String toString();

    @Override
    public int compareTo(@NotNull Accessory accessory){
        return Integer.compare(this.id, accessory.id); //returns -1,0,or 1
    }
}
