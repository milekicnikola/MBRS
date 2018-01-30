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

public class HibernateGenerator extends BasicGenerator{

	public HibernateGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
		// TODO Auto-generated constructor stub
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
			if(!packages.contains(cl.getTypePackage())) {
				packages.add(cl.getTypePackage());
			}
			
			if(!packagesToMenus.contains(cl.getTypePackage().substring(cl.getTypePackage().lastIndexOf(".") + 1))) {
				packagesToMenus.add(cl.getTypePackage().substring(cl.getTypePackage().lastIndexOf(".")+ 1));
			}
			
			if(!imports.contains(cl.getTypePackage())){
				imports.add(cl.getTypePackage());
			}
		}
		
		
		
		
		
		/*for (int i = 0; i < classes.size(); i++) {
			FMClass cl = classes.get(i);
			Writer out;
			Map<String, Object> context = new HashMap<String, Object>();
			try {
				out = getWriter(cl.getName(), cl.getTypePackage());
				if (out != null) {
					context.clear();
					context.put("class", cl);
					context.put("properties", cl.getProperties());
					context.put("importedPackages", cl.getImportedPackages());
					getTemplate().process(context, out);
					out.flush();
				}
			} catch (TemplateException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}*/
		
		
		
		
		
				Writer out;
				Map<String, Object> context = new HashMap<String, Object>();
				try {
					out = getWriter("hibernate","");
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
				}	
				catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}	
						
		}
}
