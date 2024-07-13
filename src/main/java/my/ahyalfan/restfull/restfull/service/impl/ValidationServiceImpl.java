package my.ahyalfan.restfull.restfull.service.impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import my.ahyalfan.restfull.restfull.dto.request.RegisterUserRequest;
import my.ahyalfan.restfull.restfull.service.ValidationService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final Validator validator; // kita buat validatornya di service
//    karena terkadang diservice butuh yg di validasi

    public void validate(Object request){
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);
        if (!constraintViolations.isEmpty()){
//            artinya jika ada error maka tangkap dan masukan ke exception
            throw new ConstraintViolationException(constraintViolations);
        }
    }

}
