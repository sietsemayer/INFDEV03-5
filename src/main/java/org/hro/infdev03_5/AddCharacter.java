package org.hro.infdev03_5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.PopupCloseLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class AddCharacter extends WebPage {

	private static final long serialVersionUID = 1L;
	private static final List<String> RACE = Arrays.asList(new String[] { "Saradet", "Throgaran", "Clitingar",
			"Darustther", "Womostur", "	Hatlorard", "Chiburir", "	Chauden", });
	private static final List<String> CLASS = Arrays.asList(new String[] { "Human", "Orc", "Human / Orc" });

	private static final List<Integer> LEVEL = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 });

	 
	private String selectedClass = "Saradet";
	private String selectedRace = "Human";
	private String selectedLevel = "1";

	public AddCharacter(PageParameters parameters) {

		add(new FeedbackPanel("feedback"));

		final TextField<String> username = new TextField<String>("username", Model.of(""));
		username.setRequired(true);

		DropDownChoice<String> listSites1 = new DropDownChoice<String>("class",
				new PropertyModel<String>(this, "selectedClass"), CLASS);

		DropDownChoice<String> listSites2 = new DropDownChoice<String>("race",
				new PropertyModel<String>(this, "selectedRace"), RACE);

		DropDownChoice<Integer> listSites3 = new DropDownChoice<Integer>("level",
				new PropertyModel<Integer>(this, selectedLevel), LEVEL);

		Form<?> form = new Form<Void>("userForm") {

			@Override
			protected void onSubmit() {

				final String usernameValue = username.getModelObject();

				PageParameters pageParameters = new PageParameters();
				pageParameters.add("username", usernameValue);
				setResponsePage(SuccessPage.class, pageParameters);

			}

		};

		add(form);
		form.add(username);
		form.add(listSites1);
		form.add(listSites2);
		form.add(listSites3);

		add(new PopupCloseLink("close"));
	}
}
