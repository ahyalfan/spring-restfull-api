package my.ahyalfan.restfull.restfull.service;

import my.ahyalfan.restfull.restfull.dto.request.RegisterUserRequest;
import my.ahyalfan.restfull.restfull.dto.request.UpdateUserRequest;
import my.ahyalfan.restfull.restfull.dto.response.UserResponse;
import my.ahyalfan.restfull.restfull.entity.User;

public interface UserService {
    void register(RegisterUserRequest request);
    UserResponse getUser(User user);
    UserResponse updateUser(User user,UpdateUserRequest updateUserRequest);
}
