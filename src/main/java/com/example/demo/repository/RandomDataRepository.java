package com.example.demo.repository;

import com.example.demo.model.LedData;
import com.example.demo.model.RandomData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RandomDataRepository extends JpaRepository<RandomData, Long>, PagingAndSortingRepository<RandomData, Long> {

}