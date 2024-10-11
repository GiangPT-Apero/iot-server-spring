package com.example.demo.repository;

import com.example.demo.model.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
}
