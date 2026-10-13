package com.retailbilling.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retailbilling.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {
}