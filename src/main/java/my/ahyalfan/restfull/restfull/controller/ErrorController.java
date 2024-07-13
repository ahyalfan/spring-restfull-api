package my.ahyalfan.restfull.restfull.controller;

import jakarta.validation.ConstraintViolationException;
import my.ahyalfan.restfull.restfull.dto.response.WebResponse;
import my.ahyalfan.restfull.restfull.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse<String>> constraint(ConstraintViolationException e) {
        return ResponseEntity.badRequest()
                .body(WebResponse.<String>builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .errors(e.getMessage())
                        .data("")
                        .build());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> responseException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(WebResponse.<String>builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .errors(e.getMessage())
                        .data("")
                        .build());
    }
}
