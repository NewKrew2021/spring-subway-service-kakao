package subway.auth.ui;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import subway.auth.application.AuthService;
import subway.auth.domain.AuthenticationPrincipal;
import subway.auth.infrastructure.AuthorizationExtractor;
import subway.exception.UnAuthorizedException;
import subway.member.domain.LoginMember;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private AuthService authService;

    public AuthenticationPrincipalArgumentResolver(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String token = AuthorizationExtractor.extract((HttpServletRequest) webRequest.getNativeRequest());

        if (loginRequired(parameter) && tokenNotIncluded(token)) {
            throw new UnAuthorizedException();
        }

        if(tokenNotIncluded(token)) {
            return LoginMember.NO_ONE;
        }

        return authService.validateToken(token);
    }

    private boolean loginRequired(MethodParameter parameter) {
        return parameter.getParameterAnnotation(AuthenticationPrincipal.class).required();
    }

    private boolean tokenNotIncluded(String token) {
        return token == null;
    }
}
