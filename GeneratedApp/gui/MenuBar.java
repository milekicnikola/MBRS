package gui;

import gui.actions.Banking.Companies.*;
import gui.actions.Banking.Accounts.*;
import gui.actions.Banking.Money.*;
import gui.actions.Banking.People.*;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JMenu Companies;
	private JMenu Accounts;
	private JMenu Money;
	private JMenu People;
	
	
	public MenuBar() {
		super();
		createGUI();
	}
	
	private void createGUI() {
		Companies = new MenuCompanies();
		add(Companies);
		Accounts = new MenuAccounts();
		add(Accounts);
		Money = new MenuMoney();
		add(Money);
		People = new MenuPeople();
		add(People);
		
	}
	
		class MenuCompanies extends JMenu {
			public MenuCompanies() {
				setText("Companies");
					add(new JMenuItem(new BankAction()));	
					add(new JMenuItem(new CompanyAction()));	
			}
		}
		class MenuAccounts extends JMenu {
			public MenuAccounts() {
				setText("Accounts");
					add(new JMenuItem(new AccountAction()));	
			}
		}
		class MenuMoney extends JMenu {
			public MenuMoney() {
				setText("Money");
					add(new JMenuItem(new CurrencyAction()));	
			}
		}
		class MenuPeople extends JMenu {
			public MenuPeople() {
				setText("People");
					add(new JMenuItem(new PersonAction()));	
					add(new JMenuItem(new CountryAction()));	
					add(new JMenuItem(new CityAction()));	
			}
		}

}