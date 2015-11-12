package org.hro.infdev03_5;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;

public class SuccessPage extends WebPage {

	public SuccessPage(final PageParameters parameters) {

		String username = parameters.get("username").toString();
		String password = parameters.get("password").toString();

		final Label result = new Label("result", "Username : " + username + "\nPassword : " + password);
		add(result);

	}
}