package my.ahyalfan.restfull.restfull.service.impl;

import lombok.RequiredArgsConstructor;
import my.ahyalfan.restfull.restfull.dto.request.CreateAddressRequest;
import my.ahyalfan.restfull.restfull.dto.request.UpdateAddressRequest;
import my.ahyalfan.restfull.restfull.dto.response.AddressResponse;
import my.ahyalfan.restfull.restfull.entity.Address;
import my.ahyalfan.restfull.restfull.entity.Contact;
import my.ahyalfan.restfull.restfull.entity.User;
import my.ahyalfan.restfull.restfull.repository.AddressRepository;
import my.ahyalfan.restfull.restfull.repository.ContactRepository;
import my.ahyalfan.restfull.restfull.service.AddressService;
import my.ahyalfan.restfull.restfull.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final ContactRepository contactRepository;
    private final AddressRepository addressRepository;
    private final ValidationServiceImpl validationService;

    @Transactional
    public AddressResponse create(User user, CreateAddressRequest request){
        validationService.validate(request);
        Contact contact = contactRepository.findById(request.getContactId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));
        Address address = new Address();
        address.setPostalCode(request.getPostalCode());
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setProvince(request.getProvince());
        address.setCountry(request.getCountry());
        address.setContact(contact);
        addressRepository.save(address);
        return toAddressResponse(address);
    }

    private AddressResponse toAddressResponse(Address address) {
        return AddressResponse.builder()
               .id(address.getId())
               .postalCode(address.getPostalCode())
               .street(address.getStreet())
               .city(address.getCity())
               .province(address.getProvince())
               .country(address.getCountry())
               .build();
    }

    @Override
    @Transactional(readOnly = true)
    public AddressResponse get(User user, Long contactId,Long addressId) {
        Contact contact = contactRepository.findFirstByUserAndId(user, contactId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "contact not found"));

        var address = addressRepository.findFirstByContactAndId(contact,addressId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        return toAddressResponse(address);
    }

    @Override
    @Transactional
    public AddressResponse update(User user, UpdateAddressRequest request) {
        validationService.validate(request);
        Contact contact = contactRepository.findFirstByUserAndId(user, request.getContactId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "contact not found"));

        var address = addressRepository.findFirstByContactAndId(contact, request.getIdAddress())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        address.setPostalCode(request.getPostalCode());
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setProvince(request.getProvince());
        address.setCountry(request.getCountry());
        addressRepository.save(address);
        return toAddressResponse(address);
    }

    @Transactional
    public void delete(User user, Long contactId,Long addressId) {
        Contact contact = contactRepository.findFirstByUserAndId(user, contactId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "contact not found"));

        var address = addressRepository.findFirstByContactAndId(contact,addressId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        addressRepository.deleteById(addressId);
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> getAll(User user, Long contactId) {
        Contact contact = contactRepository.findFirstByUserAndId(user, contactId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "contact not found"));

        List<Address> address = addressRepository.findAllByContact(contact);

        return address.stream().map(this::toAddressResponse).toList();
    }


}
