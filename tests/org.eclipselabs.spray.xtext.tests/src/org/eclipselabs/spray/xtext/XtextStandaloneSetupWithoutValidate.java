package org.eclipselabs.spray.xtext;

import org.xpect.setup.ISetupInitializer;

public class XtextStandaloneSetupWithoutValidate extends EcoreAwareXtextStandaloneSetup {

	@Override
	public Config beforeFile(IFileSetupContext frameworkCtx, ClassCtx userCtx, ISetupInitializer<Config> initializer) throws Exception {
		Config ctx = new Config();
		new Defaults(initializer).initialize(ctx);
		loadThisResource(frameworkCtx.getInjector(), frameworkCtx, ctx);
		return ctx;
	}
}
