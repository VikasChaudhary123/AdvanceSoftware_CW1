public class Vehicle {
	
	private Phase phase ;
	private String segment;
    private String plateId;
    private String type ;
    private int crossingTime;
    private VehicleDirection direction;
    private int length;
    private int emission;
    private Status crossingStatus;    
    
    private enum VehicleDirection {
    	STRAIGHT,
    	RIGHT,
    	LEFT;
    	
    	//string to Enum ignore case
    	//https://stackoverflow.com/questions/28332924/case-insensitive-matching-of-a-string-to-a-java-enum
	    public static VehicleDirection directionLookup(String direction) {
	    	for (VehicleDirection d : VehicleDirection.values()) {
	    		if (d.name().equalsIgnoreCase(direction)) {
	    			return d;
				}
			}
	    	return null;
		}
    }
    
    private enum Status{
    	CROSSED,
    	WAITING;
    	
    	//string to Enum ignore case
    	//https://stackoverflow.com/questions/28332924/case-insensitive-matching-of-a-string-to-a-java-enum
    	public static Status StatusLookup(String status) {
    		for (Status s : Status.values()) {
    			if (s.name().equalsIgnoreCase(status)) {
    				return s;
				}
			}
    		return null;
		}
	}
   
    public Vehicle(String segment, String plateId, String type, String crossingTime, String direction, String length, String emission, String crossingStatus) {
    	this.segment = segment;
    	this.plateId = plateId;
        this.type = type;
        this.crossingTime = Integer.parseInt(crossingTime);
        this.direction = VehicleDirection.directionLookup(direction);
        this.length = Integer.parseInt(length);
        this.emission = Integer.parseInt(emission);
        this.crossingStatus = Status.StatusLookup(crossingStatus);
        
//        this.phase = 
    }
    
//    private Phase CalculatePhase(String segment, VehicleDirection d)
//    {
////    	switch (segment) {
//    		case "S1":
//    			
//    			break ;
//    	}
//    }
    
    /**
     * @return A  string containing all details.
     */
    public String toString()
    {
//        return String.format("%-5s", id ) + String.format("%-20s", name) +
//                 String.format("%5d", hoursWorked );
    	
    	return segment+" " +plateId+" " + type+" " + crossingTime+" "+direction+" " + length+" "+emission+" "+crossingStatus ;
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

    public VehicleDirection getDirection() {
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
