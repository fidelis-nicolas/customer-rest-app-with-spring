package com.rest.restapp1.service;

import com.rest.restapp1.entity.Customers;

import java.util.List;

public interface CustomerService {
    List<Customers> getAllCustomers();
    Customers getCustomerbyID(int id);
    Customers addNewCustomer(Customers customers);
    void deleteCustomer(int customerId);

    void updateCustomer(int customerId, Customers requestBody);
}
