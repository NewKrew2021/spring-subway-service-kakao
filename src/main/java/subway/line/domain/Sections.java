package subway.line.domain;

import subway.common.domain.Distance;
import subway.common.exception.AlreadyExistException;
import subway.common.exception.NotExistException;
import subway.station.domain.Station;

import java.util.*;
import java.util.stream.Collectors;

public class Sections {
    private List<Section> sections = new ArrayList<>();

    public List<Section> getSections() {
        return sections;
    }

    public Sections() {
    }

    public Sections(List<Section> sections) {
        this.sections = sections;
    }

    public void addSection(Section section) {
        if (this.sections.isEmpty()) {
            this.sections.add(section);
            return;
        }

        try {
            checkAlreadyExisted(section);
            checkExistedAny(section);

            addSectionUpToUp(section);
            addSectionDownToDown(section);

            this.sections.add(section);
        } catch (NotExistException | AlreadyExistException e) {
            throw new IllegalStateException("지하철 구간을 추가할 수 없습니다. (" + e.getMessage() +")");
        }
    }

    private void checkAlreadyExisted(Section section) {
        List<Station> stations = getStations();
        if (!stations.contains(section.getUpStation()) && !stations.contains(section.getDownStation())) {
            throw new NotExistException("존재하지 않는 지하철 역입니다.");
        }
    }

    private void checkExistedAny(Section section) {
        List<Station> stations = getStations();
        List<Station> stationsOfNewSection = Arrays.asList(section.getUpStation(), section.getDownStation());
        if (stations.containsAll(stationsOfNewSection)) {
            throw new AlreadyExistException("이미 존재하는 지하철 구간입니다.");
        }
    }

    private void addSectionUpToUp(Section section) {
        this.sections.stream()
                .filter(it -> it.getUpStation().equals(section.getUpStation()))
                .findFirst()
                .ifPresent(it -> replaceSectionWithDownStation(section, it));
    }

    private void addSectionDownToDown(Section section) {
        this.sections.stream()
                .filter(it -> it.getDownStation().equals(section.getDownStation()))
                .findFirst()
                .ifPresent(it -> replaceSectionWithUpStation(section, it));
    }

    private void replaceSectionWithUpStation(Section newSection, Section existSection) {
        if (!newSection.isShorter(existSection)) {
            throw new IllegalStateException("기존 구간은 길이가 더 긴 구간으로 대체될 수 없습니다.");
        }
        this.sections.add(Section.of(existSection.getUpStation(), newSection.getUpStation(), existSection.getDifferenceOfDistance(newSection)));
        this.sections.remove(existSection);
    }

    private void replaceSectionWithDownStation(Section newSection, Section existSection) {
        if (!newSection.isShorter(existSection)) {
            throw new IllegalStateException("기존 구간은 길이가 더 긴 구간으로 대체될 수 없습니다.");
        }
        this.sections.add(Section.of(newSection.getDownStation(), existSection.getDownStation(), existSection.getDifferenceOfDistance(newSection)));
        this.sections.remove(existSection);
    }

    public List<Station> getStations() {
        if (sections.isEmpty()) {
            return Collections.emptyList();
        }

        List<Station> stations = new ArrayList<>();
        Section upEndSection = findUpEndSection();
        stations.add(upEndSection.getUpStation());

        Section nextSection = upEndSection;
        while (nextSection != null) {
            stations.add(nextSection.getDownStation());
            nextSection = findSectionByNextUpStation(nextSection.getDownStation());
        }

        return stations;
    }

    private Section findUpEndSection() {
        List<Station> downStations = this.sections.stream()
                .map(Section::getDownStation)
                .collect(Collectors.toList());

        return this.sections.stream()
                .filter(it -> !downStations.contains(it.getUpStation()))
                .findFirst()
                .orElseThrow(() -> new NotExistException("상행 종점이 존재하지 않습니다."));
    }

    private Section findSectionByNextUpStation(Station station) {
        return this.sections.stream()
                .filter(it -> it.getUpStation().equals(station))
                .findFirst()
                .orElse(null);
    }

    public void removeStation(Station station) {
        if (sections.size() <= 1) {
            throw new IllegalStateException("삭제할 수 있는 지하철 역이 없습니다.");
        }

        Optional<Section> upSection = sections.stream()
                .filter(it -> it.getUpStation().equals(station))
                .findFirst();
        Optional<Section> downSection = sections.stream()
                .filter(it -> it.getDownStation().equals(station))
                .findFirst();

        if (upSection.isPresent() && downSection.isPresent()) {
            Station newUpStation = downSection.get().getUpStation();
            Station newDownStation = upSection.get().getDownStation();
            Distance newDistance = upSection.get().getSumOfDistance(downSection.get());
            sections.add(Section.of(newUpStation, newDownStation, newDistance));
        }

        upSection.ifPresent(it -> sections.remove(it));
        downSection.ifPresent(it -> sections.remove(it));
    }
}
