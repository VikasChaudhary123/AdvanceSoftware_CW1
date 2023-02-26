import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class GUIClass extends JFrame implements ActionListener{
	private JTable jt;
	private JTable jt1;
	private JTable jtn;
	private JTable jtp;
	private JButton exitbutton;
	private JButton addbutton;
	private JButton cancelbutton;
	private JPanel panel;
	private JTextField CO2 ;
	private VehicleList vehicleList;
public GUIClass(VehicleList vehicleList) {
	   this.vehicleList=vehicleList;
	
String vehiclecolumn[]={"Segment","Vehicle" ,"Type" ,"Crossing time", "Direction", "Length" ,"Emission", "Status" };  
String phasecolumn[]={"Phase" ,"Duration" };
Object addvehicledata[][]={ 
		{ " "," ", " " , Integer.valueOf(0) ," " ,Integer.valueOf(0),Integer.valueOf(0) ," " },    
      
        };    
Object segmentsummary[][]={ 
		{ "S1" , Integer.valueOf(3)  ,Integer.valueOf(5),Integer.valueOf(5) ,Integer.valueOf(15)},    
        { "S2" ,Integer.valueOf(12), Integer.valueOf(11), Integer.valueOf(12),Integer.valueOf(25)},    
        };    
String segmentcolumn[]={"Segment","Vehicles waiting" ,"Waiting Length" ,"Cross time", "Wait time"};  
DefaultTableModel vehiclemodel = new DefaultTableModel(vehicleList.listDetails(),vehiclecolumn) {
    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 3:
                return Integer.class;
            case 5:
                return Integer.class;
            case 6:
                return Integer.class;
            default:
                return String.class;
        }
    }
};
DefaultTableModel addvehiclemodel = new DefaultTableModel(addvehicledata,vehiclecolumn) {
    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 3:
                return Integer.class;
            case 5:
                return Integer.class;
            case 6:
                return Integer.class;
            default:
                return String.class;
        }
    }
};
DefaultTableModel segmentmodel = new DefaultTableModel(segmentsummary,segmentcolumn) {
    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 0:
                return String.class;
            default:
                return Integer.class;
        }
    }
};
DefaultTableModel modelphase = new DefaultTableModel(vehicleList.listPhases(),phasecolumn) {
    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 1:
                return Integer.class;
           
            default:
                return String.class;
        }
    }
};
//https://stackoverflow.com/questions/6592192/why-does-my-jtable-sort-an-integer-column-incorrectly/6592538#6592538 for above snippet
	
    jt= new JTable(vehiclemodel);
	jt.setBounds(30,40,80,80); 
	jt.setAutoCreateRowSorter(true);
    JScrollPane sp=new JScrollPane(jt);   
	add(sp,BorderLayout.NORTH);
	
	jtp= new JTable(addvehiclemodel);
    jtp.setBounds(30,40,80,80); 
    JScrollPane spp=new JScrollPane(jtp);   
    add(spp,BorderLayout.CENTER);
	
	jtn= new JTable(segmentmodel);
	jtn.setBounds(30,40,80,80); 
    jtn.setAutoCreateRowSorter(true);
    JScrollPane spn=new JScrollPane(jtn);   
    add(spn,BorderLayout.EAST);
	
	jt1= new JTable(modelphase);
	jt1.setBounds(60,80,40,40); 
	jt1.setAutoCreateRowSorter(true);
    JScrollPane sp1=new JScrollPane(jt1);   
	add(sp1,BorderLayout.WEST);
	
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
    CO2= new JTextField(15);
    panel.add(CO2);
    add(panel,BorderLayout.SOUTH);
    
	
	
	setTitle("Traffic Simulation");
	setSize(1000,800);
	pack();
	setVisible(true);
}




public void actionPerformed(ActionEvent e) {
	
	      if(e.getSource()==exitbutton)
	      {
			System.out.println("Exit button has been clicked");
			File file = new File("myFile.txt");
			FileWriter fw;
			try {
				fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("Phase Summary");
				bw.close();
			} 
			catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	      }
	      if(e.getSource()==addbutton) {
	    	  System.out.println("Add button clicked");
	    	  DefaultTableModel model = (DefaultTableModel) jt.getModel();
	    	  model.addRow(new Object[]{jtp.getValueAt(0,0), jtp.getValueAt(0,1), jtp.getValueAt(0,2), jtp.getValueAt(0,3), jtp.getValueAt(0,4), jtp.getValueAt(0,5), jtp.getValueAt(0,6), jtp.getValueAt(0,7)
	    		    	});
	    	 
	    	  Vehicle v = new Vehicle(jtp.getValueAt(0,0).toString(), jtp.getValueAt(0,1).toString(), jtp.getValueAt(0,2).toString(),jtp.getValueAt(0,3).toString(),jtp.getValueAt(0,4).toString(), 
	    			  jtp.getValueAt(0,5).toString(), jtp.getValueAt(0,6).toString(), jtp.getValueAt(0,7).toString());
	    	  vehicleList.AddNewVehicle(v);

	      }
	      if(e.getSource()==cancelbutton) {
	    	  System.out.println("Cancel button clicked");
	    	  for(int i = 0; i < 8; i++) {
	    		jtp.setValueAt("", 0, i);
	    		  
	    	  }
	    	  
	      }
	    	  
			
		}
	





}
