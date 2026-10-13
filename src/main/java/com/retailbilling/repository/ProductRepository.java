package com.retailbilling.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retailbilling.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}