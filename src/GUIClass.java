import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class GUIClass extends JFrame implements ActionListener{
	private JTable vehicleTable;
	private JTable phaseTable;
	private JTable segmentsummaryTable;
	private JTable addvehicleTable;
	private JButton exitbutton;
	private JButton addbutton;
	private JButton cancelbutton;
	private JPanel panel;
	private JTextField CarbonEmissions ;
	private VehicleList vehicleList;
public GUIClass(VehicleList vehicleList) {
	
	this.vehicleList=vehicleList;
	TrafficGUIDataModel table = new TrafficGUIDataModel(vehicleList);
	
	// table to display vehicle data with option to sort rows by either segment, status or type of vehicle
	vehicleTable = table.vehicleModel();	
	vehicleTable.setBounds(30,40,80,80); 
	vehicleTable.getTableHeader().setReorderingAllowed(false);
	JScrollPane sp=new JScrollPane(vehicleTable);   
	add(sp,BorderLayout.NORTH);
	
	// table to get a row of vehicle data from user
	TableModel addvehiclemodel = table.addvehicleModel();
	addvehicleTable= new JTable(addvehiclemodel);
	addvehicleTable.setBounds(30,40,80,80); 
	addvehicleTable.getTableHeader().setReorderingAllowed(false);
	JScrollPane scrollVehicle=new JScrollPane(addvehicleTable);   
	add(scrollVehicle,BorderLayout.CENTER);
    
	// table to display segment summary
    TableModel segmentsummarymodel =table.segmentSummaryModel();
    segmentsummaryTable= new JTable(segmentsummarymodel);
    segmentsummaryTable.setBounds(30,40,80,80); 
    segmentsummaryTable.getTableHeader().setReorderingAllowed(false);
    JScrollPane scrollSegment=new JScrollPane(segmentsummaryTable);   
    add(scrollSegment,BorderLayout.EAST);
    
    // table to display phase details
    TableModel phasemodel = table.phaseModel();
    phaseTable= new JTable(phasemodel);
    phaseTable.setBounds(60,80,40,40); 
    phaseTable.getTableHeader().setReorderingAllowed(false);
    JScrollPane scrollPhase=new JScrollPane(phaseTable);   
	add(scrollPhase,BorderLayout.WEST);
	
    // add, exit and cancel buttons along with carbon emission textfields
	panel= new JPanel();
	panel.setBounds(700,20,100,100);
	
	exitbutton = new JButton("Exit");
	panel.add(exitbutton);
	exitbutton.addActionListener(this);
	
	addbutton = new JButton("Add");
	panel.add(addbutton);
	addbutton.addActionListener(this);
	
	cancelbutton = new JButton("Cancel");
	panel.add(cancelbutton);
	cancelbutton.addActionListener(this);
	
	panel.add(new JLabel("CO2 Emissions"));   
	CarbonEmissions= new JTextField(15);
	CarbonEmissions.setEditable(false);
	CarbonEmissions.setText(String.valueOf(vehicleList.StatsCo2()) + " KG");
    panel.add(CarbonEmissions);
    
    add(panel,BorderLayout.SOUTH);
    
	
	//setting other frame attributes
	setTitle("Traffic Simulation");
	setSize(1000,800);
	pack();
	setVisible(true);
	setExtendedState(MAXIMIZED_BOTH);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
}




public void actionPerformed(ActionEvent e) {
	
	      if(e.getSource()==exitbutton)
	      {
			System.out.println("Exit button has been clicked");
			File file = new File("PhaseSummary.txt");
			FileWriter fw;
			try {
				fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("Phase Summary");
				bw.close();
			} 
			catch (IOException e1) {
				
				e1.printStackTrace();
			}
	      }
	      if(e.getSource()==addbutton) {
	    	  System.out.println("Add button clicked");
	    	 Vehicle v;
			try {
				v = new Vehicle(
						addvehicleTable.getValueAt(0,0).toString(), 
						addvehicleTable.getValueAt(0,1).toString(),
						addvehicleTable.getValueAt(0,2).toString(),
						addvehicleTable.getValueAt(0,3).toString(),
						addvehicleTable.getValueAt(0,4).toString(), 
						addvehicleTable.getValueAt(0,5).toString(), 
						addvehicleTable.getValueAt(0,6).toString(), 
						addvehicleTable.getValueAt(0,7).toString()
						);
				vehicleList.AddNewVehicle(v);
				 DefaultTableModel model = (DefaultTableModel) vehicleTable.getModel();
		    	  model.addRow(new Object[]{
		    			  addvehicleTable.getValueAt(0,0),
		    			  addvehicleTable.getValueAt(0,1), 
		    			  addvehicleTable.getValueAt(0,2),
		    			  addvehicleTable.getValueAt(0,3),
		    			  addvehicleTable.getValueAt(0,4),
		    			  addvehicleTable.getValueAt(0,5),
		    			  addvehicleTable.getValueAt(0,6), 
		    			  addvehicleTable.getValueAt(0,7)
		    		    	});
				CarbonEmissions.setText(String.valueOf(vehicleList.StatsCo2()) + " KG");
				 for(int i = 0; i < 8; i++) {
					 addvehicleTable.setValueAt("", 0, i);
			    		  
			    	  }
				 
				 TrafficGUIDataModel tablekey = new TrafficGUIDataModel(vehicleList);
				 TableModel updatesegmentsmodel = tablekey.segmentSummaryModel();
				 segmentsummaryTable.setModel(updatesegmentsmodel);
				 
				 
			} catch (CarPlateNumberInvalid e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	  

	      }
	      if(e.getSource()==cancelbutton) {
	    	  System.out.println("Cancel button clicked");
	    	  for(int i = 0; i < 8; i++) {
	    		  addvehicleTable.setValueAt("", 0, i);
	    		  
	    	  }
	    	  
	      }
	    	  
			
		}
	





}
