package subway.station.domain;

import java.util.Objects;

public class Station {
    private final Long id;
    private final String name;

    private Station(Long id, String name) {
        validate(name);
        this.id = id;
        this.name = name;
    }

    private void validate(String name) {
        if (isEmpty(name)) {
            throw new IllegalArgumentException("지하철역 이름은 비어있을 수 없습니다.");
        }
    }

    private boolean isEmpty(String name) {
        return name == null || name.isEmpty();
    }

    public static Station from(String name) {
        return new Station(null, name);
    }

    public static Station of(Long id, String name) {
        return new Station(id, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return id.equals(station.id) && name.equals(station.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
