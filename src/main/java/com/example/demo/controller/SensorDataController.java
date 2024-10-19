package com.example.demo.controller;

import com.example.demo.model.SensorData;
import com.example.demo.service.SensorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensor-data")
public class SensorDataController {

    private final SensorDataService sensorDataService;

    @Autowired
    public SensorDataController(SensorDataService sensorDataService) {
        this.sensorDataService = sensorDataService;
    }

    @GetMapping("/newest")
    public SensorData getLatestData() {
        return sensorDataService.getLatestSensorData();
    }
    // Tìm kiếm theo nhiệt độ với phân trang
    @GetMapping("/temperature/{temperature}")
    public Page<SensorData> getByTemperature(@PathVariable float temperature,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        return sensorDataService.findByTemperature(temperature, page, size);
    }

    // Tìm kiếm theo độ ẩm với phân trang
    @GetMapping("/humidity/{humidity}")
    public Page<SensorData> getByHumidity(@PathVariable float humidity,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return sensorDataService.findByHumidity(humidity, page, size);
    }

    // Tìm kiếm theo ánh sáng với phân trang
    @GetMapping("/light/{light}")
    public Page<SensorData> getByLight(@PathVariable float light,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return sensorDataService.findByLight(light, page, size);
    }

    @GetMapping("/all")
    public Page<SensorData> getAllData(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return sensorDataService.getAllSensorData(page, size);
    }
}



