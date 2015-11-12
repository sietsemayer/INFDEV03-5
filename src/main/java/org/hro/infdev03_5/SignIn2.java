package org.hro.infdev03_5;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.hro.infdev03_5.entity.User;

public class SignIn2 extends WebPage {

	private TextField<String> usernameField;
	private PasswordTextField passwordField;
	private Form<String> form;

	public SignIn2() {

		usernameField = new TextField<String>("userId", new Model<String>(""));
		passwordField = new PasswordTextField("password", new Model<String>(""));

		passwordField.setResetPassword(false);

		form = new LoginForm("loginForm");
		form.add(usernameField);
		form.add(passwordField);
		add(form);
	}

	// Define your LoginForm and override onSubmit
	class LoginForm extends Form {
		public LoginForm(String id) {
			super(id);
		}

		@Override
		public void onSubmit() {
			String username = SignIn2.this.getUsername();
			String password = SignIn2.this.getPassword();
			System.out.println("You entered User id " + username + " and Password " + password);
			
			EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			User user = entityManager.find(User.class, username);
						
			System.out.println(user.getFirstName());
			System.out.println(user.getLastName());
			
			
		}
	}

	/** Helper methods to retrieve the userId and the password **/

	protected String getUsername() {
		return usernameField.getDefaultModelObjectAsString();
	}

	protected String getPassword() {
		return passwordField.getDefaultModelObjectAsString();
	}
}