package framework;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class StandardTableModel<T extends EntityAbstract> extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6665505613762799964L;
	private DaoGeneric<T> dao;

	public StandardTableModel(DaoGeneric<T> dao, String[] columnLabels) {
		super(columnLabels, 0);
		this.dao = dao;

	}

	public void fillData() {
		try {
			@SuppressWarnings("unchecked")
			List<EntityAbstract> allElements = (List<EntityAbstract>) dao.findAll();
			for (EntityAbstract e : allElements) {
				addRow(e.getValues());
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

	}

	public void insertRow(T entity) {
		try {
			dao.save(entity);
			addRow(entity.getValues());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

	}

	public void deleteRow(int row) {
		try {
			Integer id = (Integer) getValueAt(row, 0);
			dao.deleteById(id);
			removeRow(row);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	public void updateRow(T entity, int row) {
		try {
			dao.merge(entity);
			Object[] values = entity.getValues();
			for (int i = 0; i < values.length; i++) {
				setValueAt(values[i], row, i);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	public DaoGeneric<T> getDao() {
		return dao;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
