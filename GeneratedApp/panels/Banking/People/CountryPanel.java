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
import enumerations.*;
import framework.*;

import net.miginfocom.swing.MigLayout;

public class CountryPanel extends JPanel{
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
				private  JTextField tfcode = new JTextField(3);
		   	private  JLabel     lblcode = new JLabel("Country code*");
	    	    
	    
	    
	    
	    
				private  JTextField tfname = new JTextField(20);
		   	private  JLabel     lblname = new JLabel("Country name*");
	    	    
	    
	    
	    
	    
	    	    
	    
	    
	    
	    
	    	    
	    
	    
	    
	    
	
	private JButton btnCancel = new JButton();
	
	private JButton btnCommit = new JButton();
	
	public CountryPanel(){
		
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
					tfname.addKeyListener(new TextFieldWidth(20));
					tfname.setSize(new Dimension(20, tfname.getHeight()));
					tfname.setName("name");
					add(tfname,"grow 0,wrap 20");
		    
		    
		    
		    
		    
		    
		    
		    
		    
		
	}
	 public List<String> getTableColumns(){
		 List<String> retVal = new ArrayList<String>();
		 retVal.add("code");
		 retVal.add("name");
		 return retVal;
	 }
}
