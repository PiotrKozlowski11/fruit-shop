package org.kozlowski.fruitshop.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kozlowski.fruitshop.api.model.CustomerDTO;
import org.kozlowski.fruitshop.services.CustomerService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest {

    @InjectMocks
    CustomerController customerController;

    @Mock
    CustomerService customerService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getAllCustomers() throws Exception {
        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName("Michale");
        customer1.setLastName("Weston");
        customer1.setCustomerUrl("/api/v1/customers/1");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setFirstName("Sam");
        customer2.setLastName("Axe");
        customer2.setCustomerUrl("/api/v1/customers/2");

        List<CustomerDTO> customerDTOList = Arrays.asList(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customerDTOList);

        mockMvc.perform(get("/api/customers/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));

    }

    @Test
    void getCustomerById() throws Exception {

        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName("Michale");
        customer1.setLastName("Weston");
        customer1.setCustomerUrl("/api/customers/1");


        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);

        mockMvc.perform(get("/api/customers/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Michale")))
                .andExpect(jsonPath("$.lastName", equalTo("Weston")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/customers/1")));
    }


    @Test
    void createNewCustomer() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Michale");
        customer.setLastName("Weston");

        CustomerDTO returnedDTO = new CustomerDTO();
        returnedDTO.setFirstName(customer.getFirstName());
        returnedDTO.setLastName(customer.getLastName());
        returnedDTO.setCustomerUrl("/api/customers/1");

        when(customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(returnedDTO);

        mockMvc.perform(post("/api/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(HelperRestControllerTest.asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo("Michale")))
                .andExpect(jsonPath("$.lastName", equalTo("Weston")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/customers/1")));

    }

    @Test
    void updateCustomer() throws Exception {

        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Michale");
        customer.setLastName("Weston");

        CustomerDTO returnedDTO = new CustomerDTO();
        returnedDTO.setFirstName(customer.getFirstName());
        returnedDTO.setLastName(customer.getLastName());
        returnedDTO.setCustomerUrl("/api/customers/1");

        when(customerService.saveCustomerByDTO(anyLong(),any(CustomerDTO.class))).thenReturn(returnedDTO);

        mockMvc.perform(put("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(HelperRestControllerTest.asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Michale")))
                .andExpect(jsonPath("$.lastName", equalTo("Weston")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/customers/1")));
    }
}