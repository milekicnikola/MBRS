package mdplugin.analyzer;

import java.util.Iterator;

import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Type;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import gui.ComponentKind;
import mdplugin.generator.fmmodel.FMClass;
import mdplugin.generator.fmmodel.FMModel;
import mdplugin.generator.fmmodel.FMProperty;
import mdplugin.generator.options.Resources;

public class PanelAnalyzer extends BaseAnalyzer {

	public PanelAnalyzer(Package root, String filePackage) {
		super(root, filePackage);
	}

	@Override
	public void processPackage(Package pack, String packageOwner) throws AnalyzeException {
		if (pack.getName() == null)
			throw new AnalyzeException("Packages must have names!");

		String packageName = packageOwner;
		if (pack != root) {
			packageName += "." + pack.getName();
		}

		if (pack.hasOwnedElement()) {

			for (Iterator<Element> it = pack.getOwnedElement().iterator(); it.hasNext();) {
				Element ownedElement = it.next();

				if (ownedElement instanceof Class) {
					if (StereotypesHelper.getAppliedStereotypeByString(ownedElement, Resources.STANDARD_FORM) != null) {

						Class cl = (Class) ownedElement;
						FMClass fmClass = getClassData(cl, packageName);
						FMModel.getInstance().getClasses().add(fmClass);
					}

				}
			}

			for (Iterator<Element> it = pack.getOwnedElement().iterator(); it.hasNext();) {
				Element ownedElement = it.next();
				if (ownedElement instanceof Package) {
					Package ownedPackage = (Package) ownedElement;
					if (StereotypesHelper.getAppliedStereotypeByString(ownedPackage, "BusinessApp") != null)
						processPackage(ownedPackage, packageName);
				}

			}

			/**
			 * @ToDo: Process other package elements, as needed
			 */
		}
	}

	private FMClass getClassData(Class cl, String packageName) throws AnalyzeException {
		if (cl.getName() == null)
			throw new AnalyzeException("Classes must have names!");

		FMClass fmClass = new FMClass(cl.getName(), packageName, cl.getVisibility().toString());
		Iterator<Property> it = ModelHelper.attributes(cl);
		while (it.hasNext()) {
			Property p = it.next();
			FMProperty prop = getPropertyData(p, cl);
			fmClass.getProperties().add(prop);
		}

		/**
		 * @ToDo: Add import declarations etc.
		 */
		return fmClass;
	}

