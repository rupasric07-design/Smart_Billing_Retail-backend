package com.retailbilling.controller;

import com.retailbilling.entity.Bill;
import com.retailbilling.service.BillService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "http://localhost:5173")
public class SalesController {

    private final BillService billService;

    public SalesController(BillService billService) {
        this.billService = billService;
    }

    // Get all sales
    @GetMapping
    public List<Bill> getAllSales() {
        return billService.getAllBills();
    }

    // Get a sale by ID
    @GetMapping("/{id}")
    public Bill getSaleById(@PathVariable Long id) {
        return billService.getBillById(id);
    }
}