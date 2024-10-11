package com.example.demo.controller;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/led")
public class LedController {
    @Autowired
    private MqttClient mqttClient;

    @PostMapping("/toggle/{ledId}")
    public ResponseEntity<String> toggleLed(@PathVariable String ledId, @RequestBody String command) {
        try {
            // Tạo MqttMessage từ chuỗi command
            MqttMessage message = new MqttMessage(command.getBytes());
            message.setQos(1); // Đặt QoS nếu cần

            // Xác định chủ đề dựa trên ledId
            String topic = "home/commands/" + ledId;

            mqttClient.publish(topic, message);
            return ResponseEntity.ok("Command sent to " + ledId + ": " + command);
        } catch (MqttException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}

