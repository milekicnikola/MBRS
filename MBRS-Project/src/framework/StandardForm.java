package framework;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

@SuppressWarnings("rawtypes")
public class StandardForm extends JDialog implements StandardFormInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5714216712564369000L;

	private StandardToolbar toolbar;

	private StandardTableModel model;
	private JTable table;

	private StandardPanel dataPanel;

	final int ADD_STATE = 0;
	final int EDIT_STATE = 1;
	final int SEARCH_STATE = 2;
	int current_state = EDIT_STATE;

	private JPanel fieldsPanel;
	private EntityInterface entity;

	private static final String DAO_SUFFIX = "HibernateDao";
	private static final String DAO_PACKAGE = "dao";
	private static final String PANEL_SUFFIX = "Panel";
	private static final String PANEL_PACKAGE = "panels";

	@SuppressWarnings("unchecked")
	public StandardForm(EntityInterface entity, final DaoGeneric dao, JPanel fieldsPanel) {
		this.entity = entity;
		this.fieldsPanel = fieldsPanel;
		setTitle(entity.getClass().getSimpleName());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		int w = (int) width;
		int h = (int) height;
		setSize(w * 8 / 10, h * 8 / 10);
		setLocationRelativeTo(getParent());

		toolbar = new StandardToolbar(this);

		String[] columns = getFieldLabels(fieldsPanel);

		model = new StandardTableModel(dao, columns);
		model.fillData();

		table = new JTable();
		table.setModel(model);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				current_state = 1;
				dataPanel.changeStatus(1);
				dataSync();
			}
		});

		// uklanja se ID kolona iz tabele
		int index = table.getColumnModel().getColumnIndex("ID");
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(index));

		JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(500, 500));

		dataPanel = new StandardPanel(this, fieldsPanel);

		setLayout(new BorderLayout());

		add(toolbar, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(dataPanel, BorderLayout.SOUTH);

	}

	// dodavanje vrednosti sa panela u tableModel
	public String[] getFieldLabels(JPanel panel) {
		ArrayList<String> labels = new ArrayList<String>();

		for (Component cmp : panel.getComponents()) {
			if (cmp instanceof JLabel) {
				JLabel label = (JLabel) cmp;
				String text = label.getText();

				if (!text.isEmpty()) {
					if (text.contains("*")) {
						text = text.replace("*", "").trim();
					}
					labels.add(text);
				}
			}
		}

		String[] retVal = new String[labels.size() + 1];
		if (!labels.isEmpty()) {
			retVal[0] = "ID";
		}
		for (int i = 1; i < retVal.length; i++) {
			retVal[i] = labels.get(i - 1);
		}

		return retVal;
	}

	@Override
	public void add() {
		dataPanel.clearDataPanel(dataPanel);
		current_state = ADD_STATE;
		dataPanel.changeStatus(current_state);
	}

	@Override
	public void delete() {
		int row = table.getSelectedRow();
		if (row == -1)
			return;

		model.deleteRow(row);
		dataPanel.clearDataPanel(dataPanel);

	}

	@Override
	public void nextForm(JComponent caller) {
		int rowCount = table.getSelectedRow();
		if (rowCount == -1)
			return;

		String entityClassName = entity.getClass().getSimpleName();
		Field[] fields = entity.getClass().getDeclaredFields();
		for (Field f : fields) {
			for (Component c : fieldsPanel.getComponents()) {
				if (f.getName().equals(c.getName())) {
					String methodName = "set" + fieldNameWithUpperCase(f.getName());
					if (c instanceof JTextField) {
						invokeMethodWithParameter(methodName, ((JTextField) c).getText());
					}
				}
			}
		}
		ArrayList<Field> nextCandidates = new ArrayList<Field>();
		for (Field f : fields) {
			Annotation[] annotations = f.getAnnotations();
			for (Annotation a : annotations) {
				if (a.annotationType().equals(javax.persistence.OneToMany.class)) {
					nextCandidates.add(f);
				}
			}
		}

		if (nextCandidates.size() == 0) {
			return;
		} else if (nextCandidates.size() > 1) {
			JPopupMenu popUp = new JPopupMenu();
			for (final Field field : nextCandidates) {
				JMenuItem item = new JMenuItem(field.getName());
				popUp.add(item);

				preprareNextForm(entityClassName, fields, field, item);
			}

			popUp.show(caller, 0, 0);
		} else {
			preprareNextForm(entityClassName, fields, nextCandidates.get(0), null);
		}
	}

	private void preprareNextForm(String entityClassName, Field[] fields, final Field field, JMenuItem item) {

		ParameterizedType typeInSet = (ParameterizedType) field.getGenericType();
		Class<?> classInSet = (Class<?>) typeInSet.getActualTypeArguments()[0];

		String fieldName = fieldNameWithUpperCase(field.getName());

		String classPackage = classInSet.getPackage().getName();
		String panelPackage = getPackageName(classPackage, PANEL_PACKAGE);
		String daoPackage = getPackageName(classPackage, DAO_PACKAGE);

		final EntityInterface nextEntity = (EntityInterface) getClassInstance(classPackage, fieldName, "");
		final DaoGeneric nextDao = (DaoGeneric) getClassInstance(daoPackage, fieldName, DAO_SUFFIX);
		final JPanel panel = (JPanel) getClassInstance(panelPackage, fieldName, PANEL_SUFFIX);

		for (Component c : panel.getComponents()) {
			if (c instanceof JComboBox) {
				if (c.getName().equals(entityClassName.toLowerCase())) {
					ComboBoxModel cmbModel = ((JComboBox) c).getModel();
					for (int i = 0; i < cmbModel.getSize(); i++) {
						Object entityAt = cmbModel.getElementAt(i);
						if (entity.toString().equals(entityAt.toString())) {
							cmbModel.setSelectedItem(entityAt);
							c.setEnabled(true);
						}
					}
				}
			}

		}

		if (item != null) {
			setItemListener(item, nextEntity, nextDao, panel);
		} else {
			instantiationOfNextForm(nextEntity, nextDao, panel);
		}
	}

	// Postavalja se da prvo slovo bude veliko
	private String fieldNameWithUpperCase(String fieldName) {
		String fl = fieldName.substring(0, 1).toUpperCase();
		String ol = fieldName.substring(1, fieldName.length());
		return fl + ol;
	}

	// Metoda koja vraca instancu odredjene klase
	private Object getClassInstance(String packageName, String fieldName, String suffix) {
		Object retVal = null;
		try {
			String className = packageName + "." + fieldName + suffix;
			Class c = Class.forName(className.trim());
			retVal = c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

	// Metoda koja vraca naziv paketa
	private String getPackageName(String className, String parameter) {
		return className.replace("ejb", parameter);
	}

	// Za instanciranje standardne forme preko next mehanizma
	private void instantiationOfNextForm(final EntityInterface nextEntity, final DaoGeneric nextDao,
			final JPanel panel) {
		StandardForm form = new StandardForm(nextEntity, nextDao, panel);
		form.setModal(true);
		form.setLocationRelativeTo(null);
		form.setVisible(true);
	}

	// Setovanje listener-a na JMenuItem
	private void setItemListener(JMenuItem item, final EntityInterface nextEntity, final DaoGeneric nextDao,
			final JPanel panel) {
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				instantiationOfNextForm(nextEntity, nextDao, panel);
			}
		});
	}

	@Override
	public void firstRow() {
		int rowCount = table.getRowCount();
		if (rowCount == 0)
			return;

		table.setRowSelectionInterval(0, 0);
		table.scrollRectToVisible(new Rectangle(table.getCellRect(0, 0, true)));
	}

	@Override
	public void previousRow() {
		int rowCount = table.getRowCount();
		int row = table.getSelectedRow();
		if (rowCount == 0 || row == -1)
			return;

		if (row == 0) {
			table.setRowSelectionInterval(rowCount - 1, rowCount - 1);
			table.scrollRectToVisible(new Rectangle(table.getCellRect(rowCount - 1, 0, true)));
		} else {
			table.setRowSelectionInterval(row - 1, row - 1);
			table.scrollRectToVisible(new Rectangle(table.getCellRect(row - 1, 0, true)));
		}

	}

	@Override
	public void nextRow() {
		int rowCount = table.getRowCount();
		int row = table.getSelectedRow();
		if (rowCount == 0 || row == -1)
			return;

		if (row == rowCount - 1) {
			table.setRowSelectionInterval(0, 0);
			table.scrollRectToVisible(new Rectangle(table.getCellRect(0, 0, true)));
		} else {
			table.setRowSelectionInterval(row + 1, row + 1);
			table.scrollRectToVisible(new Rectangle(table.getCellRect(row + 1, 0, true)));
		}

	}

	@Override
	public void lastRow() {
		int rowCount = table.getRowCount();
		if (rowCount == 0)
			return;

		table.setRowSelectionInterval(rowCount - 1, rowCount - 1);
		table.scrollRectToVisible(new Rectangle(table.getCellRect(rowCount - 1, 0, true)));

	}

	@Override
	public void search() {
		dataPanel.clearDataPanel(dataPanel);
		current_state = SEARCH_STATE;
		dataPanel.changeStatus(current_state);
	}

	@Override
	public void refresh() {
		model.setRowCount(0);
		model.fillData();
	}

	@Override
	public void help() {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "Developed by: Nikola Milekic");

	}

	@SuppressWarnings("unchecked")
	@Override
	public void commit() {
		switch (current_state) {
		case ADD_STATE:
			if (!validation()) {
				break;
			}
			setEntityValuesFromFields();
			model.insertRow((EntityAbstract) entity);
			dataPanel.clearDataPanel(dataPanel);
			entity = makeNewInstance(entity);
			break;

		case EDIT_STATE:
			int row = table.getSelectedRow();
			if (row == -1)
				return;

			Integer id = (Integer) model.getValueAt(row, 0);
			setEntityId(entity, id);
			if (!validation()) {
				break;
			}
			setEntityValuesFromFields();
			model.updateRow((EntityAbstract) entity, row);
			break;

		case SEARCH_STATE:
			SearchEngine engine = new SearchEngine(entity, fieldsPanel, model);
			engine.search();
			break;
		}
	}

	private EntityInterface makeNewInstance(EntityInterface currentEntity) {
		try {
			return currentEntity.getClass().newInstance();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	@Override
	public void cancel() {
		table.clearSelection();
		dataPanel.clearDataPanel(dataPanel);
		current_state = EDIT_STATE;
		dataPanel.changeStatus(current_state);
	}

	// Setovanje vrednosti iz panela u entitet koji se cuva u bazi
	private void setEntityValuesFromFields() {
		for (Component cmp : fieldsPanel.getComponents()) {
			if (cmp instanceof JTextField) {
				String methodName = nameBuilder(cmp);
				invokeMethodWithParameter(methodName, ((JTextField) cmp).getText());

			} else if (cmp instanceof JComboBox) {
				JComboBox comboBox = (JComboBox) cmp;
				String methodName = nameBuilder(comboBox);
				Object object = comboBox.getSelectedItem();
				invokeMethodWithParameter(methodName, object);
			} else if (cmp instanceof JScrollPane) {
				JTextArea txtArea = (JTextArea) ((JScrollPane) cmp).getViewport().getView();
				String methodName = nameBuilder(txtArea);
				invokeMethodWithParameter(methodName, txtArea.getText());
			} else if (cmp instanceof JDatePickerImpl) {
				Date date = (Date) ((JDatePickerImpl) cmp).getModel().getValue();
				String methodName = nameBuilder(cmp);
				invokeMethodWithParameter(methodName, date);
			} else if (cmp instanceof JCheckBox) {
				String methodName = nameBuilder(cmp);
				invokeMethodWithParameter(methodName, ((JCheckBox) cmp).isSelected());
			}
		}

	}

	// Pozivanje set metode entiteta sa parametrom
	private Object invokeMethodWithParameter(String methodName, Object... parameter) {
		for (Method m : entity.getClass().getMethods()) {
			if (methodName.equals(m.getName())) {
				try {
					Class[] parameterTypes = m.getParameterTypes();
					if (parameterTypes[0].equals(Integer.class)) {
						Integer value = Integer.parseInt((String) parameter[0]);
						return m.invoke(entity, value);
					}

					if (parameterTypes[0].equals(Float.TYPE)) {
						Float value = Float.parseFloat((String) parameter[0]);
						return m.invoke(entity, value);
					}

					return m.invoke(entity, parameter);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		return null;
	}

	// Na osnovu naziva komponente kreira se naziv set metode za atribut sa istim
	// nazivom
	private String nameBuilder(Component cmp) {
		StringBuilder builder = new StringBuilder();
		builder.append("set");
		String firstLetter = cmp.getName().substring(0, 1).toUpperCase();
		String otherLetters = cmp.getName().substring(1, cmp.getName().length());
		builder.append(firstLetter);
		builder.append(otherLetters);

		String methodName = builder.toString();
		return methodName;
	}

	// Pozivanje setID() metode za entitet koji se menja ili brise
	private void setEntityId(EntityInterface entity, Integer id) {
		try {
			Method method = entity.getClass().getMethod("setId", Integer.class);
			method.invoke(entity, id);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	@Override
	public void dataSync() {
		if (current_state == ADD_STATE)
			return;
		if (current_state == SEARCH_STATE)
			return;

		int row = table.getSelectedRow();
		if (row == -1)
			return;

		for (int i = 1; i < model.getColumnCount(); i++) {
			for (Component cmp : fieldsPanel.getComponents()) {
				if (cmp instanceof JTextField) {
					processTextComponent(row, i, (JTextComponent) cmp);
				} else if (cmp instanceof JComboBox) {
					ComboBoxModel cmbModel = ((JComboBox) cmp).getModel();
					for (int j = 0; j < cmbModel.getSize(); j++) {
						Object object = cmbModel.getElementAt(j);
						if (object.toString().equals(model.getValueAt(row, i))) {
							cmbModel.setSelectedItem(object);
						}
					}
				} else if (cmp instanceof JScrollPane) {
					JTextArea txtArea = (JTextArea) ((JScrollPane) cmp).getViewport().getView();
					processTextComponent(row, i, txtArea);
				} else if (cmp instanceof JDatePickerImpl) {
					processDateComponent(row, i, (JDatePickerImpl) cmp);
				} else if (cmp instanceof JCheckBox) {
					String name = procesRequired(cmp.getName());
					String columnName = extractColumnName(i);
					String checkColumn = columnName.toLowerCase();
					String checkName = name.toLowerCase();
					if (checkColumn.contains(checkName)) {
						String value = (String) model.getValueAt(row, i);
						((JCheckBox) cmp).setSelected(value.equals("true"));
					}
				}
			}
		}
	}

	// Ako se unutar imena komponente nalazi znak *, izbacuje se
	private String procesRequired(String name) {
		if (name.contains("*")) {
			name = name.replace("*", "").trim();
		}
		return name;
	}

	// Sinhronizacija tekstualnih komponenti
	private void processTextComponent(int row, int i, JTextComponent cmp) {
		String name = procesRequired(cmp.getName());

		String columnName = extractColumnName(i);
		String checkColumn = columnName.toLowerCase();
		String checkName = name.toLowerCase();
		if (checkColumn.contains(checkName)) {
			Object value = model.getValueAt(row, i);
			if (value != null)
				((JTextComponent) cmp).setText(value.toString());
		}
	}

	// Sinhronizacija komponenti sa datumom
	@SuppressWarnings("deprecation")
	private void processDateComponent(int row, int i, JDatePickerImpl cmp) {
		String name = procesRequired(cmp.getName());

		String columnName = extractColumnName(i);
		if (name.equals(columnName)) {
			String dateString = String.valueOf(model.getValueAt(row, i));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date;
			try {
				date = dateFormat.parse(dateString);
				((JDatePickerImpl) cmp).getModel().setDate(date.getYear() + 1900, date.getMonth(), date.getDate());
				((JDatePickerImpl) cmp).getModel().setSelected(true);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}

	// Na osnovu imena kolone iz tabele kreira se naziv komponente na panelu
	private String extractColumnName(int i) {
		String columnName = model.getColumnName(i);
		if (columnName.contains("*")) {
			columnName = columnName.replace("*", "").trim();
		}
		if (columnName.contains(" ")) {
			StringBuffer buffer = new StringBuffer();
			StringTokenizer tokenizer = new StringTokenizer(columnName);
			int firstToken = 0;
			while (tokenizer.hasMoreElements()) {
				String t = (String) tokenizer.nextElement();
				if (firstToken == 0) {
					t = t.toLowerCase();
					firstToken++;
				} else {
					t = t.substring(0, 1).toUpperCase() + t.substring(1, t.length());
				}
				buffer.append(t);
			}
			columnName = buffer.toString();
		} else {
			columnName = columnName.toLowerCase();
		}
		return columnName;
	}

	private boolean validation() {
		boolean retVal = true;
		retVal = requiredValidation();
		if (!retVal) {
			return false;
		}
		retVal = numberValidation();
		if (!retVal) {
			return false;
		}
		return retVal;
	}

	// Da li su popunjena obavezna polja
	private boolean requiredValidation() {
		Map<String, String> labels = new HashMap<String, String>();
		for (Component cmp : fieldsPanel.getComponents()) {
			if (cmp instanceof JLabel) {
				JLabel label = (JLabel) cmp;
				String text = label.getText();
				if (!text.isEmpty()) {
					if (text.endsWith("*")) {
						labels.put(label.getName(), text.substring(0, text.length() - 1));
					}
				}
			}
		}
		for (Component cmp : fieldsPanel.getComponents()) {
			if (cmp instanceof JTextField) {
				if (labels.containsKey(cmp.getName())
						&& (((JTextField) cmp).getText().equals("") || ((JTextField) cmp).getText() == null)) {
					JOptionPane.showMessageDialog(null,
							"Text field " + "'" + labels.get(cmp.getName()) + "'" + " cannot be empty!");
					return false;
				}
			} else if (cmp instanceof JComboBox) {
				if (labels.containsKey(cmp.getName()) && (((JComboBox) cmp).getSelectedItem() == null)) {
					JOptionPane.showMessageDialog(null,
							"Combobox " + "'" + labels.get(cmp.getName()) + "'" + " cannot be empty!");
					return false;
				}
			} else if (cmp instanceof JScrollPane) {
				JTextArea tempArea = (JTextArea) ((JScrollPane) cmp).getViewport().getView();
				if (labels.containsKey(tempArea.getName())
						&& (tempArea.getText().equals("") || tempArea.getText() == null)) {
					JOptionPane.showMessageDialog(null,
							"Text field " + "'" + labels.get(tempArea.getName()) + "'" + " cannot be empty!");
					return false;
				}
			} else if (cmp instanceof JDatePickerImpl) {
				Date selectedDate = (Date) ((JDatePickerImpl) cmp).getModel().getValue();
				if (selectedDate == null) {
					JOptionPane.showMessageDialog(null, "Date field " + "'"
							+ labels.get(((JDatePickerImpl) cmp).getName()) + "'" + " cannot be empty!");
					return false;
				}
			}
		}
		return true;
	}

	private boolean numberValidation() {
		Map<String, String> numberFields = new HashMap<String, String>();
		for (Component cmp : fieldsPanel.getComponents()) {
			if (cmp instanceof TextField) {
				TextField fieldNumber = (TextField) cmp;
				String text = fieldNumber.getText();
				numberFields.put(fieldNumber.getName(), text);
			}
		}
		Map<String, String> labels = new HashMap<String, String>();
		for (Component cmp : fieldsPanel.getComponents()) {
			if (cmp instanceof JLabel) {
				JLabel label = (JLabel) cmp;
				if (numberFields.containsKey(label.getName())) {
					String text = label.getText();
					if (!text.isEmpty()) {
						if (text.endsWith("*")) {
							labels.put(label.getName(), text.substring(0, text.length() - 1));
						} else {
							labels.put(label.getName(), text);
						}
					}
				}
			}
		}
		for (Component cmp : fieldsPanel.getComponents()) {
			if (cmp instanceof JTextField) {
				JTextField fieldNumber = (JTextField) cmp;
				if (numberFields.containsKey(fieldNumber.getName())) {
					if (numberFields.get(fieldNumber.getName()).equals("I")) { // da li je polje Integer
						try {
							Integer.parseInt(fieldNumber.getText());
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(null, "Text field " + "'" + labels.get(fieldNumber.getName())
									+ "'" + " must be an integer!");
							return false;
						}
					} else if (numberFields.get(fieldNumber.getName()).equals("R")) { // da li je polje Real
						try {
							Double.parseDouble(fieldNumber.getText());
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(null, "Text field " + "'" + labels.get(fieldNumber.getName())
									+ "'" + " must be a real number!");
							return false;
						}
					}
				}
			}
		}
		return true;
	}
}
