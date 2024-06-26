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

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final DataSource dataSource;


    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO, DataSource dataSource) {
        this.customerDAO = customerDAO;
        this.dataSource = dataSource;

    }

    @Override
    public List<Customers> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    @Override
    @Transactional(readOnly = true)
    public Customers getCustomerbyID(int customerID) {
        Customers customer = customerDAO.getCustomerbyID(customerID);
        if (customer == null) {
            logger.error("No customer found for ID: {}", customerID);
            throw new CustomerNotFoundException("There no customer for id: " + customerID);
        }
        return customerDAO.getCustomerbyID(customerID);
    }

    @Override
    public Customers getCustomerbyPhone(long id) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<?> addNewCustomer(Customers customer) {
        int getCustomerId = customer.getId();
        if(customerExists(getCustomerId)){
            throw new CustomerNotFoundException("There is already an existing customer with this id: " +
                    customer.getId() + ". Try again with another.");
        } else{
            String getCustomerEmail = customer.getCustomerEmail();
            if(customerEmailCheck(getCustomerEmail)){
                throw new CustomerNotFoundException("This email address - " + customer.getCustomerEmail() +
                        " is already associated with an account. " + ". Try again with another email");
            }
            long getCustomerPhoneNumber = customer.getPhoneNumber();
            if(checkCustomerPhoneNumber(getCustomerPhoneNumber)){
                throw new CustomerNotFoundException("There is already an existing customer with this id: " +
                        customer.getPhoneNumber() + ". Try again with another.");
            }
            customerDAO.addNewCustomer(customer);
            return ResponseEntity.ok("Customer added!");
        }

    }

//    @Override
//    @Transactional
//    public ResponseEntity<?> updateCustomer(int customerId, Customers customer) {
//        Customers updatedCustomer =  customerDAO.getCustomerbyID(customerId);
//        if(updatedCustomer == null){
//            throw new CustomerNotFoundException("There is no customer with id: " + customerId);
//        }else{
//            if (isValidName(updatedCustomer.getCustomerName())) {
//                updatedCustomer.setCustomerName(customer.getCustomerName());
//            }
//            if (isValidEmail(updatedCustomer.getCustomerEmail())) {
//                updatedCustomer.setCustomerEmail(customer.getCustomerEmail());
//            }
//            if (isValidPhoneNumber(updatedCustomer.getPhoneNumber())) {
//                updatedCustomer.setPhoneNumber(customer.getPhoneNumber());
//            }
//            if (isValidAddress(updatedCustomer.getCustomerAddress())) {
//                updatedCustomer.setCustomerAddress(customer.getCustomerAddress());
//            }
//            String getCustomerEmail = customer.getCustomerEmail();
//            if(customerEmailCheck(getCustomerEmail)){
//                throw new CustomerNotFoundException("This email address - " + customer.getCustomerEmail() +
//                        " is already associated with an account. " + ". Try again with another email");
//            }
//            long getCustomerPhoneNumber = customer.getPhoneNumber();
//            if(checkCustomerPhoneNumber(getCustomerPhoneNumber)){
//                throw new CustomerNotFoundException("There is already an existing customer with this id: " +
//                        customer.getPhoneNumber() + ". Try again with another.");
//            }
//           customerDAO.updateCustomer();
//            return ResponseEntity.ok("Customer updated!");
//        }
//    }

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

    @Override
    public Customers updateCustomer(Customers customers) {
        return customerDAO.updateCustomers(customers);
    }

    public boolean customerExists(int customerId) {
        boolean flag = false;
        String query = "SELECT * FROM customers WHERE id =? ;";
        try (
                Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, customerId);
            // Set the customer ID to the query
            ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    flag = true;  // Returns true if the customer exists, false otherwise
            }
        } catch (SQLException e) {
            logger.error("Error checking if customer with ID {} exists ", customerId, e);
        }
       return flag;
    }

    public boolean customerEmailCheck(String email) {
        boolean flag = false;
        if(isValidEmail(email)){
        String query = "SELECT customer_email FROM customers WHERE customer_email =? ;";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            // Set the customer ID to the query
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                flag = true;  // Returns true if the customer exists, false otherwise
            }
        } catch (SQLException e) {
            logger.error("Error in checking customer with email address exists {}", email, e);
        }}else{
            throw new CustomerNotFoundException(email + " is not valid.");
        }
        return flag;
    }

    public boolean checkCustomerPhoneNumber(long phoneNumber) {
        String query = "SELECT phone_number FROM customers WHERE phone_number = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, phoneNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true; // Customer exists
            }
        } catch (SQLException e) {
            logger.error("Error checking if customer with phone number {} exists", phoneNumber, e);
            throw new RuntimeException("Database query failed", e);
        }
        return false;
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
