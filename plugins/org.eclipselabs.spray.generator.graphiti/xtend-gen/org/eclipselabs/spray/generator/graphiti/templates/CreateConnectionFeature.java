package org.eclipselabs.spray.generator.graphiti.templates;

import com.google.inject.Inject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.BooleanExtensions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.eclipse.xtext.xtend2.lib.StringConcatenation;
import org.eclipselabs.spray.generator.graphiti.templates.FileGenerator;
import org.eclipselabs.spray.generator.graphiti.templates.JavaGenFile;
import org.eclipselabs.spray.generator.graphiti.util.GeneratorUtil;
import org.eclipselabs.spray.generator.graphiti.util.MetaModel;
import org.eclipselabs.spray.mm.spray.Connection;
import org.eclipselabs.spray.mm.spray.Diagram;
import org.eclipselabs.spray.mm.spray.MetaClass;
import org.eclipselabs.spray.mm.spray.Shape;
import org.eclipselabs.spray.mm.spray.extensions.SprayExtensions;

@SuppressWarnings("all")
public class CreateConnectionFeature extends FileGenerator {
  
  @Inject
  private SprayExtensions e1;
  
  public StringConcatenation generateBaseFile(final EObject modelElement) {
    JavaGenFile _javaGenFile = this.getJavaGenFile();
    String _baseClassName = _javaGenFile.getBaseClassName();
    StringConcatenation _mainFile = this.mainFile(((MetaClass) modelElement), _baseClassName);
    return _mainFile;
  }
  
  public StringConcatenation generateExtensionFile(final EObject modelElement) {
    JavaGenFile _javaGenFile = this.getJavaGenFile();
    String _className = _javaGenFile.getClassName();
    StringConcatenation _mainExtensionPointFile = this.mainExtensionPointFile(((MetaClass) modelElement), _className);
    return _mainExtensionPointFile;
  }
  
