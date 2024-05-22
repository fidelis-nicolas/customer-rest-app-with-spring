package com.rest.restapp1.DAO;

import com.rest.restapp1.entity.Customers;

import java.util.List;

public interface CustomerDAO {
    List<Customers> getAllCustomers();

    Customers getCustomerbyID(int id);
    Customers addNewCustomer(Customers customers);
    void deleteCustomer(int customerId);
    Customers updateCustomers(Customers customers);
}
