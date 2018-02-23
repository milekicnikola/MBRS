package panels.Banking.Accounts;

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
import dao.Banking.Companies.BankHibernateDao;
import ejb.Banking.Companies.Bank;
import panels.Banking.Companies.BankPanel;
import dao.Banking.Money.CurrencyHibernateDao;
import ejb.Banking.Money.Currency;
import panels.Banking.Money.CurrencyPanel;
import enumerations.*;
import framework.*;

import net.miginfocom.swing.MigLayout;

public class AccountPanel extends JPanel{
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
				private  JTextField tfcode = new JTextField(9);
		   	private  JLabel     lblcode = new JLabel("Account code*");
	    	    
	    
	    
	    
	    
				private  JTextField tfest = new JTextField(10);
		   	private  JLabel     lblest = new JLabel("Date of establishment*");
	    	    
	    
	    
	    
	    
	    	    
	   		private JComboBox  combobank = new JComboBox();
	   		private  JLabel     lblbank = new JLabel("Bank");
	    
	    
	    
	    
	    	    
	    
	    
	    
	    
	    	    
	    
	    
		  	private JComboBox  combopaymentMethod = new JComboBox(PaymentMethod.values());
			private  JLabel     lblpaymentMethod = new JLabel("Payment method*");
	    
	    
	    	    
	   		private JComboBox  combocurrency = new JComboBox();
	   		private  JLabel     lblcurrency = new JLabel("Account currency");
	    
	    
	    
	    
	    	    
	    
	  		private JCheckBox checkactive = new JCheckBox();
	   		private  JLabel     lblactive = new JLabel("Active account*");
	    
	    
	    
	    	    
	    
	    
	    
	    
	
	private JButton btnCancel = new JButton();
	
	private JButton btnCommit = new JButton();
	
	public AccountPanel(){
		
		btnCancel.setIcon(new ImageIcon("images/cancel.png"));
		btnCommit.setIcon(new ImageIcon("images/commit.png"));

		setLayout(new MigLayout("", "[align l][align l, grow]", ""));
		add(new JLabel(""),"wrap 20");
				add(lblcode,"gapleft 30");
					lblcode.setName("code");
					tfcode.addKeyListener(new TextFieldWidth(9));
					tfcode.setSize(new Dimension(9, tfcode.getHeight()));
					tfcode.setName("code");
					add(tfcode,"grow 0,wrap 20");
						TextField textcode = new TextField(1);
						textcode.setName("code");
						textcode.setText("I");
						textcode.setVisible(false);
						add(textcode,"grow 0,hidemode 3");
		    
		    
		    
				add(lblest,"gapleft 30");
					lblest.setName("est");
					UtilDateModel modelest = new UtilDateModel();
					JDatePanelImpl datePanelest = new JDatePanelImpl(modelest);
					JDatePickerImpl datePickerest = new JDatePickerImpl(datePanelest);
					datePickerest.setName("est");
					add(datePickerest,"grow 0,wrap 20");
		    
		    
		    
				add(lblbank,"gapleft 30");
				lblbank.setName("bank");
				combobank.setName("bank");
				final BankHibernateDao daobank = new BankHibernateDao();
				for(Bank c : daobank.findAll()){
					combobank.addItem(c);
				}
				add(combobank,"gapleft 30");
		    
		    
		    
		    
		    
		    
		    
		    
				add(lblpaymentMethod,"gapleft 30");
				lblpaymentMethod.setName("paymentMethod");
				combopaymentMethod.setName("paymentMethod");
				add(combopaymentMethod,"grow 0,wrap 20");			    
		    
				add(lblcurrency,"gapleft 30");
				lblcurrency.setName("currency");
				combocurrency.setName("currency");
				final CurrencyHibernateDao daocurrency = new CurrencyHibernateDao();
				for(Currency c : daocurrency.findAll()){
					combocurrency.addItem(c);
				}
				add(combocurrency,"gapleft 30");
		    
		    
		    
		    
				add(lblactive,"gapleft 30");
				lblactive.setName("active");
				checkactive.setName("active");
				add(checkactive,"grow 0,wrap 20");
		    
		    
		    
		    
		    
		
	}
	 public List<String> getTableColumns(){
		 List<String> retVal = new ArrayList<String>();
		 retVal.add("code");
		 retVal.add("est");
		 retVal.add("bank");
		 retVal.add("paymentMethod");
		 retVal.add("currency");
		 retVal.add("active");
		 return retVal;
	 }
}
