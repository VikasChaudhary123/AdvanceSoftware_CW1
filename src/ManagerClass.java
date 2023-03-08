import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ManagerClass {

	public VehicleList vehicleList ;
	private GUIClass guiClass ;
	
	
	public ManagerClass()
	{
		try {
			// Initialise empty list of vehicles
			vehicleList = new VehicleList() ;
			// Passing reference of vehicleList to GUIClass constructor
			guiClass = new GUIClass(vehicleList) ;
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
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
	}
	
	public VehicleList getVehicleList() {
		return vehicleList ;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ManagerClass manager = new ManagerClass() ;
		
	
	}

}
