package pl.studentmed.facultative.services.addresses.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.studentmed.facultative.models.address.Address;

interface AddressRepository extends JpaRepository<Address, Long> {
}
