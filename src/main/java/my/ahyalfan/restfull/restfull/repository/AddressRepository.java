package my.ahyalfan.restfull.restfull.repository;

import my.ahyalfan.restfull.restfull.entity.Address;
import my.ahyalfan.restfull.restfull.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findFirstByContactAndId(Contact contact, Long id);

    List<Address> findAllByContact(Contact contact);
}