package com.retailbilling.service;

import com.retailbilling.entity.Bill;
import com.retailbilling.entity.Product;
import com.retailbilling.repository.BillRepository;
import com.retailbilling.repository.ProductRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final ProductRepository productRepository;

    public BillService(
            BillRepository billRepository,
            ProductRepository productRepository) {
        this.billRepository = billRepository;
        this.productRepository = productRepository;
    }

    // Get all bills
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    // Create bill and update stock
    public Bill createBill(Bill bill) {

        if (bill.getQuantity() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Quantity must be greater than zero");
        }

        if (bill.getProductName() == null ||
                bill.getProductName().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Product name is required");
        }

        Product product = productRepository.findAll().stream()
                .filter(p -> p.getName().equalsIgnoreCase(
                        bill.getProductName()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found"));

        if (product.getQuantity() < bill.getQuantity()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Insufficient stock");
        }

        bill.setCustomerName(
                bill.getCustomerName() == null
                        ? "Walk-in Customer"
                        : bill.getCustomerName());

        bill.setProductName(product.getName());
        bill.setUnitPrice(product.getPrice());
        bill.setTotalAmount(product.getPrice() * bill.getQuantity());

        product.setQuantity(product.getQuantity() - bill.getQuantity());
        productRepository.save(product);

        bill.setId(null);

        return billRepository.save(bill);
    }

    // Get bill by ID
    public Bill getBillById(Long id) {
        return billRepository.findById(id).orElse(null);
    }
}