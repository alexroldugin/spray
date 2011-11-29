package org.eclipselabs.spray.generator.graphiti.templates.diagram

import com.google.inject.Inject
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.xtend2.lib.StringConcatenation
import org.eclipselabs.spray.generator.graphiti.templates.FileGenerator
import org.eclipselabs.spray.generator.graphiti.util.NamingExtensions
import org.eclipselabs.spray.mm.spray.Connection
import org.eclipselabs.spray.mm.spray.Container
import org.eclipselabs.spray.mm.spray.CustomBehavior
import org.eclipselabs.spray.mm.spray.Diagram
import org.eclipselabs.spray.mm.spray.MetaReference
import org.eclipselabs.spray.xtext.util.GenModelHelper

import static org.eclipselabs.spray.generator.graphiti.util.GeneratorUtil.*

import static extension org.eclipselabs.spray.generator.graphiti.util.MetaModel.*

class FeatureProvider extends FileGenerator {
    @Inject extension NamingExtensions
    @Inject extension GenModelHelper
    
    override StringConcatenation generateBaseFile(EObject modelElement) {
        mainFile( modelElement as Diagram, javaGenFile.baseClassName)
    }

    override StringConcatenation generateExtensionFile(EObject modelElement) {
        mainExtensionPointFile( modelElement as Diagram, javaGenFile.className)
    }
    
    def mainExtensionPointFile(Diagram diagram, String className) '''
        «extensionHeader(this)»
        package «diagram_package()»;
        
        import org.eclipse.graphiti.dt.IDiagramTypeProvider;
        
        public class «className» extends «className»Base {
        
            public «className»(IDiagramTypeProvider dtp) {
                super(dtp);
            }
        
        }
    '''
    
    def mainFile (Diagram diagram, String className) '''
        «header(this)»
        package «diagram_package()»;
        
        import org.eclipse.emf.ecore.EObject;
        import org.eclipse.graphiti.dt.IDiagramTypeProvider;
        import org.eclipse.graphiti.features.IAddFeature;
        import org.eclipse.graphiti.features.ICreateConnectionFeature;
        import org.eclipse.graphiti.features.ICreateFeature;
        import org.eclipse.graphiti.features.ILayoutFeature;
        import org.eclipse.graphiti.features.IMoveShapeFeature;
        import org.eclipse.graphiti.features.IUpdateFeature;
        import org.eclipse.graphiti.features.IDeleteFeature;
        import org.eclipse.graphiti.features.IRemoveFeature;
        import org.eclipse.graphiti.features.context.IAddContext;
        import org.eclipse.graphiti.features.context.ICustomContext;
        import org.eclipse.graphiti.features.context.IDeleteContext;
        import org.eclipse.graphiti.features.context.ILayoutContext;
        import org.eclipse.graphiti.features.context.IMoveShapeContext;
        import org.eclipse.graphiti.features.context.IUpdateContext;
        import org.eclipse.graphiti.features.context.IRemoveContext;
        import org.eclipse.graphiti.features.custom.ICustomFeature;
        import org.eclipse.graphiti.mm.pictograms.PictogramElement;
        import org.eclipse.graphiti.mm.pictograms.Shape;
        import org.eclipselabs.spray.runtime.graphiti.ISprayConstants;
        import org.eclipselabs.spray.runtime.graphiti.features.DefaultDeleteFeature;
        import org.eclipselabs.spray.runtime.graphiti.features.DefaultFeatureProvider;
        import org.eclipselabs.spray.runtime.graphiti.features.DefaultRemoveFeature;
        import «util_package()».OwnerPropertyDeleteFeature;
        «IF !diagram.metaClasses.empty»
        «ENDIF»
        // MARKER_IMPORT
        
        public class «className» extends DefaultFeatureProvider {
            «generate_additionalFields(diagram)»
            public «className»(IDiagramTypeProvider dtp) {
                super(dtp);
            }
        
            «generate_getAddFeature(diagram)»
            «generate_getCreateFeatures(diagram)»
            «generate_getCreateConnectionFeatures(diagram)»
            «generate_getUpdateFeature(diagram)»
            «generate_getLayoutFeature(diagram)»
            «generate_getRemoveFeature(diagram)»
            «generate_getDeleteFeature(diagram)»
            «generate_getMoveShapeFeature(diagram)»
            «generate_additionalFields(diagram)»
        }
    '''
    
