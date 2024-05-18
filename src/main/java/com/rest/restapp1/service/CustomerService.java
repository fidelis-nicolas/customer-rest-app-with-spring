package com.rest.restapp1.service;

import com.rest.restapp1.entity.Customers;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerService {
    List<Customers> getAllCustomers();
    Customers getCustomerbyID(int id);

    Customers getCustomerbyPhone(long id);
    ResponseEntity<?> addNewCustomer(Customers customers);
    void deleteCustomer(int customerId);
    Customers updateCustomers (Customers customers);

    Customers updateCustomer(Customers customers);
}
