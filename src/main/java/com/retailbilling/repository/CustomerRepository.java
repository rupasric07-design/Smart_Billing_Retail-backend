package com.retailbilling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.retailbilling.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}