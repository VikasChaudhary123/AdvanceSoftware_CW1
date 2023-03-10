import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Code execution starts from this class as it contains main method GUIClass
 * VehicleList classes interact and pass information through ManagerClass 
 * It helps decouple GUIClass from any other unrelated code
 */
public class ManagerClass {

	private VehicleList vehicleList;
	private GUIClass guiClass;

	/**
	 * Constructor creates instances of VehicleList and GUIClass Catches exceptions
	 * thrown by VehicleList constructor while reading .csv files or while creating
	 * Vehicle instances
	 */
	public ManagerClass() {
		try {
			// Initialise empty list of vehicles
			vehicleList = new VehicleList();
			// Passing reference of vehicleList to GUIClass constructor
			guiClass = new GUIClass(this);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			System.out.println(fnfe.getMessage());
			System.exit(1);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.exit(1);
		} catch (CarPlateNumberInvalid e) {
			// TODO Auto-generated catch block
			
			
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.exit(1);
			
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param vdata has vechile informtion entered by user from GUIClass like segment, plateId, crossingTime etc. 
	 * GUIClass calls this function to pass information to ManagerClass to add new Vehicle
	 */
	public void createVehicle(String[] vdata) {
		try {
			vehicleList.addNewVehicle(vdata);
			//			If Vehicle data added by user is valid, notify GUIClass to show success message to user 
			guiClass.vehicleAddStatus("success", new Exception());
		} catch (Exception e) {
			// All the exceptions are being caught here, to show to the user
			e.printStackTrace();
			//If Vehicle data added by user is invalid, notify GUIClass to show error message to user 
			guiClass.vehicleAddStatus("failure",e);
		}
	}

	/**
	 * GUIClass calls this function to get C02 stats
	 */
	public float getCo2Stats() {
		return vehicleList.statsCo2();
	}

	/**
	 * ManagerClass gets segment summary from VehicleList class and passes it to GUIClass
	 */
	public Map<String, Object> getSegmentSummary() {
		return vehicleList.segmentSummary();
	}
	
	/**
	 * GUIClass calls this function when user presses exit button
	 */
	public void setPhaseSummary() {
		vehicleList.phaseSummaryToTextFile();
	}

	
	/**
	 * @return Phase array that contains 8 phases each having phaseNumber and phaseDuration
	 * GUIClass calls this function to get details about the phases
	 */
	public Phase[] getPhaseList() {
		return vehicleList.getPhaseList();
	}

	
	/**
	 * @return List of Vehicles, GUIClass uses this to show Vehicle details
	 */
	public List<Vehicle> retrieveVehicleList() {
		return vehicleList.getVehicleList();
	}

	
	/**
	 * Code execution starts from here
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ManagerClass manager = new ManagerClass();

	}

}
