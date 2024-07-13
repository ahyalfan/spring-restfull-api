package my.ahyalfan.restfull.restfull.service.impl;

import lombok.RequiredArgsConstructor;
import my.ahyalfan.restfull.restfull.dto.request.LoginUserRequest;
import my.ahyalfan.restfull.restfull.dto.response.TokenResponse;
import my.ahyalfan.restfull.restfull.entity.User;
import my.ahyalfan.restfull.restfull.repository.UserRepository;
import my.ahyalfan.restfull.restfull.security.BCrypt;
import my.ahyalfan.restfull.restfull.service.AuthService;
import my.ahyalfan.restfull.restfull.service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ValidationService validationService;


    @Transactional
    public TokenResponse login(LoginUserRequest request){
        validationService.validate(request); //validasi
        User user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User or Password failed"));

        if (BCrypt.checkpw(request.getPassword(), user.getPassword())){
            user.setToken(UUID.randomUUID().toString()); // ini sebenarnya tidak direkomendasikan
//             jika ingin lebih baik bisa menggunak spring boot security di kominasikan dengan JWT
            user.setTokenExpiration(next30Day());
            userRepository.save(user);
            return TokenResponse.builder()
                    .token(user.getToken())
                    .expiredAt(user.getTokenExpiration())
                    .build();
        }else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User or Password failed");
        }
    }

    private Long next30Day(){
        return System.currentTimeMillis() + (1000L * 60 *  60 * 24 * 30);
    }

    @Transactional
    public void logout(User user) {
        user.setToken(null);
        user.setTokenExpiration(null);
        userRepository.save(user);
    }
}
