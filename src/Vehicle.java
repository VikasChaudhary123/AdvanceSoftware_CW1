public class Vehicle {
	private String segment;
    private String plateId;
    private String type ;
    private int crossingTime;
    private vehicleDirection direction;
    private int length;
    private int emission;
    private Status crossingStatus;    
    
    private enum vehicleDirection {
	    STRAIGHT,
	    RIGHT,
	    LEFT
	}
    
    private enum Status{
        CROSSED, 
        NOTCROSSED
    }
   
    public Vehicle(String segment, String plateId, String type, String crossingTime, String direction, String length, String emission, String crossingStatus) {
    	this.segment = segment;
    	this.plateId = plateId;
        this.type = type;
        this.crossingTime = Integer.parseInt(crossingTime);
        this.direction = vehicleDirection.valueOf(direction);
        this.length = Integer.parseInt(crossingStatus);
        this.emission = Integer.parseInt(emission);
        this.crossingStatus = Status.valueOf(crossingStatus);
    }
    
    public String getSegment() {
        return segment;
    }
    
    public String getPlateId() {
        return plateId;
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
    
    public int getLength() {
        return length;
    }

    public int getEmission() {
        return emission;
    }

    public Status getStatus() {
        return crossingStatus;
    }
}
