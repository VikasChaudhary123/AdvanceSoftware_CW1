import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    
    public enum VehicleDirection {
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
    
    public enum Status{
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
   
    public Vehicle(String segment, String plateId, String type, String crossingTime, String direction, 
    		String length, String emission, String crossingStatus) throws CarPlateNumberInvalid {
    	this.segment = segment.toUpperCase();
    	
    	// https://www.javatpoint.com/java-regex
    	// https://gist.github.com/danielrbradley/7567269
    	String pattern = "(^[A-Z]{2}[0-9]{2}\\s?[A-Z]{3}$)|(^[A-Z][0-9]{1,3}[A-Z]{3}$)|(^[A-Z]{3}[0-9]{1,3}[A-Z]$)|(^[0-9]{1,4}[A-Z]{1,2}$)|(^[0-9]{1,3}[A-Z]{1,3}$)|(^[A-Z]{1,2}[0-9]{1,4}$)|(^[A-Z]{1,3}[0-9]{1,3}$)|(^[A-Z]{1,3}[0-9]{1,4}$)|(^[0-9]{3}[DX]{1}[0-9]{3}$)\r\n"
    			+ "" ;
    	Pattern p = Pattern.compile(pattern) ;
    	Matcher m = p.matcher(plateId);  
    	if (m.matches())
    	{
    		this.plateId = plateId;
    	}else {
    		throw new CarPlateNumberInvalid("Car plate number is not valid") ;
    	}
    	
        this.type = type;
        this.crossingTime = Integer.parseInt(crossingTime);
        this.direction = VehicleDirection.directionLookup(direction);
        this.length = Integer.parseInt(length);
        this.emission = Integer.parseInt(emission);
        this.crossingStatus = Status.StatusLookup(crossingStatus);

    }
    
    
    /**
     * @return A  string containing all details.
     */
    public String toString()
    {
//        return String.format("%-5s", id ) + String.format("%-20s", name) +
//                 String.format("%5d", hoursWorked );
    	
    	return 	String.format("%-4s",segment)+
    			plateId+" " + 
    			String.format("%-7s",type)+ 
    			String.format("%-5s",crossingTime)+
    			String.format("%-10s",direction)+
    			String.format("%-5s",length)+
    			String.format("%-5s",emission)+
    			String.format("%-9s",crossingStatus)+
    			String.format("%-10s",phase);
    }
    public void setPhase(Phase p) {
    	phase = p ; 
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
