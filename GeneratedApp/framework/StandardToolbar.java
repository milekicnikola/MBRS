package framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class StandardToolbar extends JToolBar {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4227471537609545859L;
	private JButton btnSearch;
	private JButton btnRefresh;
	private JButton btnHelp;
	
	private JButton btnFirstRow;
	private JButton btnPreviousRow;
	private JButton btnNextRow;
	private JButton btnLastRow;
	
	private JButton btnAdd;
	private JButton btnDelete;
	private JButton btnNextForm;
	

	public StandardToolbar(final StandardForm form) {
		
		btnSearch = new JButton(new ImageIcon("images/search.png"));
		btnSearch.setToolTipText("Search");
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.search();
			}
		});
		
		add(btnSearch);
		
		btnRefresh = new JButton(new ImageIcon("images/refresh.png"));
		btnRefresh.setToolTipText("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.refresh();
			}
		});
		add(btnRefresh);
		
		btnHelp = new JButton(new ImageIcon("images/help.png"));
		btnHelp.setToolTipText("Help");
		btnHelp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.help();
			}
		});
		add(btnHelp);
		add(new Separator());
		
		btnFirstRow = new JButton(new ImageIcon("images/first.png"));
		btnFirstRow.setToolTipText("First row");
		btnFirstRow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.firstRow();
			}
		});
		add(btnFirstRow);
		
		btnPreviousRow = new JButton(new ImageIcon("images/previous.png"));
		btnPreviousRow.setToolTipText("Previous row");
		btnPreviousRow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.previousRow();
			}
		});
		add(btnPreviousRow);
		
		btnNextRow = new JButton(new ImageIcon("images/next.png"));
		btnNextRow.setToolTipText("Next row");
		btnNextRow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.nextRow();
			}
		});
		add(btnNextRow);
		
		btnLastRow = new JButton(new ImageIcon("images/last.png"));
		btnLastRow.setToolTipText("Last row");
		btnLastRow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.lastRow();
			}
		});
		add(btnLastRow);
		add(new Separator());
		
		
		btnAdd = new JButton(new ImageIcon("images/add.png"));
		btnAdd.setToolTipText("Add");
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.add();
			}
		});
		add(btnAdd);
		
		btnDelete = new JButton(new ImageIcon("images/delete.png"));
		btnDelete.setToolTipText("Delete");
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.delete();
			}
		});
		add(btnDelete);
		add(new Separator());
		
		
		btnNextForm = new JButton(new ImageIcon("images/link.png"));
		btnNextForm.setToolTipText("Next form");
		btnNextForm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				form.nextForm(btnNextForm);
			}
		});
		add(btnNextForm);
		
	}

	public JButton getBtnSearch() {
		return btnSearch;
	}

	public JButton getBtnRefresh() {
		return btnRefresh;
	}

	public JButton getBtnHelp() {
		return btnHelp;
	}

	public JButton getBtnFirstRow() {
		return btnFirstRow;
	}

	public JButton getBtnPreviousRow() {
		return btnPreviousRow;
	}

	public JButton getBtnNextRow() {
		return btnNextRow;
	}

	public JButton getBtnLastRow() {
		return btnLastRow;
	}

	public JButton getBtnAdd() {
		return btnAdd;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public JButton getBtnNextForm() {
		return btnNextForm;
	}
	
		
}
