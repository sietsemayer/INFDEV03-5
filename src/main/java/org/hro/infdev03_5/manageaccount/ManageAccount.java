package org.hro.infdev03_5.manageaccount;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.hro.infdev03_5.WebSite;
import org.hro.infdev03_5.entity.User;

@SuppressWarnings("unused")
public class ManageAccount extends WebSite {

	private static final long serialVersionUID = 1L;
	private User user;

	public ManageAccount(final PageParameters parameters) {

		// add(new FeedbackPanel("feedback"));
		String usernameValue = parameters.get("username").toString();
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("INFDEV03_5_WITHDATABASE");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		System.out.println("EntityManager Created for Manage Account");
		entityManager.getTransaction().begin();
		user = entityManager.find(User.class, usernameValue);
		// entityManager.clear();
		// entityManagerFactory.close();

		final Label usernameLabel = new Label("usernameLabel", user.getUserName());
		final Label userBalanceLabel = new Label("userBalanceLabel", user.getBalance());
		final Label userFirstNameLabel = new Label("userFirstNameLabel", user.getFirstName());
		final Label userLastnameLabel = new Label("userLastnameLabel", user.getLastName());
		final Label userIBANLabel = new Label("userIBANLabel", user.getIban());
		final Label userCharactetSlotsLabel = new Label("userCharactetSlotsLabel", user.getCharacterSlots());
		final Label userLastPaymentLabel = new Label("userLastPaymentLabel", user.getLastPayment());
		final Label userMonthsPayesLabel = new Label("userMonthsPayesLabel", user.getMonthsPayed());
		final Label userBannedLabel = new Label("userBannedLabel", user.getBanned());

		add(usernameLabel, userBalanceLabel, userFirstNameLabel, userLastnameLabel, userIBANLabel,
				userCharactetSlotsLabel, userLastPaymentLabel, userMonthsPayesLabel, userBannedLabel);

		PopupSettings popupSettings = new PopupSettings("popuppagemap").setHeight(500).setWidth(500);
		add(new BookmarkablePageLink<>("ChangePassword", ChangePassword.class, getPageParameters())
				.setPopupSettings(popupSettings));
		add(new BookmarkablePageLink<>("ChangeIBAN", ChangeIBAN.class, getPageParameters())
				.setPopupSettings(popupSettings));
		add(new BookmarkablePageLink<>("AddSlot", AddSlot.class, getPageParameters()).setPopupSettings(popupSettings));
		add(new BookmarkablePageLink<>("AddBalance", AddBalance.class, getPageParameters())
				.setPopupSettings(popupSettings));
		add(new BookmarkablePageLink<>("AddPlaytime", AddPlaytime.class, getPageParameters())
				.setPopupSettings(popupSettings));

		entityManager.clear();
		entityManagerFactory.close();

	}

}
