import java.util.ArrayList;

public class VehicleList {
		
		//vehicles ArrayList
		private ArrayList<Vehicle> vehicles;
		
		public VehicleList()
		{
			vehicles = new ArrayList<Vehicle>() ;
		}
		
	    //Add new Vehicle Method
		public void AddNewVehicle(Vehicle vehicle) {
			vehicles.add(vehicle);
		}
		
		/**
	     * @return All the vehicle details
	     */
	    public String listDetails()
	    {
	    	StringBuffer allEntries = new StringBuffer();
	        for(Vehicle v : vehicles) {
	            allEntries.append(v);
	            allEntries.append('\n');
	        }
	        return allEntries.toString();
	    }
}