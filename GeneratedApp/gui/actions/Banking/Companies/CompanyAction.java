package gui.actions.Banking.Companies;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import framework.StandardForm;

import ejb.Banking.Companies.*;
import dao.Banking.Companies.*;
import panels.Banking.Companies.*;

public class CompanyAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CompanyAction() {
		putValue(NAME, "Company");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Company company = new Company();
		CompanyHibernateDao dao = new CompanyHibernateDao();
		CompanyPanel panel = new CompanyPanel();
		
		StandardForm form = new StandardForm(company, dao , panel);
		
		form.setModal(true);
		form.setLocationRelativeTo(null);
		form.setVisible(true);
	}
}