package nl.hro.infsen212.neo4j;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.model.PropertyModel;

public class RmConn extends WebSite {
	private static final long serialVersionUID = -3774325308068514600L;
	private String selectedDeleteConnection;

	public RmConn() {
		super();

		ListChoice<String> deleteConnection = new ListChoice<String>(
				"deleteConnection", new PropertyModel<String>(this,
						"selectedDeleteConnection"), ManagerClass.getCityNames());

		deleteConnection.setMaxRows(5);

		Form<?> deletedConnectionForm = new Form<Void>("deletedCityForm") {

			private static final long serialVersionUID = 1070957041739644733L;

			@Override
			protected void onSubmit() {

				info("Selected city to delete : " + selectedDeleteConnection);

			}
		};

		add(deletedConnectionForm);
		deletedConnectionForm.add(deleteConnection);

	}

}
