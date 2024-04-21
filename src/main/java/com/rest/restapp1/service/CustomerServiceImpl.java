package com.rest.restapp1.service;

import com.rest.restapp1.DAO.CustomerDAO;
import com.rest.restapp1.Exceptions.CustomerNotFoundException;
import com.rest.restapp1.Exceptions.ErrorMessage;
import com.rest.restapp1.entity.Customers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDAO customerDAO;
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);


    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;

    }

    @Override
    public List<Customers> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    @Override
    @Transactional(readOnly = true)
    //Not sure if I did this right though? Not sure if I retrieved the customerId right.
    public Customers getCustomerbyID(int customerID) {
        Customers customer = customerDAO.getCustomerbyID(customerID);
        if (customer == null) {
            logger.error("No customer found for ID: {}", customerID);
            throw new CustomerNotFoundException("There no customer for id: " + customerID);
        }
        return customerDAO.getCustomerbyID(customerID);
    }
}
