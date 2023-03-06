import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class JUnitTesting {

    @Test
    public void testOfGettingPlateId() {
        Vehicle vehicle = new Vehicle("segment1", "ABC123", "car", 10, vehicleDirection.STRAIGHT, 5, 100, crossingStatus.NOTCROSSED);
        assertEquals("ABC123", vehicle.getPlateId());
    }

    @Test
    public void testOfGettingSegment() {
        Vehicle vehicle = new Vehicle("segment1", "ABC123", "car", 10, vehicleDirection.STRAIGHT, 5, 100, crossingStatus.NOTCROSSED);
        assertEquals("segment1", vehicle.getSegment());
    }

    @Test
    public void testOfGettingType() {
        Vehicle vehicle = new Vehicle("segment1", "ABC123", "car", 10, vehicleDirection.STRAIGHT, 5, 100, crossingStatus.NOTCROSSED);
        assertEquals("car", vehicle.getType());
    }

    @Test
    public void testOfGettingCrossingTime() {
        Vehicle vehicle = new Vehicle("segment1", "ABC123", "car", 10, vehicleDirection.STRAIGHT, 5, 100, crossingStatus.NOTCROSSED);
        assertEquals(10, vehicle.getCrossingTime());
    }

    @Test
    public void testOfGettingDirection() {
        Vehicle vehicle = new Vehicle("segment1", "ABC123", "car", 10, vehicleDirection.STRAIGHT, 5, 100, crossingStatus.NOTCROSSED);
        assertEquals(vehicleDirection.STRAIGHT, vehicle.getDirection());
    }

    @Test
    public void testOfGettingStatus() {
        Vehicle vehicle = new Vehicle("segment1", "ABC123", "car", 10, vehicleDirection.STRAIGHT, 5, 100, crossingStatus.NOTCROSSED);
        assertEquals(crossingStatus.NOTCROSSED, vehicle.getStatus());
    }

    @Test
    public void testOfGettingLength() {
        Vehicle vehicle = new Vehicle("segment1", "ABC123", "car", 10, vehicleDirection.STRAIGHT, 5, 100, crossingStatus.NOTCROSSED);
        assertEquals(5, vehicle.getLength());
    }

    @Test
    public void testOfGettingEmission() {
        Vehicle vehicle = new Vehicle("segment1", "ABC123", "car", 10, vehicleDirection.STRAIGHT, 5, 100, crossingStatus.NOTCROSSED);
        assertEquals(100, vehicle.getEmission());
    }
}

