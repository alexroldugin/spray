package org.eclipselabs.spray.xtext.tests;

import org.eclipse.xtext.formatting.INodeModelFormatter;
import org.eclipse.xtext.formatting.INodeModelFormatter.IFormattedRegion;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipselabs.spray.xtext.SprayXpectRunner;
import org.eclipselabs.spray.xtext.XtextStandaloneSetupWithoutValidate;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.xpect.expectation.IStringExpectation;
import org.xpect.expectation.StringExpectation;
import org.xpect.parameter.ParameterParser;
import org.xpect.runner.Xpect;
import org.xpect.runner.XpectTestFiles;
import org.xpect.runner.XpectTestFiles.FileRoot;
import org.xpect.setup.XpectSetup;
import org.xpect.xtext.lib.setup.ThisOffset;
import org.xpect.xtext.lib.setup.ThisResource;

import com.google.inject.Inject;

@RunWith(SprayXpectRunner.class)
@XpectTestFiles(relativeTo = FileRoot.PROJECT, baseDir = "model/testcases/formatter", fileExtensions = "spray")
@XpectSetup({ XtextStandaloneSetupWithoutValidate.class })
@Ignore("Doesn't work with referenced ecore yet")
public class SprayFormatterTest {

	@Inject
	protected INodeModelFormatter formatter;

	@ParameterParser(syntax = "('from' offset=OFFSET 'to' to=OFFSET)?")
	@Xpect
	public void formatted(
			@StringExpectation(whitespaceSensitive = true) IStringExpectation expectation,
			@ThisResource XtextResource resource, @ThisOffset int offset,
			@ThisOffset int to) {
		ICompositeNode rootNode = resource.getParseResult().getRootNode();
		IFormattedRegion r = null;
		if (offset >= 0 && to > offset) {
			r = formatter.format(rootNode, offset, to - offset);
		} else {
			r = formatter.format(rootNode, rootNode.getOffset(),
					rootNode.getTotalLength());
		}
		String formatted = r.getFormattedText()
				.replaceAll("\\r\\n", "\n")
				.replaceAll("\\r\\b", "\n")
				+ getEnding();
		expectation.assertEquals(formatted);
	}

	private String getEnding() {
		String ls = System.getProperty("line.separator");
		return !"\r\n".equals(ls) ? "\r" : "";
	}
}