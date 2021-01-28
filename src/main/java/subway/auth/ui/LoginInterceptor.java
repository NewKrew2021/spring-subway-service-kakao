package subway.auth.ui;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import subway.auth.infrastructure.AuthorizationExtractor;
import subway.exception.InvalidTokenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = AuthorizationExtractor.extract(request);
        if(accessToken == null){
            throw new InvalidTokenException();
        }
        return super.preHandle(request, response, handler);
    }
}
