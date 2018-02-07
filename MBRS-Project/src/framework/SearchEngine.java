package framework;

import java.awt.Component;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

public class SearchEngine {
	
	private EntityInterface entity;
	@SuppressWarnings("rawtypes")
	private JPanel fieldsPanel;
	@SuppressWarnings("rawtypes")
	private StandardTableModel model;
	
	@SuppressWarnings("rawtypes")
	public SearchEngine(EntityInterface entity, JPanel fieldsPanel, StandardTableModel model){
		this.entity = entity;
		this.fieldsPanel = fieldsPanel;
		this.model = model;
	}
	
	public void search(){
		System.out.println("searching...");
		Session session = HibernateUtil.getSessionfactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		Criteria criteria = session.createCriteria(entity.getClass(), "entity");
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		Field[] fields = entity.getClass().getDeclaredFields();
		Component[] components = fieldsPanel.getComponents();
		for(Field field : fields){
			for(Component cmp : components){
				if(cmp instanceof JTextField){
					if(field.getName().equals(cmp.getName())){
						String value = ((JTextField)cmp).getText().trim();
						if(!value.isEmpty()){
							if(value.endsWith("*")){
								value = value.replace("*", "%");
								criteria.add(Restrictions.like(field.getName(), value));
							}else{
								criteria.add(Restrictions.eq(field.getName(), value));
							}
						}
					}
				}else if(cmp instanceof JComboBox){
					if(field.getName().equals(cmp.getName())){
						Object value = (Object) ((JComboBox) cmp).getSelectedItem();
						if(value instanceof EntityInterface){
							String alias = "entity." + field.getName();
							criteria.createAlias(alias, field.getName());
							String id = field.getName()+".id";
							criteria.add(Restrictions.eq(id, ((EntityInterface)value).getId()));
						}
						else if(value.getClass().isEnum()){
							criteria.add(Restrictions.eq(field.getName(), value));
						}
					}
				} else if(cmp instanceof JDatePickerImpl){
					if(field.getName().equals(cmp.getName())){
						Date date = (Date) ((JDatePickerImpl)cmp).getModel().getValue();
						if(date != null){
							criteria.add(Restrictions.like(field.getName(), date));
						}
					}
				} else if(cmp instanceof JCheckBox){
					if(field.getName().equals(cmp.getName())){
						criteria.add(Restrictions.eq(field.getName(), ((JCheckBox) cmp).isSelected()));
					}
				}
			}
		}
		
		List<EntityInterface> results = criteria.list();
		transaction.commit();
		
		model.setRowCount(0);
		for(EntityInterface entity : results){
			model.addRow(entity.getValues());
		}
	}

}
