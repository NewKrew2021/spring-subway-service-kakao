package subway.auth.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import subway.auth.application.AuthService;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.member.application.MemberService;

@RestController
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    public AuthController(AuthService authService, MemberService memberService) {
        this.authService = authService;
        this.memberService = memberService;
    }

    // TODO: 로그인(토큰 발급) 요청 처리하기
    @PostMapping("/login/token")
    public ResponseEntity tokenLogin(@RequestBody TokenRequest tokenRequest) {
        if (!memberService.isPossibleLogin(tokenRequest)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        TokenResponse tokenResponse = authService.createToken(tokenRequest);
        return ResponseEntity.ok().body(tokenResponse);
    }
}
