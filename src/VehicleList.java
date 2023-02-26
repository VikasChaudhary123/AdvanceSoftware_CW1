import java.util.ArrayList;

public class VehicleList {
	
		public VehicleList() {
			vehicles = new ArrayList<Vehicle>();
		}
		
		//vehicles ArrayList
		private ArrayList<Vehicle> vehicles;
		
	    //Add new Vehicle Method
		public void AddNewVehicle(Vehicle vehicle) {
			vehicles.add(vehicle);
		}
}