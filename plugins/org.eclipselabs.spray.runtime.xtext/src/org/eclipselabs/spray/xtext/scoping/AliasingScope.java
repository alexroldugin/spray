package org.eclipselabs.spray.xtext.scoping;

import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.AliasedEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.AbstractScope;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

/**
 * A scope that aliases the IEObjectDescriptions of the wrapped scope.
 * 
 * @author Karsten Thoms (karsten.thoms@itemis.de)
 */
public class AliasingScope extends AbstractScope {
    public static final Function<QualifiedName, QualifiedName> LAST_SEGMENT = new Function<QualifiedName, QualifiedName>() {
                                                                                @Override
                                                                                public QualifiedName apply(QualifiedName input) {
                                                                                    return QualifiedName.create(input.getLastSegment());
                                                                                }
                                                                            };

    private Iterable<IEObjectDescription>                      elements;

    /**
     * Creates the aliasing scope.
     * 
     * @param parent
     *            Parent scope
     * @param aliasingFunction
     *            A function that maps qualified names to their aliases
     */
    public AliasingScope(IScope parent, final Function<QualifiedName, QualifiedName> aliasingFunction) {
        super(IScope.NULLSCOPE, false);
        final Function<IEObjectDescription, IEObjectDescription> toAliasedDescription = new Function<IEObjectDescription, IEObjectDescription>() {
            @Override
            public IEObjectDescription apply(IEObjectDescription input) {
                return new AliasedEObjectDescription(aliasingFunction.apply(input.getQualifiedName()), input);
            }
        };
        elements = Iterables.transform(parent.getAllElements(), toAliasedDescription);
    }

    @Override
    protected Iterable<IEObjectDescription> getAllLocalElements() {
        return elements;
    }
}
