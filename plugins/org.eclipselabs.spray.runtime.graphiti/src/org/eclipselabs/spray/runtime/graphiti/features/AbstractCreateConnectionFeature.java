package org.eclipselabs.spray.runtime.graphiti.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaCreateService;
import org.eclipse.graphiti.services.IGaLayoutService;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.services.IPeService;

/**
 * Spray specific feature base class.
 * 
 * @author Karsten Thoms (karsten.thoms@itemis.de)
 */
public abstract class AbstractCreateConnectionFeature extends org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature {
    protected final IGaCreateService gaCreateService;
    protected final IGaLayoutService gaLayoutService;
    protected final IGaService       gaService;
    protected final IPeCreateService peCreateService;
    protected final IPeService       peService;
    private boolean                  doneChanges;

    public AbstractCreateConnectionFeature(IFeatureProvider fp, String name, String description) {
        super(fp, name, description);
        gaCreateService = Graphiti.getGaCreateService();
        gaLayoutService = Graphiti.getGaLayoutService();
        gaService = Graphiti.getGaService();
        peCreateService = Graphiti.getPeCreateService();
        peService = Graphiti.getPeService();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDoneChanges() {
        return doneChanges;
    }

    protected final void setDoneChanges(boolean doneChanges) {
        this.doneChanges = doneChanges;
    }

    @Override
    public boolean canUndo(IContext context) {
        return false;
    }
}