package gui.actions.Banking.Money;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import framework.StandardForm;

import ejb.Banking.Money.*;
import dao.Banking.Money.*;
import panels.Banking.Money.*;

public class CurrencyAction extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CurrencyAction() {
		putValue(NAME, "Currency");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Currency currency = new Currency();
		CurrencyHibernateDao dao = new CurrencyHibernateDao();
		CurrencyPanel panel = new CurrencyPanel();
		
		StandardForm form = new StandardForm(currency, dao , panel);
		
		form.setModal(true);
		form.setLocationRelativeTo(null);
		form.setVisible(true);
	}
}