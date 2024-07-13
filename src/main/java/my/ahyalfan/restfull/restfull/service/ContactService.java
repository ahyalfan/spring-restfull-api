package my.ahyalfan.restfull.restfull.service;

import jakarta.validation.ConstraintViolationException;
import my.ahyalfan.restfull.restfull.dto.request.CreateContactRequest;
import my.ahyalfan.restfull.restfull.dto.request.SearchContactRequest;
import my.ahyalfan.restfull.restfull.dto.request.UpdateContactRequest;
import my.ahyalfan.restfull.restfull.dto.response.ContactResponse;
import my.ahyalfan.restfull.restfull.entity.User;
import org.springframework.data.domain.Page;

public interface ContactService {
    ContactResponse save(User user, CreateContactRequest request);
    ContactResponse findById(User user,Long id);
    ContactResponse updateById(User user, UpdateContactRequest updateContactRequest);
    void deleteById(User user, Long id);
    Page<ContactResponse> search(User user, SearchContactRequest contactRequest);
}
