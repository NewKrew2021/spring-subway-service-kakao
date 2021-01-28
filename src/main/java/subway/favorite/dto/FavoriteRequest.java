package subway.favorite.dto;

import subway.exception.InvalidInputException;

public class FavoriteRequest {
    private Long source;
    private Long target;

    public FavoriteRequest() {
    }

    public FavoriteRequest(Long source, Long target) {
        validInput(source, target);
        this.source = source;
        this.target = target;
    }

    private void validInput(Long source, Long target) {
        if (source == null || source < 0) {
            throw new InvalidInputException("소스 아이디가 존재하지 않거나 유효하지 않습니다.");
        }
        if (target == null || target < 0) {
            throw new InvalidInputException("타 아이디가 존재하지 않거나 유효하지 않습니다.");
        }
    }

    public Long getSource() {
        return source;
    }

    public Long getTarget() {
        return target;
    }

}
