import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vehicle implements Comparable<Vehicle>{
	
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
    		String length, String emission, String crossingStatus) 
    				throws CarPlateNumberInvalid, NumberFormatException {
    	
    	String seg = segment.trim().toUpperCase() ;
    	if (seg.length()==2 && seg.charAt(0)=='S')
    	{
    		int ascii = seg.charAt(1) ; // character to ascii value
    		int ascii1 = (int) '1' ; // ascii value of 1
    		int ascii8 = (int) '8' ; // ascii value of 8
    		if  (ascii1<=ascii && ascii<=ascii8) //ascii value should be between[1,8]
    		{
    			this.segment = seg ;
    		}
    		else {
    			throw new IllegalArgumentException("Segment can only be S1, S2, S3, S4, S5, S6, S7 or S8") ;    		
        	}
    		
    	}
    	else {
    		throw new IllegalArgumentException("Segment can only be S1, S2, S3, S4, S5, S6, S7 or S8") ;
    	}
    	
    	// https://www.javatpoint.com/java-regex
    	// https://gist.github.com/danielrbradley/7567269
    	String pattern = "(^[A-Z]{2}[0-9]{2}\\s?[A-Z]{3}$)|(^[A-Z][0-9]{1,3}[A-Z]{3}$)|(^[A-Z]{3}[0-9]{1,3}[A-Z]$)|(^[0-9]{1,4}[A-Z]{1,2}$)|(^[0-9]{1,3}[A-Z]{1,3}$)|(^[A-Z]{1,2}[0-9]{1,4}$)|(^[A-Z]{1,3}[0-9]{1,3}$)|(^[A-Z]{1,3}[0-9]{1,4}$)|(^[0-9]{3}[DX]{1}[0-9]{3}$)\r\n"
    			+ "" ;
    	Pattern p = Pattern.compile(pattern) ;
    	Matcher m = p.matcher(plateId.trim().toUpperCase());  
    	if (m.matches())
    	{
    		this.plateId = plateId.trim().toUpperCase();
    	}else {
    		throw new CarPlateNumberInvalid("Car plate number is not valid") ;
    	}
    	
    	
        this.type = type.trim().toUpperCase();
        
        //https://www.freecodecamp.org/news/java-string-to-int-how-to-convert-a-string-to-an-integer/#:~:text=Use%20Integer.parseInt()%20to%20Convert%20a%20String%20to%20an%20Integer&text=If%20the%20string%20does%20not,inside%20the%20try%2Dcatch%20block.
        if(!IsNumeric(crossingTime.trim())) {
        	throw new NumberFormatException("CrossingTime not a valid integer") ;
        }
        this.crossingTime = Integer.parseInt(crossingTime);
        
        if(!IsNumeric(length.trim())) {
        	throw new NumberFormatException("Length not a valid integer") ;
        }
        this.length = Integer.parseInt(length.trim());
        if(!IsNumeric(emission.trim())) {
        	throw new NumberFormatException("Emission not a valid integer") ;
        }
        this.emission = Integer.parseInt(emission.trim());
        
        VehicleDirection d = VehicleDirection.directionLookup(direction.trim().toUpperCase()); 
        if (d == null)
        {
        	throw new IllegalArgumentException("Vehicle direction can only be left, right or straight") ;
        }
        else {
        	this.direction = d ;
        }
        
        Status s = Status.StatusLookup(crossingStatus.trim().toUpperCase()) ;
        if (s == null)
        {
        	throw new IllegalArgumentException("Crossing Status can only be crossed, waiting");
        }
        this.crossingStatus = s; 
    }
    
    private boolean IsNumeric(String str)
    {
    	if (str==null || str.isEmpty() || !str.matches("[0-9.]+"))
    	{
    		return false ;
    	}
    	return true ;
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
    
    public boolean equals(Object other)
    {
        if(other instanceof Vehicle) {
            Vehicle otherVehicle = (Vehicle) other;
            return segment.equals(otherVehicle.getSegment());
        }
        else {
            return false;
        }
    }
    
    /**
     * Compare this Staff object against another, for the purpose
     * of sorting. The fields are compared by id.
     * @param otherDetails The details to be compared against.
     * @return a negative integer if this id comes before the parameter's id,
     *         zero if they are equal and a positive integer if this
     *         comes after the other.
     */
    public int compareTo(Vehicle otherDetails)
    {
        return segment.compareTo(otherDetails.getSegment());
    }
    
    public void setPhase(Phase p) {
    	phase = p ; 
    }
    
    public Phase getPhase() {
    	return phase ;
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
