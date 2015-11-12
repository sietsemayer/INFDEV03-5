package nl.hro.infsen212.neo4j;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class AddCity extends WebSite {

	private static final long serialVersionUID = -285366517180814531L;

	public AddCity() {
		super();
		add(new FeedbackPanel("addCity"));
		Form<?> form = new Form<Void>("addCityForm") {
			private static final long serialVersionUID = 4121951189022933907L;

			@Override
			protected void onSubmit() {
				info("City added");
			}
		};

		add(form);

	}
}