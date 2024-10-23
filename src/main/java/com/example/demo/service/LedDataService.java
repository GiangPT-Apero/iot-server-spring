package com.example.demo.service;

import com.example.demo.cache.ServerCache;
import com.example.demo.model.LedData;
import com.example.demo.model.LedState;
import com.example.demo.repository.LedDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private Sort getSortType(boolean isASC) {
        if (isASC) {
            return Sort.by(Sort.Direction.fromString("ASC"), "id");
        } else {
            return Sort.by(Sort.Direction.fromString("DESC"), "id");
        }
    }

    public void saveData(LedData ledData) {
        ledDataRepository.save(ledData);
    }

    public Page<LedData> getAllLedData(int page, int size, boolean isASC) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                getSortType(isASC)
        );
        return ledDataRepository.findAll(pageable);
    }

    // Tìm kiếm theo ledName
    public Page<LedData> searchByLedName(String ledName, int page, int size, boolean isASC) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                getSortType(isASC)
        );
        return ledDataRepository.findByLedName(ledName, pageable);
    }

    // Tìm kiếm theo action
    public Page<LedData> searchByAction(String action, int page, int size, boolean isASC) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                getSortType(isASC)
        );
        return ledDataRepository.findByAction(action, pageable);
    }
}
