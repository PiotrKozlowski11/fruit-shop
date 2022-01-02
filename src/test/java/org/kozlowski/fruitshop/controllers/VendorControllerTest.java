package org.kozlowski.fruitshop.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kozlowski.fruitshop.api.model.VendorDTO;
import org.kozlowski.fruitshop.services.ResourceNotFoundException;
import org.kozlowski.fruitshop.services.VendorService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VendorControllerTest {

    @InjectMocks
    VendorController vendorController;

    @Mock
    VendorService vendorService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void getAllVendors() throws Exception {
        //given
        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO1.setName("First Vendor");
        vendorDTO1.setVendorUrl(VendorController.BASE_URL + "/1");

        VendorDTO vendorDTO2 = new VendorDTO();
        vendorDTO2.setName("Second Vendor");
        vendorDTO2.setVendorUrl(VendorController.BASE_URL + "/2");

        List<VendorDTO> vendorDTOList = Arrays.asList(vendorDTO1, vendorDTO2);

        when(vendorService.getAllVendors()).thenReturn(vendorDTOList);

        mockMvc.perform(get(VendorController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    void getVendorById() throws Exception {
        //given
        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO1.setName("First Vendor");
        vendorDTO1.setVendorUrl(VendorController.BASE_URL + "/1");

        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO1);

        mockMvc.perform(get(VendorController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("First Vendor")))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }

    @Test
    void createNewVendor() throws Exception {
        //given
        String name = "First Vendor";
        String vendorUrl = VendorController.BASE_URL + "/1";

        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO1.setName(name);
        vendorDTO1.setVendorUrl(vendorUrl);


        VendorDTO returned = new VendorDTO();
        returned.setName(vendorDTO1.getName());
        returned.setVendorUrl(vendorDTO1.getVendorUrl());

        when(vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(returned);

        mockMvc.perform(post(VendorController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(HelperRestControllerTest.asJsonString(vendorDTO1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(name)))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorUrl)));

    }

    @Test
    void updateVendor() throws Exception {
        String name = "First Vendor";
        String vendorUrl = VendorController.BASE_URL + "/1";

        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO1.setName(name);
        vendorDTO1.setVendorUrl(vendorUrl);


        VendorDTO returned = new VendorDTO();
        returned.setName(vendorDTO1.getName());
        returned.setVendorUrl(vendorDTO1.getVendorUrl());

        when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(returned);

        mockMvc.perform(put(VendorController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(HelperRestControllerTest.asJsonString(vendorDTO1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(name)))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorUrl)));


    }

    @Test
    void patchVendor() throws Exception {
        //given
        String name = "First Vendor";
        String vendorUrl = VendorController.BASE_URL + "/1";

        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO1.setName(name);
        vendorDTO1.setVendorUrl(vendorUrl);


        VendorDTO returned = new VendorDTO();
        returned.setName(vendorDTO1.getName());
        returned.setVendorUrl(vendorDTO1.getVendorUrl());

        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(returned);

        mockMvc.perform(patch(VendorController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(HelperRestControllerTest.asJsonString(vendorDTO1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(name)))
                .andExpect(jsonPath("$.vendor_url", equalTo(vendorUrl)));
    }

    @Test
    void deleteVendor() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService, times(1)).deleteVendorById(anyLong());
    }

    @Test
    void getByIdNotFound() throws Exception {
        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}