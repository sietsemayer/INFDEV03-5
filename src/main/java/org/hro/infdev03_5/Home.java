package org.hro.infdev03_5;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class Home extends WebSite {
	private static final long serialVersionUID = 1L;
	
	public Home(final PageParameters parameters){
		
		String usernameValue = parameters.get("username").toString();

		final Label result = new Label("result", "Username : " + usernameValue );
		//add(result);
	}


}

