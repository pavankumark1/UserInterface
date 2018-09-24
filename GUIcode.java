import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class GUIcode extends JFrame {
	
	public JLabel date,make,state, gender ;
	public JTextField  maketf, statetf,datetf ;
	public JComboBox<String> genderb; 
	public JButton sb, clear, analyze ;
	public JPanel panel1,panel2, panel3, panel4;
	public JTable table ;
	public DefaultTableModel dtm;
	public JScrollPane sp;
	String d,sex,st,car,query,q ;
	ImageIcon icon, clearicon, anaicon;
	File viz;
	
public GUIcode() {
	
		CreateTextField();
		genderbox();
		searchbutton();
		panelwindow();
		
}
		
	public void CreateTextField() {
			
			//SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
		date 	= new JLabel("DATE")  	;
			datetf = new JTextField(12);
			
			datetf.setToolTipText("Format"+ " : " +"YYYY-MM-DD");
			datetf.setEditable(true);
		
		make 	= new JLabel("MAKE")  	;
			maketf = new JTextField(30);
			maketf.setEditable(true);
		
		state 	= new JLabel("STATE") 	;
			statetf = new JTextField(30);
			statetf.setText("MI");
			statetf.setEditable(true);
		
	}
	
	// Creates a gender box with Male and Female variables
	public void genderbox() {
		
		gender 	= new JLabel("GENDER")	;
	
		genderb = new JComboBox<String>();
			genderb.setEditable(false);
			genderb.addItem("M");
			genderb.addItem("F");
			genderb.setBackground(Color.lightGray);
			
			
	}
	
	public void searchbutton() {
		
		 icon = new ImageIcon("DB.png");
		 sb = new JButton("SEARCH");
		 sb.setIcon(icon);
		 
		 clearicon = new ImageIcon("clear.png");
		 clear = new JButton("CLEAR");
		 clear.setIcon(clearicon);
		 
		 anaicon = new ImageIcon("analyze.png");
		 analyze = new JButton("Analyze");
		 analyze.setToolTipText("Uses entire data");
		 analyze.setIcon(anaicon);
	}	
	
	public void panelwindow() {
		
		panel1 = new JPanel();
	
		panel1.setLayout(new GridLayout(5,1));
		
		panel1.add(date);
		panel1.add(datetf);
		panel1.add(state);
		panel1.add(statetf);
		panel1.add(make);
		panel1.add(maketf);
		panel1.add(gender);
		panel1.add(genderb);
		
		panel2 = new JPanel();
		panel2.add(sb,BorderLayout.EAST);
		panel2.add(clear,BorderLayout.CENTER);
		panel2.add(analyze,BorderLayout.WEST);
		
			
	// sets action to the search button on GUI	
	sb.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				
				d = datetf.getText().toString();
				sex = genderb.getSelectedItem().toString();
				st = statetf.getText();
				car = maketf.getText();
				
				 
				//RAW QUERY
				q = "SELECT UID,`Date`,Description,State,Vehicle,Make,`Year`,Model,Color,D_Race,D_Gender,`DL_State`"
						+ " FROM traffic_db where `Date`= '"+ d + "' and `D_Gender` = '"+ sex + "' and `State`= '"+ st + "' and `Make` = '" +car + "' ;" ;
				
				query = q.toString();
				
				try {
					
					//connecting to database
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?verifyServerCertificate=false&useSSL=false&requireSSL=false","root","8010" );
						
						//Statements 
					Statement statement = con.createStatement();
						
						//Query of database 
					ResultSet rs = statement.executeQuery(query);
					
					ResultSetMetaData rsmd = rs.getMetaData();
					
					//names of columns
					Vector<String> columnNames = new Vector<String>();
					int columnCount = rsmd.getColumnCount();
					
					for (int i = 1; i <= columnCount; i++) {
		                columnNames.add(rsmd.getColumnName(i));
		                
		            }
					
					//data from the database
					Vector<Vector<Object>> data = new Vector<Vector<Object>>();
					while(rs.next()) {
						
						Vector<Object> vector = new Vector<Object>();
		                for (int i = 1; i <= columnCount; i++) {
		                    vector.add(rs.getObject(i));
		                }
		                data.add(vector);
					} 
					
				 table = new JTable(data,columnNames);
				 //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				 
				 table.setEnabled(false);
				 
				 sp = new JScrollPane(table);
				 
				 sp.getVerticalScrollBar().setPreferredSize(new Dimension(15,0));
				 sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);	
				 sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				 
								 
				 JFrame rframe = new JFrame("Result Table");
				 rframe.setSize(1020,400);
				 rframe.add(sp);
				 rframe.setVisible(true);
				   
				 
				}
				
				catch(Exception ex) {
					
					System.err.println(ex.getMessage());
					
				}
			}
			
		});
	
	
	//sets action to the clear button on GUI
	clear.addActionListener(new ActionListener(){
		
		public void actionPerformed(ActionEvent e){
			
			datetf.setText("");
			maketf.setText("");
			statetf.setText("MI");
		}
		
	});
	
	//sets action to the analyze button 
	analyze.addActionListener(new ActionListener(){
		
		public void actionPerformed(ActionEvent e){
			
			//path to the visualization
			viz = new File( "G:/GVSU/3rd Sem (Spring)/CIS 660 - Information Management and Science/Project/Viz/TrafficViolations.twbx");
			try {
				
				Desktop.getDesktop().open(viz);
				
				}	 catch (IOException e1) {
				
				System.err.println(e1.getMessage());
			}
			
		}
	});
	
	panel3 = new JPanel();
	panel3.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	panel3.setLayout(new BorderLayout());
	panel3.add(panel1,BorderLayout.NORTH);
	panel3.add(panel2,BorderLayout.CENTER) ;
	
	add(panel3);
	panel3.setVisible(true);
		
	}
	
}	

