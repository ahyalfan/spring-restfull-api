package my.ahyalfan.restfull.restfull.service.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.ahyalfan.restfull.restfull.dto.request.CreateContactRequest;
import my.ahyalfan.restfull.restfull.dto.request.SearchContactRequest;
import my.ahyalfan.restfull.restfull.dto.request.UpdateContactRequest;
import my.ahyalfan.restfull.restfull.dto.response.ContactResponse;
import my.ahyalfan.restfull.restfull.entity.Contact;
import my.ahyalfan.restfull.restfull.entity.User;
import my.ahyalfan.restfull.restfull.repository.ContactRepository;
import my.ahyalfan.restfull.restfull.service.ContactService;
import my.ahyalfan.restfull.restfull.service.ValidationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl  implements ContactService {
    private final ContactRepository contactRepository;
    private final ValidationService validationService;

    @Transactional
//    kita pastikan ada usernya
    public ContactResponse save(User user, CreateContactRequest request) {
        log.info("New contact saved: {}", request);
        validationService.validate(request);

        var contact = new Contact();
        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contact.setUser(user);
        contactRepository.save(contact);

        return ContactResponse.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .build();
    }

    @Transactional(readOnly = true)
    public ContactResponse findById(User user,Long id) {
        var contact = contactRepository.findFirstByUserAndId(user,id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Contact not found"));
        return ContactResponse.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .phone(contact.getPhone())
                .email(contact.getEmail())
                .build();
    }

    @Transactional
    @Override
    public ContactResponse updateById(User user, UpdateContactRequest updateContactRequest) {
        validationService.validate(updateContactRequest);
        var contact = contactRepository.findFirstByUserAndId(user,updateContactRequest.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Contact not found"));
        contact.setFirstName(updateContactRequest.getFirstName());
        contact.setLastName(updateContactRequest.getLastName());
        contact.setEmail(updateContactRequest.getEmail());
        contact.setPhone(updateContactRequest.getPhone());
        contactRepository.save(contact);

        return ContactResponse.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .build();
    }

    private ContactResponse toContactResponse(Contact contact){
        return ContactResponse.builder()
               .id(contact.getId())
               .firstName(contact.getFirstName())
               .lastName(contact.getLastName())
               .phone(contact.getPhone())
               .email(contact.getEmail())
               .build();
    }

    @Override
    @Transactional
    public void deleteById(User user, Long id) {
        var contact = contactRepository.findFirstByUserAndId(user,id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Contact not found"));
        contactRepository.delete(contact);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactResponse> search(User user, SearchContactRequest request) {
        Specification<Contact> specification = (root, query, builder) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("user"), user));
            if (Objects.nonNull(request.getName())) {
                predicates.add(builder.or(
                        builder.like(root.get("firstName"), "%" + request.getName() + "%"),
                        builder.like(root.get("lastName"), "%" + request.getName() + "%")
                ));
            }
            if (Objects.nonNull(request.getEmail())) {
                predicates.add(builder.like(root.get("email"), "%" + request.getEmail() + "%"));
            }
            if (Objects.nonNull(request.getPhone())) {
                predicates.add(builder.like(root.get("phone"), "%" + request.getPhone() + "%"));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Contact> contacts = contactRepository.findAll(specification, pageable);
        List<ContactResponse> contactResponses = contacts.getContent().stream()
                .map(this::toContactResponse)
                .toList();

        return new PageImpl<>(contactResponses, pageable, contacts.getTotalElements());
    }
}
