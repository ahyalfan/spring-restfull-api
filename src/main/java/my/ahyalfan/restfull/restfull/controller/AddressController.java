package my.ahyalfan.restfull.restfull.controller;

import lombok.RequiredArgsConstructor;
import my.ahyalfan.restfull.restfull.dto.request.CreateAddressRequest;
import my.ahyalfan.restfull.restfull.dto.request.UpdateAddressRequest;
import my.ahyalfan.restfull.restfull.dto.response.AddressResponse;
import my.ahyalfan.restfull.restfull.dto.response.WebResponse;
import my.ahyalfan.restfull.restfull.entity.User;
import my.ahyalfan.restfull.restfull.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/contacts")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping(
            path = "/{contactId}/addresses",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddressResponse> createAddress(User user,
                                                      @RequestBody CreateAddressRequest request,
                @PathVariable("contactId") Long id
    ) {
        request.setContactId(id);
        // TODO implement this method
        var response = addressService.create(user, request);
        return WebResponse.<AddressResponse>builder()
                .status(HttpStatus.CREATED)
                .data(response)
                .build();
    }

    @GetMapping(
            path = "/{contactId}/addresses/{addressId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddressResponse> getAddress(
            User user,
            @PathVariable("contactId") Long contactId,
            @PathVariable("addressId") Long addressId
    ) {
        var response = addressService.get(user, contactId, addressId);
        return WebResponse.<AddressResponse>builder()
                .status(HttpStatus.OK)
                .data(response)
                .build();
    }

    @PutMapping(
            path = "/{contactId}/addresses/{addressId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddressResponse> update(User user,
                                                      @RequestBody UpdateAddressRequest request,
                                                      @PathVariable("contactId") Long contactId,
                                               @PathVariable("addressId") Long addressId
    ) {
        request.setContactId(contactId);
        request.setIdAddress(addressId);
        // TODO implement this method
        var response = addressService.update(user, request);
        return WebResponse.<AddressResponse>builder()
                .status(HttpStatus.CREATED)
                .data(response)
                .build();
    }
    @DeleteMapping(
            path = "/{contactId}/addresses/{addressId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(User user,
                                                      @PathVariable("contactId") Long contactId,
                                               @PathVariable("addressId") Long addressId
    ) {
        // TODO implement this method
        addressService.delete(user, contactId, addressId);
        return WebResponse.<String>builder()
                .status(HttpStatus.CREATED)
                .data("OK")
                .build();
    }

    @GetMapping(
            path = "/{contactId}/addresses",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<AddressResponse>> getAddress(
            User user,
            @PathVariable("contactId") Long contactId
    ) {
        var response = addressService.getAll(user, contactId);
        return WebResponse.<List<AddressResponse>>builder()
                .status(HttpStatus.OK)
                .data(response)
                .build();
    }
}
