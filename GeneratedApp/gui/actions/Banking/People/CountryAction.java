package gui.actions.Banking.People;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import framework.StandardForm;

import ejb.Banking.People.*;
import dao.Banking.People.*;
import panels.Banking.People.*;

public class CountryAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CountryAction() {
		putValue(NAME, "Country");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Country country = new Country();
		CountryHibernateDao dao = new CountryHibernateDao();
		CountryPanel panel = new CountryPanel();
		
		StandardForm form = new StandardForm(country, dao , panel);
		
		form.setModal(true);
		form.setLocationRelativeTo(null);
		form.setVisible(true);
	}
}