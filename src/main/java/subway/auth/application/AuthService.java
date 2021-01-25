package subway.auth.application;

import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.member.application.MemberService;
import subway.member.domain.LoginMember;
import subway.member.domain.Member;

@Service
public class AuthService {

    public static final String INVALID_USER_INFORMATION_ERROR_MESSAGE = "유효하지 않은 입력입니다.";
    private JwtTokenProvider jwtTokenProvider;
    private MemberService memberService;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    private boolean validateMember(Member member, TokenRequest tokenRequest) {
        return member.getEmail().equals(tokenRequest.getEmail()) && member.getPassword().equals(tokenRequest.getPassword());
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        Member member = memberService.findMemberByEmail(tokenRequest.getEmail());
        if(!validateMember(member, tokenRequest)) {
            throw new IllegalArgumentException(INVALID_USER_INFORMATION_ERROR_MESSAGE);
        }
        String accessToken = jwtTokenProvider.createToken(tokenRequest.getEmail());
        return new TokenResponse(accessToken);
    }

    public boolean checkToken(String accessToken) {
        return jwtTokenProvider.validateToken(accessToken);
    }

    public String getPayload(String accessToken) {
        return jwtTokenProvider.getPayload(accessToken);
    }

    public LoginMember findByEmail(String email) {
        Member member = memberService.findMemberByEmail(email);
        return new LoginMember(member.getId(), member.getEmail(), member.getAge());
    }
}
