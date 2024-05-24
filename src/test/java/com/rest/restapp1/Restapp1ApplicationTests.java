package com.rest.restapp1;

import com.rest.restapp1.DAO.CustomerDAO;
import com.rest.restapp1.entity.Customers;
import com.rest.restapp1.service.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Restapp1ApplicationTests {
	@Mock
	CustomerDAO customerDAO;
	@Mock
	DataSource dataSource;
	@InjectMocks
	CustomerServiceImpl customerServiceImpl;


	private Customers customers;
	@BeforeEach
	void addUp (){
		customers = new Customers();
		customers.setId(33);
		customers.setCustomerEmail("test@yahoo.com");
		customers.setCustomerName("John Doe");
		customers.setPhoneNumber(1234567890);
		customers.setCustomerAddress("123 twisting street Ave.");
	}

	@Test
	void contextLoads() {
	}

	@Test
	void dummyTest(){
		when(customerDAO.getCustomerbyID(33)).thenReturn(customers);
		Customers test1 = customerServiceImpl.getCustomerbyID(33);
		assertNotNull(test1);
		assertEquals(33, test1.getId());
		verify(customerDAO, times(1)).getCustomerbyID(33);


	}

}
