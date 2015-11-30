package org.hro.infdev03_5;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.PopupCloseLink;


public class Popup extends WebPage
{

	public Popup()
	{
		add(new PopupCloseLink("close"));
	}
}
