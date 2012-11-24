package org.eclipselabs.spray.styles.generator

import org.eclipselabs.spray.styles.Gradient
import org.eclipselabs.spray.styles.GradientLayout
import org.eclipselabs.spray.styles.GradientColorArea
import org.eclipselabs.spray.styles.ColorConstantRef
import org.eclipselabs.spray.styles.RGBColor
import org.eclipselabs.spray.styles.generator.util.GradientUtilClass

class GradientGenerator {
	def filepath(Gradient g) { g.packagePath + g.className + ".java" }
	def className(Gradient g) { g.name.toFirstUpper }
	def packageName(Gradient g) { "org.eclipselabs.spray.styles.gradients" }
	def packagePath(Gradient g) { "org/eclipselabs/spray/styles/gradients/" }
	
	int elementIndex = 0
	
	def compile(Gradient g) {
		'''
		«g.head»
		
		«g.body»
		'''
	}
	
	def head(Gradient g) {
		'''
		/**
		 * This is a generated Gradient class for Spray.
		 */
		package «g.packageName»;
		
		import org.eclipse.emf.common.util.EList;
		import org.eclipse.graphiti.mm.algorithms.styles.GradientColoredArea;
		import org.eclipse.graphiti.mm.algorithms.styles.GradientColoredAreas;
		import org.eclipse.graphiti.mm.algorithms.styles.LocationType;
		import org.eclipse.graphiti.mm.algorithms.styles.StylesFactory;
		import org.eclipse.graphiti.util.IGradientType;
		import org.eclipselabs.spray.runtime.graphiti.styles.ISprayGradient;
		import org.eclipse.graphiti.util.IPredefinedRenderingStyle;
		import org.eclipse.graphiti.util.PredefinedColoredAreas;
		'''
	}
	
	def body(Gradient g) {
		'''
		/**
		 * Description: «g.description»
		 */
		@SuppressWarnings("all")
		public class «g.className» extends PredefinedColoredAreas implements ISprayGradient {
		    
		    /**
		     * This method returns the gradient color area.
		     * Description: «g.description»
		     */
		     public GradientColoredAreas getGradientColoredAreas( ) {
		         final GradientColoredAreas gradientColoredAreas = org.eclipse.graphiti.mm.algorithms.styles.StylesFactory.eINSTANCE.createGradientColoredAreas();
		         final EList<org.eclipse.graphiti.mm.algorithms.styles.GradientColoredArea> gcas = gradientColoredAreas.getGradientColor();
		         «g.layout.createColorAreas»
		         return gradientColoredAreas;
		     }
		
		}
		'''
	}

	def createColorAreas(GradientLayout l) {
		l.area.sortBy(entity | entity.offset)
		elementIndex = -1
		'''
		«FOR element : l.area»
			«IF(increaseCounter < l.area.size - 1)»
			«createArea(element,l.area.get(elementIndex+1))»
			«ELSEIF(l.area.size == 1)»
			
			«ENDIF»
      	«ENDFOR»
      	'''	
	}
	
	def createArea(GradientColorArea first, GradientColorArea second){
		var offset_1 = (first.offset*100).intValue
		var offset_2 = (second.offset*100).intValue  
		'''addGradientColoredArea(gcas,"«first.color.createColorValue»",«offset_1»,org.eclipse.graphiti.mm.algorithms.styles.LocationType.LOCATION_TYPE_RELATIVE, "«second.color.createColorValue»",«offset_2»,org.eclipse.graphiti.mm.algorithms.styles.LocationType.LOCATION_TYPE_RELATIVE);'''
	}
	
	def dispatch createColorValue(ColorConstantRef c) {'''«GradientUtilClass::colorConstantToHexString(c)»''' }
	def dispatch createColorValue(RGBColor c) { '''«GradientUtilClass::RGBColorToHexString(c)»''' }
	def increaseCounter(){ elementIndex = elementIndex + 1 }
}