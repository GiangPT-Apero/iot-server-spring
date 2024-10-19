package com.example.demo.cache;

import com.example.demo.model.LedState;
import com.example.demo.model.SensorData;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class ServerCache {

    public static String idNewest = "newestData";
    public static String idLedState = "led_state";
    private final ConcurrentHashMap<String, SensorData> sensorDataMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, LedState> ledStateMap = new ConcurrentHashMap<>();

    // Lưu dữ liệu mới vào cache
    public void putSensorData(String sensorId, SensorData data) {
        sensorDataMap.put(sensorId, data);
    }

    // Lấy dữ liệu từ cache
    public SensorData getSensorData(String sensorId) {
        return sensorDataMap.get(sensorId);
    }

    public void putLedState(String ledStateId, LedState led) {
        ledStateMap.put(ledStateId, led);
    }

    public LedState getLedState(String ledStateId) {
        return ledStateMap.get(ledStateId);
    }

}
