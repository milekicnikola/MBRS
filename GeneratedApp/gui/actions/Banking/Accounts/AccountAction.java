package gui.actions.Banking.Accounts;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import framework.StandardForm;

import ejb.Banking.Accounts.*;
import dao.Banking.Accounts.*;
import panels.Banking.Accounts.*;

public class AccountAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountAction() {
		putValue(NAME, "Account");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Account account = new Account();
		AccountHibernateDao dao = new AccountHibernateDao();
		AccountPanel panel = new AccountPanel();
		
		StandardForm form = new StandardForm(account, dao , panel);
		
		form.setModal(true);
		form.setLocationRelativeTo(null);
		form.setVisible(true);
	}
}