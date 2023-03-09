import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

  public class JUnitTesting {

	// test to check totalCrossedVehicleEmissions(PhaseSummary)
	    @Test
	    public void totalCrossedVehicleEmissions() throws InvalidInputException, IOException, CarPlateNumberInvalid {
	    	
	    	List<Vehicle> vehicles = new ArrayList<Vehicle>();
	        float totalPhaseEmissions = 0f;
	        
	        for (Vehicle v : vehicles) {
	            if (v.getStatus() == Vehicle.Status.CROSSED) {
	            	totalPhaseEmissions += (v.getEmission() * v.getCrossingTime() / 60f);
	            }
	        }
	        
      	VehicleList vehicleList = new VehicleList();
	        float statsCo2 = vehicleList.statsCo2();
	        
	        Assert.assertTrue("Total phase emissions should be less than or equal to StatsCo2", totalPhaseEmissions <= statsCo2);
	    }
	    
	 // test to check totalWaitingVehicleEmissions(SegmentSummary)
	    @Test
	    public void totalWaitingVehicleEmissions() throws InvalidInputException, IOException, CarPlateNumberInvalid {
	    	
	    	List<Vehicle> vehicles = new ArrayList<Vehicle>();
	        float totalSegmentEmissions = 0f;
	        
	        for (Vehicle v : vehicles) {
	            if (v.getStatus() == Vehicle.Status.WAITING) {
	            	totalSegmentEmissions += (v.getEmission() * v.getCrossingTime() / 60f);
	            }
	        }
	        
      	VehicleList vehicleList = new VehicleList();
	        float statsCo2 = vehicleList.statsCo2();
	        
	        Assert.assertTrue("Total segment emissions should be less than or equal to StatsCo2", totalSegmentEmissions <= statsCo2);
	    }
	    
      // checking that the  valid vehicle can be created successfully or not
	    @Test
	    public void testCreatingOfValidVehicle() throws Exception{
	        // instance of a vehicle
	        Vehicle vehicle = new Vehicle("S1", "AB1234", "CAR", "10", "LEFT", "2", "50", "CROSSED");
	
	        // getters returning the exact values or not
	        Assertions.assertEquals("S1", vehicle.getSegment());
	        Assertions.assertEquals("AB1234", vehicle.getPlateId());
	        Assertions.assertEquals("CAR", vehicle.getType());
	        Assertions.assertEquals(10, vehicle.getCrossingTime());
	        Assertions.assertEquals(Vehicle.VehicleDirection.LEFT, vehicle.getDirection());
	        Assertions.assertEquals(2, vehicle.getLength());
	        Assertions.assertEquals(50, vehicle.getEmission());
	        Assertions.assertEquals(Vehicle.Status.CROSSED, vehicle.getStatus());
	    }

	 // test to check that a Vehicle with an invalid plate number throws a CarPlateNumberInvalid exception
	    @Test
	    public void testInvalidPlateNumber() {
	        Assertions.assertThrows(CarPlateNumberInvalid.class, () -> new Vehicle("S1", "1234", "CAR", "10", "LEFT", "2", "50", "CROSSED"));
	    }

	    
	 // test to check that a Vehicle with an invalid type throws a InvalidInputException
	    @Test
	    public void testInvalidType() {
	        Assertions.assertThrows(InvalidInputException.class, () -> new Vehicle("S1", "AB1234", "JEEP", "10", "LEFT", "2", "50", "CROSSED"));
	    }

	    
	 // test to check that a Vehicle with an invalid crossing time throws a NumberFormatException or not
	    @Test
	    public void testInvalidCrossingTime() {
	        Assertions.assertThrows(NumberFormatException.class, () -> new Vehicle("S1", "AB1234", "CAR", "ten", "LEFT", "2", "50", "CROSSED"));
	    }

	    
	 // test to check that a Vehicle with an invalid length throws a NumberFormatException
	    @Test
	    public void testInvalidLength() {
	        Assertions.assertThrows(NumberFormatException.class, () -> new Vehicle("S1", "AB1234", "CAR", "10", "LEFT", "two", "50", "CROSSED"));
	    }

	    
	 // test to check that a Vehicle with an invalid emission throws a NumberFormatException
	    @Test
	    public void testInvalidEmission() {
	        Assertions.assertThrows(NumberFormatException.class, () -> new Vehicle("S1", "AB1234", "CAR", "10", "LEFT", "2", "fifty", "CROSSED"));
	    }

	    
	 // test to check that a Vehicle with an invalid Direction throws a InvalidInputException
	    @Test
	    public void testInvalidDirection() {
	        Assertions.assertThrows(InvalidInputException.class, () -> new Vehicle("S1", "AB1234", "CAR", "10", "FORWARD", "2", "50", "CROSSED"));
	    }
	    
	    
	 // test to check that a Vehicle with an invalid status throws a InvalidInputException
	    @Test
	    public void testInvalidStatus() {
	        Assertions.assertThrows(InvalidInputException.class, () -> new Vehicle("S1", "AB1234", "CAR", "10", "LEFT", "2", "50", "MOVING"));
	    }
	    
	}
  