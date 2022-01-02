package org.kozlowski.fruitshop.api.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kozlowski.fruitshop.api.model.VendorDTO;
import org.kozlowski.fruitshop.domain.Vendor;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VendorMapperTest {
    public static final Long ID = 2L;
    public static final String NAME = "Vendor Name";

    VendorMapper vendorMapper;

    @BeforeEach
    void setUp() {
        vendorMapper = VendorMapper.INSTANCE;
    }

    @Test
    void vendorToVendorDTO() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    void vendorDTOToVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        //when
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        assertEquals(NAME, vendor.getName());
    }
}