    def generate_getAddFeature (Diagram diagram) '''
        «overrideHeader»
        public IAddFeature getAddFeature(IAddContext context) {
            // is object for add request a EClass or EReference?
            EObject object = (EObject) context.getNewObject() ;
            String reference = (String)context.getProperty(ISprayConstants.PROPERTY_REFERENCE);
            
            «FOR cls : diagram.metaClasses»
                if ( object.eClass() == «cls.type.EPackageClassName.shortName».Literals.«cls.type.literalConstant» ) {
                    if ( reference == null ){
                        return new «cls.addFeatureClassName.shortName»(this);
                        «FOR reference :  cls.references.filter(ref|ref.representedBy != null)  »
                        } else if( reference.equals(«reference.literalConstant».getName())){
                            return new «reference.addReferenceAsConnectionFeatureClassName.shortName»(this);
                        «ENDFOR»
                    }
                } 
                «IF cls.representedBy instanceof Container»
                    «val container = cls.representedBy as Container»
                    «FOR reference : container.parts.filter(typeof(MetaReference))  »
                        if( object instanceof «reference.target.EReferenceType.javaInterfaceName.shortName» ){
                            return new «reference.addReferenceAsListFeatureClassName.shortName»(this);
                        }
                    «ENDFOR»    
                «ENDIF»
            «ENDFOR»
            return super.getAddFeature(context);
        }
    '''
    
    def generate_getCreateFeatures (Diagram diagram) '''
        «overrideHeader»
        public ICreateFeature[] getCreateFeatures() {
            return new ICreateFeature[] { 
            «FOR cls : diagram.metaClasses.filter(e| ! (e.representedBy instanceof Connection) ) SEPARATOR ","»
                new «cls.createFeatureClassName.shortName»(this) 
                «IF cls.representedBy instanceof Container»
                    «val container = cls.representedBy as Container»
                    «FOR reference : container.parts.filter(typeof(MetaReference))»
                        «val target = reference.target»
                        «IF ! target.EReferenceType.abstract»
                        , new «reference.createFeatureClassName.shortName»(this)
                        «ENDIF»
                        «FOR subclass : target.EReferenceType.getSubclasses() »
                            «IF ! subclass.abstract »
                            , new «reference.getCreateReferenceAsListFeatureClassName(subclass).shortName»(this)
                            «ENDIF»
                        «ENDFOR»
                    «ENDFOR»    
                «ENDIF»
            «ENDFOR»
            };
        }    
    '''
    
    def generate_getUpdateFeature (Diagram diagram) '''
        «overrideHeader»
        public IUpdateFeature getUpdateFeature(IUpdateContext context) {
            PictogramElement pictogramElement = context.getPictogramElement();
        //    if (pictogramElement instanceof ContainerShape) {
                EObject bo = (EObject) getBusinessObjectForPictogramElement(pictogramElement);
                if (bo == null) return null;
            «FOR cls : diagram.metaClasses »
                «IF ! (cls.representedBy instanceof Connection) »
                if ( bo.eClass() == «cls.type.EPackageClassName.shortName».Literals.«cls.type.literalConstant» ) { // 11
                    return new «cls.updateFeatureClassName.shortName»(this); 
                }
                «ENDIF»
                «IF cls.representedBy instanceof Container»
                    «val container = cls.representedBy as Container»
                    «FOR reference : container.parts.filter(typeof(MetaReference))  »
                        «var eClass = reference.target.EReferenceType » 
                        «IF  eClass.abstract»
                            if (bo instanceof «eClass.javaInterfaceName.shortName») { // 22
                                return new «reference.updateReferenceAsListFeatureClassName.shortName»(this); 
                            }
                        «ENDIF»
                    «ENDFOR»
                «ELSEIF cls.representedBy instanceof Connection»
                    «var connection = cls.representedBy as Connection»
                        «IF !cls.type.abstract»
                            if (bo instanceof «cls.javaInterfaceName.shortName») { // 33
                                return new «cls.updateFeatureClassName.shortName»(this); 
                            }
                        «ENDIF»
                «ENDIF»
            «ENDFOR»
    //        }
            return super.getUpdateFeature(context);
        }
    '''
    
    def generate_getLayoutFeature (Diagram diagram) '''
        «overrideHeader»
        public ILayoutFeature getLayoutFeature(ILayoutContext context) {
            PictogramElement pictogramElement = context.getPictogramElement();
            EObject bo = (EObject) getBusinessObjectForPictogramElement(pictogramElement);
            if (bo == null) return null;
            «FOR cls : diagram.metaClasses.filter(m |! (m.representedBy instanceof Connection) )  »
            if ( bo.eClass()==«cls.type.EPackageClassName.shortName».Literals.«cls.type.literalConstant» ) {
                return new «cls.layoutFeatureClassName.shortName»(this);
            }
            «ENDFOR»
            return super.getLayoutFeature(context);
        }
    '''

