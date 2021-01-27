package subway.auth.ui;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import subway.auth.application.AuthService;
import subway.auth.domain.AuthenticationPrincipal;
import subway.auth.infrastructure.AuthorizationExtractor;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import subway.auth.exception.InvalidTokenException;

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
        try {
            String accessToken = AuthorizationExtractor.extract(Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class)));
            return authService.findMember(accessToken);
        } catch (InvalidTokenException e) {
            validateLoginMemberNecessary(parameter.getParameterAnnotation(AuthenticationPrincipal.class));
            return null;
        }
    }

    private void validateLoginMemberNecessary(AuthenticationPrincipal authenticationPrincipal) {
        if(authenticationPrincipal.required()) {
            throw new InvalidTokenException();
        }
    }
}
