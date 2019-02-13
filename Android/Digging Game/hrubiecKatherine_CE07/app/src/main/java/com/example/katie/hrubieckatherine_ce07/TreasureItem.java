package com.example.katie.hrubieckatherine_ce07;

public class TreasureItem {

    private final String name;
    private final int worth;
    int x;
    int y;

    public TreasureItem(String name, int worth, int x, int y) {
        this.name = name;
        this.worth = worth;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public int getWorth() {
        return worth;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return name + " $" + worth;
    }
}
