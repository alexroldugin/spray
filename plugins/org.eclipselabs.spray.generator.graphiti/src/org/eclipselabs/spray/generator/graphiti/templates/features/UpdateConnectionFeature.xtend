package org.eclipselabs.spray.generator.graphiti.templates.features

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.xtend2.lib.StringConcatenation
import org.eclipselabs.spray.generator.graphiti.templates.FileGenerator
import org.eclipselabs.spray.generator.graphiti.util.NamingExtensions
import org.eclipselabs.spray.mm.spray.Connection

import static org.eclipselabs.spray.generator.graphiti.util.GeneratorUtil.*
import static org.eclipselabs.spray.generator.graphiti.util.MetaModel.*

class UpdateConnectionFeature extends FileGenerator  {
    @Inject extension NamingExtensions
    
    override StringConcatenation generateBaseFile(EObject modelElement) {
        mainFile( modelElement as Connection, javaGenFile.baseClassName)
    }

    override StringConcatenation generateExtensionFile(EObject modelElement) {
        mainExtensionPointFile( modelElement as Connection, javaGenFile.className)
    }
    
    def mainExtensionPointFile(Connection connection, String className) '''    
        «extensionHeader(this)»
        package «feature_package()»;
        
        import org.eclipse.graphiti.features.IFeatureProvider;
        
        public class «className» extends «className»Base {
            public «className»(IFeatureProvider fp) {
                super(fp);
            }
        
            @Override
            public boolean hasDoneChanges() {
                return false;
            }
        
        }
    '''
    
    def mainFile(Connection connection, String className) '''
        «val diagramName = connection.represents.diagram.name »
        «val metaClassName = connection.represents.name»
        «val pack = connection.represents.type.EPackage.name »
        «val fullPackage = fullPackageName(connection.represents.type) »
        «val labelName = "name" »
        «header(this)»
        package «feature_package()»;

        import java.util.Map;
        
        import org.eclipse.emf.ecore.EObject;
        import org.eclipse.graphiti.features.IFeatureProvider;
        import org.eclipse.graphiti.features.IReason;
        import org.eclipse.graphiti.features.context.IUpdateContext;
        import org.eclipse.graphiti.features.context.IContext;
        import org.eclipse.graphiti.features.impl.Reason;
        import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
        import org.eclipse.graphiti.mm.algorithms.Text;
        import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
        import org.eclipse.graphiti.mm.pictograms.Diagram;
        import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
        import org.eclipse.graphiti.mm.pictograms.PictogramElement;
        import org.eclipse.graphiti.services.Graphiti;
        import org.eclipselabs.spray.runtime.graphiti.features.AbstractEMFUpdateFeature;
        import «connection.represents.javaInterfaceName»;
        // MARKER_IMPORT
                
        public class «className» extends AbstractEMFUpdateFeature {
        
            Map<String, String> values = null;
        
            public «className»(IFeatureProvider fp) {
                super(fp);
            }
        
            @Override
            public boolean canUpdate(IUpdateContext context) {
                // return true, if linked business object is a EClass
                EObject bo = getBusinessObjectForPictogramElement(context.getPictogramElement());
                PictogramElement pictogramElement = context.getPictogramElement();
                return (bo instanceof «metaClassName») && (!(pictogramElement instanceof Diagram));
            }
        
            @Override
            public IReason updateNeeded(IUpdateContext context) {
                PictogramElement pictogramElement = context.getPictogramElement();
                EObject bo = getBusinessObjectForPictogramElement(pictogramElement);
                if ( ! (bo instanceof «metaClassName»)) {
                    return Reason.createFalseReason();
                }
                «metaClassName» eClass = («metaClassName») bo;
        
                if (pictogramElement instanceof FreeFormConnection) {
                    FreeFormConnection free = (FreeFormConnection) pictogramElement;
                    for (ConnectionDecorator decorator : free.getConnectionDecorators()) {
                        String type = Graphiti.getPeService().getPropertyValue(decorator, "MODEL_TYPE");
                        String value = getValue(type, eClass);
                        GraphicsAlgorithm ga = decorator.getGraphicsAlgorithm();
                        Text text = (Text) ga;
                        String current = text.getValue();
                        if (! current.equals(value) ) {
                            return Reason.createTrueReason(type + " is changed");
                        }
                    }
                }
                return Reason.createFalseReason();
            }
        
            @Override
            public boolean update(IUpdateContext context) {
                PictogramElement pictogramElement = context.getPictogramElement();
                EObject bo = getBusinessObjectForPictogramElement(pictogramElement);
                «metaClassName» eClass = («metaClassName») bo;
        
                FreeFormConnection free = (FreeFormConnection) pictogramElement;
                for (ConnectionDecorator decorator : free.getConnectionDecorators()) {
                    String type = Graphiti.getPeService().getPropertyValue(decorator, "MODEL_TYPE");
                    String value = getValue(type, eClass);
                    GraphicsAlgorithm ga = decorator.getGraphicsAlgorithm();
                    Text text = (Text) ga;
                    String current = text.getValue();
                    if (! current.equals(value) ) {
                        text.setValue(value);
                    }
                }
                //
                // return SprayContainerService.update(pictogramElement,
                // getValues(eClass));
                //
                return true;
            }
        
            protected String getValue(String type, «connection.represents.name» eClass) {
                String result = "";
                if( "FROM_LABEL".equals(type) ){
                    «var fromLabel =  connection.fromLabel»
                    «IF fromLabel != null»
                    «valueGenerator(connection.fromLabel, "eClass")»
                    «ENDIF»
                }
                if( "TO_LABEL".equals(type) ){
                    «IF connection.toLabel!=null»
                    «valueGenerator(connection.toLabel, "eClass")»
                    «ENDIF»
                }
                if( "CONNECTION_LABEL".equals(type) ){
                    «IF connection.connectionLabel!=null»
                    «valueGenerator(connection.connectionLabel, "eClass")»
                    «ENDIF»
                }
                return result;
            }
        
            @Override
            public boolean hasDoneChanges() {
                return false;
            }
        
            @Override
            public boolean canUndo(IContext context) {
                return false;
            }
        
        }
        '''
        
}