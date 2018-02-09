package framework;

import javax.swing.JComponent;

public interface StandardFormInterface {
	
	public void add();
	public void delete();
	public void nextForm(JComponent caller);
	
	public void firstRow();
	public void previousRow();
	public void nextRow();
	public void lastRow();
	
	public void search();
	public void refresh();
	public void help();
	
	public void commit();
	public void cancel();
	
	public void dataSync();
	
}
