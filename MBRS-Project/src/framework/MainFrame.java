package framework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.MenuBar;

public class MainFrame extends JFrame {

	public MainFrame() {
		setTitle("MBRS Application");
		Container content = getContentPane();
		content.setBackground(Color.WHITE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		int w = (int) width;
		int h = (int) height;
		setSize(w*9/10, h*9/10);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ImageIcon image = new ImageIcon("images/java.png");
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
