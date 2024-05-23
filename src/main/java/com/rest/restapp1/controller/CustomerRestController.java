package com.rest.restapp1.controller;

import com.rest.restapp1.entity.Customers;
import com.rest.restapp1.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        System.out.println("I am testing!");
        return customerService.getAllCustomers();
    }

    @GetMapping("/customers/{customerID}")
    public Customers getCustomerByID(@PathVariable int customerID){
        Customers customer = customerService.getCustomerbyID(customerID);
        return customer;
    }
    @PostMapping("/customers")
    public ResponseEntity<?> addNewCustomer(@RequestBody Customers customers){
        customers.setId(0);
        ResponseEntity<?> customers1 = customerService.addNewCustomer(customers);
        return customers1;
    }

    @PutMapping("/customers")
    public  Customers updateCustomer(@RequestBody Customers requestBody){
        Customers customer =  customerService.updateCustomer(requestBody);
        return customer;
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable int id){
        customerService.deleteCustomer(id);
    }

}
