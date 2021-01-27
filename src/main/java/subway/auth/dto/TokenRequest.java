package subway.auth.dto;

public class TokenRequest {
    private String email;
    private String password;

    public TokenRequest() {
    }

    public TokenRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean isValidRequest() {
        return !email.isEmpty() && !password.isEmpty();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
