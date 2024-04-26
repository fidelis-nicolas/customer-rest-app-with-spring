package com.rest.restapp1.DAO;

import com.rest.restapp1.entity.Customers;

import java.util.List;

public interface CustomerDAO {
    List<Customers> getAllCustomers();

    Customers getCustomerbyID(int id);

    Customers addNewCustomer(Customers customers);
    void deleteCustomer(int customerId);

    void updateCustomer(int customerId, String customerName, String customerEmail, long phoneNumber, String customerAddress);
}
