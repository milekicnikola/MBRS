package framework;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class StandardPanel extends JPanel {
	
	private JButton btnCommit;;
	private JButton btnCancel;
	private JPanel pnlButtons;
	
	private JPanel pnlData;
	private JLabel lblStatus;
	
	public StandardPanel(final StandardForm form, JPanel pnlData) {
		this.pnlData = pnlData;
		setLayout(new BorderLayout());
		
		btnCommit = new JButton(new ImageIcon("images/commit.png"));
		btnCommit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				form.commit();
			}
		});
		
		btnCancel = new JButton(new ImageIcon("images/cancel.png"));
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				form.cancel();
			}
		});
		
		pnlButtons = new JPanel();
		BoxLayout box = new BoxLayout(pnlButtons, BoxLayout.Y_AXIS);
		pnlButtons.setLayout(box);
		pnlButtons.add(btnCommit);
		pnlButtons.add(btnCancel);
		
		JPanel statusBar = new JPanel();
		statusBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		lblStatus = new JLabel("State: EDIT");
		statusBar.add(lblStatus);
		
		add(pnlData, BorderLayout.CENTER);
		add(pnlButtons, BorderLayout.EAST);
		add(statusBar, BorderLayout.SOUTH);
	}
	
	public void clearDataPanel(JPanel panel){
		Component[] components = panel.getComponents();
		for(Component c : components){
			if(c instanceof JTextField){
				((JTextField) c).setText("");
			}
			if(c instanceof JPanel){
				clearDataPanel((JPanel) c);
			}
			if(c instanceof JScrollPane){
				JTextArea txtArea = (JTextArea)((JScrollPane)c).getViewport().getView();
				txtArea.setText("");
			}
		}
	}
	
	public void changeStatus(int status){
		switch (status) {
		case 0:
			lblStatus.setText("State: ADD");
			break;

		case 1:
			lblStatus.setText("State: EDIT");
			break;
		
		case 2:
			lblStatus.setText("State: SEARCH");
			break;
		}
	}
	
}
