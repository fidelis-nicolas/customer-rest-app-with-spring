package com.rest.restapp1.controller;

import com.rest.restapp1.DAO.CustomerDAO;
import com.rest.restapp1.DAO.CustomerDaoImplImpl;
import com.rest.restapp1.entity.Customers;
import com.rest.restapp1.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

   CustomerService customerService;
    @Autowired
    public CustomerRestController(CustomerService customerService){
        this.customerService = customerService;
    }
    @GetMapping("/customers")
    public List<Customers> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/customers/{customerID}")
    public Customers getCustomerByID(@PathVariable int customerID){
        Customers customer = customerService.getCustomerbyID(customerID);
        return customer;
    }
    @PostMapping("/customers")
    public Customers addNewCustomer(@RequestBody Customers customers){
        customers.setId(0);
        Customers customers1 = customerService.addNewCustomer(customers);
        return customers1;
    }

    @PutMapping("/customers/{customerID}")
    public void updateCustomer(@PathVariable int customerID, @RequestBody Customers requestBody){
        customerService.updateCustomer(customerID, requestBody);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable int id){
        customerService.deleteCustomer(id);
    }

}
