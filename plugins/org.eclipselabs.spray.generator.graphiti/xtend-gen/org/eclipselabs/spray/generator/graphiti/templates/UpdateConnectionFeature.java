package org.eclipselabs.spray.generator.graphiti.templates;

import com.google.inject.Inject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.xtend2.lib.StringConcatenation;
import org.eclipselabs.spray.generator.graphiti.templates.FileGenerator;
import org.eclipselabs.spray.generator.graphiti.templates.JavaGenFile;
import org.eclipselabs.spray.generator.graphiti.util.GeneratorUtil;
import org.eclipselabs.spray.generator.graphiti.util.MetaModel;
import org.eclipselabs.spray.mm.spray.Connection;
import org.eclipselabs.spray.mm.spray.Diagram;
import org.eclipselabs.spray.mm.spray.MetaClass;
import org.eclipselabs.spray.mm.spray.Text;
import org.eclipselabs.spray.mm.spray.extensions.SprayExtensions;

@SuppressWarnings("all")
public class UpdateConnectionFeature extends FileGenerator {
  
  @Inject
  private SprayExtensions e1;
  
  public StringConcatenation generateBaseFile(final EObject modelElement) {
    JavaGenFile _javaGenFile = this.getJavaGenFile();
    String _baseClassName = _javaGenFile.getBaseClassName();
    StringConcatenation _mainFile = this.mainFile(((Connection) modelElement), _baseClassName);
    return _mainFile;
  }
  
  public StringConcatenation generateExtensionFile(final EObject modelElement) {
    JavaGenFile _javaGenFile = this.getJavaGenFile();
    String _className = _javaGenFile.getClassName();
    StringConcatenation _mainExtensionPointFile = this.mainExtensionPointFile(((Connection) modelElement), _className);
    return _mainExtensionPointFile;
  }
  
