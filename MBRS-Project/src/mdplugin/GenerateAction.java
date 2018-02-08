package mdplugin;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdmodels.Model;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import mdplugin.analyzer.ActionAnalyzer;
import mdplugin.analyzer.AnalyzeException;
import mdplugin.analyzer.ClassAnalyzer;
import mdplugin.analyzer.DaoAnalyzer;
import mdplugin.analyzer.EnumerationAnalyzer;
import mdplugin.analyzer.HibernateAnalyzer;
import mdplugin.analyzer.MenuBarAnalyzer;
import mdplugin.analyzer.PanelAnalyzer;
import mdplugin.generator.ActionGenerator;
import mdplugin.generator.DaoGenerator;
import mdplugin.generator.EJBGenerator;
import mdplugin.generator.EnumerationGenerator;
import mdplugin.generator.HibernateGenerator;
import mdplugin.generator.MenuBarGenerator;
import mdplugin.generator.PanelGenerator;
import mdplugin.generator.fmmodel.FMModel;
import mdplugin.generator.options.GeneratorOptions;
import mdplugin.generator.options.ProjectOptions;

/** Action that activate code generation */
class GenerateAction extends MDAction {

	public GenerateAction(String name) {
		super("", name, null, null);
	}

	public void actionPerformed(ActionEvent evt) {

		if (Application.getInstance().getProject() == null)
			return;
		Model root = Application.getInstance().getProject().getModel();
		if (root == null)
			return;

		ClassAnalyzer classAnalyzer = new ClassAnalyzer(root, "ejb");
		ActionAnalyzer actionAnalyzer = new ActionAnalyzer(root, "gui.actions");
		EnumerationAnalyzer enumerationAnalyzer = new EnumerationAnalyzer(root, "enumerations");
		DaoAnalyzer daoAnalyzer = new DaoAnalyzer(root, "dao");
		MenuBarAnalyzer menuBarAnalyzer = new MenuBarAnalyzer(root, "gui.actions");
		HibernateAnalyzer hibernateAnalyzer = new HibernateAnalyzer(root, "ejb");
		PanelAnalyzer panelAnalizer = new PanelAnalyzer(root, "panels");

		try {
			daoAnalyzer.prepareModel();
			GeneratorOptions goDao = ProjectOptions.getProjectOptions().getGeneratorOptions().get("DaoGenerator");
			DaoGenerator daoGenerator = new DaoGenerator(goDao);
			daoGenerator.generate();

			GeneratorOptions goHibernateDao = ProjectOptions.getProjectOptions().getGeneratorOptions()
					.get("HibernateDaoGenerator");
			DaoGenerator hibernateDaoGenerator = new DaoGenerator(goHibernateDao);
			hibernateDaoGenerator.generate();

			enumerationAnalyzer.prepareModel();
			GeneratorOptions goEnumeration = ProjectOptions.getProjectOptions().getGeneratorOptions()
					.get("EnumerationGenerator");
			EnumerationGenerator enumerationGenerator = new EnumerationGenerator(goEnumeration);
			enumerationGenerator.generate();

			classAnalyzer.prepareModel();
			GeneratorOptions goClass = ProjectOptions.getProjectOptions().getGeneratorOptions().get("ClassGenerator");
			EJBGenerator ejbGenerator = new EJBGenerator(goClass);
			ejbGenerator.generate();

			actionAnalyzer.prepareModel();
			GeneratorOptions goAction = ProjectOptions.getProjectOptions().getGeneratorOptions().get("ActionGenerator");
			ActionGenerator actionGenerator = new ActionGenerator(goAction);
			actionGenerator.generate();

			menuBarAnalyzer.prepareModel();
			GeneratorOptions goMenuBar = ProjectOptions.getProjectOptions().getGeneratorOptions()
					.get("MenuBarGenerator");
			MenuBarGenerator menuBarGenerator = new MenuBarGenerator(goMenuBar);
			menuBarGenerator.generate();

			hibernateAnalyzer.prepareModel();
			GeneratorOptions goHibernate = ProjectOptions.getProjectOptions().getGeneratorOptions()
					.get("HibernateConfigGenerator");
			HibernateGenerator hibernateGenerator = new HibernateGenerator(goHibernate);
			hibernateGenerator.generate();

			panelAnalizer.prepareModel();
			GeneratorOptions goPanel = ProjectOptions.getProjectOptions().getGeneratorOptions().get("PanelGenerator");
			PanelGenerator generatePanel = new PanelGenerator(goPanel);
			generatePanel.generate();

			/** @ToDo: Also call other generators */
			JOptionPane.showMessageDialog(null,
					"Code is successfully generated! Generated code is in folder: " + goClass.getOutputPath());

			exportToXml();
		} catch (AnalyzeException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
/*		try {

		String srcPath = "D:\\Master\\MBRS\\MBRSGIT\\MBRS-Project\\images";

		String currentDir = System.getProperty("user.dir");
		String destPath = currentDir + "/GeneratedApp";

		JOptionPane.showMessageDialog(null, srcPath + "   " + destPath);

		File srcDir = new File(srcPath);
		File destDir = new File(destPath);

		
		FileUtils.copyDirectory(srcDir, destDir);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		

	}

	private void exportToXml() {
		if (JOptionPane.showConfirmDialog(null, "Do you want to extract model metadata?") == JOptionPane.OK_OPTION) {
			JFileChooser jfc = new JFileChooser();
			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				String fileName = jfc.getSelectedFile().getAbsolutePath();

				XStream xstream = new XStream(new DomDriver());
				BufferedWriter out;
				try {
					out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF8"));
					xstream.toXML(FMModel.getInstance().getClasses(), out);
					xstream.toXML(FMModel.getInstance().getEnumerations(), out);
					JOptionPane.showMessageDialog(null, "Metadata successfully extracted!");

				} catch (UnsupportedEncodingException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		}
	}

}
