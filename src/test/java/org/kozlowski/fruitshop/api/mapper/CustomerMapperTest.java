package org.kozlowski.fruitshop.api.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kozlowski.fruitshop.api.model.CustomerDTO;
import org.kozlowski.fruitshop.domain.Customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerMapperTest {

    public static final String FIRST_NAME = "Andrew";
    public static final String LAST_NAME = "Weston";
    CustomerMapper customerMapper;

    @BeforeEach
    void setUp() {

        customerMapper = CustomerMapper.INSTANCE;
    }

    @Test
    void customerToCustomerDTO() {
        //given
        Customer customer = new Customer();
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        //when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        //then
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
    }
}