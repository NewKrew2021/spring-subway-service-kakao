package subway.favorite.dto;

import subway.exceptions.InvalidRequestException;

public class FavoriteRequest {
    private Long source;
    private Long target;

    public FavoriteRequest() {
    }

    public FavoriteRequest(Long source, Long target) {
        this.source = source;
        this.target = target;
    }

    public Long getSource() {
        return source;
    }

    public Long getTarget() {
        return target;
    }

    public void checkValidation() {
        if (this.source == null || this.target == null) {
            throw new InvalidRequestException("FavoriteRequest는 출발지와 목적지를 모두 가지고 있어야 합니다.");
        }
    }
}
