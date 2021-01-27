package subway.auth.application;

import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exception.AuthorizationFailException;
import subway.member.application.MemberService;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;

@Service
public class AuthService {
    private JwtTokenProvider jwtTokenProvider;
    private MemberService memberService;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        memberService.findByEmail(tokenRequest.getEmail())
                .checkValidMember(new Member(tokenRequest.getEmail(), tokenRequest.getPassword()));
        return new TokenResponse(jwtTokenProvider.createToken(tokenRequest.getEmail()));
    }

    public Member getMemberByToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationFailException();
        }
        return memberService.findByEmail(jwtTokenProvider.getPayload(token));
    }
}
