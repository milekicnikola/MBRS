package panels.Banking.Companies;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.TextField;
import javax.swing.JCheckBox;
import dao.Banking.People.CityHibernateDao;
import ejb.Banking.People.City;
import panels.Banking.People.CityPanel;
import enumerations.*;
import framework.*;

import net.miginfocom.swing.MigLayout;

public class BankPanel extends JPanel{
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
				private  JTextField tfswift = new JTextField(11);
		   	private  JLabel     lblswift = new JLabel("SWIFT code*");
	    	    
	    
	    
	    
	    
				private  JTextField tfname = new JTextField(15);
		   	private  JLabel     lblname = new JLabel("Name*");
	    	    
	    
	    
	    
	    
				private  JTextField tfaddress = new JTextField(20);
		   	private  JLabel     lbladdress = new JLabel("Address*");
	    	    
	    
	    
	    
	    
				private  JTextField tfemail = new JTextField(20);
		   	private  JLabel     lblemail = new JLabel("Email*");
	    	    
	    
	    
	    
	    
				private  JTextField tfphoneNumber = new JTextField(9);
		   	private  JLabel     lblphoneNumber = new JLabel("Phone number*");
	    	    
	    
	    
	    
	    
	    	    
	   		private JComboBox  combocity = new JComboBox();
	   		private  JLabel     lblcity = new JLabel("City of the bank");
	    
	    
	    
	    
	    	    
	    
	    
	    
	    
	
	private JButton btnCancel = new JButton();
	
	private JButton btnCommit = new JButton();
	
	public BankPanel(){
		
		btnCancel.setIcon(new ImageIcon("images/cancel.png"));
		btnCommit.setIcon(new ImageIcon("images/commit.png"));

		setLayout(new MigLayout("", "[align l][align l, grow]", ""));
		add(new JLabel(""),"wrap 20");
				add(lblswift,"gapleft 30");
					lblswift.setName("swift");
					tfswift.addKeyListener(new TextFieldWidth(11));
					tfswift.setSize(new Dimension(11, tfswift.getHeight()));
					tfswift.setName("swift");
					add(tfswift,"grow 0,wrap 20");
		    
		    
		    
				add(lblname,"gapleft 30");
					lblname.setName("name");
					tfname.addKeyListener(new TextFieldWidth(15));
					tfname.setSize(new Dimension(15, tfname.getHeight()));
					tfname.setName("name");
					add(tfname,"grow 0,wrap 20");
		    
		    
		    
				add(lbladdress,"gapleft 30");
					lbladdress.setName("address");
					tfaddress.addKeyListener(new TextFieldWidth(20));
					tfaddress.setSize(new Dimension(20, tfaddress.getHeight()));
					tfaddress.setName("address");
					add(tfaddress,"grow 0,wrap 20");
		    
		    
		    
				add(lblemail,"gapleft 30");
					lblemail.setName("email");
					tfemail.addKeyListener(new TextFieldWidth(20));
					tfemail.setSize(new Dimension(20, tfemail.getHeight()));
					tfemail.setName("email");
					add(tfemail,"grow 0,wrap 20");
		    
		    
		    
				add(lblphoneNumber,"gapleft 30");
					lblphoneNumber.setName("phoneNumber");
					tfphoneNumber.addKeyListener(new TextFieldWidth(9));
					tfphoneNumber.setSize(new Dimension(9, tfphoneNumber.getHeight()));
					tfphoneNumber.setName("phoneNumber");
					add(tfphoneNumber,"grow 0,wrap 20");
						TextField textphoneNumber = new TextField(1);
						textphoneNumber.setName("phoneNumber");
						textphoneNumber.setText("I");
						textphoneNumber.setVisible(false);
						add(textphoneNumber,"grow 0,hidemode 3");
		    
		    
		    
				add(lblcity,"gapleft 30");
				lblcity.setName("city");
				combocity.setName("city");
				final CityHibernateDao daocity = new CityHibernateDao();
				for(City c : daocity.findAll()){
					combocity.addItem(c);
				}
				add(combocity,"gapleft 30");
		    
		    
		    
		    
		    
		    
		
	}
	 public List<String> getTableColumns(){
		 List<String> retVal = new ArrayList<String>();
		 retVal.add("swift");
		 retVal.add("name");
		 retVal.add("address");
		 retVal.add("email");
		 retVal.add("phoneNumber");
		 retVal.add("city");
		 return retVal;
	 }
}
