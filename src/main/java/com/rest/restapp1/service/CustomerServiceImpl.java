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
    @Transactional //(readOnly = true)
    public Customers getCustomerbyID(int customerID) {
        Customers customer = customerDAO.getCustomerbyID(customerID);
        if (customer == null) {
            logger.error("No customer found for ID: {}", customerID);
            throw new CustomerNotFoundException("There is no customer for id: " + customerID);
        }
        return customer;
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
                        " is already associated with an account. " + " Try again with another email");
            }
            long getCustomerPhoneNumber = customer.getPhoneNumber();
            if(checkCustomerPhoneNumber(getCustomerPhoneNumber)){
                throw new CustomerNotFoundException("There is already an existing customer with this phone number: " +
                        customer.getPhoneNumber() + ". Try again with another.");
            }

            customerDAO.addNewCustomer(customer);
            return ResponseEntity.ok("Customer added!");
        }

    }

    @Override
    @Transactional
    public Customers updateCustomer(Customers customers) {
        int getCustomerId = customers.getId();
        if (customerExists(getCustomerId)) {
            if (isValidName(customers.getCustomerName()) &&
                    isValidEmail(customers.getCustomerEmail()) &&
                    isValidPhoneNumber(customers.getPhoneNumber()) &&
                    isValidAddress(customers.getCustomerAddress())){

                return customerDAO.updateCustomers(customers);
            } else {
                throw new CustomerNotFoundException("Kindly check your name, email, phone number, " +
                        "and address. Confirm that all are valid. All entries are required to proceed.");
            }
        } else {
            throw new CustomerNotFoundException("Customer with ID " + getCustomerId + " does not exist. "
            + "Kindly add as a new customer.");
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
        }
        }else{
            throw new CustomerNotFoundException(email + " is not a valid email address!");
        }
        return flag;
    }

    public boolean checkCustomerPhoneNumber(long phoneNumber) {
        if (isValidPhoneNumber(phoneNumber)){
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
            }}else {
            throw new CustomerNotFoundException(phoneNumber + " not valid!");

        }

        return false;
    }


    /*Since multiple individuals can live in the same address, we will avoid this verification.
    However, we may use it in the future for further validations.
    * */
    public boolean checkValidAddress(String address) {
        if (isValidAddress(address)){
            String query = "SELECT customer_address FROM customers WHERE customer_address = ?";
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, address);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return true; // address exists
                }
            } catch (SQLException e) {
                logger.error("Error checking if customer with phone number {} exists", address, e);
                throw new RuntimeException("Database query failed", e);
            }}else {
            throw new CustomerNotFoundException(address + " not a valid address!");

        }

        return false;
    }


    /*Since multiple individuals can have same name and last name, we will avoid this verification.
    However, we may use it in the future for further validations.
    * */
    public boolean check_forValidName(String name) {
        boolean flag = false;
        if(isValidName(name)){
            String query = "SELECT customer_name FROM customers WHERE customer_name =? ;";
            try (
                    Connection conn = dataSource.getConnection();
                    PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, name);
                // Set the customer ID to the query
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    flag = true;  // Returns true if the customer exists, false otherwise
                }
            } catch (SQLException e) {
                logger.error("Error in checking customer with email address exists {}", name, e);
            }
        }else{
            throw new CustomerNotFoundException(name + " is not valid!");
        }
        return flag;
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
