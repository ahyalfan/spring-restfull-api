package my.ahyalfan.restfull.restfull.resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import my.ahyalfan.restfull.restfull.entity.User;
import my.ahyalfan.restfull.restfull.repository.UserRepository;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserRepository userRepository;
    @Override
//    kita cek controllernya yg memliki parameter User
    public boolean supportsParameter(MethodParameter parameter) {
        return User.class.equals(parameter.getParameterType());
    }

    @Override // maka method ini akan dijalankan
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String token = webRequest.getHeader("X-API-TOKEN");
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid API token");
        }

//        ini akan menjadi parameter controller yg memiliki tipe data User
        var user = userRepository.findFirstByToken(token).orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid API token"));
        if (user.getTokenExpiration() < System.currentTimeMillis()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid API token");
        }
        return user;

    }
}
