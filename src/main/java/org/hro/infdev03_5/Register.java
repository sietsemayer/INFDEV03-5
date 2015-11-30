package org.hro.infdev03_5;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.logging.log4j.core.appender.SyslogAppender;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.PopupCloseLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.Strings;
import org.hro.infdev03_5.entity.User;
import org.hro.infdev03_5.util.PasswordValidator;
import org.hro.infdev03_5.util.UsernameValidator;

public class Register extends WebPage {

	private static final long serialVersionUID = 1L;
	private User user;

	public Register(final PageParameters parameters) {
		add(new FeedbackPanel("feedback"));

		final TextField<String> firstname = new TextField<String>("firstname", Model.of(""));
		firstname.setRequired(true);

		final TextField<String> lastname = new TextField<String>("lastname", Model.of(""));
		lastname.setRequired(true);

		final TextField<String> username = new TextField<String>("username", Model.of(""));
		username.setRequired(true);
		username.add(new UsernameValidator());

		final PasswordTextField password = new PasswordTextField("password", Model.of(""));
		password.setRequired(true);
		password.add(new PasswordValidator());

		final TextField<String> iban = new TextField<String>("iban", Model.of(""));
		iban.setRequired(true);

		add(new PopupCloseLink<Object>("close"));

		Form<?> form = new Form<Void>("userForm") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				String passwordString = password.getModelObject();
				char[] pwd = passwordString.toCharArray();
				String queryParameter =  username.getModelObject();
				
				EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("INFDEV03_5_WITHDATABASE");
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				Query queryFindUsername = entityManager.createNamedQuery("User.findByUserName");
				queryFindUsername.setParameter("userName", queryParameter);
				@SuppressWarnings("unchecked")
				List<User> ResultsetUserList = queryFindUsername.getResultList();
				if(ResultsetUserList.isEmpty()){
					System.out.println("USER DOES NOT EXISTS!! USER IS BEING CREATED!!");
					entityManager.getTransaction().begin();
					User user = new User();
					user.setUserName(username.getModelObject());
					user.setFirstName(firstname.getModelObject());
					user.setLastName(lastname.getModelObject());
					user.setPassword(pwd);
					user.setIban(iban.getModelObject());
					user.setBanned(false);
					user.setMonthsPayed(0);
					user.setCharacterSlots(5);
					user.setBalance(10);
					entityManager.persist(user);
					entityManager.getTransaction().commit();
					entityManager.close();
					entityManagerFactory.close();
					info("User " + username.getModelObject() + " was created successfully ");
				} else {
					System.out.println("USER EXISTS!! END OF STORY!!");
					info("This name is already taken ");
					entityManager.close();
					entityManagerFactory.close();
				}

			}

		};
		add(form);
		form.add(firstname);
		form.add(lastname);
		form.add(username);
		form.add(password);
		form.add(iban);

	}

}