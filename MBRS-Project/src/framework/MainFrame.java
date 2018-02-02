package framework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.MenuBar;

public class MainFrame extends JFrame {

	public MainFrame() {
		setTitle("MBRS Application");
		Container content = getContentPane();
		content.setBackground(Color.WHITE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ImageIcon image = new ImageIcon("images/java.jpeg");
		JLabel label = new JLabel("", image, JLabel.CENTER);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(label, BorderLayout.CENTER);
		panel.setOpaque(true);
		panel.setBackground(Color.WHITE);

		MenuBar menuBar = new MenuBar();
		setJMenuBar(menuBar);
		add(panel, BorderLayout.CENTER);

	}
}
