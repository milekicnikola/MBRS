package ${class.typePackage};

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import framework.StandardForm;

<#list class.importedPackages as package>
import ${package}.*;
</#list>

${class.visibility} class ${class.name}Action extends AbstractAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ${class.name}Action() {
		putValue(NAME, "${class.name}");
		<#if class.tooltip??>
		putValue(SHORT_DESCRIPTION, "${class.tooltip}");
		</#if>
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		${class.name} ${class.name?lower_case} = new ${class.name}();
		${class.name}HibernateDao dao = new ${class.name}HibernateDao();
		${class.name}Panel panel = new ${class.name}Panel();
		
		StandardForm form = new StandardForm(${class.name?lower_case}, dao , panel);
		
		form.setModal(true);
		form.setLocationRelativeTo(null);
		form.setVisible(true);
	}
}