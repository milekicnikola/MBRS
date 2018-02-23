package gui.actions.Banking.People;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import framework.StandardForm;

import ejb.Banking.People.*;
import dao.Banking.People.*;
import panels.Banking.People.*;

public class CityAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CityAction() {
		putValue(NAME, "City");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		City city = new City();
		CityHibernateDao dao = new CityHibernateDao();
		CityPanel panel = new CityPanel();
		
		StandardForm form = new StandardForm(city, dao , panel);
		
		form.setModal(true);
		form.setLocationRelativeTo(null);
		form.setVisible(true);
	}
}