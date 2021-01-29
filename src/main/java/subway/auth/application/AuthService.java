package subway.auth.application;

import org.springframework.stereotype.Service;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.member.application.MemberService;
import subway.member.dto.MemberResponse;

@Service
public class AuthService {

    private JwtTokenProvider jwtTokenProvider;
    private MemberService memberService;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    public String createToken(String email, String password) {
        validateLogin(email, password);
        return jwtTokenProvider.createToken(email);
    }

    public MemberResponse findMember(String principal) {
        return memberService.findByEmail(principal);
    }

    public MemberResponse findMemberByToken(String token) {
        String payload = jwtTokenProvider.getPayload(token);
        return findMember(payload);
    }

    public void validateLogin(String principal, String credentials) {
        memberService.validateMember(principal, credentials);
    }
}
