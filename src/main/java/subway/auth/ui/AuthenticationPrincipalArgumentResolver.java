package subway.auth.ui;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import subway.auth.domain.AuthenticationPrincipal;
import subway.auth.infrastructure.AuthorizationExtractor;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exceptions.AuthorizationException;
import subway.member.service.MemberService;

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
        AuthenticationPrincipal auth = parameter.getParameterAnnotation(AuthenticationPrincipal.class);
        String token = AuthorizationExtractor.extract((HttpServletRequest) webRequest.getNativeRequest());

        if (token == null) {
            if (auth.isThrow()) {
                throw new AuthorizationException("로그인이 필요한 기능입니다.");
            }
            return null;
        }
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException("유효한 token이 아닙니다.");
        }

        return memberService.findMemberByEmail(jwtTokenProvider.getPayload(token));
    }
}
