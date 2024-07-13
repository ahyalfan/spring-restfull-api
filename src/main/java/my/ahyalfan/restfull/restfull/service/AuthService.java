package my.ahyalfan.restfull.restfull.service;

import my.ahyalfan.restfull.restfull.dto.request.LoginUserRequest;
import my.ahyalfan.restfull.restfull.dto.response.TokenResponse;
import my.ahyalfan.restfull.restfull.entity.User;

public interface AuthService {
    TokenResponse login(LoginUserRequest request);
    void logout(User user);
}
