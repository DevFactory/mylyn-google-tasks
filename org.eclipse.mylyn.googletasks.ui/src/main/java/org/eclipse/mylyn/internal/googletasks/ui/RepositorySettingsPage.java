/*
   Copyright 2011 Christian Trutz

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.eclipse.mylyn.internal.googletasks.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.wizards.AbstractRepositorySettingsPage;
import org.eclipse.swt.widgets.Composite;

public class RepositorySettingsPage extends AbstractRepositorySettingsPage {

	protected static final String URL = "https://www.googleapis.com/auth/tasks"; //$NON-NLS-1$

	public RepositorySettingsPage(TaskRepository taskRepository) {
		super(Messages.RepositorySettingsPage_Title,
				Messages.RepositorySettingsPage_Description, taskRepository);
	}

	@Override
	public String getConnectorKind() {
		return RepositoryConnector.CONNECTOR_KIND;
	}

	@Override
	protected void createAdditionalControls(Composite parent) {
		serverUrlCombo.setText(URL);
		serverUrlCombo.setEnabled(false);
		repositoryLabelEditor
				.setStringValue(Messages.RepositorySettingsPage_RepositoryLabelDefault);
	}

	@Override
	protected boolean isValidUrl(String url) {
		return URL.equals(url);
	}

	@Override
	protected Validator getValidator(TaskRepository repository) {
		return new Validator() {

			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				setStatus(Status.OK_STATUS);
			}
		};
	}

}