    def generate_getCreateConnectionFeatures (Diagram diagram) '''
        «overrideHeader»
        public ICreateConnectionFeature[] getCreateConnectionFeatures() {
            return new ICreateConnectionFeature[] { 
            «FOR cls : diagram.metaClasses.filter(e|e.representedBy instanceof Connection) SEPARATOR ","»
                new «cls.createFeatureClassName.shortName»(this) 
            «ENDFOR»
            «IF ! diagram.metaClasses.filter(e|e.representedBy instanceof Connection).isEmpty »
            ,
            «ENDIF»
            «FOR metaClass : diagram.metaClasses»
                «FOR reference : metaClass.references.filter(ref|ref.representedBy != null) SEPARATOR ","»
                      new «reference.createReferenceAsConnectionFeatureClassName.shortName»(this) 
                «ENDFOR»
            «ENDFOR»
            };
        }
    '''
    
    def generate_getRemoveFeature (Diagram diagram) '''
        «overrideHeader»
        public IRemoveFeature getRemoveFeature(IRemoveContext context) {
            // Spray specific DefaultRemoveFeature
            return new DefaultRemoveFeature(this);
        }
    '''
    
    def generate_getDeleteFeature (Diagram diagram) '''
        public IDeleteFeature getDeleteFeature(IDeleteContext context) {
            PictogramElement pictogramElement = context.getPictogramElement();
            EObject bo = getBusinessObjectForPictogramElement(pictogramElement);
            if (bo == null) return null;
            String reference = peService.getPropertyValue(pictogramElement, ISprayConstants.PROPERTY_REFERENCE);
    
            «FOR cls : diagram.metaClasses »
            if ( bo.eClass()==«cls.type.EPackageClassName.shortName».Literals.«cls.type.literalConstant» ) {
                if( reference == null ){
                    return new DefaultDeleteFeature(this); 
                «FOR reference : cls.references.filter(ref|ref.representedBy != null)  »
                } else if( reference.equals(«reference.literalConstant».getName())){
                    return new «reference.deleteReferenceFeatureClassName.shortName»(this);
                «ENDFOR»    
                }
            } 
                «IF cls.representedBy instanceof Container»
                    «val container = cls.representedBy as Container»
                    «FOR reference : container.parts.filter(typeof(MetaReference))  »
                        «val target = reference.target» 
                    if( bo instanceof «target.EReferenceType.name» ){
                        return new OwnerPropertyDeleteFeature(this);
                    }
                    «ENDFOR»    
                «ENDIF»
            «ENDFOR»
            
            return new DefaultDeleteFeature(this); 
        }
    '''
    
    def generate_getMoveShapeFeature (Diagram diagram) '''
        /** 
         * Ensure that any shape with property {@link ISprayConstants#CAN_MOVE} set to false will not have a move feature.
         */
        @Override
        public IMoveShapeFeature getMoveShapeFeature(IMoveShapeContext context) {
            Shape s = context.getShape();
            String stat  = peService.getPropertyValue(s, ISprayConstants.PROPERTY_CAN_MOVE);
            if( Boolean.valueOf(stat) == Boolean.FALSE){
                return null;
            }
            return super.getMoveShapeFeature(context);
        }
    '''
    
    def generate_getCustomFeatures (Diagram diagram) '''
        @Override
        public ICustomFeature[] getCustomFeatures(ICustomContext context) {
            EObject bo = (EObject) getBusinessObjectForPictogramElement(context.getPictogramElements()[0]);
            if (bo == null) return new ICustomFeature[0];
            «FOR metaClass : diagram.metaClasses »
                «IF !metaClass.behaviors.isEmpty»
                    if( bo.eClass()==«metaClass.type.EPackageClassName.shortName».Literals.«metaClass.type.literalConstant» ){
                    return new ICustomFeature[]{ 
                    «val List<String> allnames2 = new ArrayList<String>()»
                    «FOR behavior : metaClass.behaviors.filter(typeof(CustomBehavior))  SEPARATOR  ","»
                        «IF ! allnames2.contains(behavior.name)»
                            new «behavior.customFeatureClassName.shortName»(this) /*«allnames2.add(behavior.name)»*/
                        «ENDIF»
                    «ENDFOR»
                    };
                    }
                «ENDIF»
            «ENDFOR»
            return new ICustomFeature[0];
        }
    '''
}