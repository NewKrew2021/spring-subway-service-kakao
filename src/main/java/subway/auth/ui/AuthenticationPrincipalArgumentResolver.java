package subway.auth.ui;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import subway.auth.domain.AuthenticationPrincipal;
import subway.auth.exceptions.UnauthenticatedException;
import subway.auth.infrastructure.AuthorizationExtractor;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.member.domain.LoginMember;
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
        String token = AuthorizationExtractor.extract((HttpServletRequest) webRequest.getNativeRequest());
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            AuthenticationPrincipal auth = parameter.getParameterAnnotation(AuthenticationPrincipal.class);
            if (auth.required()) {
                throw new UnauthenticatedException("유효하지 않은 사용자입니다");
            }
            return null;
        }

        LoginMember member = memberService.findMemberById(Long.valueOf(jwtTokenProvider.getPayload(token)));
        if (member == null) {
            throw new UnauthenticatedException("존재하지 않는 사용자입니다");
        }
        return member;
    }
}
