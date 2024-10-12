package com.example.demo.repository;

import com.example.demo.model.SensorData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long>, PagingAndSortingRepository<SensorData, Long> {

    // Tìm kiếm theo nhiệt độ
    Page<SensorData> findByTemperature(float temperature, Pageable pageable);

    // Tìm kiếm theo độ ẩm
    Page<SensorData> findByHumidity(float humidity, Pageable pageable);

    // Tìm kiếm theo ánh sáng
    Page<SensorData> findByLight(float light, Pageable pageable);
}


