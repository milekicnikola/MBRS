package gui.actions.Banking.People;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import framework.StandardForm;

import ejb.Banking.People.*;
import dao.Banking.People.*;
import panels.Banking.People.*;

public class PersonAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PersonAction() {
		putValue(NAME, "Person");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Person person = new Person();
		PersonHibernateDao dao = new PersonHibernateDao();
		PersonPanel panel = new PersonPanel();
		
		StandardForm form = new StandardForm(person, dao , panel);
		
		form.setModal(true);
		form.setLocationRelativeTo(null);
		form.setVisible(true);
	}
}