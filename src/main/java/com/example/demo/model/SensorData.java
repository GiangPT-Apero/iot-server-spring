package com.example.demo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "sensor_data") // Tên bảng trong cơ sở dữ liệu
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private float temperature;

    @NotNull
    private float humidity;

    @NotNull
    private float light;

    @NotNull
    private String timestamp; // Thuộc tính thời gian dưới dạng chuỗi

    // Constructor không tham số
    public SensorData() {}

    // Constructor với tham số
    @JsonCreator
    public SensorData(@JsonProperty("temperature") float temperature,
                      @JsonProperty("humidity") float humidity,
                      @JsonProperty("light") float light) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.light = light;
        this.timestamp = generateCurrentTimestamp(); // Tự động thêm thời gian hiện tại
    }

    // Phương thức tạo chuỗi thời gian hiện tại theo định dạng yêu cầu
    private String generateCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        return now.format(formatter);
    }

    // Getters và Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public float getTemperature() { return temperature; }
    public void setTemperature(float temperature) { this.temperature = temperature; }
    public float getHumidity() { return humidity; }
    public void setHumidity(float humidity) { this.humidity = humidity; }
    public float getLight() { return light; }
    public void setLight(float light) { this.light = light; }
    public String getTimestamp() { return timestamp; } // Getter cho timestamp
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; } // Setter cho timestamp

    // Override toString
    @Override
    public String toString() {
        return "SensorData{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", light=" + light +
                ", timestamp='" + timestamp + '\'' + // Thêm timestamp vào toString
                '}';
    }

    // Override equals và hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SensorData)) return false;
        SensorData that = (SensorData) o;
        return Float.compare(that.temperature, temperature) == 0 &&
                Float.compare(that.humidity, humidity) == 0 &&
                Float.compare(that.light, light) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(timestamp, that.timestamp); // So sánh timestamp
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, temperature, humidity, light, timestamp); // Thêm timestamp vào hashCode
    }
}
