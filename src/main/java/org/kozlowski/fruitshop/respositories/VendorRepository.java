package org.kozlowski.fruitshop.respositories;

import org.kozlowski.fruitshop.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
