package subway.path.domain;

public enum DistanceExtraFare {
    DEFAULT(0,0,1250),
    BELOW_50KM(10,5,100),
    EXCEED_50KM(50, 8,100)
    ;

    private static final int TOTAL_FARE_DAFAULT = 0;
    private static final int REST_ZERO = 0;

    private int minDistance;
    private int period;
    private int extraFare;

    DistanceExtraFare(int minDistance,int period, int extraFare) {
        this.minDistance = minDistance;
        this.period = period;
        this.extraFare = extraFare;
    }

    public int getMinDistance() {
        return minDistance;
    }

    public int getPeriod() {
        return period;
    }

    public int getExtraFare() {
        return extraFare;
    }

    public static int getTotalFare(int distance){
        int calculatedDistance = distance;
        int totalFare = TOTAL_FARE_DAFAULT;

        if(calculatedDistance > EXCEED_50KM.getMinDistance()){
            totalFare += getTotalExtraFare(calculatedDistance, EXCEED_50KM);
            calculatedDistance = EXCEED_50KM.getMinDistance();
        }

        if(calculatedDistance > BELOW_50KM.getMinDistance()){
            totalFare += getTotalExtraFare(calculatedDistance, BELOW_50KM);
        }

        return totalFare + DEFAULT.getExtraFare();
    }

    private static int getTotalExtraFare(int distance, DistanceExtraFare distanceExtraFare){
        int periodCount = (distance - distanceExtraFare.getMinDistance()) / distanceExtraFare.getPeriod();

        if(isStatusToAddOnePeriodCount(distance,distanceExtraFare)){
            periodCount++;
        }

        return periodCount * distanceExtraFare.getExtraFare();
    }

    private static boolean isStatusToAddOnePeriodCount(int distance, DistanceExtraFare distanceExtraFare){
        return (distance - distanceExtraFare.getMinDistance()) % distanceExtraFare.getPeriod() != REST_ZERO;
    }
}
