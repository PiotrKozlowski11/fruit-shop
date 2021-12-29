package org.kozlowski.fruitshop.services;

import org.kozlowski.fruitshop.api.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);
}
