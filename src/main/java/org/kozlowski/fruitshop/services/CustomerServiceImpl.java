package org.kozlowski.fruitshop.services;

import org.kozlowski.fruitshop.api.mapper.CustomerMapper;
import org.kozlowski.fruitshop.api.model.CustomerDTO;
import org.kozlowski.fruitshop.domain.Customer;
import org.kozlowski.fruitshop.respositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }


    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(customer -> {
            CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
            customerDTO.setCustomerUrl("/api/customer/" + customer.getId());
            return customerDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository
                .findById(id)
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl("/api/customer/" + customer.getId());
                    return customerDTO;
                })
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);

        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO returnedDTO = customerMapper.customerToCustomerDTO(savedCustomer);

        returnedDTO.setCustomerUrl("/api/customer/" + savedCustomer.getId());


        return returnedDTO;
    }
}
