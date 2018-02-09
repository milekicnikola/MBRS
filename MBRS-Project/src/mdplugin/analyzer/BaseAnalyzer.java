package mdplugin.analyzer;

import java.util.Iterator;
import java.util.List;

import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Enumeration;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.EnumerationLiteral;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Type;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import mdplugin.generator.fmmodel.FMClass;
import mdplugin.generator.fmmodel.FMEnumeration;
import mdplugin.generator.fmmodel.FMModel;
import mdplugin.generator.fmmodel.FMProperty;
import mdplugin.generator.options.Resources;

public abstract class BaseAnalyzer {

	protected Package root;
	protected String filePackage;

	public enum AnalyzerTypeEnum {
		ACTION, DAO, ENUMERATION, HIBERNATE, MENU, MODEL, STANDARDFORM
	};

	public BaseAnalyzer(Package root, String filePackage) {
		super();
		this.root = root;
		this.filePackage = filePackage;
	}

	public Package getRoot() {
		return root;
	}

	public void prepareModel() throws AnalyzeException {
		FMModel.getInstance().getClasses().clear();
		FMModel.getInstance().getEnumerations().clear();
		processPackage(root, filePackage);
	}

	public abstract void processPackage(Package pack, String packageOwner) throws AnalyzeException;

	public FMClass getClassData(Class cl, String packageName, AnalyzerTypeEnum type) throws AnalyzeException {

		if (cl.getName() == null)
			throw new AnalyzeException("Classes must have names!");

		FMClass fmClass = new FMClass(cl.getName(), packageName, cl.getVisibility().toString());

		if (type == AnalyzerTypeEnum.MODEL) {
			Iterator<Property> it = ModelHelper.attributes(cl);
			while (it.hasNext()) {
				Property p = it.next();
				FMProperty prop = getPropertyData(p, cl);

				// Dodavanje importa za atribute klase koji su enumeracije
				if (p.getType() instanceof Enumeration) {
					if (!fmClass.getImportedPackages()
							.contains(Resources.IMPORT_ENUM_PREFIX + "." + p.getType().getName())) {
						fmClass.getImportedPackages().add(Resources.IMPORT_ENUM_PREFIX + "." + p.getType().getName());
					}
				}

				// klasa Date iz modela
				if (p.getType().getName().equals("Date")) {
					fmClass.getImportedPackages().add("java.util.Date");
				}

				// Ako su veze OneToOne i ManyToMany prop ce biti null
				if (prop != null) {
					Property oppositeProperty = p.getOpposite();
					if (oppositeProperty != null) {
						Element e = oppositeProperty.getOwner();
						if (e instanceof Class) {
							Class oppositePropertyClass = (Class) e;
							String tempPackageName = "";
							if (filePackage != null) {
								tempPackageName += filePackage + ".";
							}
							tempPackageName = getImportedPackage(tempPackageName, oppositePropertyClass.getPackage(),
									type) + "." + oppositePropertyClass.getPackage().getName() + "."
									+ oppositePropertyClass.getName();
							if (!fmClass.getImportedPackages().contains(tempPackageName)
									&& !tempPackageName.equals(packageName)) {
								fmClass.addImportedPackage(tempPackageName);
							}

						}
					}
					fmClass.addProperty(prop);
				}
			}
		}

		return fmClass;
	}

	public String getImportedPackage(String name, Package p, AnalyzerTypeEnum type) {
		if (p.getOwningPackage() != null) {

			if (type != AnalyzerTypeEnum.STANDARDFORM) {
				if (name.endsWith("."))
					name += p.getOwningPackage().getName();
				else
					name += "." + p.getOwningPackage().getName();
			} else {
				name = p.getOwningPackage().getName() + "." + name;
				getImportedPackage(name, p.getOwningPackage(), AnalyzerTypeEnum.MODEL);
			}
			getImportedPackage(name, p.getOwningPackage(), type);
		}
		return name;
	}

	public String getTagValue(Element el, Stereotype s, String tagName) {
		@SuppressWarnings("rawtypes")
		List value = StereotypesHelper.getStereotypePropertyValueAsString(el, s, tagName);
		if (value == null)
			return null;
		if (value.size() == 0)
			return null;
		return (String) value.get(0);
	}

	public FMEnumeration getEnumerationData(Enumeration enumeration, String packageName) throws AnalyzeException {
		FMEnumeration fmEnum = new FMEnumeration(enumeration.getName(), packageName);
		List<EnumerationLiteral> list = enumeration.getOwnedLiteral();
		for (int i = 0; i < list.size(); i++) {
			EnumerationLiteral literal = list.get(i);
			if (literal.getName() == null)
				throw new AnalyzeException("Items of the enumeration " + enumeration.getName() + " must have names!");
			fmEnum.addValue(literal.getName());
		}
		return fmEnum;
	}

	@SuppressWarnings("rawtypes")
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

		if (attType.has_associationOfEndType()) {
			prop.setReferenced(true);
			Property oppositeProperty = p.getOpposite();
			if (oppositeProperty != null) {
				int opossitePropertyUpper = oppositeProperty.getUpper();
				if (p.getUpper() == 1) {
					if (opossitePropertyUpper == 1) {
						return null;
					} else if (opossitePropertyUpper == -1) {
						prop.setRelationshipAnnotation("@ManyToOne");
						prop.setJoinColumnAnnotation("true");

					}
				} else if (p.getUpper() == -1) {
					if (opossitePropertyUpper == 1) {
						prop.setRelationshipAnnotation("@OneToMany");
						prop.setMappedBy(oppositeProperty.getName());
					} else if (opossitePropertyUpper == -1) {
						return null;
					}
				}

			}
		}

		// Obrada property-ja
		if (StereotypesHelper.getAppliedStereotypeByString(p, Resources.UI_PROPERTY) != null) {
			Stereotype propStereotype = StereotypesHelper.getAppliedStereotypeByString(p, Resources.UI_PROPERTY);

			prop.setUnique(new Boolean(getTagValue(p, propStereotype, "unique")));
			prop.setNullable(new Boolean(getTagValue(p, propStereotype, "nullable")));

			List<Property> attributes = propStereotype.getOwnedAttribute();

			for (int i = 0; i < attributes.size(); ++i) {
				Property tagDef = attributes.get(i);
				List value = StereotypesHelper.getStereotypePropertyValue(p, propStereotype, tagDef.getName());
				for (int j = 0; j < value.size(); ++j) {
					Object tagValue = (Object) value.get(j);
					prop.setColumnName(tagValue.toString());
				}
			}

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
		}
		return prop;
	}
}
