package com.rest.restapp1.service;

import com.rest.restapp1.DAO.CustomerDAO;
import com.rest.restapp1.entity.Customers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerServiceImpl implements CustomerService{
    CustomerDAO customerDAO;

    @Autowired public CustomerServiceImpl(CustomerDAO customerDAO){
        this.customerDAO = customerDAO;
    }
    @Override
    public List<Customers> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    @Override
    public Customers getCustomerbyID(int id) {
        return customerDAO.getCustomerbyID(id);
    }
}
