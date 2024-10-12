package com.example.demo.service;


import com.example.demo.model.SensorData;
import com.example.demo.repository.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SensorDataService {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    public void saveData(SensorData data) {
        sensorDataRepository.save(data);
    }

    // Phương thức tìm kiếm theo nhiệt độ với phân trang
    public Page<SensorData> findByTemperature(float temperature, int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size
        );
        return sensorDataRepository.findByTemperature(temperature, pageable);
    }

    // Phương thức tìm kiếm theo độ ẩm với phân trang
    public Page<SensorData> findByHumidity(float humidity, int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size
        );
        return sensorDataRepository.findByHumidity(humidity, pageable);
    }

    // Phương thức tìm kiếm theo ánh sáng với phân trang
    public Page<SensorData> findByLight(float light, int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size
        );
        return sensorDataRepository.findByLight(light, pageable);
    }

    // Lấy dữ liệu theo ID
    public Optional<SensorData> getDataById(Long id) {
        return sensorDataRepository.findById(id);
    }
}



