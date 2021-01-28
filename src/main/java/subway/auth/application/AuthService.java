package subway.auth.application;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exception.AuthenticationException;
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

    public TokenResponse createToken(TokenRequest tokenRequest) {
        try {
            memberService.findMemberByEmail(tokenRequest.getEmail());
        } catch (DataAccessException e) {
            throw new AuthenticationException("멤버 정보가 존재하지 않습니다.");
        }
        return new TokenResponse(jwtTokenProvider.createToken(tokenRequest.getEmail()));
    }

    public LoginMember getLoginMember(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthenticationException("유효하지 않은 토큰입니다.");
        }
        Member member = memberService.findMemberByEmail(jwtTokenProvider.getPayload(token));
        return LoginMember.of(member);
    }
}
