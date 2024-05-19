package com.rest.restapp1;

import com.rest.restapp1.DAO.CustomerDAO;
import com.rest.restapp1.entity.Customers;
import com.rest.restapp1.service.CustomerServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class Restapp1ApplicationTests {
	@Test
	void contextLoads(){

	}
	@Mock
	private CustomerDAO customerDAO;
	@Mock
	private CustomerServiceImpl customerService;
	@Mock
	private DataSource dataSource;

	private Customers customers;

	@BeforeEach
	void createCustomer(){
		customers = new Customers();
		customers.setId(1);
		customers.setCustomerEmail("fidelis@email.com");
		customers.setCustomerAddress("Lagos");
		customers.setCustomerName("Gideon");
		customers.setPhoneNumber(12345678l);
	}

	@Test
	void demoTest(){

	}

//	@Test
//	public void testGetAllCustomersAsList(){
//		List<Customers> customersList = Arrays.asList(customers);
//		when(customerDAO.getAllCustomers()).thenReturn(customersList);
//		List<Customers> test = customerService.getAllCustomers();
//
//		assertNotNull(test);
//		assertEquals(1, test.size());
//		verify(customerDAO, times(1)).getAllCustomers();
//	}

}
