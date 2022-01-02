package org.kozlowski.fruitshop.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kozlowski.fruitshop.api.mapper.VendorMapper;
import org.kozlowski.fruitshop.api.model.VendorDTO;
import org.kozlowski.fruitshop.bootstrap.Bootstrap;
import org.kozlowski.fruitshop.domain.Vendor;
import org.kozlowski.fruitshop.respositories.CategoryRepository;
import org.kozlowski.fruitshop.respositories.CustomerRepository;
import org.kozlowski.fruitshop.respositories.VendorRepository;
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
public class VendorServiceImplITTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    VendorService vendorService;

    @BeforeAll
    void setUp() throws Exception {
        System.out.println("Loading Customer Data");
        System.out.println(customerRepository.findAll().size());

        //setup data for testing
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run(); //load data

        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    void patchVendorUpdateName() {
        String updatedName = "Updated Name";
        List<Vendor> vendors = vendorRepository.findAll();
        Long id = vendors.get(0).getId();

        Vendor originalVendor = vendorRepository.getById(id);
        assertNotNull(originalVendor);

        String originalName = originalVendor.getName();

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(updatedName);

        vendorService.patchVendor(id, vendorDTO);

        Vendor updatedVendor = vendorRepository.findById(id).get();

        assertNotNull(updatedVendor);
        assertEquals(updatedName, updatedVendor.getName());
        assertThat(originalName, not(equalTo(updatedVendor.getName())));

    }
}
