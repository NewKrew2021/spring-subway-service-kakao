package subway.auth.infrastructure;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import subway.auth.application.AuthService;
import subway.auth.exception.InvalidLoginException;
import subway.auth.infrastructure.AuthorizationExtractor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private AuthService authService;

    public LoginInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = AuthorizationExtractor.extract(request);
        if (accessToken == null || !authService.validateToken(accessToken)) {
            throw new InvalidLoginException("잘못된 토큰 입니다.");
        }
        return true;
    }

}