package gui.actions.Banking.Companies;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import framework.StandardForm;

import ejb.Banking.Companies.*;
import dao.Banking.Companies.*;
import panels.Banking.Companies.*;

public class BankAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BankAction() {
		putValue(NAME, "Bank");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Bank bank = new Bank();
		BankHibernateDao dao = new BankHibernateDao();
		BankPanel panel = new BankPanel();
		
		StandardForm form = new StandardForm(bank, dao , panel);
		
		form.setModal(true);
		form.setLocationRelativeTo(null);
		form.setVisible(true);
	}
}