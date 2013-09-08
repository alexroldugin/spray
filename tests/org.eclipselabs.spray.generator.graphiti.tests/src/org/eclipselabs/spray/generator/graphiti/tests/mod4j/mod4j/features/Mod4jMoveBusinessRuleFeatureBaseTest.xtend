/** ****************************************************************************
 * Copyright (c)  The Spray Project.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Spray Dev Team - initial API and implementation
 **************************************************************************** */
package org.eclipselabs.spray.generator.graphiti.tests.mod4j.mod4j.features

import org.eclipse.xtext.junit4.InjectWith
import org.eclipselabs.spray.generator.graphiti.tests.AbstractSprayGeneratorTest
import org.eclipselabs.spray.generator.graphiti.tests.SprayTestsInjectorProvider
import org.eclipselabs.xtext.utils.unittesting.XtextRunner2
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

@RunWith(typeof(XtextRunner2))
@InjectWith(typeof(SprayTestsInjectorProvider))
class Mod4jMoveBusinessRuleFeatureBaseTest extends AbstractSprayGeneratorTest {

    @Test
    def void test() {
       val fsa = triggerGenerator("mod4j/mod4j-busmod.spray")
       val keyClass = "DEFAULT_OUTPUTorg/eclipselabs/spray/examples/mod4j/features/Mod4jMoveBusinessRuleFeatureBase.java"
       assertTrue(keyClass + " should have been generated", fsa.files.containsKey(keyClass))
       assertEquals(keyClass + " should have the expected content", fsa.files.get(keyClass).toString, expectedContent.toString)
    }

