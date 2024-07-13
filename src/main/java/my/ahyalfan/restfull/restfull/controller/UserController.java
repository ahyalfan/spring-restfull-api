package my.ahyalfan.restfull.restfull.controller;

import lombok.RequiredArgsConstructor;
import my.ahyalfan.restfull.restfull.dto.request.RegisterUserRequest;
import my.ahyalfan.restfull.restfull.dto.request.UpdateUserRequest;
import my.ahyalfan.restfull.restfull.dto.response.UserResponse;
import my.ahyalfan.restfull.restfull.dto.response.WebResponse;
import my.ahyalfan.restfull.restfull.entity.User;
import my.ahyalfan.restfull.restfull.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.awt.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping(
            path = "/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> registerUser(@RequestBody RegisterUserRequest request) {
        userService.register(request);
        return WebResponse.<String>builder()
                .status(HttpStatus.OK)
                .data("OK")
                .build();
    }

    @GetMapping(
            path = "/users/current",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> registerUser(User user) {
        UserResponse userResponse= userService.getUser(user);
        return WebResponse.<UserResponse>builder()
                .status(HttpStatus.OK)
                .data(userResponse)
                .build();
    }

    @PatchMapping(
            path = "/users/current",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> update(User user, @RequestBody UpdateUserRequest request){
        UserResponse userResponse = userService.updateUser(user, request);
        return WebResponse.<UserResponse>builder()
               .status(HttpStatus.OK)
               .data(userResponse)
               .build();
    }
}
