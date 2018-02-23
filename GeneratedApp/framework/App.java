package framework;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		HibernateUtil.getSessionfactory();

		MainFrame frame = new MainFrame();
		frame.setVisible(true);

	}

}
