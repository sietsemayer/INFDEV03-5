package org.hro.infdev03_5;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.hro.infdev03_5.entity.Servers;
import org.hro.infdev03_5.entity.User;

public class WicketApplication extends WebApplication {
	
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;	
	
	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public Class<? extends WebPage> getHomePage() {
		return SignIn.class;
	}

	@Override
	public void init() {
		super.init();
		
		entityManagerFactory = Persistence.createEntityManagerFactory("INFDEV03_5");
		entityManager = entityManagerFactory.createEntityManager();							 
		entityManager.getTransaction().begin();

		User user = new User();
		user.setUserName("test");
		user.setBalance(100);
		user.setFirstName("sietse");
		user.setLastName("mayer");
		user.setIban("NL91ABNA0417164300");
		user.setCharacterSlots(2);
		user.setMonthsPayed(12);
		user.setPassword("Welkom01".toCharArray());
		user.setBanned(false);		
		entityManager.persist(user);
		
		User user2 = new User();
		user2.setUserName("Sietse");
		user2.setBalance(100);
		user2.setFirstName("Sietse");
		user2.setLastName("Mayer");
		user2.setIban("NL91ABNA0417164301");
		user2.setCharacterSlots(2);
		user2.setMonthsPayed(12);
		user2.setPassword("Welkom01".toCharArray());
		user2.setBanned(false);		
		entityManager.persist(user2);
		
		Servers servers = new Servers();
		servers.setAdress(192_168_0_201);
		servers.setName("RotterdamServer-1");
		servers.setLocation("Rotterdam");
		servers.setMaxUsers(5);
		servers.setConnectedUsers(0);
		entityManager.persist(servers);

		/**
		 * 
		 * TO DO Create database content
		 * 
		 */
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();

	}

}