    def private expectedContent() '''
        /*************************************************************************************
         *
         * Generated by Spray MoveFeature.xtend
         *
         * This file contains generated and should not be changed.
         * Use the extension point class (the direct subclass of this class) to add manual code
         *
         *************************************************************************************/
        package org.eclipselabs.spray.examples.mod4j.features;
        
        import org.eclipse.graphiti.features.IFeatureProvider;
        import org.eclipse.graphiti.features.IRemoveFeature;
        import org.eclipse.graphiti.features.context.IMoveShapeContext;
        import org.eclipse.graphiti.features.impl.DefaultMoveShapeFeature;
        import org.eclipse.graphiti.features.context.impl.AddContext;
        import org.eclipse.graphiti.features.context.impl.RemoveContext;
        import org.eclipse.graphiti.mm.pictograms.PictogramElement;
        import org.eclipse.graphiti.mm.pictograms.ContainerShape;
        import org.eclipse.graphiti.mm.pictograms.Shape;
        
        import org.eclipselabs.spray.runtime.graphiti.GraphitiProperties;
        import org.eclipselabs.spray.runtime.graphiti.ISprayConstants;
        import org.eclipselabs.spray.runtime.graphiti.layout.SprayLayoutService;
        import org.eclipselabs.spray.runtime.graphiti.layout.SprayFixedLayoutManager;
        import org.eclipselabs.spray.runtime.graphiti.layout.SprayTopLayoutManager;
        import org.eclipselabs.spray.runtime.graphiti.layout.SprayDiagramLayoutManager;
        import org.eclipselabs.spray.runtime.graphiti.shape.SprayLayoutManager;
        import org.eclipselabs.spray.examples.mod4j.shapes.;
        
        import samplepackage.BusinessClass;
        import samplepackage.BusinessRule;
        
        
        public abstract class Mod4jMoveBusinessRuleFeatureBase extends DefaultMoveShapeFeature {
        
            SprayLayoutManager layoutManager; 
        
            public Mod4jMoveBusinessRuleFeatureBase(final IFeatureProvider fp) {
                super(fp);
                layoutManager =  new (fp).getShapeLayout( );
            }
        
             /**
              * {@inheritDoc}
              */
             @Override
            public boolean canMoveShape(IMoveShapeContext context) {
                Shape sourceShape = (Shape) context.getPictogramElement();
                ContainerShape targetContainer = context.getTargetContainer();
                Object source = getBusinessObjectForPictogramElement(sourceShape );
                Object target = getBusinessObjectForPictogramElement(targetContainer);
                // check whether it can move in the same container
                if( sourceShape.eContainer() == targetContainer ){
                    if ((SprayLayoutService.getLayoutManager(sourceShape.getContainer()) instanceof SprayFixedLayoutManager)) {
                        return true;
                    }
                    if ((SprayLayoutService.getLayoutManager(sourceShape.getContainer()) instanceof SprayTopLayoutManager)) {
                        return true;
                    }
                    if ((SprayLayoutService.getLayoutManager(sourceShape.getContainer()) instanceof SprayDiagramLayoutManager)) {
                        return true;
                    }
                    return false;
                }
                // Can move from containment to another containment compartment
                if (target instanceof BusinessClass) {
                    if (SprayLayoutService.isCompartment(context.getTargetContainer())) {
                        String id = GraphitiProperties.get(context.getTargetContainer(), ISprayConstants.TEXT_ID);
                        if ((id != null) && (id.equals("businessrules"))) {
                            return true;
                        }
                    }
                }
                // Can move from containment to another containment compartment
                if (target instanceof BusinessClass) {
                    if (SprayLayoutService.isCompartment(context.getTargetContainer())) {
                        String id = GraphitiProperties.get(context.getTargetContainer(), ISprayConstants.TEXT_ID);
                        if ((id != null) && (id.equals("simpleAttributes"))) {
                            return true;
                        }
                    }
                }
                return super.canMoveShape(context);
            }
            /**
             * {@inheritDoc}
             */
            @Override
            public void moveShape(IMoveShapeContext context) {
                PictogramElement sourceShape = context.getPictogramElement();
                ContainerShape targetContainer = context.getTargetContainer();
                ContainerShape sourceContainer = context.getSourceContainer();
                Object sourceParent = getBusinessObjectForPictogramElement(sourceContainer);
                Object source = getBusinessObjectForPictogramElement(sourceShape);
                Object target = getBusinessObjectForPictogramElement(targetContainer);
                if( sourceShape.eContainer() == targetContainer ){ 
                    super.moveShape(context);
                    return;   
                }
                if (target instanceof BusinessClass) {
                    if (SprayLayoutService.isCompartment(targetContainer) ) {
                        String id = GraphitiProperties.get(targetContainer, ISprayConstants.TEXT_ID);
                        if ((id != null) && (id.equals("businessrules"))) {
                            // create AddCointext5 fist, because the PROPERT_ALIAS property will be set to null after removing it.
                            AddContext addContext = new AddContext();
                            addContext.putProperty(ISprayConstants.PROPERTY_ALIAS, GraphitiProperties.get(sourceShape, ISprayConstants.PROPERTY_ALIAS));
            
                            RemoveContext removeContext = new RemoveContext(sourceShape);
                            IRemoveFeature rem = getFeatureProvider().getRemoveFeature(removeContext);
                            rem.remove(removeContext);
            
                            ContainerShape sourceTop = SprayLayoutService.findTopDslShape(sourceContainer);
                            if( sourceTop != null ){
                                SprayLayoutService.getLayoutManager(sourceTop).layout();
                            }
                            // remove from source container and add to target container
                            ((BusinessClass) target).getBusinessRules().add((BusinessRule) source);
                            addContext.setNewObject(source);
                            addContext.setLocation(context.getX(), context.getX());
                            addContext.setTargetContainer(targetContainer);
                            getFeatureProvider().addIfPossible(addContext);
                            return;
                        }
                    }
                }
                if (target instanceof BusinessClass) {
                    if (SprayLayoutService.isCompartment(targetContainer) ) {
                        String id = GraphitiProperties.get(targetContainer, ISprayConstants.TEXT_ID);
                        if ((id != null) && (id.equals("simpleAttributes"))) {
                            // create AddCointext5 fist, because the PROPERT_ALIAS property will be set to null after removing it.
                            AddContext addContext = new AddContext();
                            addContext.putProperty(ISprayConstants.PROPERTY_ALIAS, GraphitiProperties.get(sourceShape, ISprayConstants.PROPERTY_ALIAS));
            
                            RemoveContext removeContext = new RemoveContext(sourceShape);
                            IRemoveFeature rem = getFeatureProvider().getRemoveFeature(removeContext);
                            rem.remove(removeContext);
            
                            ContainerShape sourceTop = SprayLayoutService.findTopDslShape(sourceContainer);
                            if( sourceTop != null ){
                                SprayLayoutService.getLayoutManager(sourceTop).layout();
                            }
                            // remove from source container and add to target container
                            ((BusinessClass) target).getBusinessRules().add((BusinessRule) source);
                            addContext.setNewObject(source);
                            addContext.setLocation(context.getX(), context.getX());
                            addContext.setTargetContainer(targetContainer);
                            getFeatureProvider().addIfPossible(addContext);
                            return;
                        }
                    }
                }
            }
        }
    '''
}
