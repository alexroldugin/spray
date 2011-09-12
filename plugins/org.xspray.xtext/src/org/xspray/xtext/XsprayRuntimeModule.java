/*
 * generated by Xtext
 */
package org.xspray.xtext;

import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.xspray.xtext.customizing.XsprayQualifiedNameProvider;
import org.xspray.xtext.generator.CodeFormatterProvider;
import org.xspray.xtext.generator.IPostProcessor;
import org.xspray.xtext.generator.JavaIoFileSystemAccessExt;
import org.xspray.xtext.generator.JavaPostProcessor;

import com.google.inject.Binder;
import com.google.inject.name.Names;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class XsprayRuntimeModule extends org.xspray.xtext.AbstractXsprayRuntimeModule {
    @Override
    public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
        return XsprayQualifiedNameProvider.class;
    }
    
    public Class<? extends JavaIoFileSystemAccess> bindJavaIoFileSystemAccess () {
    	return JavaIoFileSystemAccessExt.class;
    }
    
    public void configureCodeFormatterProvider (Binder binder) {
    	binder.bind(CodeFormatter.class).toProvider(CodeFormatterProvider.class);
    }
    
    public void configureJavaPostProcessor (Binder binder) {
    	binder.bind(IPostProcessor.class).annotatedWith(Names.named("java")).to(JavaPostProcessor.class);
    }
}
