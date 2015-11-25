package org.hro.infdev03_5;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.PopupCloseLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

public class Popup extends WebPage {

 
	private static final long serialVersionUID = 1L;
	
	 
    public Popup(PageParameters pageParameters)
    {
    	StringValue usernameParameters = pageParameters.get("username");
        add(new PopupCloseLink<Object>("close"));
        System.out.println(usernameParameters);
    }
}