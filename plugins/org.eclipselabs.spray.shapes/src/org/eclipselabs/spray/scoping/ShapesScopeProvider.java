/*
 * generated by Xtext
 */
package org.eclipselabs.spray.scoping;

import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.common.types.JvmField;
import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.common.types.JvmType;
import org.eclipse.xtext.common.types.TypesPackage;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;
import org.eclipse.xtext.scoping.impl.FilteringScope;
import org.eclipse.xtext.scoping.impl.SimpleScope;
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociations;
import org.eclipse.xtext.xbase.scoping.LocalVariableScopeContext;
import org.eclipse.xtext.xbase.scoping.XbaseScopeProvider;
import org.eclipselabs.spray.shapes.WithStyle;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * This class contains custom scoping description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#scoping on
 * how and when to use it
 * 
 */
public class ShapesScopeProvider extends XbaseScopeProvider {

	@Override
	public IScope getScope(EObject context, EReference reference) {
		if (reference == TypesPackage.Literals.JVM_PARAMETERIZED_TYPE_REFERENCE__TYPE) {
			WithStyle withStyle = EcoreUtil2.getContainerOfType(context, WithStyle.class);
			if (withStyle != null) {
				return getStyleTypeScope(withStyle);
			}
		}
		return super.getScope(context, reference);
	}

	protected IScope getStyleTypeScope(WithStyle withStyle) {
		IScope typesScope = delegateGetScope(withStyle, TypesPackage.Literals.JVM_PARAMETERIZED_TYPE_REFERENCE__TYPE);
		Predicate<IEObjectDescription> stylesFilter = new StyleScopeRestrictor();
		IScope result = new FilteringScope(typesScope, stylesFilter);
		return result;
	}
	
    /**
     * Create the local variable scope for expressions.
     * The method will bind a variable 'this' which refers to the JvmType of the EClass associated with the current MetaClass.
     */
//	@Override
//    protected IScope createLocalVarScope(IScope parentScope, LocalVariableScopeContext scopeContext) {
//        // Look up the containment hierarchy of the current object to find the MetaClass
//        WithStyle ws = EcoreUtil2.getContainerOfType(scopeContext.getContext(), WithStyle.class);
//        if (ws != null) {
//            // get the JvmType for MetaClass. It is inferred by the SprayJvmModelInferrer
//            JvmGenericType jvmType = (JvmGenericType) getJvmType(ws);
//            if (jvmType == null || jvmType.getMembers().isEmpty()) {
//                // should not happen!
//                return IScope.NULLSCOPE;
//            }
//            // the JvmType has a field named 'ecoreClass'
//            JvmField eClassField = (JvmField) jvmType.getMembers().get(0);
//            Assert.isTrue(eClassField.getSimpleName().equals("ecoreClass"));
//            // get the JvmType of the associated EClass
//            JvmType jvmTypeOfEcoreClass = eClassField.getType().getType();
//            // bind the EClass' JvmType as variable 'this'
//            IScope result = new SimpleScope(parentScope, Collections.singleton(EObjectDescription.create(XbaseScopeProvider.THIS, jvmTypeOfEcoreClass)));
//            return result;
//        }
//        return super.createLocalVarScope(parentScope, scopeContext);
//    }
	
//    protected JvmType getJvmType(EObject context) {
//        Iterable<JvmType> jvmTypes = Iterables.filter(associations.getJvmElements(context), JvmType.class);
//        Iterator<JvmType> it = jvmTypes.iterator();
//        JvmType result = it.hasNext() ? it.next() : null;
//        return result;
//    }
    
    @Inject
    private IJvmModelAssociations      associations;


}