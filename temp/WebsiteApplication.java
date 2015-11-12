package nl.hro.infsen212.neo4j;
import org.apache.wicket.protocol.http.WebApplication;


public class WebsiteApplication extends WebApplication{
    public WebsiteApplication(){
        
    }
    public Class getHomePage(){
        return Home.class;
    }   
    @Override
	public void init()
	{
		super.init();
		
	}
	 
}
