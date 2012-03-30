package org.eclipselabs.spray.generator.graphiti.templates.features

import org.eclipselabs.spray.generator.graphiti.templates.FileGenerator
import org.eclipselabs.spray.mm.spray.MetaClass
import org.eclipselabs.spray.generator.graphiti.util.NamingExtensions
import org.eclipselabs.spray.generator.graphiti.util.mm.MetaClassExtensions
import com.google.inject.Inject

import static org.eclipselabs.spray.generator.graphiti.util.GeneratorUtil.*
import org.eclipselabs.spray.mm.spray.CustomBehavior

class DirectEditEClassFeature extends FileGenerator<MetaClass> {
	
	@Inject extension NamingExtensions
    @Inject extension MetaClassExtensions
    
	override generateExtensionFile(MetaClass modelElement) {
		modelElement.mainExtensionPointFile(javaGenFile.className);
	}
	
	override generateBaseFile(MetaClass modelElement) {
		modelElement.mainFile(javaGenFile.baseClassName)
	}
	
	 def mainExtensionPointFile(MetaClass metaClass, String className) '''    
        «extensionHeader(this)»
        package «feature_package()»;
        
        import org.eclipse.emf.ecore.EObject;
        import org.eclipse.graphiti.features.IFeatureProvider;
        import org.eclipse.graphiti.features.context.ICustomContext;
        
        public class «className» extends «className»Base {
            public «className»(IFeatureProvider fp) {
                super(fp);
            }
        }
    '''

    def mainFile(MetaClass metaclass, String className) '''
        «header(this)»
        package «feature_package()»;
        
        import org.eclipse.emf.ecore.EClass;
        import org.eclipse.graphiti.features.IFeatureProvider;
        import org.eclipse.graphiti.features.context.IDirectEditingContext;
        import org.eclipse.graphiti.mm.pictograms.PictogramElement;
        import org.eclipse.graphiti.mm.pictograms.Shape;
        import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
        import org.eclipse.graphiti.mm.algorithms.Text;
        import org.eclipse.graphiti.features.impl.AbstractDirectEditingFeature;
        
        public abstract class «className» extends AbstractDirectEditingFeature {
            «generate_additionalFields(metaclass)»
        
            public «className»(IFeatureProvider fp) {
                super(fp);
            }
        
            «generate_canDirectEdit(metaclass)»
            «generate_getInitalValue(metaclass)»
            «generate_getEditingType(metaclass)»
            «generate_setValue(metaclass)»
        }
    '''
	
	def generate_canDirectEdit (MetaClass metaclass) '''
        «overrideHeader»
         public boolean canDirectEdit(IDirectEditingContext context) {
        	PictogramElement pe = context.getPictogramElement();
	        Object bo = getBusinessObjectForPictogramElement(pe);
	        GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
	        // support direct editing, if it is a EClass, and the user clicked
	        // directly on the text and not somewhere else in the rectangle
	        if (bo instanceof EClass && ga instanceof Text) {
	            return true;
	        }
	        // direct editing not supported in all other cases
	        return false;  
        } 
    '''

	def generate_getInitalValue(MetaClass metaclass)'''
        «overrideHeader»
        public String getInitialValue(IDirectEditingContext context) {
			// return the current name of the EClass
	     	PictogramElement pe = context.getPictogramElement();
	     	EClass eClass = (EClass) getBusinessObjectForPictogramElement(pe);
	        return eClass.getName();
    	}
    '''
	
	def generate_getEditingType(MetaClass metaclass)'''
        «overrideHeader»
        public int getEditingType() {
	        // there are several possible editor-types supported:
	        // text-field, checkbox, color-chooser, combobox, ...
	        return TYPE_TEXT;
    	}
    '''
    
    def generate_setValue(MetaClass metaclass)'''
        «overrideHeader»
    	public void setValue(String value, IDirectEditingContext context) {
	        // set the new name for the MOF class
	        PictogramElement pe = context.getPictogramElement();
	        EClass eClass = (EClass) getBusinessObjectForPictogramElement(pe);
	        eClass.setName(value);
	        // Explicitly update the shape to display the new value in the diagram
	        // Note, that this might not be necessary in future versions of Graphiti
	        // (currently in discussion)
	        // we know, that pe is the Shape of the Text, so its container is the
	        // main shape of the EClass
	        updatePictogramElement(((Shape) pe).getContainer());
    	}
	'''     
}