package subway.auth.application;

import org.springframework.stereotype.Service;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exceptions.UnauthorizedException;
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
        Member member = memberService.findAuthorizedMember(email, password);
        return jwtTokenProvider.createToken(member.getEmail());
    }

    public LoginMember getLoginMember(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new UnauthorizedException();
        }
        String email = jwtTokenProvider.getPayload(token);
        return memberService.findLoginMember(email);
    }
}
