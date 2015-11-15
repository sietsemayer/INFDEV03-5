package org.hro.infdev03_5;

import java.util.Arrays;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.hro.infdev03_5.entity.User;

public class SignIn extends WebPage {

	private static final long serialVersionUID = 1L;

	public SignIn(final PageParameters parameters) {
		FeedbackPanel feedbackPanel = new FeedbackPanel("UserDoesNotExist");
		add(new FeedbackPanel("feedback"));

		final TextField<String> username = new TextField<String>("username", Model.of(""));
		username.setRequired(true);
		username.add(new UsernameValidator());

		final PasswordTextField password = new PasswordTextField("password", Model.of(""));
		password.setRequired(true);
		password.add(new PasswordValidator());

		Form<?> form = new Form<Void>("userForm") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				User user = null;
				final String usernameValue = username.getModelObject();
				final String passwordValye = password.getModelObject();
				PageParameters pageParameters = new PageParameters();
				System.out.println(usernameValue);
				System.out.println(passwordValye);
				pageParameters.add("username", usernameValue);
				pageParameters.add("password", passwordValye);

				// Get user from database
				EntityManagerFactory entityManagerFactory = Persistence
						.createEntityManagerFactory("INFDEV03_5_WITHDATABASE");
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				System.out.println("EntityManager Created");
				entityManager.getTransaction().begin();
				user = entityManager.find(User.class, usernameValue);
				if (user != null) {
					System.out.println(user);
					if (Arrays.equals(user.getPassword(), passwordValye.toCharArray())) {						
						setResponsePage(Home.class, pageParameters); // GOTO SUCCESSPAGE!!!
					} else {
						info(usernameValue + " is correct but with another password");
						return;
					}
				} else {
					info(usernameValue +" does not exist in the database");
				}

			}
		};

		add(form);
		add(feedbackPanel);
		form.add(username);
		form.add(password);

	}

	
}