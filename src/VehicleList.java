import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;

public class VehicleList {
		
		//vehicles ArrayList
		private List<Vehicle> vehicles;
		private Phase[] phases = new Phase[8];
		
		public VehicleList() throws IOException, CarPlateNumberInvalid, InvalidInputException
		{
			vehicles = new ArrayList<Vehicle>() ;
			readPhaseDataFile();
			readVehiclesDataFile();
		}
		
		private void readPhaseDataFile() throws FileNotFoundException{
			String phaseData [] = new String[2];
			int rowNumber = 0 ;
			
				BufferedReader buff = new BufferedReader(new FileReader("intersection.csv"));
				try {
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
			catch(IOException e){
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
		
		private void readVehiclesDataFile() throws FileNotFoundException, CarPlateNumberInvalid, InvalidInputException{
	        // to store vehicle data - segment, plateNumber, crossingTime etc. 
	    	String data [] = new String[8];
	    	int rowNumber = 0 ;
	    	BufferedReader buff = new BufferedReader(new FileReader("vehicle.csv"));
	    	try {
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
			    		addNewVehicle(v);
		    		}
		    		rowNumber++ ;
		            //read next line
		            inputLine = buff.readLine();
		            
		        }
	    	}
	    	catch(IOException e){
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
		public void addNewVehicle(Vehicle vehicle) {
			vehicles.add(vehicle);
			vehicle.setPhase(setPhase(vehicle));
		}
		
		public void addNewVehicle(String[] vehicleParameters ) 
	    		throws NumberFormatException, CarPlateNumberInvalid, InvalidInputException {
			String segment = vehicleParameters[0] ;
			String plateId = vehicleParameters[1];
			String type = vehicleParameters[2];
			String crossingTime = vehicleParameters[3];
			String direction = vehicleParameters[4] ; 
    		String length = vehicleParameters[5];
    		String emission = vehicleParameters[6] ;
    		String crossingStatus = vehicleParameters[7] ;
    		
    		if (!isDuplicateVehicle(plateId)){
    			Vehicle vehicle = new Vehicle(segment, plateId, type, crossingTime, 
    					direction, length, emission, crossingStatus) ;
    			vehicle.setPhase(setPhase(vehicle));
    			vehicles.add(vehicle);
    		}
    		else {
    			throw new CarPlateNumberInvalid("Vehicle with same PlateNumber exists") ;
    		}
    		
			
		}
		
		private boolean isDuplicateVehicle(String plateId) {
			for (Vehicle v : vehicles) {
				if (v.getPlateId().equals(plateId)) {
					return true;
				}
			}
			return false;
		}
		
		//Get phase by segment and direction
		private Phase setPhase(Vehicle vehicle) {
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
	    public String listVehicleDetails() {
	    	StringBuffer allEntries = new StringBuffer();
	        for(Vehicle v : vehicles) {
	            allEntries.append(v);
	            allEntries.append('\n');
	        }
	        return allEntries.toString();
	    }
	    
	    public String listVehiclesBySegment() {
	    	Collections.sort(vehicles);
	    	return listVehicleDetails() ;
	    }
	    
	    public String listVehiclesByStatus() {
	    	Collections.sort(vehicles, new VehicleStatusComparator());
	    	return listVehicleDetails() ;
	    }
	    
	    public String listVehiclesByType() {
	    	Collections.sort(vehicles, new VehicleTypeComparator());
	    	return listVehicleDetails() ;
	    }
	    
	    
	    //Get totalEmissions Method
	    public float statsCo2() {
	    	float totalEmission = 0;
	    	
	    	Map<Integer, List<Vehicle>> phaseVehicleMap = new HashMap<>();
	    	for (Vehicle v : vehicles) {
	    	    int currentPhase = v.getPhase().getPhaseNumber();
	    	    if(!phaseVehicleMap.containsKey(currentPhase)) {
	    	    	phaseVehicleMap.put(currentPhase, new ArrayList<Vehicle>()) ;
	    	    } 
	    	    	phaseVehicleMap.get(currentPhase).add(v) ;
	    		} 
	    	   for (Map.Entry<Integer, List<Vehicle>> entry: phaseVehicleMap.entrySet()) {
	    		 // int phaseNum = entry.getKey() ;
	    		  List<Vehicle> vList = entry.getValue() ;
	    		  int waitingTime=0;
	    		  for (int i=0; i< vList.size(); i++)
	    		  {
	    			  Vehicle vh = vList.get(i) ;
	    			  int crossingTime = vh.getCrossingTime();
	    			  float Emissions= vh.getEmission();
	    			  waitingTime += crossingTime;
	    			  totalEmission += Emissions * waitingTime/60;
	    		  }  
	    	   }
	    	    System.out.println("total Emissions in Kg: "+ totalEmission/1000);
	    	    return totalEmission ;
	    }
	    
	    //Generate Phase wise report
	    public void phaseSummary(String filename) {
	    	
	    	Map<Integer, List<Vehicle>> phaseVehicleMap = new HashMap<>();
	    	Map<Integer, Float> phaseEmissionMap = new HashMap<>();
	    	
	    	for (int i = 1; i <= 8; i++) {
	            phaseVehicleMap.put(i, new ArrayList<Vehicle>());
	            phaseEmissionMap.put(i, 0f);
	        }
	        
	        for (Vehicle v : vehicles) {
	            if (v.getStatus() == Vehicle.Status.CROSSED) {
	                int currentPhase = v.getPhase().getPhaseNumber();
	                phaseVehicleMap.get(currentPhase).add(v);
	                
	                float currentEmission = phaseEmissionMap.get(currentPhase);
	                currentEmission += (v.getEmission() * v.getCrossingTime() / 60f);
	                phaseEmissionMap.put(currentPhase, currentEmission);
	            }
	        }
	        
	        // write report to file
	        try (PrintWriter writer = new PrintWriter(new File(filename))) {
	        	for (Map.Entry<Integer, List<Vehicle>> entry : phaseVehicleMap.entrySet()) {
		            int phaseNum = entry.getKey();
		            List<Vehicle> vList = entry.getValue();
		            
		            float totalWaitingTime = 0;
		            int totalVehiclesCrossed = vList.size();
		            
		            for (int i = 0; i < totalVehiclesCrossed; i++) {
		                Vehicle vh = vList.get(i);
		                int crossingTime = vh.getCrossingTime();
		                totalWaitingTime += crossingTime;
		            }
		            
		            float avgWaitingTime = totalVehiclesCrossed > 0 ? totalWaitingTime / totalVehiclesCrossed : 0;
		            float totalEmissions = phaseEmissionMap.get(phaseNum);
		            String formattedEmissions = String.format("%.2f", totalEmissions);
		            
		            //System.out.println("Phase " + phaseNum + ": " + totalVehiclesCrossed + " vehicles crossed, Average waiting time: " + avgWaitingTime + " seconds, Total emissions: " + formattedEmissions + " grams of CO2");
		            writer.println("Phase " + phaseNum + ": " + totalVehiclesCrossed + " vehicles crossed, Average waiting time: " + avgWaitingTime + " seconds, Total emissions: " + formattedEmissions + " grams of CO2");
		        }
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	    }
	    
	    //Get SegmentSummary Method
	    public Map<String,Object> segmentSummary() 
	    {  
	        int[] counts = new int[4];
	        int[] lengths = new int[4];
	        int[] crossingTimes = new int[4];
	        float[] TotalEmission = new float[4];
	        
	        for (Vehicle v : vehicles) {
	            if (v.getStatus() == Vehicle.Status.WAITING) {
	                String segment = v.getSegment();
	                int length = v.getLength();
	                int crossingTime = v.getCrossingTime();
	                float Emissions= v.getEmission();
	                
	                switch (segment) {
	                    case "S1":
	                        counts[0]++;
	                        lengths[0] += length; 
	                        crossingTimes[0] += crossingTime;
	                        TotalEmission[0] += Emissions * crossingTimes[0]/60;
	                        break;
	                    case "S2":
	                        counts[1]++;
	                        lengths[1] += length;
	                        crossingTimes[1] += crossingTime;
	                        TotalEmission[1] += Emissions * crossingTimes[1]/60;
	                        break;
	                    case "S3":
	                        counts[2]++;
	                        lengths[2] += length; 
	                        crossingTimes[2] += crossingTime;
	                        TotalEmission[2] += Emissions * crossingTimes[2]/60;
	                        break;
	                    case "S4":
	                        counts[3]++;
	                        lengths[3] += length; 
	                        crossingTimes[3] += crossingTime;
	                        TotalEmission[3] += Emissions * crossingTimes[3]/60;
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
	        float TotalEmissions = 0;
	        for (int i = 0; i < 4; i++) {
	        	TotalEmissions += TotalEmission[i];
	            double avgTime = counts[i] > 0 ? (double) crossingTimes[i] / counts[i] : 0;
	            String keyPrefix = "S"+ Integer.toString(i+1)+ " ";
	            dataMap.put(keyPrefix + "Number of Waiting Vehicle", counts[i]);
	            dataMap.put(keyPrefix + "Length of Waiting Vehicle", lengths[i]);
	            dataMap.put(keyPrefix + "Avg Crossing Time", avgTime);
	            dataMap.put(keyPrefix + "Total Co2 Emission of Waiting Vehicle", TotalEmissions);
	           
	        }
	        return dataMap;
	    }

    	public List<Vehicle> getVehicleList() {return vehicles;}
    	public Phase[] getPhaseList() {return phases;}
}