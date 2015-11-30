package org.hro.infdev03_5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.PopupCloseLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import org.hro.infdev03_5.entity.Servers;
import org.hro.infdev03_5.entity.User;

public class SelectServer extends WebPage {

	private static final long serialVersionUID = 1L;

	private List<String> serverNames = new ArrayList<String>(); 
	private List<Servers> serverList;
	private String selected;
	private Servers servers;
	public SelectServer(final PageParameters parameters) {
		
		add(new PopupCloseLink("close"));
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("INFDEV03_5_WITHDATABASE");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		Query query = entityManager.createNamedQuery("Servers.findAllSorted");
		serverList = (List<Servers>)query.getResultList();
		for(Servers s : serverList){			
			System.out.println(s.getName());
			serverNames.add(s.getName());
		}

		add(new FeedbackPanel("feedback"));

		DropDownChoice<String> listSites = new DropDownChoice<String>(
			"listSites", new PropertyModel<String>(this, "selected"), serverNames);

		Form<?> form = new Form<Void>("form") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				
				EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("INFDEV03_5_WITHDATABASE");
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				entityManager.getTransaction().begin();
				servers = entityManager.find(Servers.class, selected);
				System.out.println("MaxUsers "+servers.getMaxUsers()+"\nConnected users "+servers.getConnectedUsers() );
				if(servers.getMaxUsers() > servers.getConnectedUsers()){
					servers.setConnectedUsers(servers.getConnectedUsers());
					servers.setConnectedUsers(servers.getConnectedUsers()+1);					
					entityManager.persist(servers);
					entityManager.getTransaction().commit();
					entityManager.close();
					entityManagerFactory.close();
					info("You are connected to "+ selected);
				} else {
					info("The server has reached the maximum number of connections. Please try a different server. ");
				}
				
				

			}
		};

		add(form);
		form.add(listSites);

	}
}