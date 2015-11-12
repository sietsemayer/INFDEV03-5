package nl.hro.infsen212.neo4j;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class WebSite extends WebPage{
	private static final long serialVersionUID = 2071262588773857425L;

@SuppressWarnings({ "unchecked", "rawtypes" })
public WebSite(){
        add(new BookmarkablePageLink("homeLink", Home.class));
        add(new BookmarkablePageLink("PlanRoute", PlanRoute.class));
        add(new BookmarkablePageLink("AddCity", AddCity.class));
        add(new BookmarkablePageLink("AddConn", AddConn.class));
        add(new BookmarkablePageLink("RmCity", RmCity.class));
        add(new BookmarkablePageLink("RmConn", RmConn.class));
    }
}