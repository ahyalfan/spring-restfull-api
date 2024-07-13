package my.ahyalfan.restfull.restfull.service.impl;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.val;
import my.ahyalfan.restfull.restfull.dto.request.LoginUserRequest;
import my.ahyalfan.restfull.restfull.dto.request.RegisterUserRequest;
import my.ahyalfan.restfull.restfull.dto.request.UpdateUserRequest;
import my.ahyalfan.restfull.restfull.dto.response.TokenResponse;
import my.ahyalfan.restfull.restfull.dto.response.UserResponse;
import my.ahyalfan.restfull.restfull.dto.response.WebResponse;
import my.ahyalfan.restfull.restfull.entity.User;
import my.ahyalfan.restfull.restfull.exception.ApiException;
import my.ahyalfan.restfull.restfull.repository.UserRepository;
import my.ahyalfan.restfull.restfull.security.BCrypt;
import my.ahyalfan.restfull.restfull.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final ValidationServiceImpl validationService;

    @Transactional
    public void register(RegisterUserRequest request){
        validationService.validate(request);

//        kita cek apakah sudah ada di database belum
        if (userRepository.existsById(request.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username sudah ada");
        }

        User user = new User();
        user.setUsername(request.getUsername());
//        nah disini kita perlu hashing passworndnya
//        yg mana hashing yg populer yaitu bcrypt
//        karena spring tidak punya algoritma bcrypt
//        maka seharunya kita perlu install spring boot security
//        tapi karena lumayan makan waktu jadi kita pakai bcrypt dari spring security saja

//      tapi sangat direkomendasikan menggunakan spring security di git.enigma sudah ada spring securitynya
        user.setPassword(BCrypt.hashpw(request.getPassword(),BCrypt.gensalt()));
//        disini kita hanya menggunkan salinan class BCrypt dari spring security
//        Usage is really simple. To hash a password for the first time, call the hashpw method with a random salt, like this:
        //String pw_hash = BCrypt.hashpw(plain_password, BCrypt.gensalt()); // hasing
//        salt itu buat generate nilai acak
        //To check whether a plaintext password matches one that has been hashed previously, use the checkpw method:
        //if (BCrypt.checkpw(candidate_password, stored_hash)) //cek
        //    System.out.println("It matches");
        //else
        //    System.out.println("It does not match");

        user.setName(request.getName());

        userRepository.save(user);
    }

    public UserResponse getUser(User user){
//        ini akan kita lakukan pengecekan di resolver
//        jadi jika ada Controller dengan parameter User akan dicek dulu
        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .build();
    }

    @Override
    @Transactional
    public UserResponse updateUser(User user,UpdateUserRequest updateUserRequest) {
        validationService.validate(updateUserRequest);
        if (updateUserRequest.getName() != null) {
            user.setName(updateUserRequest.getName());
        }
        if (updateUserRequest.getPassword() != null){
            user.setPassword(BCrypt.hashpw(updateUserRequest.getPassword(), BCrypt.gensalt()));
        }
        userRepository.save(user);
        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .build();
    }
}
