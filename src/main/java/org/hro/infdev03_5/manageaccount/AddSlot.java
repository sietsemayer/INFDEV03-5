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

public class AddSlot extends WebPage {

 
	private static final long serialVersionUID = 1L;
	private User user;
	private int balance, character;
	 
	 
    public AddSlot(PageParameters pageParameters)
    {    	
    	
		add(new FeedbackPanel("feedback"));
    	
    	//GET USERNAME FROM PARAMETERS
		final String usernameValue = pageParameters.get("username").toString();
		
        add(new PopupCloseLink<Object>("close"));
        System.out.println(usernameValue);
        
    	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("INFDEV03_5_WITHDATABASE");
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		System.out.println("EntityManager Created for Add Slot");
		
		entityManager.getTransaction().begin();
		user = entityManager.find(User.class, usernameValue);
		balance = user.getBalance();
		character = user.getCharacterSlots();
		final Label ChangeSlotLabel = new Label("ChangeSlotLabel", character);
		final Label CurrentAmountLabel = new Label("CurrentAmountLabel", balance);
		entityManager.close();
		entityManagerFactory.close();
        
    	Form<?> form = new Form<Void>("userForm") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {						
				EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("INFDEV03_5_WITHDATABASE");
				final EntityManager entityManager = entityManagerFactory.createEntityManager();
				entityManager.getTransaction().begin();		
				user = entityManager.find(User.class, usernameValue);
				System.out.println("Money check!");
				if(user.getBalance() <= 1){
					info("Insufficient funds! Please deposit money.");
					return;
				}
				System.out.println("Balance is oké");
//				entityManager.getTransaction().begin();
				user.setBalance(balance-- );
				user.setCharacterSlots(character++);
				entityManager.getTransaction().commit();
				entityManager.close();
				entityManagerFactory.close();
				info("Your CharacterSlots ( "+character+ " ) is now updated. \nSo is your bank balance. ( "+balance+ " )\nPlease close the window if you are done");
			}
    	};
        add(form);
        form.add(ChangeSlotLabel);
        form.add(CurrentAmountLabel);

       
        
    }
}