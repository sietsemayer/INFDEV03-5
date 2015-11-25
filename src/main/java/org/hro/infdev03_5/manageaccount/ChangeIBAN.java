package org.hro.infdev03_5.manageaccount;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.PopupCloseLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.hro.infdev03_5.entity.User;

public class ChangeIBAN extends WebPage {

 
	private static final long serialVersionUID = 1L;
	private User user;
	 
    public ChangeIBAN(PageParameters pageParameters)
    {    	
		add(new FeedbackPanel("feedback"));
    	
    	//GET USERNAME FROM PARAMETERS
		final String usernameValue = pageParameters.get("username").toString();
		
        add(new PopupCloseLink<Object>("close"));
        System.out.println(usernameValue);
        
    	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("INFDEV03_5_WITHDATABASE");
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		System.out.println("EntityManager Created for Change IBAN");
		
		entityManager.getTransaction().begin();
		user = entityManager.find(User.class, usernameValue);
		final Label ChangeIBANLabel = new Label("ChangeIBANLabel", user.getIban());
		entityManager.close();
		entityManagerFactory.close();
        
        final TextField<String> newibannumber = new TextField<String>("newibannumber", Model.of(""));
        newibannumber.setRequired(true);
        
    	Form<?> form = new Form<Void>("userForm") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
								
				final String newIbanNumber = newibannumber.getModelObject();
				EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("INFDEV03_5_WITHDATABASE");
				final EntityManager entityManager = entityManagerFactory.createEntityManager();
				entityManager.getTransaction().begin();
				user = entityManager.find(User.class, usernameValue);
				System.out.println(usernameValue + " has a new IBAN number "+ newIbanNumber);
				user.setIban(newIbanNumber);
				entityManager.getTransaction().commit();
				entityManager.close();
				entityManagerFactory.close();
				
				info("IBAN number is updated. Please close the window.");
			}
    	};
        add(form);
        form.add(ChangeIBANLabel);
        form.add(newibannumber);
        
    }
}