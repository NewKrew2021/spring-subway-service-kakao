package subway.auth.ui;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import subway.auth.domain.AuthenticationPrincipal;
import subway.auth.infrastructure.AuthorizationExtractor;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.member.application.MemberService;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private MemberService memberService;
    private JwtTokenProvider jwtTokenProvider;

    public AuthenticationPrincipalArgumentResolver(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String token = AuthorizationExtractor.extract((HttpServletRequest) webRequest.getNativeRequest());
        if (!jwtTokenProvider.validateToken(token)) {
            return null;
        }

        return memberService.findMemberByEmail(jwtTokenProvider.getPayload(token));
    }
}
