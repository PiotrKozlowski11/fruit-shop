package org.kozlowski.fruitshop.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VendorListDTO {
    private List<VendorDTO> vendors;
}
