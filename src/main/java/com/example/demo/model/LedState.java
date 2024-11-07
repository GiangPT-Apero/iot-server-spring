package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LedState {
    public String led1;
    public String led2;
    public String led3;
    public String led4;

    public static String ON = "ON";
    public static String OFF = "OFF";

    @JsonCreator
    public LedState(@JsonProperty("led1") String led1,
                    @JsonProperty("led2") String led2,
                    @JsonProperty("led3") String led3,
                    @JsonProperty("Led4") String led4) {
        this.led1 = led1;
        this.led2 = led2;
        this.led3 = led3;
        this.led4 = led4;
    }
}
