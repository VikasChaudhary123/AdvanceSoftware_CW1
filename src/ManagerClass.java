import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class ManagerClass {

	public VehicleList vehicleList ;
	private GUIClass guiClass ;
	
	
	public ManagerClass()
	{
		try {
			// Initialise empty list of vehicles
			vehicleList = new VehicleList() ;
			// Passing reference of vehicleList to GUIClass constructor
			guiClass = new GUIClass(this) ;
		}
		catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			System.out.println(fnfe.getMessage());
			System.exit(1);
			
		}  
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.exit(1);
		}
		catch (CarPlateNumberInvalid e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.exit(1);
		}
		catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void CreateVehicle(String[] vdata)
	{
		try {
			vehicleList.addNewVehicle(vdata);
			guiClass.vehicleAddSuccess();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			guiClass.showErrorToUser(e);
		}
	}
	
	public float GetCo2Stats() {
		return vehicleList.statsCo2() ;
	}
	
	public Map<String,Object> GetSegmentSummary(){
		return vehicleList.segmentSummary();
	}
	
	public Phase[] GetPhaseList() {
		return vehicleList.getPhaseList() ;
	}
	
	public List<Vehicle> retrieveVehicleList() {
		return vehicleList.getVehicleList() ;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ManagerClass manager = new ManagerClass() ;
		
	
	}

}
