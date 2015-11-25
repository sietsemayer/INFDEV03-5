package org.hro.infdev03_5.manageaccount;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.link.PopupCloseLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.hro.infdev03_5.entity.User;

public class AddPlaytime extends WebPage {

	private static final long serialVersionUID = 1L;

	// single list choice
	private static final List<String> PLAYTIME = Arrays
			.asList(new String[] { "One month (5€)", "Two months (8€)", "Three months (10€)", "Whole year (35€)" });

	// One month is selected by default
	private String selected = "One month (5€)";
	private User user;
	private int months = 0;
	private int amount = 0;

	public AddPlaytime(final PageParameters pageParameters) {

		final String usernameValue = pageParameters.get("username").toString();
		add(new PopupCloseLink<Object>("close"));
		System.out.println(usernameValue);

		add(new FeedbackPanel("feedback"));

		DropDownChoice<String> playtimeDropdownlist = new DropDownChoice<String>("playtimeDropdownlist",
				new PropertyModel<String>(this, "selected"), PLAYTIME);

		Form<?> form = new Form<Void>("form") {
			@Override
			protected void onSubmit() {

				switch (selected) {
				case "One month (5€)":
					months = 1;
					amount = 5;
					break;
				case "Two months (8€)":
					months = 2;
					amount = 8;
					break;
				case "Three months (10€)":
					months = 3;
					amount = 10;
					break;
				case "Whole year (35€)":
					months = 12;
					amount = 35;
					break;
				default:
					months = 0;
					amount = 0;
					break;

				}
				EntityManagerFactory entityManagerFactory = Persistence
						.createEntityManagerFactory("INFDEV03_5_WITHDATABASE");
				final EntityManager entityManager = entityManagerFactory.createEntityManager();
				entityManager.getTransaction().begin();
				user = entityManager.find(User.class, usernameValue);
				System.out.println("Money check!");
				if (user.getBalance() < amount) {
					info("Insufficient funds! Please deposit money.");
					return;
				}
				System.out.println("Balance is oké");
				user.setBalance(user.getBalance() - amount);
				user.setMonthsPayed(user.getMonthsPayed() + months);
				entityManager.getTransaction().commit();
				entityManager.close();
				entityManagerFactory.close();
				if (months > 1) {
					info("Congratulations! you have just purchased " + months + " months playtime !");
				} else {
					info("Congratulations! you have just purchased " + months + " month playtime !");
				}

			}
		};
		add(form);
		form.add(playtimeDropdownlist);
	
	}
}
