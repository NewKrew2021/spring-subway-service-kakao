package subway.exception;

public class MemberNotFoundException extends EntityNotFoundException {
    public MemberNotFoundException(Long id) {
        super(String.format("id=%d인 노선이 존재하지 않습니다", id));
    }
}
