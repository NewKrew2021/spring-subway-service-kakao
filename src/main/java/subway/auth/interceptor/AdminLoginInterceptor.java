package subway.auth.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import subway.auth.application.AuthService;
import subway.auth.infrastructure.AuthorizationExtractor;
import subway.exceptions.UnauthenticatedException;
import subway.exceptions.UnauthorizedException;
import subway.member.domain.LoginMember;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminLoginInterceptor extends HandlerInterceptorAdapter {

    private final AuthService authService;

    public AdminLoginInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = AuthorizationExtractor.extract(request);
        if(token == null) {
            throw new UnauthenticatedException();
        }
        LoginMember loginMember = authService.getLoginMember(token);
        if(!loginMember.isAdmin()){
            throw new UnauthorizedException();
        }

        return super.preHandle(request, response, handler);
    }
}
