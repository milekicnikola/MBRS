package panels.Banking.People;

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
import dao.Banking.Accounts.AccountHibernateDao;
import ejb.Banking.Accounts.Account;
import panels.Banking.Accounts.AccountPanel;
import enumerations.*;
import framework.*;

import net.miginfocom.swing.MigLayout;

public class PersonPanel extends JPanel{
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
				private  JTextField tfJMBG = new JTextField(13);
		   	private  JLabel     lblJMBG = new JLabel("Person JMBG*");
	    	    
	    
	    
	    
	    
				private  JTextField tffirstName = new JTextField(15);
		   	private  JLabel     lblfirstName = new JLabel("First name*");
	    	    
	    
	    
	    
	    
				private  JTextField tflastName = new JTextField(15);
		   	private  JLabel     lbllastName = new JLabel("Person's last name*");
	    	    
	    
	    
	    
	    
				private  JTextField tfaddress = new JTextField(30);
		   	private  JLabel     lbladdress = new JLabel("Person's address*");
	    	    
	    
	    
	    
	    
				private  JTextField tfphoneNumber = new JTextField(9);
		   	private  JLabel     lblphoneNumber = new JLabel("Phone number*");
	    	    
	    
	    
	    
	    
	    	    
	   		private JComboBox  combocity = new JComboBox();
	   		private  JLabel     lblcity = new JLabel("City of the person*");
	    
	    
	    
	    
	    	    
	   		private JComboBox  comboaccount = new JComboBox();
	   		private  JLabel     lblaccount = new JLabel("Account");
	    
	    
	    
	    
	
	private JButton btnCancel = new JButton();
	
	private JButton btnCommit = new JButton();
	
	public PersonPanel(){
		
		btnCancel.setIcon(new ImageIcon("images/cancel.png"));
		btnCommit.setIcon(new ImageIcon("images/commit.png"));

		setLayout(new MigLayout("", "[align l][align l, grow]", ""));
		add(new JLabel(""),"wrap 20");
				add(lblJMBG,"gapleft 30");
					lblJMBG.setName("JMBG");
					tfJMBG.addKeyListener(new TextFieldWidth(13));
					tfJMBG.setSize(new Dimension(13, tfJMBG.getHeight()));
					tfJMBG.setName("JMBG");
					add(tfJMBG,"grow 0,wrap 20");
		    
		    
		    
				add(lblfirstName,"gapleft 30");
					lblfirstName.setName("firstName");
					tffirstName.addKeyListener(new TextFieldWidth(15));
					tffirstName.setSize(new Dimension(15, tffirstName.getHeight()));
					tffirstName.setName("firstName");
					add(tffirstName,"grow 0,wrap 20");
		    
		    
		    
				add(lbllastName,"gapleft 30");
					lbllastName.setName("lastName");
					tflastName.addKeyListener(new TextFieldWidth(15));
					tflastName.setSize(new Dimension(15, tflastName.getHeight()));
					tflastName.setName("lastName");
					add(tflastName,"grow 0,wrap 20");
		    
		    
		    
				add(lbladdress,"gapleft 30");
					lbladdress.setName("address");
					tfaddress.addKeyListener(new TextFieldWidth(30));
					tfaddress.setSize(new Dimension(30, tfaddress.getHeight()));
					tfaddress.setName("address");
					add(tfaddress,"grow 0,wrap 20");
		    
		    
		    
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
		    
		    
		    
				add(lblaccount,"gapleft 30");
				lblaccount.setName("account");
				comboaccount.setName("account");
				final AccountHibernateDao daoaccount = new AccountHibernateDao();
				for(Account c : daoaccount.findAll()){
					comboaccount.addItem(c);
				}
				add(comboaccount,"gapleft 30");
		    
		    
		    
		
	}
	 public List<String> getTableColumns(){
		 List<String> retVal = new ArrayList<String>();
		 retVal.add("JMBG");
		 retVal.add("firstName");
		 retVal.add("lastName");
		 retVal.add("address");
		 retVal.add("phoneNumber");
		 retVal.add("city");
		 retVal.add("account");
		 return retVal;
	 }
}
