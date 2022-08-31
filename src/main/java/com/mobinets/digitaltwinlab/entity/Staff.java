package com.mobinets.digitaltwinlab.entity;

public class Staff {

    private long campusNum;
    private String name;
    private int type;

    public long getCampusNum() {
        return campusNum;
    }

    public void setCampusNum(long campusNum) {
        this.campusNum = campusNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "campusNum=" + campusNum +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
