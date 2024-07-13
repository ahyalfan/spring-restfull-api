package my.ahyalfan.restfull.restfull.repository;

import my.ahyalfan.restfull.restfull.entity.Contact;
import my.ahyalfan.restfull.restfull.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {

    Optional<Contact> findFirstByFirstName(String firstName);
    Optional<Contact> findFirstByUserAndId(User user, Long id);
}