package org.hro.infdev03_5.manageaccount;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.link.PopupCloseLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.hro.infdev03_5.entity.User;
import org.hro.infdev03_5.util.PasswordValidator;

public class ChangePassword extends WebPage {

	private static final long serialVersionUID = 1L;

	public ChangePassword(PageParameters pageParameters) {

		final String usernameValue = pageParameters.get("username").toString();

		add(new FeedbackPanel("feedback"));

		final PasswordTextField oldPassword = new PasswordTextField("oldPassword", Model.of(""));
		oldPassword.setRequired(true);

		final PasswordTextField newPassword = new PasswordTextField("newPassword", Model.of(""));
		newPassword.setRequired(true);
		newPassword.add(new PasswordValidator());

		final PasswordTextField newPasswordAgain = new PasswordTextField("newPasswordAgain", Model.of(""));
		newPasswordAgain.setRequired(true);
		newPasswordAgain.add(new PasswordValidator());

		Form<?> form = new Form<Void>("userForm") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				User user = null;
				String oldPasswordValue = oldPassword.getModelObject().toString();
				String newPasswordValue = newPassword.getModelObject().toString();
				String newPasswordAgainValue = newPasswordAgain.getModelObject().toString();
				PageParameters pageParameters = new PageParameters();
				System.out.println(oldPasswordValue);
				System.out.println(newPasswordValue);
				System.out.println(newPasswordAgainValue);

				// compare passwords
				if (newPassword.getModelObject().equals(newPasswordAgain.getModelObject())) {
					 System.out.println("The new passwords are equal!");

					if (newPassword.getModelObject().equals(oldPassword.getModelObject())) {
						System.out.println("The old and the new passwords are equal!");
						info("The oldpassword and the new passwords are the same. This is not allowed");
					} else {

						StringValue usernameParameters = pageParameters.get("username");
						System.out.println(usernameValue);

						// Get user from database
						EntityManagerFactory entityManagerFactory = Persistence
								.createEntityManagerFactory("INFDEV03_5_WITHDATABASE");
						EntityManager entityManager = entityManagerFactory.createEntityManager();
						System.out.println("EntityManager Created for class ChangePassword");
						entityManager.getTransaction().begin();
						user = entityManager.find(User.class, usernameValue);
						System.out.println(user.getUserName());
						if (Arrays.equals(user.getPassword(), oldPassword.getModelObject().toCharArray())) {
							System.out.println("Match password for user " + usernameValue);
							user.setPassword(newPasswordValue.toCharArray());
							entityManager.getTransaction().commit();
							entityManager.close();
							entityManagerFactory.close();
							info("Password is changed! Please close the window.");
							return;
						} else {
							info("The old password is not correct!");
							return;
						}
					}

				} else {
					info("Passwords are not the same!");
				}

			}
		};

		add(form);
	
		add(new PopupCloseLink<Object>("close"));
		form.add(oldPassword);
		form.add(newPassword);
		form.add(newPasswordAgain);

	}

}