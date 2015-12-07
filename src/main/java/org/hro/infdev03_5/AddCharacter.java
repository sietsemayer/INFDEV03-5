package org.hro.infdev03_5;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.PopupCloseLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.hro.infdev03_5.entity.Characters;
import org.hro.infdev03_5.entity.User;

public class AddCharacter extends WebPage {

	// TO DO : CREATE ENUM
	private static final List<String> LEVEL = Arrays
			.asList(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", });
	private static final List<String> RACE = Arrays
			.asList(new String[] { "Dustie", "Proto", "Norbit", "Socket", "Ectron", "Icot" });
	private static final List<String> CHARACTER = Arrays.asList(new String[] { "Human", "Orc", "Human / Orc" });

	private String selectedLevel = "01";
	private String selectedRace = "Dustie";
	private String selectedCharacter = "Human";
	// private User user;

	public AddCharacter(final PageParameters pageParameters) {

		final String usernameValue = pageParameters.get("username").toString();

		add(new FeedbackPanel("feedback"));
		add(new PopupCloseLink("close"));
		final TextField<String> charactername = new TextField<String>("charactername", Model.of(""));
		charactername.setRequired(true);

		DropDownChoice<String> levelList = new DropDownChoice<String>("levelList",
				new PropertyModel<String>(this, "selectedLevel"), LEVEL);

		DropDownChoice<String> raceList = new DropDownChoice<String>("raceList",
				new PropertyModel<String>(this, "selectedRace"), RACE);

		DropDownChoice<String> characterList = new DropDownChoice<String>("characterList",
				new PropertyModel<String>(this, "selectedCharacter"), CHARACTER);

		Form<?> form = new Form<Void>("form") {
			@Override
			protected void onSubmit() {

				String queryParameter = charactername.getModelObject();
				EntityManagerFactory entityManagerFactory = Persistence
						.createEntityManagerFactory("INFDEV03_5_WITHDATABASE");
				EntityManager entityManager = entityManagerFactory.createEntityManager();
				User user = entityManager.find(User.class, usernameValue);
				Query queryFindUsername = entityManager.createNamedQuery("Characters.findByName");
				queryFindUsername.setParameter("name", queryParameter);
				@SuppressWarnings("unchecked")
				List<Characters> ResultsetUserList = queryFindUsername.getResultList();
				entityManager.clear();
				if (ResultsetUserList.isEmpty()) {
					System.out.println("Character DOES NOT EXISTS!! USER IS BEING CREATED!!");
					entityManager.getTransaction().begin();
					Characters characters = new Characters();
					characters.setName(charactername.getModelObject());
					characters.setLevel(Integer.parseInt(selectedLevel));
					characters.setRace(selectedRace);
					characters.setClass1("selectedClass");
					entityManager.persist(characters);
					user.addCharacter(characters);
					entityManager.merge(user);
					entityManager.getTransaction().commit();
					entityManager.close();
					entityManagerFactory.close();
					info("Character " + charactername.getModelObject() + " was created successfully ");
				} else {
					System.out.println("USER EXISTS!! END OF STORY!!");
					info("You already hava a character with this name.");
					entityManager.close();
					entityManagerFactory.close();
				}
			}
		};

		add(form);
		form.add(charactername);
		form.add(levelList);
		form.add(raceList);
		form.add(characterList);

	}
}