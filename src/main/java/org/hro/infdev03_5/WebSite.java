package org.hro.infdev03_5;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.eclipse.persistence.descriptors.ChangedFieldsLockingPolicy;
import org.hro.infdev03_5.manageaccount.ManageAccount;

public class WebSite extends WebPage {
	private static final long serialVersionUID = 2071262588773857425L;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public WebSite() {
		add(new BookmarkablePageLink("homeLink", Home.class, getPageParameters()));
		add(new BookmarkablePageLink("manageAccount", ManageAccount.class, getPageParameters()));
		add(new BookmarkablePageLink("manageCharacters", ManageCharacters.class,getPageParameters()));

	}
}

