package org.kozlowski.fruitshop.services;

import org.kozlowski.fruitshop.api.mapper.VendorMapper;
import org.kozlowski.fruitshop.api.model.VendorDTO;
import org.kozlowski.fruitshop.controllers.VendorController;
import org.kozlowski.fruitshop.domain.Vendor;
import org.kozlowski.fruitshop.respositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository
                .findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(this.getVendorURL(vendor.getId()));

                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository
                .findById(id)
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(this.getVendorURL(vendor.getId()));

                    return vendorDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {

        return getVendorDTO(vendorMapper.vendorDTOToVendor(vendorDTO));
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);

        return getVendorDTO(vendor);
    }


    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {

        return vendorRepository.findById(id).map(vendor -> {
            if (vendor.getName() != null) {
                vendor.setName(vendorDTO.getName());
            }

            VendorDTO returnedDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
            returnedDTO.setVendorUrl(this.getVendorURL(vendor.getId()));

            return returnedDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        if (vendorRepository.findById(id).isPresent()) {
            vendorRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException();
        }

    }

    private String getVendorURL(Long id) {
        return VendorController.BASE_URL + "/" + id;
    }

    private VendorDTO getVendorDTO(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);

        VendorDTO returnedDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        returnedDTO.setVendorUrl(this.getVendorURL(savedVendor.getId()));

        return returnedDTO;
    }
}
