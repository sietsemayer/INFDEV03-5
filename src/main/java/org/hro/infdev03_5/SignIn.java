package org.hro.infdev03_5;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.hro.infdev03_5.entity.User;
import org.hro.infdev03_5.exceptions.IncorrectPasswordExeption;

public class SignIn extends WebPage {
	 
	private static final long serialVersionUID = 1L;
	
	public SignIn(final PageParameters parameters) {

		add(new FeedbackPanel("feedback"));

		final TextField<String> username = new TextField<String>("username",Model.of(""));
		username.setRequired(true);
		username.add(new UsernameValidator());
		
		final PasswordTextField password = new PasswordTextField("password",Model.of(""));
		password.setRequired(true);
		password.add(new PasswordValidator());

		Form<?> form = new Form<Void>("userForm") {

			@Override
			protected void onSubmit() {

				final String usernameValue = username.getModelObject();		
				final String passwordValye = password.getModelObject();
				PageParameters pageParameters = new PageParameters();
				System.out.println(usernameValue);
				System.out.println(passwordValye);
				pageParameters.add("username", usernameValue);
				pageParameters.add("password", passwordValye);
				
				//Get user from database
				EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("INFDEV03_5_WITHDATABASE");
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				System.out.println("EntityManager Created");
				entityManager.getTransaction().begin();
				User user = entityManager.find(User.class, usernameValue);
				System.out.println(user);
				if(Arrays.equals(user.getPassword(),passwordValye.toCharArray())){
					setResponsePage(SuccessPage.class, pageParameters); //GOTO SUCCESSPAGE!!!					
				} else {
					try {
						throw new IncorrectPasswordExeption("Wrong Password!");
					} catch (IncorrectPasswordExeption e) {
						e.printStackTrace();
						entityManager.close();
						entityManagerFactory.close();
					}
				}
				
				
			}
		};

		add(form);
		form.add(username);
		form.add(password);

	}
}