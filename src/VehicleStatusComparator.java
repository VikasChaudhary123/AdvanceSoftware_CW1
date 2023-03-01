
import java.util.Comparator;

//defines an ordering on Vehicle objects on the Status
public class VehicleStatusComparator implements Comparator<Vehicle>{

	@Override
	public int compare(Vehicle v1, Vehicle v2) {
		// TODO Auto-generated method stub
		return v1.getStatus().compareTo(v2.getStatus());
	}
}


