package com.example.demo.controller;

import com.example.demo.model.LedData;
import com.example.demo.model.LedState;
import com.example.demo.service.LedDataService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/led")
public class LedController {
    @Autowired
    private MqttClient mqttClient;

    private final LedDataService ledDataService;

    @Autowired
    public LedController(LedDataService ledDataService) {
        this.ledDataService = ledDataService;
    }

    public void blinkLed(Boolean isBlink) {
        try {
            // Tạo MqttMessage từ chuỗi command
            String test = "OFF";
            if (isBlink) {
                test = "ON";
            }
            MqttMessage message = new MqttMessage(test.getBytes());
            message.setQos(1); // Đặt QoS nếu cần

            // Xác định chủ đề dựa trên ledId
            String topic = "zang/commands/led4";

            ledDataService.saveData(new LedData("led4", test));
            System.out.println("GiangPT pub " + topic + ": " + test);
            mqttClient.publish(topic, message);
        } catch (MqttException ignored) {

        }
    }

    @PostMapping("/toggle/{ledId}")
    public ResponseEntity<String> toggleLed(@PathVariable String ledId, @RequestBody String command) {
        try {
            // Tạo MqttMessage từ chuỗi command
            String test = command.replaceAll("\"", "");
            MqttMessage message = new MqttMessage(test.getBytes());
            message.setQos(1); // Đặt QoS nếu cần

            // Xác định chủ đề dựa trên ledId
            String topic = "zang/commands/" + ledId;

            ledDataService.saveData(new LedData(ledId, test));
            System.out.println("GiangPT pub " + topic + ": " + test);
            mqttClient.publish(topic, message);
            return ResponseEntity.ok("Command sent to " + ledId + ": " + command);
        } catch (MqttException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/data")
    public Page<LedData> getAllData(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "1") int sort,
                                    @RequestParam(defaultValue = "id") String params) {
        boolean isASC = sort == 1;
        return ledDataService.getAllLedData(page, size, isASC, params);
    }

    @GetMapping("/ledName/{ledName}")
    public Page<LedData> searchByLedName(@PathVariable String ledName,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "1") int sort) {
        boolean isASC = sort == 1;
        return ledDataService.searchByLedName(ledName, page, size, isASC);
    }

    // API tìm kiếm theo action
    @GetMapping("/action/{action}")
    public Page<LedData> searchByAction(@PathVariable String action,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "1") int sort) {
        System.out.println("GiangPT " + action);
        boolean isASC = sort == 1;
        return ledDataService.searchByAction(action, page, size, isASC);
    }

    @GetMapping("/time")
    public Page<LedData> getByTimestamp(@RequestParam(defaultValue = "") String timestamp,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "1") int sort) {
        boolean isASC = sort == 1;
        System.out.println("GiangPT led" + timestamp);
        return ledDataService.findByTimestampContaining(timestamp, page, size, isASC);
    }

    @GetMapping("/state")
    private LedState getStateLed() {
        return ledDataService.getLedState();
    }
}

