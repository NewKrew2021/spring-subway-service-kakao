package subway.favorite.domain;

import subway.exception.WrongInputDataException;

public class Favorite {
    private final Long id;
    private final Long memberId;
    private final Long sourceStationId;
    private final Long targetStationId;

    public Favorite(Long memberId, Long sourceStationId, Long targetStationId) {
        this(null, memberId, sourceStationId, targetStationId);
    }

    public Favorite(Long id, Long memberId, Long sourceStationId, Long targetStationId) {
        if (sourceStationId.equals(targetStationId)) {
            throw new WrongInputDataException("역이 잘못 입력되었습니다.");
        }
        this.memberId = memberId;
        this.sourceStationId = sourceStationId;
        this.targetStationId = targetStationId;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getSourceStationId() {
        return sourceStationId;
    }

    public Long getTargetStationId() {
        return targetStationId;
    }
}
