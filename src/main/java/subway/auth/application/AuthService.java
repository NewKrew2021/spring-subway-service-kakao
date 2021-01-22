package subway.auth.application;

import org.springframework.stereotype.Service;
import subway.auth.exception.InvalidPasswordException;
import subway.auth.exception.InvalidTokenException;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.member.application.MemberService;
import subway.member.domain.LoginMember;
import subway.member.domain.Member;

@Service
public class AuthService {

    private MemberService memberService;
    private JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(String email, String password) {
        Member member = memberService.findMemberByEmail(email);
        if(!member.getPassword().equals(password)) {
            throw new InvalidPasswordException();
        }
        return jwtTokenProvider.createToken(email);
    }

    public LoginMember findMember(String accessToken) {
        if(!jwtTokenProvider.validateToken(accessToken)) {
            throw new InvalidTokenException();
        }

        return LoginMember.of(memberService.findMemberByEmail(jwtTokenProvider.getPayload(accessToken)));
    }

}
