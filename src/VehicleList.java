import java.util.ArrayList;

public class VehicleList {
		
		//vehicles ArrayList
		private ArrayList<Vehicle> vehicles;
		
	    //Add new Vehicle Method
		public void AddNewVehicle(String segment,String plateID, String type, String crossingTime, String direction, String length, String emission, String crossingStatus) {
			try {
				Vehicle vehicle = new Vehicle(segment, plateID, type, crossingTime, direction, length, emission, crossingStatus);
				vehicles.add(vehicle);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
}