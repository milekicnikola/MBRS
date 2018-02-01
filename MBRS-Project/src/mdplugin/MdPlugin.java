package mdplugin;

import java.io.File;

import javax.swing.JOptionPane;

import com.nomagic.actions.NMAction;
import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;

import mdplugin.generator.options.GeneratorOptions;
import mdplugin.generator.options.ProjectOptions;

/** MagicDraw plugin that performes code generation */
public class MdPlugin extends com.nomagic.magicdraw.plugins.Plugin {

	String pluginDir = null;

	public void init() {
		JOptionPane.showMessageDialog( null, "Magic Draw Plugin init");
		
		pluginDir = getDescriptor().getPluginDirectory().getPath();
		
		// Creating submenu in the MagicDraw main menu 	
		ActionsConfiguratorsManager manager = ActionsConfiguratorsManager.getInstance();		
		manager.addMainMenuConfigurator(new MainMenuConfigurator(getSubmenuActions()));
		
		/** @Todo: load project options (@see myplugin.generator.options.ProjectOptions) from 
		 * ProjectOptions.xml and take ejb generator options */	
		
		String currentDir = System.getProperty("user.dir");
		String path = currentDir + "/GeneratedApp";
		
		
		//for test purpose only:
		GeneratorOptions ejbOptions = new GeneratorOptions(path, "class", "FMTemplates", "{0}.java", true, "ejb");
		GeneratorOptions enumerationOptions = new GeneratorOptions(path, "enumeration", "FMTemplates", "{0}.java", true, "enumerations");
		GeneratorOptions daoOptions = new GeneratorOptions(path, "dao", "FMTemplates", "{0}Dao.java", true, "dao");
		GeneratorOptions hibernateDaoOptions = new GeneratorOptions(path, "hibernateDao", "FMTemplates", "{0}HibernateDao.java", true, "dao");
		GeneratorOptions hibernateConfigOptions = new GeneratorOptions(path, "hibernateConfig", "FMTemplates", "{0}.cfg.xml", true, "a");
		GeneratorOptions menuBarOptions = new GeneratorOptions(path, "menuBar", "FMTemplates", "{0}.java", true, "gui");
		GeneratorOptions actionOptions = new GeneratorOptions(path, "action", "FMTemplates", "{0}Action.java", true, "gui.actions");
		
		ProjectOptions.getProjectOptions().getGeneratorOptions().put("EJBGenerator", ejbOptions);
		ProjectOptions.getProjectOptions().getGeneratorOptions().put("EnumerationGenerator", enumerationOptions);
		ProjectOptions.getProjectOptions().getGeneratorOptions().put("DaoGenerator", daoOptions);
		ProjectOptions.getProjectOptions().getGeneratorOptions().put("HibernateDaoGenerator", hibernateDaoOptions);
		ProjectOptions.getProjectOptions().getGeneratorOptions().put("HibernateConfigGenerator", hibernateConfigOptions);
		ProjectOptions.getProjectOptions().getGeneratorOptions().put("MenuBarGenerator", menuBarOptions);
		ProjectOptions.getProjectOptions().getGeneratorOptions().put("ActionGenerator", actionOptions);
				
		ejbOptions.setTemplateDir(pluginDir + File.separator + ejbOptions.getTemplateDir()); //apsolutna putanja
		enumerationOptions.setTemplateDir(pluginDir + File.separator + enumerationOptions.getTemplateDir());
		daoOptions.setTemplateDir(pluginDir + File.separator + daoOptions.getTemplateDir());
		hibernateDaoOptions.setTemplateDir(pluginDir + File.separator + hibernateDaoOptions.getTemplateDir());
		hibernateConfigOptions.setTemplateDir(pluginDir + File.separator + hibernateConfigOptions.getTemplateDir());
		menuBarOptions.setTemplateDir(pluginDir + File.separator + menuBarOptions.getTemplateDir());
		actionOptions.setTemplateDir(pluginDir + File.separator + actionOptions.getTemplateDir());
	}

	private NMAction[] getSubmenuActions() {
		return new NMAction[] { new GenerateAction("Make MBRS"), };
	}

	public boolean close() {
		return true;
	}

	public boolean isSupported() {
		return true;
	}
}
