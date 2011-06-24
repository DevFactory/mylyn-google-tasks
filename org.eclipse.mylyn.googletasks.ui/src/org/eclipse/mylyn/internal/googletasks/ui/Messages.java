package org.eclipse.mylyn.internal.googletasks.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.mylyn.internal.googletasks.ui.messages"; //$NON-NLS-1$
	public static String RepositoryConnector_ConnectorLabel;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
