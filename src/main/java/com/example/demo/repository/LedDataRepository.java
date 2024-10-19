package com.example.demo.repository;

import com.example.demo.model.LedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LedDataRepository extends JpaRepository<LedData, Long>, PagingAndSortingRepository<LedData, Long> {

}
