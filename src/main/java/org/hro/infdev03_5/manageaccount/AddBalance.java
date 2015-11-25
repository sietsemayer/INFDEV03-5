package org.hro.infdev03_5.manageaccount;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.link.PopupCloseLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.hro.infdev03_5.entity.User;

public class AddBalance extends WebPage {

	// single list choice
	private static final List<Integer> Amount = Arrays.asList(new Integer[] {
			1,3,5,10,25,50,100,250 });

	// Banana is selected by default
	private Integer selectedAmount = 1;
	private User user;
	private String IBAN;
	public AddBalance(final PageParameters pageParameters) {

		final String usernameValue = pageParameters.get("username").toString();
        add(new PopupCloseLink<Object>("close"));
        System.out.println(usernameValue);
		
		add(new FeedbackPanel("feedback"));

		ListChoice<Integer> listAmounts = new ListChoice<Integer>("amount",
				new PropertyModel<Integer>(this, "selectedAmount"), Amount);

		listAmounts.setMaxRows(5);

		Form<?> form = new Form<Void>("form") {
			@Override
			protected void onSubmit() {

				//info("Selected Amount : " + selectedAmount);
				
				int selectAmount = selectedAmount;
				System.out.println("Chosen amount  "+selectAmount);
				
				EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("INFDEV03_5_WITHDATABASE");
				final EntityManager entityManager = entityManagerFactory.createEntityManager();
				entityManager.getTransaction().begin();		
				user = entityManager.find(User.class, usernameValue);				
				user.setBalance(user.getBalance()+selectAmount);
				IBAN = user.getIban();
				entityManager.getTransaction().commit();
				entityManager.close();
				entityManagerFactory.close();
				
				info("€ "+selectedAmount + " has been added to your gamebalance form account "+ IBAN +"! ");


			}
		};

		add(form);
		form.add(listAmounts);

	}
}