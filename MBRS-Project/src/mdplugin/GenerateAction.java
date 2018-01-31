package mdplugin;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.uml2.ext.magicdraw.auxiliaryconstructs.mdmodels.Model;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import mdplugin.analyzer.AnalyzeException;
import mdplugin.analyzer.ClassAnalyzer;
import mdplugin.analyzer.DaoAnalyzer;
import mdplugin.analyzer.EnumerationAnalyzer;
import mdplugin.analyzer.HibernateAnalyzer;
import mdplugin.generator.DaoGenerator;
import mdplugin.generator.EJBGenerator;
import mdplugin.generator.EnumerationGenerator;
import mdplugin.generator.HibernateGenerator;
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

		ClassAnalyzer modelAnalyzer = new ClassAnalyzer(root, "ejb");
		// ActionModelAnalyzer actionsAnalyzer = new ActionModelAnalyzer(root,
		// "gui.actions");
		// StandardFormAnalyzer standardFormAnalizer = new StandardFormAnalyzer(root,
		// "standardForm");
		EnumerationAnalyzer enumerationAnalyzer = new EnumerationAnalyzer(root, "enumerations");
		DaoAnalyzer daoAnalyzer = new DaoAnalyzer(root, "dao");
		// MenuModelAnalyzer menuAnalyzer = new MenuModelAnalyzer(root, "gui.actions");
		HibernateAnalyzer hibernateAnalyzer = new HibernateAnalyzer(root, "ejb");

		try {

			daoAnalyzer.prepareModel();
			GeneratorOptions goDao = ProjectOptions.getProjectOptions().getGeneratorOptions().get("DaoGenerator");
			DaoGenerator daoGenerator = new DaoGenerator(goDao);
			daoGenerator.generate();

			/*
			 * standardFormAnalizer.prepareModel(); GeneratorOptions goForm =
			 * ProjectOptions.getProjectOptions().getGeneratorOptions()
			 * .get("StandardFormGenerator"); StandardFormGenerator generateForm = new
			 * StandardFormGenerator(goForm); generateForm.generate();
			 * 
			 * 
			 */
			GeneratorOptions goHibernateDao = ProjectOptions.getProjectOptions().getGeneratorOptions()
					.get("HibernateDaoGenerator");
			DaoGenerator hibernateDaoGenerator = new DaoGenerator(goHibernateDao);
			hibernateDaoGenerator.generate();

			enumerationAnalyzer.prepareModel();
			GeneratorOptions goEnumeration = ProjectOptions.getProjectOptions().getGeneratorOptions()
					.get("EnumerationGenerator");
			EnumerationGenerator enumerationGenerator = new EnumerationGenerator(goEnumeration);
			enumerationGenerator.generate();

			modelAnalyzer.prepareModel();
			GeneratorOptions go = ProjectOptions.getProjectOptions().getGeneratorOptions().get("EJBGenerator");
			EJBGenerator ejbGenerator = new EJBGenerator(go);
			ejbGenerator.generate();

			/*
			 * actionsAnalyzer.prepareModel(); GeneratorOptions goAction =
			 * ProjectOptions.getProjectOptions().getGeneratorOptions().get(
			 * "ActionGenerator"); ActionGenerator actionGenerator = new
			 * ActionGenerator(goAction); actionGenerator.generate();
			 * 
			 * menuAnalyzer.prepareModel(); GeneratorOptions goMenu =
			 * ProjectOptions.getProjectOptions().getGeneratorOptions().get("MenuGenerator")
			 * ; MenuGenerator menuGenerator = new MenuGenerator(goMenu);
			 * menuGenerator.generate();
			 */

			hibernateAnalyzer.prepareModel();
			GeneratorOptions goHibernate = ProjectOptions.getProjectOptions().getGeneratorOptions()
					.get("HibernateConfigGenerator");
			HibernateGenerator hibernateGenerator = new HibernateGenerator(goHibernate);
			hibernateGenerator.generate();

			/** @ToDo: Also call other generators */
			JOptionPane.showMessageDialog(null, "Code is successfully generated! Generated code is in folder: "
					+ go.getOutputPath() + " package: " + go.getFilePackage());

			exportToXml();
		} catch (AnalyzeException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
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
