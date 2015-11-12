package nl.hro.infsen212.neo4j;

import static nl.hro.infsen212.neo4j.util.GraphUtil.registerShutdownHook;
import nl.hro.infsen212.neo4j.util.RelationTypes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.impl.util.FileUtils;

public class ManagerClass {

	private GraphDatabaseService graphDb;
	private static final String dataBasePath = "/tmp/nlhroinfsen212";
	private static List<String> cityNames = new ArrayList<String>();

	private static ManagerClass instance = null;

	public static ManagerClass getInstance() {
		if (instance == null) {
			instance = new ManagerClass();
		}
		return instance;
	}

	public void init() {
		try {
			FileUtils.deleteRecursively(new File(dataBasePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		graphDb = new EmbeddedGraphDatabase(dataBasePath);
		registerShutdownHook(graphDb);
		addCity();
		loadCityInList();
	}

	public void loadCityInList() {
		cityNames.clear();
		Transaction tx = graphDb.beginTx();
		try {
			Iterable<Node> nodes = graphDb.getAllNodes();
			for (Node node : nodes) {
				cityNames.add((String) node.getProperty("name"));
			}
		} finally {
			tx.finish();
		}
	}

	public void addCity() {

		GraphDatabaseService graphDb = new EmbeddedGraphDatabase(dataBasePath);
		registerShutdownHook(graphDb);
		Index<Node> nodeIndex = graphDb.index().forNodes("nodes");

		Transaction tx = graphDb.beginTx();
		try {
			// Monster
			Node MonsterNode = graphDb.createNode();
			MonsterNode.setProperty("name", "Monster");
			nodeIndex.add(MonsterNode, "name", "Monster");

			// Naaldwijk
			Node NaaldwijkNode = graphDb.createNode();
			NaaldwijkNode.setProperty("name", "Naaldwijk");
			nodeIndex.add(NaaldwijkNode, "name", "Naaldwijk");

			// ADD RELATIONS TO NODE
			// (Monster)-[:RoadTo {distance:5.5,time:12}]->(Naaldwijk),
			Relationship MonsterToNaaldwijk = MonsterNode.createRelationshipTo(NaaldwijkNode, RelationTypes.LEADS_TO);
			MonsterToNaaldwijk.setProperty("distance", 5.6);
			MonsterToNaaldwijk.setProperty("time", 11);

			tx.success();
		} finally {
			tx.finish();
		}
		graphDb.shutdown();
	}
	
	public static List<String> getCityNames() {
		return cityNames;
	}
}