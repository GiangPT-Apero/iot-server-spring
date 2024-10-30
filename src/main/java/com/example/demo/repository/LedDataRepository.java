package com.example.demo.repository;

import com.example.demo.model.LedData;
import com.example.demo.model.SensorData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LedDataRepository extends JpaRepository<LedData, Long>, PagingAndSortingRepository<LedData, Long> {

    // Tìm kiếm theo ledName
    Page<LedData> findByLedName(String ledName, Pageable pageable);

    // Tìm kiếm theo action
    Page<LedData> findByAction(String action, Pageable pageable);

    // Tìm kiếm theo chuỗi trong timeStamp
    Page<LedData> findByTimeStampContaining(String timeStampPart, Pageable pageable);
}
