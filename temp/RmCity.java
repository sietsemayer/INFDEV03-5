package nl.hro.infsen212.neo4j;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.model.PropertyModel;

public class RmCity extends WebSite {
	private static final long serialVersionUID = 5441871693487820063L;
	private String selectedDeleteCity;

	public RmCity() {
		super();

		ListChoice<String> deleteCity = new ListChoice<String>("deleteCity",
				new PropertyModel<String>(this, "selectedDeleteCity"), ManagerClass.getCityNames());

		deleteCity.setMaxRows(5);

		Form<?> deletedCityForm = new Form<Void>("deletedCityForm") {

			private static final long serialVersionUID = 1070957041739644733L;

			@Override
			protected void onSubmit() {

				info("Selected city to delete : " + selectedDeleteCity);

			}
		};

		add(deletedCityForm);
		deletedCityForm.add(deleteCity);
	}
}