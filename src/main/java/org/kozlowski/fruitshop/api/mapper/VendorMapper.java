package org.kozlowski.fruitshop.api.mapper;

import org.kozlowski.fruitshop.api.model.VendorDTO;
import org.kozlowski.fruitshop.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VendorMapper {
    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    @Mapping(target = "vendorUrl", ignore = true)
    VendorDTO vendorToVendorDTO(Vendor vendor);

    @Mapping(target = "id", ignore = true)
    Vendor vendorDTOToVendor(VendorDTO vendorDTO);
}
