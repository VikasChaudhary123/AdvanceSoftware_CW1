import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class TrafficGUIDataModel {
	DefaultTableModel model;
	ManagerClass manager;
	public TrafficGUIDataModel(ManagerClass manager)
	{
		this.manager=manager;
	}
	public JTable vehicleModel() {
	     model = new DefaultTableModel(
	        new Object[] { "Segment", "Vehicle","Type","Crossing Time","Direction","Length","Emission","Status" }, 0
	    );
	    
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
  
	public TableModel addvehicleModel() {
	     model = new DefaultTableModel(
	        new Object[] { "Segment", "Vehicle","Type","Crossing Time","Direction","Length","Emission","Status" }, 0
	    );
	   
	    model.addRow(new Object[] {"","","","","","","","", });
       
       
	    return model;
	}
	
	public TableModel phaseModel() {
	     model = new DefaultTableModel(
	        new Object[] { "Phase", "Duration"}, 0
	    );
	   
	    for(Phase v : manager.getPhaseList()) {
          
           model.addRow(new Object[] {v.getPhaseNumber(),v.getPhaseDuration() });
       
       }
	    return model;
	}
	public TableModel segmentSummaryModel() {
	     model = new DefaultTableModel(
	        new Object[] {"Segment","Vehicles waiting" ,"Waiting Length" ,"Cross time"}, 0
	    );
	     String[] segments = {"S1", "S2", "S3", "S4"};
	     Map<String,Object> segmentMap= manager.getSegmentSummary();
	     for(int i = 0; i < 4; i++)
	     {
	    	 model.addRow(new Object[] {segments[i],
	    			 segmentMap.get(segments[i]+ " Number of Waiting Vehicle"),
	    			 segmentMap.get(segments[i]+ " Length of Waiting Vehicle"),
	    			 segmentMap.get(segments[i]+ " Avg Crossing Time")});
	     }
        
	    return model;
	}
	
	
	

}
