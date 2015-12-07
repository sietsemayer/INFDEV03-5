package org.hro.infdev03_5;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class PerformanceTest extends WebSite {

	private static final long serialVersionUID = 1L;

	public PerformanceTest(final PageParameters parameters) {

		String usernameValue = parameters.get("username").toString();
		add(new FeedbackPanel("feedback"));
		//
		// PopupSettings popupSettings = new
		// PopupSettings("popuppagemap").setHeight(500).setWidth(500);
		// add(new BookmarkablePageLink<>("addCharacter", AddCharacter.class,
		// getPageParameters())
		// .setPopupSettings(popupSettings));
		//
		// add(new BookmarkablePageLink<>("selectCharacter", Popup.class,
		// getPageParameters())
		// .setPopupSettings(popupSettings));
		//
		// add(new BookmarkablePageLink<>("connectToServer", SelectServer.class,
		// getPageParameters())
		// .setPopupSettings(popupSettings));
		//
		// add(new BookmarkablePageLink<>("characterOverVieuw", Popup.class,
		// getPageParameters())
		// .setPopupSettings(popupSettings));

		Form<?> form = new Form<Void>("form") {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onSubmit() {
				System.out.println("Submit");
				EntityManagerFactory entityManagerFactory = Persistence
						.createEntityManagerFactory("INFDEV03_5_WITHDATABASE");
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				entityManager.getTransaction().begin();
				info("submit");
				entityManager.close();
				entityManagerFactory.close();
			}
		};
		add(form);

	}

}
