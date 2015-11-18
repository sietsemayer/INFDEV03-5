package org.hro.infdev03_5;

import org.apache.wicket.markup.html.link.PopupCloseLink;

public class Popup extends WebSite
{
 
	private static final long serialVersionUID = 1L;

	 
    public Popup()
    {
        add(new PopupCloseLink<Object>("close"));
    }
}