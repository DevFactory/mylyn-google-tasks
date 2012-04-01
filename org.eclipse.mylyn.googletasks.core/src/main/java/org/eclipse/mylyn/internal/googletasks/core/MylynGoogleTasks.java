package org.eclipse.mylyn.internal.googletasks.core;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.tasks.Tasks;

/**
 * 
 * @author Christian Trutz
 * 
 */
public class MylynGoogleTasks {

	/**
	 * 
	 */
	public static final String OOB_REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

	/**
	 * 
	 */
	public static final String GOOGLE_TASKS_API_SCOPE = "https://www.googleapis.com/auth/tasks";

	/**
	 * 
	 */
	public static Tasks TASKS = null;

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Tasks getTasks() throws IOException {

		if (TASKS == null) {
			HttpTransport transport = new NetHttpTransport();
			JsonFactory jsonFactory = new JacksonFactory();

			// load client secrets
			GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
					jsonFactory, MylynGoogleTasks.class
							.getResourceAsStream("client_secrets.json"));

			// Google Tasks API scope
			Set<String> scopes = new HashSet<String>();
			scopes.add(GOOGLE_TASKS_API_SCOPE);

			// authorization flow
			GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
					transport, jsonFactory, clientSecrets, scopes).build();
			String authorizationUrl = flow.newAuthorizationUrl()
					.setRedirectUri(OOB_REDIRECT_URI).build();

			String code = "";

			// open browser
			final Browser browser = new Browser(
					new Shell(Display.getCurrent()), SWT.NONE);
			browser.setUrl(authorizationUrl);
			browser.addProgressListener(new ProgressListener() {

				public void changed(ProgressEvent event) {
				}

				public void completed(ProgressEvent event) {
					Object codeFromBrowser = browser
							.evaluate("return document.getElementById('code').childNodes[0].nodeValue;");
					System.out.println(codeFromBrowser);
				}
			});

			// token request
			GoogleTokenResponse response = flow.newTokenRequest(code)
					.setRedirectUri(OOB_REDIRECT_URI).execute();
			Credential credential = flow.createAndStoreCredential(response,
					null);
			TASKS = Tasks.builder(transport, new JacksonFactory())
					.setApplicationName("Mylyn-Google-Tasks/1.0")
					.setHttpRequestInitializer(credential).build();
		}
		return TASKS;

	}

}
