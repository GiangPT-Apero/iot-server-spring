package com.example.demo.model;


import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "led_data")
public class LedData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String ledName;

    @NotNull
    private String action;

    @NotNull
    private String timeStamp;

    public LedData() {
    }

    public LedData(String ledName, String action) {
        this.ledName = ledName;
        this.action = action;
        this.timeStamp = generateCurrentTimestamp();
    }

    private String generateCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        return now.format(formatter);
    }

    public String getLedName() {
        return ledName;
    }

    public void setLedName(String ledName) {
        this.ledName = ledName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
