package org.eclipselabs.spray.xtext.tests;

import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipselabs.spray.xtext.SprayTestsInjectorProvider;
import org.eclipselabs.xtext.utils.unittesting.XtextRunner2;
import org.eclipselabs.xtext.utils.unittesting.XtextTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import BusinessDomainDsl.BusinessDomainDslPackage;

@RunWith(XtextRunner2.class)
@InjectWith(SprayTestsInjectorProvider.class)
public class ValidationRulesTest extends XtextTest {

	@Before
    public void before() {
        suppressSerialization();
		EPackage.Registry.INSTANCE.put(BusinessDomainDslPackage.eNS_URI, BusinessDomainDslPackage.eINSTANCE);
        EPackage.Registry.INSTANCE.put(GenModelPackage.eNS_URI, GenModelPackage.eINSTANCE);

        EcorePlugin.getEPackageNsURIToGenModelLocationMap(true).put(BusinessDomainDslPackage.eNS_URI, URI.createURI("classpath:/mod4j/BusinessDomainDsl.genmodel"));
    }

    @Test
    public void test_alias_01() {
        testFile("testcases/validation/alias-01.spray", "mod4j/BusinessDomainDsl.ecore");
        assertConstraints(issues.oneOfThemContains("Duplicate alias name line1"));
    }

    @Test
    public void test_alias_02() {
        testFile("testcases/validation/alias-02.spray", "mod4j/BusinessDomainDsl.ecore");
        assertConstraints(issues.oneOfThemContains("Duplicate alias name BC1"));
    }

    //@Ignore
    @Test
    public void test_connection_01() {
        testFile("testcases/validation/connection-01.spray", "mod4j/BusinessDomainDsl.ecore");
        assertConstraints(issues.oneOfThemContains("Duplicate connection reference: associationsTo : connection ()"));
    }

    @Test
    public void test_connection_02() {
        testFile("testcases/validation/connection-02.spray", "mod4j/BusinessDomainDsl.ecore");
        assertConstraints(issues.errorsOnly().theOneAndOnlyContains("Couldn't resolve reference to EReference 'businessRules'"));
    }

    @Test
    public void test_import_notexists() {
        testFile("testcases/validation/import_notexists.spray", "mod4j/BusinessDomainDsl.ecore");
        assertConstraints(issues.errorsOnly().sizeIs(1));
        assertConstraints(issues.theOneAndOnlyContains("The import foo.bar cannot be resolved"));
    }
}
