import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ManagerClass {

	private VehicleList vehicleList ;
	
	
	
	public ManagerClass()
	{
		// Initialise empty list of vehicles
		vehicleList = new VehicleList() ;
        
        
        BufferedReader buff = null ;
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
		    		//create vehicle object
		    		Vehicle v = new Vehicle(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim(), 
		    				data[5].trim(), data[6].trim(), data[7].trim());
		    		//add to list
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
        }
        finally {
        	try{
        		buff.close();
        	}
        	catch (IOException ioe) {
        		//don't do anything
        	}
        }
        
        System.out.println(vehicleList.listDetails()) ;
        
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ManagerClass manager = new ManagerClass() ;
		
	}

}
