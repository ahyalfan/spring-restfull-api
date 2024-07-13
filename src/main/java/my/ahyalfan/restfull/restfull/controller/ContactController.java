package my.ahyalfan.restfull.restfull.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.ahyalfan.restfull.restfull.dto.request.CreateContactRequest;
import my.ahyalfan.restfull.restfull.dto.request.SearchContactRequest;
import my.ahyalfan.restfull.restfull.dto.request.UpdateContactRequest;
import my.ahyalfan.restfull.restfull.dto.response.ContactResponse;
import my.ahyalfan.restfull.restfull.dto.response.PagingResponse;
import my.ahyalfan.restfull.restfull.dto.response.WebResponse;
import my.ahyalfan.restfull.restfull.entity.User;
import my.ahyalfan.restfull.restfull.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/contacts")
@Slf4j
public class ContactController {
    private final ContactService contactService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> createContact(User user,@RequestBody CreateContactRequest request) {
        log.info("Creating contact for user {}", request);
        var contact = contactService.save(user, request);
        return WebResponse.<ContactResponse>builder()
                .status(HttpStatus.CREATED)
                .data(contact)
                .build();
    }

    @GetMapping(path = "/{id}")
    public WebResponse<ContactResponse> getContactById(User user,@PathVariable Long id) {
        var contact = contactService.findById(user,id);
        return WebResponse.<ContactResponse>builder()
                .status(HttpStatus.OK)
                .data(contact)
                .build();
    }

    @PutMapping(
            path = "/{contactId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> update(User user,
                                               @RequestBody UpdateContactRequest request,
                                               @PathVariable Long contactId
    ) {
        request.setId(contactId);
        var contact = contactService.updateById(user, request);
        return WebResponse.<ContactResponse>builder()
                .status(HttpStatus.CREATED)
                .data(contact)
                .build();
    }

    @DeleteMapping(
            path = "/{contactId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(User user, @PathVariable Long contactId) {
        contactService.deleteById(user, contactId);
        return WebResponse.<String>builder()
                .status(HttpStatus.OK)
                .data("OK")
                .build();
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ContactResponse>> seacrh(
            User user,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    )
    {
        var request = SearchContactRequest.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .page(page)
                .size(size)
                .build();
        var contacts = contactService.search(user,request);
        return WebResponse.<List<ContactResponse>>builder()
               .status(HttpStatus.OK)
                .paging(PagingResponse.builder()
                        .currentPage(contacts.getNumber())
                        .totalPages(contacts.getTotalPages())
                        .size(contacts.getSize())
                        .build())
               .data(contacts.getContent())
               .build();
    }
}
