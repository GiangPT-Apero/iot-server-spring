package com.example.demo.service;

import com.example.demo.cache.ServerCache;
import com.example.demo.model.LedData;
import com.example.demo.model.LedState;
import com.example.demo.model.SensorData;
import com.example.demo.repository.LedDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LedDataService {
    @Autowired
    private LedDataRepository ledDataRepository;

    @Autowired
    private ServerCache serverDataCache;

    // Lấy dữ liệu từ cache
    public LedState getLedState() {
        return serverDataCache.getLedState(ServerCache.idLedState);
    }

    public void saveData(LedData ledData) {
        ledDataRepository.save(ledData);
    }

    public Page<LedData> getAllLedData(int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size
        );
        return ledDataRepository.findAll(pageable);
    }
}
