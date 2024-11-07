package com.example.demo.mqtt;

import com.example.demo.cache.ServerCache;
import com.example.demo.controller.LedController;
import com.example.demo.model.LedState;
import com.example.demo.model.RandomData;
import com.example.demo.model.SensorData;
import com.example.demo.service.SensorDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqttSubscriber implements MqttCallback {
    @Autowired
    private SensorDataService sensorDataService;

    @Autowired
    private MqttClient mqttClient;

    @Autowired
    private LedController ledController;

    @Autowired
    private ServerCache serverDataCache;

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost: " + cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String payload = new String(message.getPayload());
        System.out.println("GiangPT" + topic + ": " + payload);
        if (topic.equals("zang/sensors/data")) {
            SensorData data = parseDataJson(payload);
            serverDataCache.putSensorData(ServerCache.idNewest, data);
            sensorDataService.saveData(data);
        }
        if (topic.equals("zang/led/status")) {
            handleLedStatus(payload);
        }
        if (topic.equals("zang/sensor/random")) {
            RandomData random = parseRandomData(payload);
            if (random != null && random.getValue() > 80) {
                ledController.blinkLed(true);
            }
            serverDataCache.putRandomData(random);
        }
    }

    private void handleLedStatus(String payload) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Chuyển đổi JSON thành đối tượng LedState
            LedState ledState = objectMapper.readValue(payload, LedState.class);
            serverDataCache.putLedState(ServerCache.idLedState, ledState);
            System.out.println("LED States updated: " + ledState);
        } catch (Exception e) {
            // Xử lý lỗi
            System.err.println("Error parsing LED status: " + e.getMessage());
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    private SensorData parseData(String payload) {
        String[] parts = payload.replace("{", "").replace("}", "").split(",");
        SensorData data = new SensorData();
        for (String part : parts) {
            String[] keyValue = part.split(":");
            switch (keyValue[0].trim()) {
                case "\"temperature\"":
                    data.setTemperature(Float.parseFloat(keyValue[1]));
                    break;
                case "\"humidity\"":
                    data.setHumidity(Float.parseFloat(keyValue[1]));
                    break;
                case "\"light\"":
                    data.setLight(Float.parseFloat(keyValue[1]));
                    break;
            }
        }
        return data;
    }

    private SensorData parseDataJson(String payload) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(payload, SensorData.class);
        } catch (Exception e) {
            // Xử lý lỗi
            return null;
        }
    }

    private RandomData parseRandomData(String payload) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(payload, RandomData.class);
        }  catch (Exception e) {
            // Xử lý lỗi
            return null;
        }
    }
    @PostConstruct
    public void subscribe() throws Exception {
        mqttClient.setCallback(this);
        mqttClient.subscribe("zang/sensors/data");
        mqttClient.subscribe("zang/led/status");
        mqttClient.subscribe("zang/sensor/random");
    }
}
