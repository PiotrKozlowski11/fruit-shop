package org.kozlowski.fruitshop.services;

import org.kozlowski.fruitshop.api.mapper.CustomerMapper;
import org.kozlowski.fruitshop.api.model.CustomerDTO;
import org.kozlowski.fruitshop.controllers.CustomerController;
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
            customerDTO.setCustomerUrl(this.getCustomerUrl(customer.getId()));
            //customerDTO.setCustomerUrl("/api/customer/" + customer.getId());
            return customerDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository
                .findById(id)
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl(this.getCustomerUrl(id));
                    //customerDTO.setCustomerUrl("/api/customer/" + customer.getId());
                    return customerDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {

        return saveAndReturnDTO(customerMapper.customerDTOToCustomer(customerDTO));
    }

    private CustomerDTO saveAndReturnDTO(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO returnedDTO = customerMapper.customerToCustomerDTO(savedCustomer);

        returnedDTO.setCustomerUrl(this.getCustomerUrl(savedCustomer.getId()));
        //returnedDTO.setCustomerUrl("/api/customer/" + savedCustomer.getId());
        return returnedDTO;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {

        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {
            if (customerDTO.getFirstName() != null) {
                customer.setFirstName(customerDTO.getFirstName());
            }

            if (customerDTO.getLastName() != null) {
                customer.setLastName(customerDTO.getLastName());
            }

            CustomerDTO returnedDTO = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
            returnedDTO.setCustomerUrl(this.getCustomerUrl(customer.getId()));
            //returnedDTO.setCustomerUrl("/api/customer/" + customer.getId());

            return returnedDTO;

//            return customerMapper.customerToCustomerDTO(customer);
        }).orElseThrow(ResourceNotFoundException::new);

        //todo implement better exception handling;
    }

    private String getCustomerUrl(Long id) {
        return CustomerController.BASE_URL + "/" + id;
    }

    @Override
    public void deleteCustomerById(Long id) {
        if (customerRepository.findById(id).isPresent()) {
            customerRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException();
        }


    }
}
