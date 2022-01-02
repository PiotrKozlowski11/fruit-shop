package org.kozlowski.fruitshop.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kozlowski.fruitshop.api.mapper.CustomerMapper;
import org.kozlowski.fruitshop.api.model.CustomerDTO;
import org.kozlowski.fruitshop.bootstrap.Bootstrap;
import org.kozlowski.fruitshop.domain.Customer;
import org.kozlowski.fruitshop.respositories.CategoryRepository;
import org.kozlowski.fruitshop.respositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerServiceImplITTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    CustomerService customerService;

    @BeforeAll
    void setUp() throws Exception {
        System.out.println("Loading Customer Data");
        System.out.println(customerRepository.findAll().size());

        //setup data for testing
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run(); //load data

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    public void patchCustomerUpdateFirstName() {
        String updatedFirstName = "UpdatedFirstName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getById(id);
        assertNotNull(originalCustomer);

        //save original first name
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(updatedFirstName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedFirstName, updatedCustomer.getFirstName());
        assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstName())));
        assertThat(originalLastName, equalTo(updatedCustomer.getLastName()));

    }

    @Test
    public void patchCustomerUpdateLastName() {
        String updatedLastName = "UpdatedLastName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getById(id);
        assertNotNull(originalCustomer);

        //save original first/last name
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName(updatedLastName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedLastName, updatedCustomer.getLastName());
        assertThat(originalFirstName, equalTo(updatedCustomer.getFirstName()));
        assertThat(originalLastName, not(equalTo(updatedCustomer.getLastName())));
    }

    private Long getCustomerIdValue() {
        List<Customer> customers = customerRepository.findAll();

        System.out.println("Customers Found: " + customers.size());

        //return first id
        return customers.get(0).getId();
    }
}