  public StringConcatenation mainExtensionPointFile(final MetaClass metaClass, final String className) {
    StringConcatenation _builder = new StringConcatenation();
    StringConcatenation _extensionHeader = this.extensionHeader(this);
    _builder.append(_extensionHeader, "");
    _builder.newLineIfNotEmpty();
    _builder.append("package ");
    String _feature_package = GeneratorUtil.feature_package();
    _builder.append(_feature_package, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.IFeatureProvider;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    _builder.append(className, "");
    _builder.append(" extends ");
    _builder.append(className, "");
    _builder.append("Base {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("public ");
    _builder.append(className, "    ");
    _builder.append("(IFeatureProvider fp) {");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("super(fp);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("public boolean hasDoneChanges() {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("return false;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public StringConcatenation mainFile(final MetaClass metaClass, final String className) {
    StringConcatenation _builder = new StringConcatenation();
    Shape _representedBy = metaClass.getRepresentedBy();
    final Connection connection = ((Connection) _representedBy);
    _builder.newLineIfNotEmpty();
    EReference _from = connection.getFrom();
    EClassifier _eType = _from.getEType();
    final EClassifier fromType = _eType;
    _builder.newLineIfNotEmpty();
    EReference _to = connection.getTo();
    EClassifier _eType_1 = _to.getEType();
    final EClassifier toType = _eType_1;
    _builder.newLineIfNotEmpty();
    String _name = fromType.getName();
    final String fromName = _name;
    _builder.newLineIfNotEmpty();
    String _name_1 = toType.getName();
    final String toName = _name_1;
    _builder.newLineIfNotEmpty();
    EClass _type = metaClass.getType();
    EPackage _ePackage = _type.getEPackage();
    String _name_2 = _ePackage.getName();
    final String pack = _name_2;
    _builder.newLineIfNotEmpty();
    EClass _type_1 = metaClass.getType();
    String _fullPackageName = MetaModel.fullPackageName(_type_1);
    String fullPackage = _fullPackageName;
    _builder.newLineIfNotEmpty();
    Diagram _diagram = metaClass.getDiagram();
    String _name_3 = _diagram.getName();
    String diagramName = _name_3;
    _builder.newLineIfNotEmpty();
    StringConcatenation _header = this.header(this);
    _builder.append(_header, "");
    _builder.newLineIfNotEmpty();
    _builder.append("package ");
    String _feature_package = GeneratorUtil.feature_package();
    _builder.append(_feature_package, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("import java.io.IOException;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import ");
    _builder.append(fullPackage, "");
    _builder.append(".");
    String _name_4 = this.e1.getName(metaClass);
    _builder.append(_name_4, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("import ");
    String _fullPackageName_1 = MetaModel.fullPackageName(toType);
    _builder.append(_fullPackageName_1, "");
    _builder.append(".");
    _builder.append(toName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("import ");
    String _fullPackageName_2 = MetaModel.fullPackageName(fromType);
    _builder.append(_fullPackageName_2, "");
    _builder.append(".");
    _builder.append(fromName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("import ");
    _builder.append(fullPackage, "");
    _builder.append(".");
    _builder.append(pack, "");
    _builder.append("Factory;");
    _builder.newLineIfNotEmpty();
    _builder.append("import org.eclipse.core.runtime.CoreException;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.IFeatureProvider;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.context.ICreateConnectionContext;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.context.IContext;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.context.impl.AddConnectionContext;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.Anchor;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.Connection;");
    _builder.newLine();
    _builder.append("import ");
    String _util_package = GeneratorUtil.util_package();
    _builder.append(_util_package, "");
    _builder.append(".SampleUtil;");
    _builder.newLineIfNotEmpty();
    _builder.append("import ");
    String _diagram_package = GeneratorUtil.diagram_package();
    _builder.append(_diagram_package, "");
    _builder.append(".");
    Diagram _diagram_1 = metaClass.getDiagram();
    String _name_5 = _diagram_1.getName();
    _builder.append(_name_5, "");
    _builder.append("ImageProvider;");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public class ");
    _builder.append(className, "");
    _builder.append(" extends AbstractCreateConnectionFeature {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    _builder.append(className, "	");
    _builder.append("(IFeatureProvider fp) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("// provide name and description for the UI, e.g. the palette");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("super(fp, \"");
    String _visibleName = GeneratorUtil.visibleName(metaClass);
    _builder.append(_visibleName, "		");
    _builder.append("\", \"Create ");
    String _visibleName_1 = GeneratorUtil.visibleName(metaClass);
    _builder.append(_visibleName_1, "		");
    _builder.append("\");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public boolean canCreate(ICreateConnectionContext context) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// return true if both anchors belong to an EClass");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// and those EClasses are not identical");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append(fromName, "		");
    _builder.append(" source = get");
    _builder.append(fromName, "		");
    _builder.append("(context.getSourceAnchor());");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append(toName, "		");
    _builder.append(" target = get");
    _builder.append(toName, "		");
    _builder.append("(context.getTargetAnchor());");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("if ( (source != null) && (target != null) && (source != target) ) {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("return true;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return false;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public boolean canStartConnection(ICreateConnectionContext context) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// return true if start anchor belongs to a EClass");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("if (get");
    _builder.append(fromName, "		");
    _builder.append("(context.getSourceAnchor()) != null) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("return true;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return false;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public Connection create(ICreateConnectionContext context) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("Connection newConnection = null;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// get EClasses which should be connected");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append(fromName, "		");
    _builder.append(" source = get");
    _builder.append(fromName, "		");
    _builder.append("(context.getSourceAnchor());");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append(toName, "		");
    _builder.append(" target = get");
    _builder.append(toName, "		");
    _builder.append("(context.getTargetAnchor());");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("if (source != null && target != null) {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("// create new business object");
    _builder.newLine();
    _builder.append("\t\t\t");
    String _name_6 = this.e1.getName(metaClass);
    _builder.append(_name_6, "			");
    _builder.append(" eReference = create");
    String _name_7 = this.e1.getName(metaClass);
    _builder.append(_name_7, "			");
    _builder.append("(source, target);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("// add connection for business object");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("AddConnectionContext addContext = new AddConnectionContext(");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("context.getSourceAnchor(), context.getTargetAnchor());");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("addContext.setNewObject(eReference);");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("newConnection = (Connection) getFeatureProvider().addIfPossible(addContext);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return newConnection;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/**");
    _builder.newLine();
    _builder.append("\t ");
    _builder.append("* Returns the ");
    _builder.append(fromName, "	 ");
    _builder.append(" belonging to the anchor, or null if not available.");
    _builder.newLineIfNotEmpty();
    _builder.append("\t ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private ");
    _builder.append(fromName, "	");
    _builder.append(" get");
    _builder.append(fromName, "	");
    _builder.append("(Anchor anchor) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("if (anchor != null) {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("Object object = getBusinessObjectForPictogramElement(anchor.getParent());");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("if (object instanceof ");
    _builder.append(fromName, "			");
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t\t");
    _builder.append("return (");
    _builder.append(fromName, "				");
    _builder.append(") object;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return null;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    {
      boolean _operator_notEquals = ObjectExtensions.operator_notEquals(fromName, toName);
      if (_operator_notEquals) {
        _builder.append("\t");
        _builder.append("/**");
        _builder.newLine();
        _builder.append("\t");
        _builder.append(" ");
        _builder.append("* Returns the ");
        _builder.append(toName, "	 ");
        _builder.append(" belonging to the anchor, or null if not available.");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append(" ");
        _builder.append("*/");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("private ");
        _builder.append(toName, "	");
        _builder.append(" get");
        _builder.append(toName, "	");
        _builder.append("(Anchor anchor) {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("if (anchor != null) {");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("Object object = getBusinessObjectForPictogramElement(anchor.getParent());");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("if (object instanceof ");
        _builder.append(toName, "			");
        _builder.append(") {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t\t\t");
        _builder.append("return (");
        _builder.append(toName, "				");
        _builder.append(") object;");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("\t\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        _builder.append("return null;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/**");
    _builder.newLine();
    _builder.append("\t ");
    _builder.append("* Creates a EReference between two EClasses.");
    _builder.newLine();
    _builder.append("\t ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("protected ");
    String _name_8 = this.e1.getName(metaClass);
    _builder.append(_name_8, "	");
    _builder.append(" create");
    String _name_9 = this.e1.getName(metaClass);
    _builder.append(_name_9, "	");
    _builder.append("(");
    _builder.append(fromName, "	");
    _builder.append(" source, ");
    _builder.append(toName, "	");
    _builder.append(" target) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("// TODO: Domain Object");
    _builder.newLine();
    _builder.append("\t\t");
    String _name_10 = this.e1.getName(metaClass);
    _builder.append(_name_10, "		");
    _builder.append(" domainObject = ");
    _builder.append(pack, "		");
    _builder.append("Factory.eINSTANCE.create");
    String _name_11 = this.e1.getName(metaClass);
    _builder.append(_name_11, "		");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    {
      EClass _type_2 = metaClass.getType();
      EList<EAttribute> _eAttributes = _type_2.getEAttributes();
      final Function1<EAttribute,Boolean> _function = new Function1<EAttribute,Boolean>() {
          public Boolean apply(final EAttribute att) {
            String _name_12 = att.getName();
            boolean _operator_equals = ObjectExtensions.operator_equals(_name_12, "name");
            return ((Boolean)_operator_equals);
          }
        };
      boolean _exists = IterableExtensions.<EAttribute>exists(_eAttributes, _function);
      if (_exists) {
        _builder.append("\t\t");
        _builder.append("domainObject.setName(\"new ");
        String _visibleName_2 = GeneratorUtil.visibleName(metaClass);
        _builder.append(_visibleName_2, "		");
        _builder.append("\");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.append("domainObject.set");
    EReference _from_1 = connection.getFrom();
    String _name_13 = _from_1.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name_13);
    _builder.append(_firstUpper, "		");
    _builder.append("(source);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("domainObject.set");
    EReference _to_1 = connection.getTo();
    String _name_14 = _to_1.getName();
    String _firstUpper_1 = StringExtensions.toFirstUpper(_name_14);
    _builder.append(_firstUpper_1, "		");
    _builder.append("(target);");
    _builder.newLineIfNotEmpty();
    _builder.append("//\t\tgetDiagram().eResource().getContents().add(domainObject);");
    _builder.newLine();
    _builder.newLine();
    _builder.append("        ");
    _builder.append("try {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("SampleUtil.saveToModelFile(domainObject, getDiagram(), \"");
    Diagram _diagram_2 = metaClass.getDiagram();
    String _modelfileExtension = _diagram_2.getModelfileExtension();
    String _lowerCase = _modelfileExtension.toLowerCase();
    _builder.append(_lowerCase, "			");
    _builder.append("\");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("} catch (CoreException e) {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("// TODO Auto-generated catch block");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("e.printStackTrace();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("} catch (IOException e) {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("// TODO Auto-generated catch block");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("e.printStackTrace();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return domainObject;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      boolean _operator_and = false;
      String _icon = metaClass.getIcon();
      boolean _operator_notEquals_1 = ObjectExtensions.operator_notEquals(_icon, null);
      if (!_operator_notEquals_1) {
        _operator_and = false;
      } else {
        String _icon_1 = metaClass.getIcon();
        boolean _equalsIgnoreCase = _icon_1.equalsIgnoreCase("");
        boolean _operator_not = BooleanExtensions.operator_not(_equalsIgnoreCase);
        _operator_and = BooleanExtensions.operator_and(_operator_notEquals_1, _operator_not);
      }
      if (_operator_and) {
        _builder.append("@Override");
        _builder.newLine();
        _builder.append("public String getCreateImageId() {");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("return ");
        Diagram _diagram_3 = metaClass.getDiagram();
        String _name_15 = _diagram_3.getName();
        _builder.append(_name_15, "    ");
        _builder.append("ImageProvider.");
        Diagram _diagram_4 = metaClass.getDiagram();
        String _name_16 = _diagram_4.getName();
        _builder.append(_name_16, "    ");
        _builder.append("_");
        String _icon_2 = metaClass.getIcon();
        String _base = GeneratorUtil.base(_icon_2);
        _builder.append(_base, "    ");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("    ");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("public boolean hasDoneChanges() {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("return false;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("public boolean canUndo(IContext context) {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("return false;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}