package nl.hro.infsen212.neo4j;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

public class PlanRoute extends WebSite {
	
	private static final long serialVersionUID = 1160576997869903381L;
	private String selectedStart;
	private String selectedFinish;
	
	public PlanRoute(){
		super();
		ManagerClass.getInstance();
		add(new FeedbackPanel("planRoute"));
		
		DropDownChoice<String> startCityDropDownList = new DropDownChoice<String>("startCities",
				new PropertyModel<String>(this, "selectedStart"), ManagerClass.getCityNames());

		DropDownChoice<String> finishCityDropDownList = new DropDownChoice<String>("finishCities",
				new PropertyModel<String>(this, "selectedFinish"), ManagerClass.getCityNames());

		Form<?> form = new Form<Void>("planRouteForm") {
			private static final long serialVersionUID = 4634606760919492100L;

			@Override
			protected void onSubmit() {

				info("From  " + selectedStart + " to " + selectedFinish);

			}
		};

		add(form);
		form.add(startCityDropDownList);
		form.add(finishCityDropDownList);
		
		
	}

}
