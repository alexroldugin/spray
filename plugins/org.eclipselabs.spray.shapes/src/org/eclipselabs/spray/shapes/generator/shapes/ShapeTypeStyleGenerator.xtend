package org.eclipselabs.spray.shapes.generator.shapes

import org.eclipselabs.spray.shapes.shapes.ShapestyleLayout
import org.eclipselabs.spray.styles.styles.YesNoBool
import org.eclipselabs.spray.styles.styles.LineStyle
import com.google.inject.Inject
import org.eclipselabs.spray.styles.generator.StyleGenerator
import org.eclipse.xtext.xbase.compiler.output.FakeTreeAppendable

class ShapeTypeStyleGenerator {
	
	@Inject extension StyleGenerator 
	
	def generateStyleForElement(String attName, ShapestyleLayout ssl) {
	'''
	«IF(ssl != null && ssl.layout != null)»
		«IF(ssl.layout.background != null)»
		«attName».setBackground(gaService.manageColor(diagram,«new FakeTreeAppendable().createColorValue(ssl.layout.background).content»));
		«ENDIF»
		«IF(ssl.layout.transparency != Double::MIN_VALUE)»
		«attName».setTransparency(«ssl.layout.transparency»);		
		«ENDIF»
		«createLineAttributes(attName, ssl)»
		«createFontAttributes(attName, ssl)»
	«ENDIF»
	'''
	}
	
	def createFontAttributes(String attName, ShapestyleLayout l) {
        '''
		«IF (l.layout.fontName != null || l.layout.fontSize != Integer::MIN_VALUE || l.layout.fontItalic != YesNoBool::NULL || l.layout.fontBold != YesNoBool::NULL)»
		{
			Style style = «attName».getStyle();
			«IF l.layout.fontName == null»
			String fontName = style.getFont().getName();
			«ELSE»
			String fontName = "«l.layout.fontName»";
			«ENDIF»
			«IF l.layout.fontSize == Integer::MIN_VALUE»
			int fontSize = style.getFont().getSize();
			«ELSE»
			int fontSize = «l.layout.fontSize»;
			«ENDIF»
			«IF l.layout.fontItalic == YesNoBool::NULL»
			boolean fontItalic = style.getFont().isItalic();
			«ELSE»
			boolean fontItalic = «l.layout.fontItalic.transformYesNoToBoolean»;
			«ENDIF»
			«IF l.layout.fontBold == YesNoBool::NULL»
			boolean fontBold = style.getFont().isBold();
			«ELSE»
			boolean fontBold = «l.layout.fontBold.transformYesNoToBoolean»;
			«ENDIF»
			style.setFont(gaService.manageFont(diagram, fontName, fontSize, fontItalic, fontBold));
		}
		«ENDIF»
        '''    
    }
    
    def createLineAttributes(String attName, ShapestyleLayout ssl){
    	'''
    	«IF(ssl.layout.lineColor != null)»
			«attName».setForeground(gaService.manageColor(diagram,«new FakeTreeAppendable().createColorValue(ssl.layout.lineColor).content»));    	
    	«ENDIF»
    	«IF(ssl.layout.lineStyle != null && ssl.layout.lineStyle != LineStyle::NULL)»
  			«attName».setLineStyle(LineStyle.«ssl.layout.lineStyle.name»);	
    	«ENDIF»
    	«IF(ssl.layout.lineWidth != Integer::MIN_VALUE)»
    		«attName».setLineWidth(«ssl.layout.lineWidth»);
    	«ENDIF»    	
    	'''
    }
}