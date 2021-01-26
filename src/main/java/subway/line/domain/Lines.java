package subway.line.domain;

import java.util.List;

public class Lines {
    private final List<Line> lines;
    private final Sections sections;

    public Lines(List<Line> lines) {
        this.lines = lines;
        sections = addAllSections();
    }

    public List<Line> getLines() {
        return lines;
    }

    public Sections addAllSections(){
        Sections sections = new Sections();
        for(Line line : lines){
            addSections(line,sections);
        }
        return sections;
    }

    private void addSections(Line line, Sections sections) {
        for(Section section : line.getSections().getSections()){
            sections.getSections().add(section);
        }
    }

    public Sections getAllSections() {
        return sections;
    }
}
