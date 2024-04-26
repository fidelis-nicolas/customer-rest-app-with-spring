package com.rest.restapp1.service;

import com.rest.restapp1.DAO.CustomerDAO;
import com.rest.restapp1.Exceptions.CustomerNotFoundException;
import com.rest.restapp1.entity.Customers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Override
    @Transactional
    public Customers addNewCustomer(Customers customers) {
        return customerDAO.addNewCustomer(customers);
    }

    @Override
    public void updateCustomer(int customerId, Customers customer) {
        Customers updatedCustomer =  customerDAO.getCustomerbyID(customerId);
        if(updatedCustomer == null){
            throw new CustomerNotFoundException("There is no customer with id: " + customerId);
        }else{
            if (isValidName(updatedCustomer.getCustomerName())) {
                updatedCustomer.setCustomerName(customer.getCustomerName());
            }
            if (isValidEmail(updatedCustomer.getCustomerEmail())) {
                updatedCustomer.setCustomerEmail(customer.getCustomerEmail());
            }
            if (isValidPhoneNumber(updatedCustomer.getPhoneNumber())) {
                updatedCustomer.setPhoneNumber(customer.getPhoneNumber());
            }
            if (isValidAddress(updatedCustomer.getCustomerAddress())) {
                updatedCustomer.setCustomerAddress(customer.getCustomerAddress());
            }
            ResponseEntity.ok("Customer updated!");
        }
    }

    @Override
    @Transactional
    public void deleteCustomer(int customerId) {
        Customers customer =  customerDAO.getCustomerbyID(customerId);
        if(null != customer){
            customerDAO.deleteCustomer(customerId);
            ResponseEntity.ok("Customer Deleted");
        }else{
            throw new CustomerNotFoundException("There is no customer with id: " + customerId);
        }
    }

    private boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }


    private boolean isValidPhoneNumber(long phoneNumber) {
       //Validate based on length or pattern
        // Always adjust regex as necessary for your needs
        return Long.toString(phoneNumber).matches("\\d{10}");
    }

    private boolean isValidAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            return false;
        }

        // Optionally, check for special characters that might not be allowed
        return address.matches("[\\w\\s.,'-]*");
    }


}
