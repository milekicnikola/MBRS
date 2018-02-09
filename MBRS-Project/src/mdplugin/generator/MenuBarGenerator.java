package mdplugin.generator;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import freemarker.template.TemplateException;
import mdplugin.generator.fmmodel.FMClass;
import mdplugin.generator.fmmodel.FMModel;
import mdplugin.generator.options.GeneratorOptions;

public class MenuBarGenerator extends BasicGenerator {

	public MenuBarGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}

	public void generate() {

		try {
			super.generate();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		List<FMClass> classes = FMModel.getInstance().getClasses();
		List<String> packages = new ArrayList<String>();
		List<String> packagesToMenus = new ArrayList<String>();
		List<String> imports = new ArrayList<String>();
		for (int i = 0; i < classes.size(); i++) {
			FMClass cl = classes.get(i);
			if (!packages.contains(cl.getTypePackage())) {
				packages.add(cl.getTypePackage());
			}

			if (!packagesToMenus.contains(cl.getTypePackage().substring(cl.getTypePackage().lastIndexOf(".") + 1))) {
				packagesToMenus.add(cl.getTypePackage().substring(cl.getTypePackage().lastIndexOf(".") + 1));
			}

			if (!imports.contains(cl.getTypePackage())) {
				imports.add(cl.getTypePackage());
			}
		}
		Writer out;
		Map<String, Object> context = new HashMap<String, Object>();
		try {
			out = getWriter("MenuBar", "gui");
			if (out != null) {
				context.clear();
				context.put("classes", classes);
				context.put("packages", packages);
				context.put("packagesToMenus", packagesToMenus);
				context.put("imports", imports);
				getTemplate().process(context, out);
				out.flush();
			}
		} catch (TemplateException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

	}

}
