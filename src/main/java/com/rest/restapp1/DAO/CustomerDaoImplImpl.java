package com.rest.restapp1.DAO;

import com.rest.restapp1.entity.Customers;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CustomerDaoImplImpl implements CustomerDAO{
    EntityManager entityManager;

    @Autowired
    public CustomerDaoImplImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Override
    public List<Customers> getAllCustomers() {
        TypedQuery<Customers> query = entityManager.createQuery("From Customers", Customers.class);
        List<Customers> dbCustomers = query.getResultList();
        return dbCustomers;
    }

    @Override
    public Customers getCustomerbyID(int id) {
        Customers customer = entityManager.find(Customers.class, id);
        return customer;
    }

    @Override
    public Customers addNewCustomer(Customers customers) {
        Customers customers1 = entityManager.merge(customers);
        return customers1;
    }

    @Override
    public void deleteCustomer(int id) {
        Customers customers = entityManager.find(Customers.class, id);
        entityManager.remove(customers);
    }
}
