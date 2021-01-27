package subway.auth.application;

import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exceptions.AuthorizationException;
import subway.member.application.MemberService;
import subway.member.domain.Member;
import subway.member.dto.MemberResponse;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    public MemberResponse findMemberByToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException("유효하지 않은 토큰입니다.");
        }
        String payload = jwtTokenProvider.getPayload(token);
        return findMember(payload);
    }

    private MemberResponse findMember(String email) {
        Member member = memberService.findMemberByEmail(email);
        return MemberResponse.of(member);
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        if (checkInvalidLogin(tokenRequest.getEmail(), tokenRequest.getPassword())) {
            throw new AuthorizationException("일치하는 회원정보가 없습니다.");
        }

        String accessToken = jwtTokenProvider.createToken(tokenRequest.getEmail());
        return new TokenResponse(accessToken);
    }

    private boolean checkInvalidLogin(String email, String password) {
        return memberService.isInValidMember(email, password);
    }
}
