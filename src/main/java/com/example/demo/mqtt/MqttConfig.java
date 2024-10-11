package com.example.demo.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

    @Bean
    public MqttClient mqttClient() throws Exception {
        String broker = "tcp://localhost:1883"; // Địa chỉ của broker
        String clientId = "mqtt-client";
        MemoryPersistence persistence = new MemoryPersistence();

        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false); // Giữ lại phiên để nhận các tin nhắn bị bỏ lỡ
        options.setAutomaticReconnect(true); // Tự động kết nối lại
        options.setConnectionTimeout(100); // Thời gian timeout khi kết nối

        MqttClient client = new MqttClient(broker, clientId, persistence);
        client.connect(options);
        return client;
    }
}
