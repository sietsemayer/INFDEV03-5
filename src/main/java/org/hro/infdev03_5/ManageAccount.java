package org.hro.infdev03_5;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.hro.infdev03_5.entity.User;

public class ManageAccount extends WebSite {

	private static final long serialVersionUID = 1L;
	private User user;

	public ManageAccount(final PageParameters parameters) {
		FeedbackPanel feedbackPanel = new FeedbackPanel("UserDoesNotExist");
		add(new FeedbackPanel("feedback"));
		String usernameValue = parameters.get("username").toString();

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("INFDEV03_5_WITHDATABASE");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		System.out.println("EntityManager Created for Manage Account");
		entityManager.getTransaction().begin();
		user = entityManager.find(User.class, usernameValue);
		//entityManager.clear();
		//entityManagerFactory.close();

		final Label usernameLabel = new Label("usernameLabel", user.getUserName());
		final Label userBalanceLabel = new Label("userBalanceLabel", user.getBalance());
		final Label userFirstNameLabel = new Label("userFirstNameLabel", user.getFirstName());
		final Label userLastnameLabel = new Label("userLastnameLabel", user.getLastName());
		final Label userIBANLabel = new Label("userIBANLabel", user.getIban());
		final Label userCharactetSlotsLabel = new Label("userCharactetSlotsLabel", user.getCharacterSlots());
		final Label userLastPaymentLabel = new Label("userLastPaymentLabel", user.getLastPayment());
		final Label userMonthsPayesLabel = new Label("userMonthsPayesLabel", user.getMonthsPayed());
		final Label userBannedLabel = new Label("userBannedLabel", user.getBanned());

		final TextField<String> ManAccountusername = new TextField<String>("ManAccountusername", Model.of(""));
		// Add a form with an onSubmit implementation that sets a message
		Form form = new Form("form") {
			protected void onSubmit() {
				info("Form.onSubmit executed");
				System.out.println("1");
			}
		};
		form.add(usernameLabel, userBalanceLabel, userFirstNameLabel, userLastnameLabel, userIBANLabel,
				userCharactetSlotsLabel, userLastPaymentLabel, userMonthsPayesLabel, userBannedLabel);

		// CHANGE USERNAME
		Button ChangeUserName = new Button("ChangeUserName") {
			public void onSubmit() {
				System.out.println("Change username");
			}
		};
		form.add(ChangeUserName);
		ChangeUserName.setDefaultFormProcessing(false);

		// CHANGE IBAN
		Button ChangeIBAN = new Button("ChangeIBAN") {
			public void onSubmit() {
				System.out.println("ChangeIBAN");
			}
		};
		form.add(ChangeIBAN);
		ChangeIBAN.setDefaultFormProcessing(false);

		// CHANGE ADDSLOT
		Button AddSlot = new Button("AddSlot") {
			public void onSubmit() {
				System.out.println("AddSlot");
			}
		};
		form.add(AddSlot);
		AddSlot.setDefaultFormProcessing(false);

		// CHANGE AddMoney
		Button AddMoney = new Button("AddMoney") {
			public void onSubmit() {
				System.out.println("AddMoney");
			}
		};
		form.add(AddMoney);
		AddMoney.setDefaultFormProcessing(false);

		// CHANGE AddPlayTime
		Button AddPlayTime = new Button("AddPlayTime") {
			public void onSubmit() {
				System.out.println("AddPlayTime");

			}
		};
		form.add(AddPlayTime);
		AddPlayTime.setDefaultFormProcessing(false);

		add(form);
		entityManager.clear();
		entityManagerFactory.close();

	}

	public void ChangeUsername(String usernameValueManageAccount) {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("INFDEV03_5_WITHDATABASE");
		EntityManager entitymanager = entityManagerFactory.createEntityManager();
		User user = entitymanager.find(User.class, usernameValueManageAccount);
		if (user.getUserName() == null) {
			entitymanager.getTransaction().begin();
			entitymanager.find(User.class, user.getUserName());
			System.out.println(user.getUserName() + "is being update to " + usernameValueManageAccount);
			user.setUserName(usernameValueManageAccount);
			entitymanager.getTransaction().commit();
			entitymanager.close();
			entityManagerFactory.close();
		} else {
			info(usernameValueManageAccount + " is already in use");
		}

	}

}
