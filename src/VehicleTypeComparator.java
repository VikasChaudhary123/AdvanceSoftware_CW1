
import java.util.Comparator;

//defines an ordering on Vehicle objects on the Status
public class VehicleTypeComparator implements Comparator<Vehicle>{

	@Override
	public int compare(Vehicle v1, Vehicle v2) {
		// TODO Auto-generated method stub
		return v1.getType().compareTo(v2.getType());
	}
}


