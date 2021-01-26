package subway.auth.ui;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import subway.auth.application.AuthService;
import subway.auth.dto.TokenResponse;

import java.util.Map;

@RestController
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping(value = "/login/token", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponse> createAuth(@RequestBody Map<String, String> params){
        String email = params.get("email");
        String password = params.get("password");
        TokenResponse tokenResponse = authService.createAuth(email, password);
        return ResponseEntity.ok().body(tokenResponse);
    }

}
