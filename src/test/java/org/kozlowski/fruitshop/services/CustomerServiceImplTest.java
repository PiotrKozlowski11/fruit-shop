package org.kozlowski.fruitshop.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kozlowski.fruitshop.api.mapper.CustomerMapper;
import org.kozlowski.fruitshop.api.model.CustomerDTO;
import org.kozlowski.fruitshop.domain.Customer;
import org.kozlowski.fruitshop.respositories.CustomerRepository;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CustomerServiceImplTest {

    public static final String FIRST_NAME = "Andrew";
    public static final String LAST_NAME = "Weston";
    public static final long ID = 2L;
    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void testGetAllCustomers() {

        //given
        List<Customer> customerList = Arrays.asList(new Customer(), new Customer(), new Customer());

        when(customerRepository.findAll()).thenReturn(customerList);

        //when
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        //then

        assertEquals(3, customerDTOS.size());


    }

    @Test
    void testGetCustomerById() {
        //given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        //when
        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        //then
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
    }


    @Test
    void createNewCustomer() {
        //given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());

        when(customerRepository.save(any())).thenReturn(customer);

        //when
        CustomerDTO customerReturned = customerService.createNewCustomer(customerDTO);


        //then
        assertEquals(customerDTO.getFirstName(), customerReturned.getFirstName());
        assertEquals("/api/customer/" + ID, customerReturned.getCustomerUrl());


    }

    @Test
    void saveCustomerByDTO() {
        //given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());

        when(customerRepository.save(any())).thenReturn(customer);

        //when
        CustomerDTO customerReturned = customerService.saveCustomerByDTO(ID,customerDTO);


        //then
        assertEquals(customerDTO.getFirstName(), customerReturned.getFirstName());
        assertEquals("/api/customer/" + ID, customerReturned.getCustomerUrl());
    }
}