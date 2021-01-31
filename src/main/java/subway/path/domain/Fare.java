package subway.path.domain;

import java.util.*;
import java.util.stream.Collectors;

public class Fare {
    private int fare;

    public Fare(int fare){
        this.fare = fare;
    }

    public int getFare() {
        return fare;
    }
}
