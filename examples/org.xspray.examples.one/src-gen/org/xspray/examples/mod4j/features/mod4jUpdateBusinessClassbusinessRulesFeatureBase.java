/*************************************************************************************
 *
 * Generated on Mon Aug 29 17:53:16 CEST 2011 by XSpray UpdateReferenceAsListFeature.xtend
 *
 * This file contains generated and should not be changed.
 * Use the extension point class (the direct subclass of this class) to add manual code
 *
 *************************************************************************************/
package org.xspray.examples.mod4j.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.impl.AbstractUpdateFeature;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import BusinessDomainDsl.AbstractBusinessRule;

public class mod4jUpdateBusinessClassbusinessRulesFeatureBase extends AbstractUpdateFeature {

    public mod4jUpdateBusinessClassbusinessRulesFeatureBase(IFeatureProvider fp) {
        super(fp);
    }

    @Override
    public boolean canUpdate(IUpdateContext context) {
        // return true, if linked business object is a EClass
        Object bo = getBusinessObjectForPictogramElement(context.getPictogramElement());
        return (bo instanceof AbstractBusinessRule);
    }

    @Override
    public IReason updateNeeded(IUpdateContext context) {
        // retrieve name from pictogram model
        String pictogramName = null;
        PictogramElement pictogramElement = context.getPictogramElement();
        if (pictogramElement instanceof Shape) {
            Shape cs = (Shape) pictogramElement;
            if (cs.getGraphicsAlgorithm() instanceof Text) {
                Text text = (Text) cs.getGraphicsAlgorithm();
                // Graphiti.getPeService().getPropertyValue(shape, "REFERENCE");
                if (pictogramName == null) {
                    pictogramName = text.getValue();
                }
            }
        }

        // retrieve name from business model
        String businessName = null;
        Object bo = getBusinessObjectForPictogramElement(pictogramElement);
        if (bo instanceof AbstractBusinessRule) {
            AbstractBusinessRule reference = (AbstractBusinessRule) bo;
            businessName = reference.getName();
        }

        // update needed, if names are different
        boolean updateNameNeeded = ((pictogramName == null && businessName != null) || (pictogramName != null && !pictogramName.equals(businessName)));
        if (updateNameNeeded) {
            return Reason.createTrueReason("Property Name is out of date");
        } else {
            return Reason.createFalseReason();
        }
    }

    @Override
    public boolean update(IUpdateContext context) {
        // retrieve name from business model
        String businessName = null;
        PictogramElement pictogramElement = context.getPictogramElement();
        Object bo = getBusinessObjectForPictogramElement(pictogramElement);
        if (bo instanceof AbstractBusinessRule) {
            AbstractBusinessRule eClass = (AbstractBusinessRule) bo;
            businessName = eClass.getName();
        }

        // Set name in pictogram model
        if (pictogramElement instanceof Shape) {
            Shape cs = (Shape) pictogramElement;
            if (cs.getGraphicsAlgorithm() instanceof Text) {
                Text text = (Text) cs.getGraphicsAlgorithm();
                text.setValue(businessName);
                layoutPictogramElement(cs.getContainer().getContainer());
                return true;
            }
        }
        return false;
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