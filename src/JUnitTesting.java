import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class JUnitTesting {


    //  checking that the  valid vehicle can be created successfully or not
    @Test
    public void testCreatingOfValidVehicle() {
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
        Assertions.assertEquals(Vehicle.Status.CROSSED, vehicle.getCrossingStatus());
    }

    // test to check that a Vehicle with an invalid plate number throws a CarPlateNumberInvalid exception
    @Test
    public void testInvalidPlateNumber() {
        Assertions.assertThrows(CarPlateNumberInvalid.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Vehicle("S1", "1234", "CAR", "10", "LEFT", "2", "50", "CROSSED");
            }
        });
    }

    // test to check that a Vehicle with an invalid crossing time throws a NumberFormatException or not
    @Test
    public void testInvalidCrossingTime() {
        Assertions.assertThrows(NumberFormatException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Vehicle("S1", "AB1234", "CAR", "ten", "LEFT", "2", "50", "CROSSED");
            }
        });
    }

    // test to check that a Vehicle with an invalid length throws a NumberFormatException
    @Test
    public void testInvalidLength() {
        Assertions.assertThrows(NumberFormatException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Vehicle("S1", "AB1234", "CAR", "10", "LEFT", "two", "50", "CROSSED");
            }
        });
    }

    // test to check that a Vehicle with an invalid emission throws a NumberFormatException
    @Test
    public void testInvalidEmission() {
        Assertions.assertThrows(NumberFormatException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new Vehicle("S1", "AB1234", "CAR", "10", "LEFT", "2", "fifty", "CROSSED");
            }
        });
    }


}

