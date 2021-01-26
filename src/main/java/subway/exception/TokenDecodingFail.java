package subway.exception;

public class TokenDecodingFail extends RuntimeException {

    public TokenDecodingFail(String message) {
        super(message);
    }
}
