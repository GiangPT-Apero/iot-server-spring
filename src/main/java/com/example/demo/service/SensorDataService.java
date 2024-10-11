package com.example.demo.service;


import com.example.demo.model.SensorData;
import com.example.demo.repository.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorDataService {
    @Autowired
    private SensorDataRepository repository;

    public void saveData(SensorData data) {
        repository.save(data);
    }
}

