package org.kozlowski.fruitshop.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kozlowski.fruitshop.api.mapper.VendorMapper;
import org.kozlowski.fruitshop.api.model.VendorDTO;
import org.kozlowski.fruitshop.controllers.VendorController;
import org.kozlowski.fruitshop.domain.Vendor;
import org.kozlowski.fruitshop.respositories.VendorRepository;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class VendorServiceImplTest {
    public static final Long ID = 2L;
    public static final String NAME = "Vendor Name";

    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    void getAllVendors() {
        List<Vendor> vendorList = Arrays.asList(new Vendor(), new Vendor(), new Vendor());

        when(vendorRepository.findAll()).thenReturn(vendorList);

        //when
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        //then

        assertEquals(vendorList.size(), vendorDTOS.size());
    }

    @Test
    void getVendorById() {
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(ID);

        //then
        assertEquals(NAME, vendorDTO.getName());

    }

    @Test
    void createNewVendor() {
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO returnedVendor = vendorService.createNewVendor(vendorDTO);

        assertEquals(NAME, returnedVendor.getName());
        assertEquals(VendorController.BASE_URL + "/" + ID, returnedVendor.getVendorUrl());
    }

    @Test
    void saveVendorByDTO() {
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDTO returnedVendor = vendorService.saveVendorByDTO(ID, vendorDTO);

        assertEquals(NAME, returnedVendor.getName());
        assertEquals(VendorController.BASE_URL + "/" + ID, returnedVendor.getVendorUrl());


    }

    @Test
    void patchVendor() {
    }

    @Test
    void deleteVendorById() {

        when(vendorRepository.findById(ID)).thenReturn(Optional.of(new Vendor()));

        vendorService.deleteVendorById(ID);

        verify(vendorRepository, times(1)).deleteById(ID);
    }
}