package my.ahyalfan.restfull.restfull.controller;

import lombok.RequiredArgsConstructor;
import my.ahyalfan.restfull.restfull.dto.request.LoginUserRequest;
import my.ahyalfan.restfull.restfull.dto.response.TokenResponse;
import my.ahyalfan.restfull.restfull.dto.response.WebResponse;
import my.ahyalfan.restfull.restfull.entity.User;
import my.ahyalfan.restfull.restfull.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/api/v1/auth/login",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TokenResponse> login(@RequestBody LoginUserRequest request) {
        TokenResponse tokenResponse = authService.login(request);
        return WebResponse.<TokenResponse>builder()
                .status(HttpStatus.ACCEPTED)
                .data(tokenResponse)
                .build();
    }
    @DeleteMapping(
            path = "/api/v1/auth/logout",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> logout(User user){
        authService.logout(user);
        return WebResponse.<String>builder()
                .status(HttpStatus.OK)
                .data("OK")
                .build();
    }


}
