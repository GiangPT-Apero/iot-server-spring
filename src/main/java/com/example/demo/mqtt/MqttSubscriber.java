package com.example.demo.mqtt;

import com.example.demo.model.SensorData;
import com.example.demo.service.SensorDataService;
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

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost: " + cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String payload = new String(message.getPayload());
        if (topic.equals("home/sensors/data")) {
            SensorData data = parseData(payload);
            sensorDataService.saveData(data);
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

    @PostConstruct
    public void subscribe() throws Exception {
        mqttClient.setCallback(this);
        mqttClient.subscribe("home/sensors/data");
    }
}
