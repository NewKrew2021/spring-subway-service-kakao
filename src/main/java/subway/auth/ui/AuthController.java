package subway.auth.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import subway.auth.application.AuthService;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;

@RestController
public class AuthController {

    AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login/token")
    public ResponseEntity createToken(@RequestBody TokenRequest request) {
        TokenResponse tokenResponse = authService.createToken(request);
        return ResponseEntity.ok().body(tokenResponse);
    }

}