  public StringConcatenation mainExtensionPointFile(final Connection connection, final String className) {
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
  
  public StringConcatenation mainFile(final Connection connection, final String className) {
    StringConcatenation _builder = new StringConcatenation();
    MetaClass _represents = connection.getRepresents();
    Diagram _diagram = _represents.getDiagram();
    String _name = _diagram.getName();
    String diagramName = _name;
    _builder.newLineIfNotEmpty();
    MetaClass _represents_1 = connection.getRepresents();
    String _name_1 = this.e1.getName(_represents_1);
    String metaClassName = _name_1;
    _builder.newLineIfNotEmpty();
    MetaClass _represents_2 = connection.getRepresents();
    EClass _type = _represents_2.getType();
    EPackage _ePackage = _type.getEPackage();
    String _name_2 = _ePackage.getName();
    String pack = _name_2;
    _builder.newLineIfNotEmpty();
    MetaClass _represents_3 = connection.getRepresents();
    EClass _type_1 = _represents_3.getType();
    String _fullPackageName = MetaModel.fullPackageName(_type_1);
    String fullPackage = _fullPackageName;
    _builder.newLineIfNotEmpty();
    String labelName = "name";
    _builder.newLineIfNotEmpty();
    StringConcatenation _header = this.header(this);
    _builder.append(_header, "");
    _builder.newLineIfNotEmpty();
    _builder.append("package ");
    String _feature_package = GeneratorUtil.feature_package();
    _builder.append(_feature_package, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import java.util.HashMap;");
    _builder.newLine();
    _builder.append("import java.util.Map;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.IFeatureProvider;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.IReason;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.context.IUpdateContext;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.context.IContext;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.impl.AbstractUpdateFeature;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.features.impl.Reason;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.algorithms.Text;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.ContainerShape;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.Diagram;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.PictogramElement;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.mm.pictograms.Shape;");
    _builder.newLine();
    _builder.append("import org.eclipse.graphiti.services.Graphiti;");
    _builder.newLine();
    _builder.append("import ");
    MetaClass _represents_4 = connection.getRepresents();
    EClass _type_2 = _represents_4.getType();
    String _fullPackageName_1 = MetaModel.fullPackageName(_type_2);
    _builder.append(_fullPackageName_1, "");
    _builder.append(".");
    MetaClass _represents_5 = connection.getRepresents();
    String _name_3 = this.e1.getName(_represents_5);
    _builder.append(_name_3, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("public class ");
    _builder.append(className, "");
    _builder.append(" extends AbstractUpdateFeature {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("Map<String, String> values = null;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    _builder.append(className, "	");
    _builder.append("(IFeatureProvider fp) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("super(fp);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public boolean canUpdate(IUpdateContext context) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// return true, if linked business object is a EClass");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("Object bo = getBusinessObjectForPictogramElement(context.getPictogramElement());");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("PictogramElement pictogramElement = context.getPictogramElement();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return (bo instanceof ");
    _builder.append(metaClassName, "		");
    _builder.append(") && (!(pictogramElement instanceof Diagram));");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public IReason updateNeeded(IUpdateContext context) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("PictogramElement pictogramElement = context.getPictogramElement();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("Object bo = getBusinessObjectForPictogramElement(pictogramElement);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("if ( ! (bo instanceof ");
    _builder.append(metaClassName, "		");
    _builder.append(")) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t    ");
    _builder.append("return Reason.createFalseReason();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append(metaClassName, "		");
    _builder.append(" eClass = (");
    _builder.append(metaClassName, "		");
    _builder.append(") bo;");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("if (pictogramElement instanceof FreeFormConnection) {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("FreeFormConnection free = (FreeFormConnection) pictogramElement;");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("for (ConnectionDecorator decorator : free.getConnectionDecorators()) {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("String type = Graphiti.getPeService().getPropertyValue(decorator, \"MODEL_TYPE\");");
    _builder.newLine();
    _builder.append("                ");
    _builder.append("String value = getValue(type, eClass);");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("GraphicsAlgorithm ga = decorator.getGraphicsAlgorithm();");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("Text text = (Text) ga;");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("String current = text.getValue();");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("if (! current.equals(value) ) {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("return Reason.createTrueReason(type + \" is changed\");");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return Reason.createFalseReason();");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public boolean update(IUpdateContext context) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("PictogramElement pictogramElement = context.getPictogramElement();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("Object bo = getBusinessObjectForPictogramElement(pictogramElement);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append(metaClassName, "		");
    _builder.append(" eClass = (");
    _builder.append(metaClassName, "		");
    _builder.append(") bo;");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("FreeFormConnection free = (FreeFormConnection) pictogramElement;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("for (ConnectionDecorator decorator : free.getConnectionDecorators()) {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("String type = Graphiti.getPeService().getPropertyValue(decorator, \"MODEL_TYPE\");");
    _builder.newLine();
    _builder.append("\t                ");
    _builder.append("String value = getValue(type, eClass);");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("GraphicsAlgorithm ga = decorator.getGraphicsAlgorithm();");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("Text text = (Text) ga;");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("String current = text.getValue();");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("if (! current.equals(value) ) {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("text.setValue(value);");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("//");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// return SprayContainerService.update(pictogramElement,");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("// getValues(eClass));");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("//");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return true;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("protected String getValue(String type, ");
    MetaClass _represents_6 = connection.getRepresents();
    String _name_4 = this.e1.getName(_represents_6);
    _builder.append(_name_4, "    ");
    _builder.append(" eClass) {");
    _builder.newLineIfNotEmpty();
    _builder.append("    \t");
    _builder.append("String result = \"\";");
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("if( \"FROM_LABEL\".equals(type) ){");
    _builder.newLine();
    _builder.append("\t\t\t");
    Text _fromLabel = connection.getFromLabel();
    Text fromLabel = _fromLabel;
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("result = ");
    Text _fromLabel_1 = connection.getFromLabel();
    StringConcatenation _valueGenerator = this.valueGenerator(_fromLabel_1, "eClass");
    _builder.append(_valueGenerator, "			");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("    \t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("if( \"TO_LABEL\".equals(type) ){");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("result = ");
    Text _label = connection.getToLabel();
    StringConcatenation _valueGenerator_1 = this.valueGenerator(_label, "eClass");
    _builder.append(_valueGenerator_1, "			");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("    \t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("if( \"CONNECTION_LABEL\".equals(type) ){");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("result = ");
    Text _connectionLabel = connection.getConnectionLabel();
    StringConcatenation _valueGenerator_2 = this.valueGenerator(_connectionLabel, "eClass");
    _builder.append(_valueGenerator_2, "			");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("    \t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("return result;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public boolean hasDoneChanges() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return false;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public boolean canUndo(IContext context) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return false;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}