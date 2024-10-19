package com.example.demo.model;

public class LedState {
    public String led1;
    public String led2;
    public String led3;

    public static String ON = "ON";
    public static String OFF = "OFF";

    public LedState(String led1, String led2, String led3) {
        this.led1 = led1;
        this.led2 = led2;
        this.led3 = led3;
    }
}
