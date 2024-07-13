package my.ahyalfan.restfull.restfull.service;

import my.ahyalfan.restfull.restfull.dto.request.CreateAddressRequest;
import my.ahyalfan.restfull.restfull.dto.request.UpdateAddressRequest;
import my.ahyalfan.restfull.restfull.dto.response.AddressResponse;
import my.ahyalfan.restfull.restfull.entity.User;

import java.util.List;

public interface AddressService {
    AddressResponse create(User user, CreateAddressRequest request);
    AddressResponse get(User user, Long contactId,Long addressId);
    AddressResponse update(User user, UpdateAddressRequest request);
    void delete(User user, Long contactId,Long addressId);
    List<AddressResponse> getAll(User user, Long contactId);
}
