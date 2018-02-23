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
import dao.Banking.Accounts.AccountHibernateDao;
import ejb.Banking.Accounts.Account;
import panels.Banking.Accounts.AccountPanel;
import dao.Banking.People.CityHibernateDao;
import ejb.Banking.People.City;
import panels.Banking.People.CityPanel;
import enumerations.*;
import framework.*;

import net.miginfocom.swing.MigLayout;

public class CompanyPanel extends JPanel{
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
				private  JTextField tfidNumber = new JTextField(7);
		   	private  JLabel     lblidNumber = new JLabel("Id number*");
	    	    
	    
	    
	    
	    
				private  JTextField tfname = new JTextField(20);
		   	private  JLabel     lblname = new JLabel("Company name*");
	    	    
	    
	    
	    
	    
				private  JTextField tfaddress = new JTextField(20);
		   	private  JLabel     lbladdress = new JLabel("Company address*");
	    	    
	    
	    
	    
	    
				private  JTextField tfemail = new JTextField(20);
		   	private  JLabel     lblemail = new JLabel("Company email");
	    	    
	    
	    
	    
	    
				private  JTextField tfphoneNumber = new JTextField(10);
		   	private  JLabel     lblphoneNumber = new JLabel("Phone number");
	    	    
	    
	    
	    
	    
	    	    
	    
	    
	    
		 	private JTextArea  textAreadescription = new JTextArea();
		   	private  JLabel     lbldescription = new JLabel("Company description");
	    
	    	    
	   		private JComboBox  comboaccount = new JComboBox();
	   		private  JLabel     lblaccount = new JLabel("Company account");
	    
	    
	    
	    
	    	    
	   		private JComboBox  combocity = new JComboBox();
	   		private  JLabel     lblcity = new JLabel("City of the company");
	    
	    
	    
	    
	
	private JButton btnCancel = new JButton();
	
	private JButton btnCommit = new JButton();
	
	public CompanyPanel(){
		
		btnCancel.setIcon(new ImageIcon("images/cancel.png"));
		btnCommit.setIcon(new ImageIcon("images/commit.png"));

		setLayout(new MigLayout("", "[align l][align l, grow]", ""));
		add(new JLabel(""),"wrap 20");
				add(lblidNumber,"gapleft 30");
					lblidNumber.setName("idNumber");
					tfidNumber.addKeyListener(new TextFieldWidth(7));
					tfidNumber.setSize(new Dimension(7, tfidNumber.getHeight()));
					tfidNumber.setName("idNumber");
					add(tfidNumber,"grow 0,wrap 20");
						TextField textidNumber = new TextField(1);
						textidNumber.setName("idNumber");
						textidNumber.setText("I");
						textidNumber.setVisible(false);
						add(textidNumber,"grow 0,hidemode 3");
		    
		    
		    
				add(lblname,"gapleft 30");
					lblname.setName("name");
					tfname.addKeyListener(new TextFieldWidth(20));
					tfname.setSize(new Dimension(20, tfname.getHeight()));
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
					tfphoneNumber.addKeyListener(new TextFieldWidth(10));
					tfphoneNumber.setSize(new Dimension(10, tfphoneNumber.getHeight()));
					tfphoneNumber.setName("phoneNumber");
					add(tfphoneNumber,"grow 0,wrap 20");
						TextField textphoneNumber = new TextField(1);
						textphoneNumber.setName("phoneNumber");
						textphoneNumber.setText("I");
						textphoneNumber.setVisible(false);
						add(textphoneNumber,"grow 0,hidemode 3");
		    
		    
		    
		    
		    
		    
				add(lbldescription,"gapleft 30");
				lbldescription.setName("description");
				textAreadescription.addKeyListener(new TextFieldWidth(100));
				textAreadescription.setLineWrap(true);
				textAreadescription.setWrapStyleWord(true);
				textAreadescription.setName("description");
				JScrollPane areaScrollPanedescription = new JScrollPane(textAreadescription);
				areaScrollPanedescription.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				areaScrollPanedescription.setPreferredSize(new Dimension(400, 100));
				add(areaScrollPanedescription,"grow 0,wrap 20"); 
				add(lblaccount,"gapleft 30");
				lblaccount.setName("account");
				comboaccount.setName("account");
				final AccountHibernateDao daoaccount = new AccountHibernateDao();
				for(Account c : daoaccount.findAll()){
					comboaccount.addItem(c);
				}
				add(comboaccount,"gapleft 30");
		    
		    
		    
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
		 retVal.add("idNumber");
		 retVal.add("name");
		 retVal.add("address");
		 retVal.add("email");
		 retVal.add("phoneNumber");
		 retVal.add("description");
		 retVal.add("account");
		 retVal.add("city");
		 return retVal;
	 }
}
