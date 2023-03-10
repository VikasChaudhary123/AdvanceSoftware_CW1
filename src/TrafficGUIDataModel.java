import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.text.DecimalFormat;
/**
 * This code gets executed after the GUIClass passes its instance to the TrafficGUIDataModel constructor, which
 * populates the tables with the vehicle data and relays the table back to GUIClass to be displayed. This is a 
 * custom table model made to fit the vehicle data by means of the DefaultTableModel.
 */
public class TrafficGUIDataModel {
	private DefaultTableModel model;
	private ManagerClass manager;
	/**
	 * Constructor creates an instance of TrafficGUIDataModel  which retrieves the instance of ManagerClass.
	 */
	public TrafficGUIDataModel(ManagerClass manager)
	{
		this.manager=manager;
	}
	
	/**
	 *  creates a Table populated with vehicle data from the CSV file and implemented sorting only for segment,
	 *  status and type.
	 */
    public JTable getVehicleModel() {
    	return vehicleModel();
    }
    public TableModel getModel(int choice) {
    	TableModel thisModel= new DefaultTableModel();
    	switch(choice)
    	{
    	case 1:
    		  thisModel= addvehicleModel();
    		  return  thisModel;
    	case 2 :
    		 thisModel=  segmentSummaryModel();
    		 return  thisModel;
    	case 3 : 
    		 thisModel=  phaseModel();
    		 return  thisModel;
    	default :
    		
    		return  thisModel;
    	}
		
    	
    }
	private JTable vehicleModel() {
		
		//set columns
	     model = new DefaultTableModel(
	        new Object[] { "Segment", "Vehicle","Type","Crossing Time","Direction","Length","Emission","Status" }, 0
	    );
	   
	    //iterate over each vehicle object and add it as a new row to the table
	    for(Vehicle v : manager.retrieveVehicleList()) {
           
            model.addRow(new Object[] { v.getSegment(), v.getPlateId(),v.getType(),v.getCrossingTime(),v.getDirection(),v.getLength(),v.getEmission(),v.getStatus() });
        
        }
	    JTable table = new JTable(model);
	    
	  //Sorting vehicle data by segment, status or type
	    TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		int segmentIndex = 0;
		int typeIndex=2;
		int statusIndex=7;
		sortKeys.add(new RowSorter.SortKey(segmentIndex, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(typeIndex, SortOrder.ASCENDING));
		sortKeys.add(new RowSorter.SortKey(statusIndex, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
		sorter.sort();
		
		//disable sorting for columns other than segment, status and type
		for(int i = 0; i < 8; i++) 
		{
			if(i!=0 && i!=2 && i!=7)
				sorter.setSortable(i, false);
	    }
		//https://www.codejava.net/java-se/swing/6-techniques-for-sorting-jtable-you-should-know
	    return table;
	}
  
	/**
	 *  creates a Table that receives a single row of  vehicle data from 
	 *  the user.
	 */
	private TableModel addvehicleModel() {
		
		//set columns
	     model = new DefaultTableModel(
	        new Object[] { "Segment", "Vehicle","Type","Crossing Time","Direction","Length","Emission","Status" }, 0
	    );
	   
	     //add an empty row to the table, which is to be filled by the user
	    model.addRow(new Object[] {"","","","","","","","", });
       
       
	    return model;
	}
	
	/**
	 *  creates a Table that displays each phase with the 
	 *  corresponding duration
	 */
	private TableModel phaseModel() {
		
		//set columns
	     model = new DefaultTableModel(
	        new Object[] { "Phase", "Duration"}, 0
	    );
	   
	    //iterate over each phase object and add it as a new row to the table
	    for(Phase v : manager.getPhaseList()) {
          
           model.addRow(new Object[] {v.getPhaseNumber(),v.getPhaseDuration() });
       
       }
	    return model;
	}
	
	/**
	 *  creates a Table that displays summary statistics
	 *  pertaining to each segment
	 */
	private TableModel segmentSummaryModel() {
		
		//set columns
	     model = new DefaultTableModel(
	        new Object[] {"Segment","Vehicles waiting" ,"Waiting Length" ,"Cross time"}, 0
	    );
	     String[] segments = {"S1", "S2", "S3", "S4"};
	     
	     Map<String,Object> segmentMap= manager.getSegmentSummary();
	     DecimalFormat df = new DecimalFormat("#.##");
	     //iterate over the Map returned from getSegmentSummary method and add as a new row to the table
	     for(int i = 0; i < 4; i++)
	     {
	    	 model.addRow(new Object[] {segments[i],
	    			 segmentMap.get(segments[i]+ " Number of Waiting Vehicle"),
	    			 segmentMap.get(segments[i]+ " Length of Waiting Vehicle"),
	    			 String.valueOf(df.format(segmentMap.get(segments[i]+ " Avg Crossing Time")))});
	     }
        
	    return model;
	}
	
	
	

}
