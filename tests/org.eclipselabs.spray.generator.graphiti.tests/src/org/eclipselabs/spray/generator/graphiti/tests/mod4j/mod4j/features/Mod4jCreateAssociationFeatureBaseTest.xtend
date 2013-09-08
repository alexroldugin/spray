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
class Mod4jCreateAssociationFeatureBaseTest extends AbstractSprayGeneratorTest {

    @Test
    def void test() {
       val fsa = triggerGenerator("mod4j/mod4j-busmod.spray")
       val keyClass = "DEFAULT_OUTPUTorg/eclipselabs/spray/examples/mod4j/features/Mod4jCreateAssociationFeatureBase.java"
       assertTrue(keyClass + " should have been generated", fsa.files.containsKey(keyClass))
       assertEquals(keyClass + " should have the expected content", fsa.files.get(keyClass).toString, expectedContent.toString)
    }

    def private expectedContent() '''
        /*************************************************************************************
         *
         * Generated by Spray CreateConnectionFeature.xtend
         *
         * This file contains generated and should not be changed.
         * Use the extension point class (the direct subclass of this class) to add manual code
         *
         *************************************************************************************/
        package org.eclipselabs.spray.examples.mod4j.features;
        import org.eclipse.emf.common.util.EList;
        import org.eclipse.emf.ecore.EObject;
        import org.eclipse.graphiti.features.IFeatureProvider;
        import org.eclipse.graphiti.features.context.ICreateConnectionContext;
        import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
        import org.eclipse.graphiti.mm.pictograms.Anchor;
        import org.eclipse.graphiti.mm.pictograms.Connection;
        import org.eclipse.graphiti.mm.pictograms.PictogramElement;
        import org.eclipse.graphiti.mm.pictograms.Shape;
        import org.eclipse.graphiti.services.IGaService;
        import org.eclipselabs.spray.runtime.graphiti.features.AbstractCreateConnectionFeature;
        import org.eclipselabs.spray.runtime.graphiti.containers.SampleUtil;
        import org.eclipselabs.spray.runtime.graphiti.layout.SprayLayoutService;
        import null.BusinessDomainDslFactory;
        import org.eclipselabs.spray.examples.mod4j.Activator;
        import samplepackage.AbstractBusinessClass;
        import samplepackage.Association;
        import samplepackage.BusinessClass;
        import samplepackage.BusinessDomainModel;
        
        
        public abstract class Mod4jCreateAssociationFeatureBase extends AbstractCreateConnectionFeature {
            protected static String TITLE = "Create Association";
            protected static String USER_QUESTION = "Enter new Association name";
        
            public Mod4jCreateAssociationFeatureBase(final IFeatureProvider fp) {
                // provide name and description for the UI, e.g. the palette
                super(fp, "Association", "Create Association");
                gaService = Activator.get(IGaService.class);
            }
        
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean canCreate(final ICreateConnectionContext context) {
                Anchor targetAnchor = getDslShapeAnchor(context.getTargetPictogramElement());
                if (targetAnchor == null){
                    return false;
                }
                // return true if both anchors belong to an EClass of the right type and those EClasses are not identical
                Anchor sourceAnchor = getDslShapeAnchor(context.getSourcePictogramElement());
                BusinessClass source = getBusinessClass(sourceAnchor);
                AbstractBusinessClass target = getAbstractBusinessClass(targetAnchor);
                if ( (source != null) && (target != null) && (source != target) ) {
                    return true;
                }
                return false;
            }
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean canStartConnection(final ICreateConnectionContext context) {
                // return true if start anchor belongs to a EClass of the right type
                Anchor sourceAnchor = getDslShapeAnchor(context.getSourcePictogramElement());
                if (getBusinessClass(sourceAnchor) != null) {
                    return true;
                }
                return false;
            }
                /**
                 * {@inheritDoc}
                 */
                @Override
                public Connection create(final ICreateConnectionContext context) {
                    Connection newConnection = null;
                    Anchor sourceAnchor = getDslShapeAnchor(context.getSourcePictogramElement());
                    Anchor targetAnchor = getDslShapeAnchor(context.getTargetPictogramElement());
            
                    // get EClasses which should be connected
                    final BusinessClass source = getBusinessClass(sourceAnchor);
                    final AbstractBusinessClass target = getAbstractBusinessClass(targetAnchor);
                    // containment reference is not a feature of source
                    final BusinessDomainModel container = org.eclipse.xtext.EcoreUtil2.getContainerOfType(source, BusinessDomainModel.class);
                    if (source != null && target != null) {
                        // create new business object
                        final Association eReference = createAssociation(source, target);
                        // add the element to containment reference
                        container.getAssociations().add(eReference);
                        // add connection for business object
                        final AddConnectionContext addContext = new AddConnectionContext(sourceAnchor, targetAnchor);
                        addContext.setNewObject(eReference);
                        newConnection = (Connection) getFeatureProvider().addIfPossible(addContext);
                        
                        // activate direct editing after object creation
                        getFeatureProvider().getDirectEditingInfo().setActive(true);
                    }
            
                    return newConnection;
                }
            /**
             * Returns the BusinessClass belonging to the anchor, or <code>null</code> if not available.
             */
            protected BusinessClass getBusinessClass(final Anchor anchor) {
                if (anchor != null) {
                    final EObject bo = (EObject) getBusinessObjectForPictogramElement(anchor.getParent()); 
                    if (bo instanceof BusinessClass) {
                        return (BusinessClass) bo;
                    }
                }
                return null;
            }
                     /**
             * Returns the AbstractBusinessClass belonging to the anchor, or <code>null</code> if not available.
             */
            protected AbstractBusinessClass getAbstractBusinessClass(final Anchor anchor) {
                if (anchor != null) {
                    final EObject bo = (EObject) getBusinessObjectForPictogramElement(anchor.getParent()); 
                    if (bo instanceof AbstractBusinessClass) {
                        return (AbstractBusinessClass) bo;
                    }
                }
                return null;
            }
            /**
             * Creates a EReference between two EClasses.
             */
            protected Association createAssociation(final BusinessClass source, final AbstractBusinessClass target) {
                // TODO: Domain Object
                final Association domainObject = BusinessDomainDslFactory.eINSTANCE.createAssociation();
                domainObject.setSource(source);
                domainObject.setTarget(target);
            
                setDoneChanges(true);
                return domainObject;
            }
            protected Anchor getDslShapeAnchor(PictogramElement pe) {
                if( pe == null ){
                    return null;
                }
                Shape dslShape = SprayLayoutService.findDslShape(pe);
                if (dslShape != null) {
                    EList<Anchor> anchors = dslShape.getAnchors();
                    if (!anchors.isEmpty()) {
                        return anchors.get(0);
                    }
                }
                return null;
            }
        }
    '''
}
