import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class VehicleList {
	
		// vehicles ArrayList
		private List<Vehicle> vehicles;
		private Phase[] phases = new Phase[8];

		/**
		 * Constructor reads intersection.csv for Phase data and vehicle.csv for data related to Vehicles
		 * @throws CarPlateNumberInvalid - Vehicle class constructor throws this
		 * @throws InvalidInputException - Vehicle class constructor throws this
		 * @throws FileNotFoundException - during vehicle.csv or intersection.csv file read, if file not found
		 */
		public VehicleList() throws CarPlateNumberInvalid, InvalidInputException, FileNotFoundException {
			vehicles = new ArrayList<Vehicle>();
			readPhaseDataFile();
			readVehiclesDataFile();
		}

		/**
		 * It reads intersection.csv file to read phase data
		 * @throws FileNotFoundException - if csv file is not found
		 */
		private void readPhaseDataFile() throws FileNotFoundException {
			String phaseData[] = new String[2];
			int rowNumber = 0; // rowNumber == 0 will be skipped as first row is heading

			BufferedReader buff = new BufferedReader(new FileReader("intersection.csv"));
			try {
				String inputLine = buff.readLine(); // read first line
				while (inputLine != null) {
					if (rowNumber != 0) {
						// split line into parts
						phaseData = inputLine.split(",");
						String phaseNumber = phaseData[0].trim();
						String phaseDuration = phaseData[1].trim();
						// create phase object
						Phase p = new Phase(Integer.parseInt(phaseNumber), Integer.parseInt(phaseDuration));
						// add to list
						phases[rowNumber - 1] = p;
					}
					rowNumber++;
					// read next line
					inputLine = buff.readLine();

				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			} finally {
				try {
					buff.close();
				} catch (IOException ioe) {
					// don't do anything
				}
			}
		}

		/**
		 * @throws FileNotFoundException - if vehicle.csv file not found
		 * @throws CarPlateNumberInvalid - Vehicle constructor throws this if plateNumber is not valid
		 * @throws InvalidInputException - Vehicle constructor throws this if direction, crossingStatus or vehicleType are not valid
		 */
		private void readVehiclesDataFile() throws FileNotFoundException, CarPlateNumberInvalid, InvalidInputException {
			// to store vehicle data - segment, plateNumber, crossingTime etc.
			String data[] = new String[8];
			int rowNumber = 0;
			BufferedReader buff = new BufferedReader(new FileReader("vehicle.csv"));
			try {
				String inputLine = buff.readLine(); // read first line
				while (inputLine != null) {
					if (rowNumber != 0) // rowNumber == 0 , will be skipped as it is just the heading
					{
						// split line into parts
						data = inputLine.split(",");
						// read vehicle info from data array
						String segment = data[0].trim();
						String plateNumber = data[1].trim();
						String vehicleType = data[2].trim();
						String crossingTime = data[3].trim();
						String direction = data[4].trim();
						String length = data[5].trim();
						String emission = data[6].trim();
						String crossingStatus = data[7].trim();

						// create vehicle object
						if (!isDuplicateVehicle(plateNumber)) {
						Vehicle v = new Vehicle(segment, plateNumber, vehicleType, crossingTime, direction, length,
								emission, crossingStatus);
						addNewVehicle(v);
						}
						
					}
					rowNumber++;
					// read next line
					inputLine = buff.readLine();

				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			} finally {
				try {
					buff.close();
				} catch (IOException ioe) {
					// don't do anything
				}
			}
		}

		// Add new Vehicle Method, when new vechicles are added from vehicle.csv
		public void addNewVehicle(Vehicle vehicle)  {
			vehicles.add(vehicle);
			vehicle.setPhase(calculatePhase(vehicle));
		}

		/**
		 * Method overloading - Vehicle parameters can be array of string as well.
		 * 
		 * @param vehicleParameters - GUIClass will pass these to manager, to create Vehicle based on user input
		 * @throws NumberFormatException - if crossingTime, length or emission can't be
		 *                               parsed to valid integer values
		 * @throws CarPlateNumberInvalid - If vehicle with same plateId already there in
		 *                               the list on GUI
		 * @throws InvalidInputException - Vehicle constructor throws this if direction,
		 *                               crossingStatus or type are not valid
		 */
		public void addNewVehicle(String[] vehicleParameters)
				throws NumberFormatException, CarPlateNumberInvalid, InvalidInputException {
			String segment = vehicleParameters[0];
			String plateId = vehicleParameters[1];
			String type = vehicleParameters[2];
			String crossingTime = vehicleParameters[3];
			String direction = vehicleParameters[4];
			String length = vehicleParameters[5];
			String emission = vehicleParameters[6];
			String crossingStatus = vehicleParameters[7];

			// Checking if user entered a Vehicle with plateId which is already there in the
			// list
			if (!isDuplicateVehicle(plateId.toUpperCase())) {
				Vehicle vehicle = new Vehicle(segment, plateId, type, crossingTime, direction, length, emission,
						crossingStatus);
				vehicle.setPhase(calculatePhase(vehicle));
				vehicles.add(vehicle);
			} else {
				throw new CarPlateNumberInvalid("Vehicle with same PlateNumber exists");
			}

		}

		/**
		 * @param plateId to check if Vehicle exists with same plateID
		 */
		private boolean isDuplicateVehicle(String plateId) {
			for (Vehicle v : vehicles) {
				if (v.getPlateId().equals(plateId)) {
					return true;
				}
			}
			return false;
		}

		// Calcualte phase by segment and direction of the Vehicle
		private Phase calculatePhase(Vehicle vehicle) {
			Phase p = null;
			switch (vehicle.getSegment()) {
			case "S1":
				if (vehicle.getDirection() == Vehicle.VehicleDirection.LEFT) {
					p = phases[0];
				} else {
					p = phases[5];
				}
				break;
			case "S2":
				if (vehicle.getDirection() == Vehicle.VehicleDirection.LEFT) {
					p = phases[4];
				} else {
					p = phases[1];
				}
				break;
			case "S3":
				if (vehicle.getDirection() == Vehicle.VehicleDirection.LEFT) {
					p = phases[2];
				} else {
					p = phases[7];
				}
				break;
			case "S4":
				if (vehicle.getDirection() == Vehicle.VehicleDirection.LEFT) {
					p = phases[6];
				} else {
					p = phases[3];
				}
				break;
			}
			return p;
		}

		// return All the vehicle details, not being used right now.
		// For future use
		public String listVehicleDetails() {
			StringBuffer allEntries = new StringBuffer();
			for (Vehicle v : vehicles) {
				allEntries.append(v);
				allEntries.append('\n');
			}
			return allEntries.toString();
		}

		/**
		 * To sort vehicles by Segment For future use, not being used as sorting is done
		 * from GUI
		 */
		public String listVehiclesBySegment() {
			Collections.sort(vehicles);
			return listVehicleDetails();
		}

		/**
		 * To sort vehicles by Status For future use, not being used as sorting is done
		 * from GUI
		 */
		public String listVehiclesByStatus() {
			Collections.sort(vehicles, new VehicleStatusComparator());
			return listVehicleDetails();
		}

		/**
		 * To sort vehicles by Type For future use, not being used as sorting is done
		 * from GUI
		 */
		public String listVehiclesByType() {
			Collections.sort(vehicles, new VehicleTypeComparator());
			return listVehicleDetails();
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
	    	    //System.out.println("total Emissions in Kg: "+ totalEmission/1000);
	    	    return totalEmission ;
	    }
	    
	    //Generate Phase wise report
	    public void phaseSummaryToTextFile() {
	    	String fileName = "PhaseSummary.txt" ;
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
	        
	        //write report to file
	        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
	        	String header = String.format("%-7s%-24s%-28s%s", "Phases", "Total Crossed Vehicles", "Average Waiting Time(s)", "Total Emissions(grams of CO2)");
	            writer.println(header);
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
		            float phaseEmissions = phaseEmissionMap.get(phaseNum);
		            
		            String formattedEmissions = String.format("%.2f", phaseEmissions);
		            String reportLine = String.format("%-7d%-24d%-28.2f%s", phaseNum, totalVehiclesCrossed, avgWaitingTime, formattedEmissions);
		            writer.println(reportLine);
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

	public List<Vehicle> getVehicleList() {
		return vehicles;
	}

	public Phase[] getPhaseList() {
		return phases;
	}
}