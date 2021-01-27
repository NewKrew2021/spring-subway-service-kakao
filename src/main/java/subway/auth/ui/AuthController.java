package subway.auth.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.service.AuthService;
import subway.member.application.MemberService;

@RestController
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    public AuthController(AuthService authService, MemberService memberService) {
        this.authService = authService;
        this.memberService = memberService;
    }

    @PostMapping("/login/token")
    public ResponseEntity tokenLogin(@RequestBody TokenRequest tokenRequest) {
        if (!tokenRequest.isValidRequest()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("부적절한 요청입니다.");
        }

        if (!memberService.isPossibleLogin(tokenRequest)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("존재하지 않는 유저입니다.");
        }

        TokenResponse tokenResponse = authService.createToken(tokenRequest);
        return ResponseEntity.ok().body(tokenResponse);
    }
}
