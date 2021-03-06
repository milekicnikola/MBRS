package panels.Banking.Money;

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
import dao.Banking.People.CountryHibernateDao;
import ejb.Banking.People.Country;
import panels.Banking.People.CountryPanel;
import enumerations.*;
import framework.*;

import net.miginfocom.swing.MigLayout;

public class CurrencyPanel extends JPanel{
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
				private  JTextField tfcode = new JTextField(3);
		   	private  JLabel     lblcode = new JLabel("Currency code*");
	    	    
	    
	    
	    
	    
				private  JTextField tfname = new JTextField(10);
		   	private  JLabel     lblname = new JLabel("Currency name*");
	    	    
	    
	    
	    
	    
	    	    
	    
	  		private JCheckBox checkdomicile = new JCheckBox();
	   		private  JLabel     lbldomicile = new JLabel("Domicile currency*");
	    
	    
	    
	    	    
	   		private JComboBox  combocountry = new JComboBox();
	   		private  JLabel     lblcountry = new JLabel("Currency country");
	    
	    
	    
	    
	    	    
	    
	    
	    
	    
	
	private JButton btnCancel = new JButton();
	
	private JButton btnCommit = new JButton();
	
	public CurrencyPanel(){
		
		btnCancel.setIcon(new ImageIcon("images/cancel.png"));
		btnCommit.setIcon(new ImageIcon("images/commit.png"));

		setLayout(new MigLayout("", "[align l][align l, grow]", ""));
		add(new JLabel(""),"wrap 20");
				add(lblcode,"gapleft 30");
					lblcode.setName("code");
					tfcode.addKeyListener(new TextFieldWidth(3));
					tfcode.setSize(new Dimension(3, tfcode.getHeight()));
					tfcode.setName("code");
					add(tfcode,"grow 0,wrap 20");
		    
		    
		    
				add(lblname,"gapleft 30");
					lblname.setName("name");
					tfname.addKeyListener(new TextFieldWidth(10));
					tfname.setSize(new Dimension(10, tfname.getHeight()));
					tfname.setName("name");
					add(tfname,"grow 0,wrap 20");
		    
		    
		    
		    
				add(lbldomicile,"gapleft 30");
				lbldomicile.setName("domicile");
				checkdomicile.setName("domicile");
				add(checkdomicile,"grow 0,wrap 20");
		    
		    
				add(lblcountry,"gapleft 30");
				lblcountry.setName("country");
				combocountry.setName("country");
				final CountryHibernateDao daocountry = new CountryHibernateDao();
				for(Country c : daocountry.findAll()){
					combocountry.addItem(c);
				}
				add(combocountry,"gapleft 30");
		    
		    
		    
		    
		    
		    
		
	}
	 public List<String> getTableColumns(){
		 List<String> retVal = new ArrayList<String>();
		 retVal.add("code");
		 retVal.add("name");
		 retVal.add("domicile");
		 retVal.add("country");
		 return retVal;
	 }
}
