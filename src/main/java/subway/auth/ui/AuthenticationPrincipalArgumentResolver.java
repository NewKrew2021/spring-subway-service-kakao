package subway.auth.ui;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import subway.auth.domain.AuthenticationPrincipal;
import subway.auth.infrastructure.AuthorizationExtractor;
import subway.auth.service.AuthService;
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
        if (token == null) {
            return new LoginMember(0L, "", 0);
        }
        if (!authService.validToken(token)) {
            throw new IllegalArgumentException("잘못된 Token");
        }
        return authService.findMemberByToken(token);
    }
}
