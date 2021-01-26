package subway.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.auth.application.AuthService;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.exception.AuthorizationException;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponse> login(@RequestBody TokenRequest tokenRequest) {
        try{
            String accessToken = authService.login(tokenRequest);
            return ResponseEntity.ok().body(new TokenResponse(accessToken));
        } catch (Exception e) {
            throw new AuthorizationException("로그인에 실패했습니다.");
        }
    }
}
