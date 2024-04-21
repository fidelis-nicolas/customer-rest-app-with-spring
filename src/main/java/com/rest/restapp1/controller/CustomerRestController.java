package com.rest.restapp1.controller;

import com.rest.restapp1.DAO.CustomerDAO;
import com.rest.restapp1.DAO.CustomerDaoImplImpl;
import com.rest.restapp1.entity.Customers;
import com.rest.restapp1.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
