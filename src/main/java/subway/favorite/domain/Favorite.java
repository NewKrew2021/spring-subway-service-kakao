package subway.favorite.domain;

import java.util.Objects;

public class Favorite {
    private Long id;
    private final Long memberId;
    private final Long sourceStationId;
    private final Long targetStationId;

    public Favorite(Long id, Long memberId, Long sourceStationId, Long targetStationId) {
        this.id = id;
        this.memberId = memberId;
        this.sourceStationId = sourceStationId;
        this.targetStationId = targetStationId;
    }

    public Favorite(Long memberId, Long sourceStationId, Long targetStationId) {
        this.memberId = memberId;
        this.sourceStationId = sourceStationId;
        this.targetStationId = targetStationId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorite favorite = (Favorite) o;
        return Objects.equals(id, favorite.id) && Objects.equals(memberId, favorite.memberId) && Objects.equals(sourceStationId, favorite.sourceStationId) && Objects.equals(targetStationId, favorite.targetStationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, memberId, sourceStationId, targetStationId);
    }
}
