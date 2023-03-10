import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
/**
 * This code gets executed after the ManagerClass passes its instance to the GUI constructor, which
 * populates the tables with the relevant data and displays appropriate status messages.
 */
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
    private ManagerClass manager ;

    /**
	 * Constructor creates an instance of GUIClass which sets up the required tables and textfields
	 * in the GUI. It populates the data by means of relaying the manager instance with the TrafficGUIDataModel. 
	 */
public GUIClass(ManagerClass manager) {

    this.manager = manager ;
    
    TrafficGUIDataModel table = new TrafficGUIDataModel(manager);
    
    // table to display vehicle data with option to sort rows by either segment, status or type of vehicle
    vehicleTable = table.vehicleModel();    
    vehicleTable.setBounds(30,40,80,80); 
    vehicleTable.getTableHeader().setReorderingAllowed(false);
    vehicleTable.setEnabled(false);
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
    segmentsummaryTable.setEnabled(false);
    JScrollPane scrollSegment=new JScrollPane(segmentsummaryTable);   
    add(scrollSegment,BorderLayout.EAST);
    
    // table to display phase details
    TableModel phasemodel = table.phaseModel();
    phaseTable= new JTable(phasemodel);
    phaseTable.getTableHeader().setReorderingAllowed(false);
    phaseTable.setEnabled(false);
    JScrollPane scrollPhase=new JScrollPane(phaseTable);   
    add(scrollPhase,BorderLayout.WEST);
    scrollPhase.setPreferredSize(new Dimension(200, 20));
    
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
    CarbonEmissions.setText(String.valueOf(manager.getCo2Stats()) + " KG");
    panel.add(CarbonEmissions);
    add(panel,BorderLayout.SOUTH);
    
    //setting other frame attributes
    setTitle("Traffic Simulation");
    setSize(1000,800);
    pack();
    setVisible(true);
    setExtendedState(MAXIMIZED_BOTH);
    addWindowListener(new WindowAdapter(){
        @Override
        public void windowClosing(WindowEvent et) {
          manager.setPhaseSummary();
        }
   });
    setDefaultCloseOperation(EXIT_ON_CLOSE);
}

/**
 * Based on the type of exception that arises as a result of invalid data by user or file, the exception 
 * is passed to this function which displays the corresponding message by means of a pop-up dialog box 
 */
public void showErrorToUser(Exception ex) {
	
    JOptionPane.showMessageDialog(null,ex.getMessage(), ex.getClass().toString().replace("class", ""),
            JOptionPane.INFORMATION_MESSAGE);
}

/**
 * Gets executed only if the data entered by the user is valid. It displays
 * a status message to the user via a dialog box informing them that their data was 
 * successfully added.
 */
public void vehicleAddSuccess()
{
   
	  JOptionPane.showMessageDialog(null,"Vehicle has been added successfully", "Status Message",
            JOptionPane.INFORMATION_MESSAGE);
     DefaultTableModel model = (DefaultTableModel) vehicleTable.getModel();
      model.addRow(new Object[]{
              addvehicleTable.getValueAt(0,0).toString().toUpperCase(),
              addvehicleTable.getValueAt(0,1).toString().toUpperCase(), 
              addvehicleTable.getValueAt(0,2).toString().toUpperCase(),
              addvehicleTable.getValueAt(0,3),
              addvehicleTable.getValueAt(0,4).toString().toUpperCase(),
              addvehicleTable.getValueAt(0,5),
              addvehicleTable.getValueAt(0,6), 
              addvehicleTable.getValueAt(0,7).toString().toUpperCase()
                });
//    CarbonEmissions.setText(String.valueOf(manager.GetCo2Stats()) + " KG");
     for(int i = 0; i < 8; i++) {
         addvehicleTable.setValueAt("", 0, i);
      }
     TrafficGUIDataModel tablekey = new TrafficGUIDataModel(manager);
     TableModel updatesegmentsmodel = tablekey.segmentSummaryModel();
     segmentsummaryTable.setModel(updatesegmentsmodel);
}

/**
 * Event handler function for the add, cancel and exit buttons
 *  
 */
public void actionPerformed(ActionEvent e) {
          if(e.getSource()==exitbutton)
          {
        	manager.setPhaseSummary();
            System.exit(0);
          }
          if(e.getSource()==addbutton) {
              System.out.println("Add button clicked");
                String[] vehicleData = {
                        addvehicleTable.getValueAt(0,0).toString(), 
                        addvehicleTable.getValueAt(0,1).toString(),
                        addvehicleTable.getValueAt(0,2).toString(),
                        addvehicleTable.getValueAt(0,3).toString(),
                        addvehicleTable.getValueAt(0,4).toString(), 
                        addvehicleTable.getValueAt(0,5).toString(), 
                        addvehicleTable.getValueAt(0,6).toString(), 
                        addvehicleTable.getValueAt(0,7).toString()
                } ;
            
                manager.createVehicle(vehicleData);
          }
          if(e.getSource()==cancelbutton) {
              System.out.println("Cancel button clicked");
              for(int i = 0; i < 8; i++) {
                  addvehicleTable.setValueAt("", 0, i);
              }
          }
        }
}