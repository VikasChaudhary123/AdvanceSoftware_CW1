import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ManagerClass {

	public VehicleList vehicleList ;
	private Phase[] phases ;
	
	
	public ManagerClass()
	{
		// Initialise empty list of vehicles
		vehicleList = new VehicleList() ;
		// To store phases, there are 8 phases in total
		phases = new Phase[8] ;
//        //this will handle text interaction with user
//        interaction = new StaffListInterface(entries);
        BufferedReader buff = null;
        String phaseData [] = new String[2];
        try {
        	int rowNumber = 0 ;
        	buff = new BufferedReader(new FileReader("intersection.csv"));
	    	String inputLine = buff.readLine();  //read first line
	    	while(inputLine != null){  
	    		if (rowNumber != 0)
	    		{
		    		//split line into parts
		    		phaseData  = inputLine.split(",");
		    		String phaseNumber = phaseData[0].trim() ;
		    		String phaseDuration = phaseData[1].trim() ;
		    		//create phase object
		    		Phase p = new Phase(Integer.parseInt(phaseNumber),
		    				Integer.parseInt(phaseDuration)) ;
		    		//add to list
		            phases[rowNumber-1] = p ;
	    		}
	    		rowNumber++ ;
	            //read next line
	            inputLine = buff.readLine();
	            
	        }
        }
        catch(FileNotFoundException e) {
        	System.out.println(e.getMessage());
            System.exit(1);
        }
        catch (IOException e) {
        	e.printStackTrace();
            System.exit(1);        	
        }
        finally {
        	try{
        		buff.close();
        	}
        	catch (IOException ioe) {
        		//don't do anything
        	}
        }
        
        buff = null ;
        // to store vehicle data - segment, plateNumber, crossingTime etc. 
    	String data [] = new String[8];
        try {
        	int rowNumber = 0 ;
        	buff = new BufferedReader(new FileReader("vehicle.csv"));
	    	String inputLine = buff.readLine();  //read first line
	    	while(inputLine != null){  
	    		if (rowNumber != 0)
	    		{
		    		//split line into parts
		    		data  = inputLine.split(",");
		    		// read vehicle info from data array
		    		String segment = data[0].trim(); 
		    		String plateNumber = data[1].trim() ;
		    		String vehicleType = data[2].trim() ;
		    		String crossingTime = data[3].trim() ;
		    		String direction = data[4].trim() ;
		    		String length = data[5].trim() ;
		    		String emission = data[6].trim() ;
		    		String crossingStatus = data[7].trim() ;
		    		
		    		//create vehicle object
		    		Vehicle v = new Vehicle(segment, plateNumber, vehicleType, crossingTime, direction, 
		    				length, emission, crossingStatus) ;
		    		vehicleList.AddNewVehicle(v);
	    		}
	    		rowNumber++ ;
	            //read next line
	            inputLine = buff.readLine();
	            
	        }
        }
        catch(FileNotFoundException e) {
        	System.out.println(e.getMessage());
            System.exit(1);
        }
        catch (IOException e) {
        	e.printStackTrace();
            System.exit(1);        	
        }
        catch (NumberFormatException nfe) {
        	System.out.println(data[0] + ": Hours worked not a number :" + data[2]);
        	System.out.println("Program stopped");
        	System.exit(1);
        } catch (CarPlateNumberInvalid e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally {
        	try{
        		buff.close();
        	}
        	catch (IOException ioe) {
        		//don't do anything
        	}
        }
        
   
	}
	
	public VehicleList getVehicleList() {
		return vehicleList ;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ManagerClass manager = new ManagerClass() ;
		manager.vehicleList.SegmentSummary();
		
		// Passing reference of vehicleList to GUIClass constructor
		GUIClass gui = new GUIClass(manager.getVehicleList()) ;
		
	}

}
