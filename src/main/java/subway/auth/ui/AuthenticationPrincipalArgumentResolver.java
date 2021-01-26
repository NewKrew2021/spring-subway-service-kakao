package subway.auth.ui;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import subway.auth.application.AuthService;
import subway.auth.domain.AuthenticationPrincipal;
import subway.auth.infrastructure.AuthorizationExtractor;
import subway.exceptions.UnauthorizedException;
import subway.member.domain.LoginMember;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService;

    public AuthenticationPrincipalArgumentResolver(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String token = AuthorizationExtractor.extract(webRequest.getNativeRequest(HttpServletRequest.class));
        if (loginRequired(parameter) && loginInfoNotExist(token)) {
            throw new UnauthorizedException();
        }

        if (loginInfoNotExist(token)) {
            return LoginMember.NOT_LOGINED;
        }
        return authService.getLoginMember(token);
    }

    private boolean loginRequired(MethodParameter parameter) {
        return parameter.getParameterAnnotation(AuthenticationPrincipal.class).required();
    }

    private boolean loginInfoNotExist(String token) {
        return token == null;
    }
}
