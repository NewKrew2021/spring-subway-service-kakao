package subway.auth.application;

import org.springframework.stereotype.Service;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.member.application.MemberService;
import subway.member.domain.LoginMember;
import subway.member.domain.Member;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    public String createToken(String email, String password) {
        Member member = memberService.getAuthorizedMember(email, password);
        return jwtTokenProvider.createToken(member.getEmail());
    }

    public LoginMember getLoginMember(String token) {
        String email = jwtTokenProvider.getPayload(token);
        return memberService.getLoginMember(email);
    }
}
