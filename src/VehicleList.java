import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class VehicleList {
		
		//vehicles ArrayList
		private ArrayList<Vehicle> vehicles;
		private Phase[] phases ;
		
		public VehicleList()
		{
			vehicles = new ArrayList<Vehicle>() ;
			// To store phases, there are 8 phases in total
			phases = new Phase[8] ;
		
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
		}
		
	    //Add new Vehicle Method
		public void AddNewVehicle(Vehicle vehicle) {
			vehicles.add(vehicle);
			vehicle.setPhase(GetPhase(vehicle));
		}
		
		//Get phase by segment and direction
		private Phase GetPhase(Vehicle vehicle) {
			Phase p = null ;
			switch (vehicle.getSegment()){
			case "S1":
				if (vehicle.getDirection() == Vehicle.VehicleDirection.LEFT) {
					 p = phases[0];
				}
				else {
					p = phases[5];
				}
				break; 
			case "S2":
				if (vehicle.getDirection() == Vehicle.VehicleDirection.LEFT) {
					 p = phases[4];
				}
				else {
					p = phases[1];
				}	
				break; 
			case "S3":
				if (vehicle.getDirection() == Vehicle.VehicleDirection.LEFT) {
					 p = phases[2];
				}
				else {
					p = phases[7];
				}	
				break; 
			case "S4":
				if (vehicle.getDirection() == Vehicle.VehicleDirection.LEFT) {
					 p = phases[6];
				}
				else {
					p = phases[3];
				}	
				break; 
			}
			return p ;
		}
		
	    //return All the vehicle details 
	    public String listDetails()
	    {
	    	StringBuffer allEntries = new StringBuffer();
	        for(Vehicle v : vehicles) {
	            allEntries.append(v);
	            allEntries.append('\n');
	        }
	        return allEntries.toString();
	    }

}