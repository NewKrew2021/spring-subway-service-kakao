package subway.auth.ui;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import subway.auth.application.AuthService;
import subway.auth.exception.AuthorizationException;
import subway.auth.infrastructure.AuthorizationExtractor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    private AuthService authService;

    public LoginInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = AuthorizationExtractor.extract(request);

        if (!authService.validateToken(token)) {
            throw new AuthorizationException("유효하지 않은 토큰입니다.");
        }

        return super.preHandle(request, response, handler);
    }
}
