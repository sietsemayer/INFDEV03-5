package org.hro.infdev03_5;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.wicket.Session;
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
		
		Servers servers1 = new Servers();
		servers1.setAdress(192_168_0_201);
		servers1.setName("Rotterdam Server");
		servers1.setLocation("Rotterdam");
		servers1.setMaxUsers(5);
		servers1.setConnectedUsers(0);
		entityManager.persist(servers1);
		
		Servers servers2 = new Servers();
		servers2.setAdress(192_168_0_202);
		servers2.setName("Amsterdam Server");
		servers2.setLocation("Amsterdam");
		servers2.setMaxUsers(5);
		servers2.setConnectedUsers(0);
		entityManager.persist(servers2);
		
		Servers servers3 = new Servers();
		servers3.setAdress(192_168_0_203);
		servers3.setName("Groningen Server");
		servers3.setLocation("Groningen");
		servers3.setMaxUsers(5);
		servers3.setConnectedUsers(0);
		entityManager.persist(servers3);
		
		Servers servers4 = new Servers();
		servers4.setAdress(192_168_0_204);
		servers4.setName("Den Haag Server");
		servers4.setLocation("Den Haag");
		servers4.setMaxUsers(5);
		servers4.setConnectedUsers(0);
		entityManager.persist(servers4);
		
		Servers servers5 = new Servers();
		servers5.setAdress(192_168_0_205);
		servers5.setName("Maastricht Server");
		servers5.setLocation("Maastricht");
		servers5.setMaxUsers(5);
		servers5.setConnectedUsers(0);
		entityManager.persist(servers5);
				

		
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();

	}

}
