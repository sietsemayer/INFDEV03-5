package org.hro.infdev03_5;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ManageAccount extends WebSite {

	public ManageAccount(final PageParameters parameters) {

		String usernameValue = parameters.get("username").toString();
		final Label result = new Label("result", "Username : " + usernameValue);
		add(result);

	}
}
