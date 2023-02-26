enum crossingStatus{
        CROSSED, NOTCROSSED
}
enum vehicleDirection {
    STRAIGHT,
    RIGHT,
    LEFT
}
class Vehicle {
    private String plateId;
    private String segment;
    private String type ;
    private int crossingTime;
    private vehicleDirection direction;
    private crossingStatus status;
    private int length;
    private int emission;

    public Vehicle(String segment, String plateId, String type, int crossingTime,
                   vehicleDirection direction,
                   int length, int emission, crossingStatus status) {
        this.plateId = plateId;
        this.segment = segment;
        this.type = type;
        this.crossingTime = crossingTime;
        this.direction = direction;
        this.status = status;
        this.length = length;
        this.emission = emission;
    }

    public String getPlateId() {
        return plateId;
    }

    public String getSegment() {
        return segment;
    }

    public String getType() {
        return type;
    }

    public int getCrossingTime() {
        return crossingTime;
    }

    public vehicleDirection getDirection() {
        return direction;
    }

    public crossingStatus getStatus() {
        return status;
    }

    public int getLength() {
        return length;
    }

    public int getEmission() {
        return emission;
    }
}
