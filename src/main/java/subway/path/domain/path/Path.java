package subway.path.domain.path;

import java.util.ArrayList;
import java.util.List;

import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.station.domain.Station;

public class Path {
	private List<Station> stations;
	private int distance;

	public Path(List<Station> stations, int distance) {
		this.stations = stations;
		this.distance = distance;
	}

	public List<Station> getStations() {
		return stations;
	}

	public int getDistance() {
		return distance;
	}

	public List<Line> getContainedLines(List<Line> lines) {
		Sections allSections = new Sections();
		lines.forEach(line -> allSections.addSections(line.getSections()));
		List<Line> containedLines = new ArrayList<>();

		for (int i = 0; i < stations.size() - 1; i++) {
			Station upStation = stations.get(i);
			Station downStation = stations.get(i + 1);
			Section section = allSections.getSection(upStation, downStation);
			containedLines.add(lines.stream().filter(line -> line.getSections().hasSection(section)).findFirst().get());
		}
		
		return containedLines;
	}
}
