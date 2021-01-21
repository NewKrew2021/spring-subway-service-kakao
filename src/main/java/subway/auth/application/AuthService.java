package subway.auth.application;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.member.application.MemberService;
import subway.member.domain.LoginMember;
import subway.member.dto.MemberResponse;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        try {
            memberService.findMemberByEmail(tokenRequest.getEmail());
        } catch (DataAccessException e) {
            throw new IllegalArgumentException();
        }
        return new TokenResponse(jwtTokenProvider.createToken(tokenRequest.getEmail()));
    }

    public LoginMember getLoginMember(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new IllegalArgumentException();
        }
        MemberResponse memberResponse = memberService.findMemberByEmail(jwtTokenProvider.getPayload(token));
        return new LoginMember(memberResponse.getId(), memberResponse.getEmail(), memberResponse.getAge());
    }
}
