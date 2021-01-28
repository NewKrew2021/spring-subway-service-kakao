package subway.auth.application;

import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exceptions.UnauthenticatedException;
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

    public String login(TokenRequest tokenRequest) {
        Member member = memberService.findAuthenticatedMember(tokenRequest.getEmail(), tokenRequest.getPassword());
        return jwtTokenProvider.createToken(String.valueOf(member.getId()));
    }

    public LoginMember getLoginMember(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new UnauthenticatedException();
        }
        Long id = Long.parseLong(jwtTokenProvider.getPayload(token));
        return memberService.findLoginMemberById(id);
    }
}