	private FMProperty getPropertyData(Property p, Class cl) throws AnalyzeException {
		String attName = p.getName();
		if (attName == null)
			throw new AnalyzeException("Properties of the class: " + cl.getName() + " must have names!");
		Type attType = p.getType();

		if (attType == null)
			throw new AnalyzeException("Property " + cl.getName() + "." + p.getName() + " must have type!");

		String typeName = attType.getName();
		if (typeName == null)
			throw new AnalyzeException("Type ot the property " + cl.getName() + "." + p.getName() + " must have name!");

		int lower = p.getLower();
		int upper = p.getUpper();

		FMProperty prop = new FMProperty(attName, typeName, p.getVisibility().toString(), lower, upper);

		/*
		 * if (getTagValue(p, StereotypesHelper.getAppliedStereotypeByString(p,
		 * Resources.UI_PROPERTY), "label") != null) { prop.setLblName( getTagValue(p,
		 * StereotypesHelper.getAppliedStereotypeByString(p, Resources.UI_PROPERTY),
		 * "label")); } else { prop.setLblName(prop.getName()); }
		 */

		if (typeName.equals("Boolean")) {

			prop.setIsBoolean(true);
		}

		if (typeName.equals("Date")) {

			prop.setIsDate(true);
		}

		if (typeName.equals("float")) {

			prop.setIsReal(true);
		}
		if (typeName.equals("Integer")) {

			prop.setIsInteger(true);
		}

		attType.getPackage();

		if (attType.has_associationOfEndType()) {
			prop.setReferenced(true);
			Property opossiteProperty = p.getOpposite();
			if (opossiteProperty != null) {
				if (p.getUpper() == 1) {
					prop.setForeignKey(true);
					prop.setPackagePath(getImportedPackage(attType.getPackage().getName(), attType.getPackage(),
							AnalyzerTypeEnum.STANDARDFORM));

					if (StereotypesHelper.getAppliedStereotypeByString(p, Resources.UI_PROPERTY) != null) {
						Stereotype propStereotypeUI = StereotypesHelper.getAppliedStereotypeByString(p,
								Resources.UI_PROPERTY);
						String isRequired = getTagValue(p, propStereotypeUI, "required");
						if (isRequired != null) {
							if (isRequired.equals("true")) {
								prop.setRequired(true);
							} else if (isRequired.equals("false")) {
								prop.setRequired(false);
							}

						}
					}

				}
			}
		}

		/**
		 * Obrada stored property stereotipa
		 */
		if (StereotypesHelper.getAppliedStereotypeByString(p, Resources.UI_PROPERTY) != null) {
			Stereotype propStereotype = StereotypesHelper.getAppliedStereotypeByString(p, Resources.UI_PROPERTY);
			if (getTagValue(p, propStereotype, "label") != null) {
				prop.setLblName(getTagValue(p, propStereotype, "label"));
			} else {
				prop.setLblName(prop.getName());
			}

			prop.setRequired(new Boolean(getTagValue(p, propStereotype, "required")));

			/*
			 * String isTextField = getTagValue(p,propStereotype,"isTextField");
			 * 
			 * if(isTextField!= null){
			 * 
			 * if(isTextField.equals("true")){ prop.setIsTextField(true); }else
			 * if(isTextField.equals("false")){ prop.setIsTextField(false); } }
			 * 
			 * 
			 * String isTextArea = getTagValue(p,propStereotype,"isTextArea");
			 * 
			 * if(isTextArea!= null){
			 * 
			 * if(isTextArea.equals("true")){ prop.setIsTextArea(true); }else
			 * if(isTextArea.equals("false")){ prop.setIsTextArea(false); } }
			 */

			String lengthString = getTagValue(p, propStereotype, "length");
			if (lengthString == null) {
				prop.setLength(null);
			} else {
				prop.setLength(Integer.parseInt(getTagValue(p, propStereotype, "length")));
			}

			String precisionString = getTagValue(p, propStereotype, "precision");
			if (precisionString == null)
				prop.setPrecision(null);
			else
				prop.setPrecision(Integer.parseInt(getTagValue(p, propStereotype, "precision")));

			String componentKindString = "";
			if ((getTagValue(p, propStereotype, "component")) != null) {
				componentKindString = getTagValue(p, propStereotype, "component");

				if (componentKindString == "textField")
					prop.setComponentKind(ComponentKind.textField);
				else if (componentKindString == "textArea")
					prop.setComponentKind(ComponentKind.textArea);
				else if (componentKindString == "editor")
					prop.setComponentKind(ComponentKind.fromOtherTable);
				else if (componentKindString == "comboBox")
					prop.setComponentKind(ComponentKind.comboBox);
				else if (componentKindString == "checkBox")
					prop.setComponentKind(ComponentKind.checkBox);
				else if (componentKindString == "dateChooser")
					prop.setComponentKind(ComponentKind.dateChooser);
				else
					prop.setComponentKind(ComponentKind.valueOf(componentKindString));
			}

			prop.setToolTip(getTagValue(p, propStereotype, "toolTip"));
			// prop.setMigLayout(getTagValue(p,propStereotype,"migLayout"));
			// prop.setMigLabel(getTagValue(p,propStereotype,"migLabel"));

			String shownString = getTagValue(p, propStereotype, "shown");
			if (shownString == null)
				prop.setShown(true);
			else {
				if (shownString.equals("true")) {
					prop.setShown(true);
				} else if (shownString.equals("false")) {
					prop.setShown(false);
				}
			}

			String tableColumnString = getTagValue(p, propStereotype, "tableColumn");
			if (tableColumnString == null)
				prop.setTableColumn(true);
			else {
				if (tableColumnString.equals("true")) {
					prop.setTableColumn(true);
				} else if (tableColumnString.equals("false")) {
					prop.setTableColumn(false);
				}
			}

			prop.setColumnName(getTagValue(p, propStereotype, "columnName"));
		}
		return prop;
	}

}
