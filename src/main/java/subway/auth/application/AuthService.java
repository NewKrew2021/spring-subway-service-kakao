package subway.auth.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.member.application.MemberService;
import subway.member.domain.Member;
import subway.member.dto.MemberResponse;

@Service
public class AuthService {

    private JwtTokenProvider jwtTokenProvider;
    private MemberService memberService;

    @Autowired
    public AuthService(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        validateLogin(tokenRequest.getEmail(), tokenRequest.getPassword());

        String accessToken = jwtTokenProvider.createToken(tokenRequest.getEmail());
        return new TokenResponse(accessToken);
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
