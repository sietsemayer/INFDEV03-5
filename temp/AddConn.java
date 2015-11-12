package nl.hro.infsen212.neo4j;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class AddConn extends WebSite{
   
	private static final long serialVersionUID = -4993595783833861441L;
	private TextField time;
	private TextField distance;
	private String selectedRelationStart;
	private String selectedRelationFinish;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AddConn(){
        super();
        DropDownChoice<String> addRelationStartCityDropDownList = new DropDownChoice<String>("relationStartCities",
				new PropertyModel<String>(this, "selectedRelationStart"), ManagerClass.getCityNames());

		DropDownChoice<String> addRelationFinishCityDropDownList = new DropDownChoice<String>("relationFinishCities",
				new PropertyModel<String>(this, "selectedRelationFinish"), ManagerClass.getCityNames());
		
		time = new TextField("time",new Model(""));
		distance = new TextField("distance", new Model(""));
		
		
		Form<?> addRelationform = new Form<Void>("addRelationForm") {
			private static final long serialVersionUID = -5531285138421509004L;

			@Override
			protected void onSubmit() {

				info("A new Relation from "+selectedRelationStart + " to " + selectedRelationFinish+ " with time "+time.toString()+ " and distance "+distance.toString() );
			}
		};

		add(addRelationform);
		addRelationform.add(addRelationStartCityDropDownList);
		addRelationform.add(addRelationFinishCityDropDownList);
		addRelationform.add(time);
		addRelationform.add(distance);
    }
}