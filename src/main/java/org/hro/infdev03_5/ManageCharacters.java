package org.hro.infdev03_5;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.hro.infdev03_5.manageaccount.ChangePassword;

public class ManageCharacters extends WebSite {

	private static final long serialVersionUID = 1L;

	public ManageCharacters(final PageParameters parameters) {

		String usernameValue = parameters.get("username").toString();

		PopupSettings popupSettings = new PopupSettings("popuppagemap").setHeight(500).setWidth(500);
		add(new BookmarkablePageLink<>("addCharacter", AddCharacter.class, getPageParameters())
				.setPopupSettings(popupSettings));
		
		add(new BookmarkablePageLink<>("selectCharacter", Popup.class, getPageParameters())
				.setPopupSettings(popupSettings));
		
		add(new BookmarkablePageLink<>("connectToServer", SelectServer.class, getPageParameters())
				.setPopupSettings(popupSettings));
		
		add(new BookmarkablePageLink<>("characterOverVieuw", Popup.class, getPageParameters())
				.setPopupSettings(popupSettings));

	}
}
