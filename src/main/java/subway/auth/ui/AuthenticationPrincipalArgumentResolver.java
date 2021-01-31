package subway.auth.ui;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import subway.auth.application.AuthService;
import subway.auth.domain.AuthenticationPrincipal;
import subway.auth.infrastructure.AuthorizationExtractor;
import subway.member.dao.MemberDao;
import subway.member.domain.LoginMember;
import subway.member.domain.Member;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private AuthService authService;
    private Map<String, LoginMember> cache = new HashMap<>();

    public AuthenticationPrincipalArgumentResolver(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    // parameter에 @AuthenticationPrincipal이 붙어있는 경우 동작
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        try{
            String token = AuthorizationExtractor.extract((HttpServletRequest) webRequest.getNativeRequest());
            if(cache.containsKey(token))
                return cache.get(token);

            Member member = authService.getMemberByToken(token);
            LoginMember loginMember = new LoginMember(member.getId(), member.getEmail(), member.getAge());
            cache.put(token, loginMember);
            return loginMember;
        }catch (Exception e){
            return null;
        }

    }
}
