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
		
		//for test purpose only:
		//GeneratorOptions ejbOptions = new GeneratorOptions("C:/Temp", "class", "FMTemplates", "{0}.java", true, "ejb");
		GeneratorOptions enumerationOptions = new GeneratorOptions("C:/Temp", "enumeration", "FMTemplates", "{0}.java", true, "enumerations");
		
		//ProjectOptions.getProjectOptions().getGeneratorOptions().put("EJBGenerator", ejbOptions);
		ProjectOptions.getProjectOptions().getGeneratorOptions().put("EnumerationGenerator", enumerationOptions);
				
		//ejbOptions.setTemplateDir(pluginDir + File.separator + ejbOptions.getTemplateDir()); //apsolutna putanja
		enumerationOptions.setTemplateDir(pluginDir + File.separator + enumerationOptions.getTemplateDir());
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
