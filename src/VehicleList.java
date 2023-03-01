import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;

public class VehicleList {
		
		//vehicles ArrayList
		private List<Vehicle> vehicles;
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
		
		public void AddNewVehicle(String segment, String plateId, String type, String crossingTime, String direction, 
	    		String length, String emission, String crossingStatus) 
	    		throws NumberFormatException, CarPlateNumberInvalid {
			Vehicle vehicle = new Vehicle(segment, plateId, type, crossingTime, 
					direction, length, emission, crossingStatus) ;
			vehicle.setPhase(GetPhase(vehicle));
			vehicles.add(vehicle);
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
	    public String[][] listDetails()
	    {
//	    	StringBuffer allEntries = new StringBuffer();
//	        for(Vehicle v : vehicles) {
//	            allEntries.append(v);
//	            allEntries.append('\n');
//	        }
//	        return allEntries.toString();
	        
	        String[][] arr = new String[vehicles.size()][8];
	        int i = 0;
	        for (Vehicle v : vehicles) {
	            arr[i][0] = v.getSegment();
	            arr[i][1] = v.getPlateId();
	            arr[i][2] = v.getType();
	            arr[i][3] = Integer.toString(v.getCrossingTime());
	            arr[i][4] = v.getDirection().toString();
	            arr[i][5] = Integer.toString(v.getLength());
	            arr[i][6] = Integer.toString(v.getEmission());
	            arr[i][7] = v.getStatus().toString();
	            i++;
	        } 
	        return arr ;
	    }
	    
	    public String ListVehicleDetails() {
	    	StringBuffer allEntries = new StringBuffer();
	        for(Vehicle v : vehicles) {
	            allEntries.append(v);
	            allEntries.append('\n');
	        }
	        return allEntries.toString();
	    }
	    
	    public String ListVehiclesBySegment() {
	    	Collections.sort(vehicles);
	    	return ListVehicleDetails() ;
	    }
	    
	    public String ListVehiclesByStatus() {
	    	Collections.sort(vehicles, new VehicleStatusComparator());
	    	return ListVehicleDetails() ;
	    }
	    
	    public String ListVehiclesByType() {
	    	Collections.sort(vehicles, new VehicleTypeComparator());
	    	return ListVehicleDetails() ;
	    }
	    
	    public String[][] listPhases(){
	    	String[][] arr = new String[phases.length][8];
	    	for(int i=0; i<8; i++)
	    	{
	    		Phase p = phases[i] ;
	    		arr[i][0] = Integer.toString(p.getPhaseNumber()) ;
	    		arr[i][1] = Integer.toString(p.getPhaseDuration()) ;

	    	}
	    	return arr ;
	    }
	    
	    //Get totalEmissions Method
	    public float StatsCo2() 
	    {
	    	float totalEmissions = 0;
	    	for (Vehicle vehicle : vehicles) 
	    	{
	    		totalEmissions += vehicle.getEmission();
	    	}
	    	return totalEmissions/1000; //Get totalEmissions in KG
	    }

	    //Get SegmentSummary Method
	    public Map<String, Object>  SegmentSummary() 
	    {  
	        int[] counts = new int[4];
	        int[] lengths = new int[4];
	        int[] crossingTimes = new int[4];
	        
	        for (Vehicle v : vehicles) {
	            if (v.getStatus() == Vehicle.Status.WAITING) {
	                String segment = v.getSegment();
	                int length = v.getLength();
	                int crossingTime = v.getCrossingTime();
	                
	                switch (segment) {
	                    case "S1":
	                        counts[0]++;
	                        lengths[0] += length; 
	                        crossingTimes[0] += crossingTime;
	                        break;
	                    case "S2":
	                        counts[1]++;
	                        lengths[1] += length;
	                        crossingTimes[1] += crossingTime;
	                        break;
	                    case "S3":
	                        counts[2]++;
	                        lengths[2] += length; 
	                        crossingTimes[2] += crossingTime;
	                        break;
	                    case "S4":
	                        counts[3]++;
	                        lengths[3] += length; 
	                        crossingTimes[3] += crossingTime;
	                        break;
	                }
	            }
	        }
	        double[] avgTimes = new double[4];
	        for (int i = 0; i < 4; i++) {
	            avgTimes[i] = counts[i] > 0 ? (double) crossingTimes[i] / counts[i] : 0;
	        }
	        
	        // create a new HashMap to hold the key-value pairs
	        Map<String, Object> dataMap = new HashMap<>();
	        
	        // add the key-value pairs to the map
//	        String[] segments = {"S1", "S2", "S3", "S4"};
	        
	        for (int i = 0; i < 4; i++) {
	            double avgTime = counts[i] > 0 ? (double) crossingTimes[i] / counts[i] : 0;
//	            String keyPrefix = segments[i] + " ";
	            String keyPrefix = "S"+ Integer.toString(i+1)+ " ";
	            dataMap.put(keyPrefix + "Number of Waiting Vehicle", counts[i]);
	            dataMap.put(keyPrefix + "Length of Waiting Vehicle", lengths[i]);
	            dataMap.put(keyPrefix + "Avg Crossing Time", avgTime);
	           
	        }
	        return dataMap;
	    }
	     public List<Vehicle> getVehicleList() {return vehicles;}
	     public Phase[] getPhaseList() {return phases;}
	   
}