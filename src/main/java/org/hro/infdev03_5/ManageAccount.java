package org.hro.infdev03_5;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.hro.infdev03_5.entity.User;

public class ManageAccount extends WebSite {

	private static final long serialVersionUID = 1L;
	private User user;

	public ManageAccount(final PageParameters parameters) {

		String usernameValue = parameters.get("username").toString();

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("INFDEV03_5_WITHDATABASE");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		System.out.println("EntityManager Created for Manage Account");
		entityManager.getTransaction().begin();
		user = entityManager.find(User.class, usernameValue);

		final Label usernameLabel = new Label("usernameLabel", user.getUserName());
		final Label userBalanceLabel = new Label("userBalanceLabel", user.getBalance());
		final Label userFirstNameLabel = new Label("userFirstNameLabel", user.getFirstName());
		final Label userLastnameLabel = new Label("userLastnameLabel", user.getLastName());
		final Label userIBANLabel = new Label("userIBANLabel", user.getIban());
		final Label userCharactetSlotsLabel = new Label("userCharactetSlotsLabel", user.getCharacterSlots());
		final Label userLastPaymentLabel = new Label("userLastPaymentLabel", user.getLastPayment());
		final Label userMonthsPayesLabel = new Label("userMonthsPayesLabel", user.getMonthsPayed());
		final Label userBannedLabel = new Label("userBannedLabel", user.getBanned());

		add(usernameLabel, userBalanceLabel, userFirstNameLabel, userLastnameLabel, userIBANLabel,
				userCharactetSlotsLabel, userLastPaymentLabel, userMonthsPayesLabel, 
				userBannedLabel);
		
        add(new Button("usernameButton"){
            public void onSubmit() {
            	System.out.println("usernameButton");
                info("OK was usernameButton!");
            }
        });
        add(new Button("userfirstnameButton"){
            public void onSubmit() {
            	System.out.println("userfirstnameButton");
                info("OK was userfirstnameButton!");
            }
        });
        
        add(new Button("userlastnameButton"){
            public void onSubmit() {
            	System.out.println("userlastnameButton");
                info("OK was userlastnameButton!");
            }
        });
        
        add(new Button("passwordButton"){
            public void onSubmit() {
            	System.out.println("passwordButton");
                info("OK was passwordButton!");
            }
        });

	}
	protected void onSubmit() {
        info("OK was pressed!");
    }
}
