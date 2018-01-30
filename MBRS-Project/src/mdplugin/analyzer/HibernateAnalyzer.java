package mdplugin.analyzer;

import java.util.Iterator;

import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;

import mdplugin.generator.fmmodel.FMClass;
import mdplugin.generator.fmmodel.FMModel;

public class HibernateAnalyzer extends BaseAnalyzer{	
	
	
	public HibernateAnalyzer(Package root, String filePackage) {
		super(root, filePackage);		
	}

	@Override
	public void processPackage(Package pack, String packageOwner) throws AnalyzeException {
		if (pack.getName() == null) throw  
			new AnalyzeException("Packages must have names!");
		
		String packageName = packageOwner;
		if (pack != root) {
			packageName += "." + pack.getName();
		}
			
		if (pack.hasOwnedElement()) {			
			for (Iterator<Element> it = pack.getOwnedElement().iterator(); it.hasNext();) {
				Element ownedElement = it.next();
				if (ownedElement instanceof Class) {
					Class cl = (Class)ownedElement;
					//if(StereotypesHelper.getAppliedStereotypeByString(cl, "StandardForm") != null) {
						FMClass fmClass = getClassData(cl, packageName, AnalyzerTypeEnum.HIBERNATE);
						fmClass.addImportedPackage("ejb"+ getImportedPackage("", pack, AnalyzerTypeEnum.HIBERNATE) + "." + pack.getName());
						fmClass.addImportedPackage("dao"+ getImportedPackage("", pack, AnalyzerTypeEnum.HIBERNATE) + "." + pack.getName());
						fmClass.addImportedPackage("standardForm"+ getImportedPackage("", pack, AnalyzerTypeEnum.HIBERNATE) + "." + pack.getName());
						FMModel.getInstance().getClasses().add(fmClass);
					//}
				}
			}
			
			
			for (Iterator<Element> it = pack.getOwnedElement().iterator(); it.hasNext();) {
				Element ownedElement = it.next();
				if (ownedElement instanceof Package) {					
					Package ownedPackage = (Package)ownedElement;
					if (StereotypesHelper.getAppliedStereotypeByString(ownedPackage, "BusinessApp") != null){
						processPackage(ownedPackage, packageName);
					}
				}
				
			}			
		}
	}
	
}
