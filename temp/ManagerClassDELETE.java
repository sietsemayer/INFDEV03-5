/*package nl.hro.infsen212.neo4j;

import static nl.hro.infsen212.neo4j.util.GraphUtil.registerShutdownHook;
import static nl.hro.infsen212.neo4j.util.GraphUtil.cleanUp;
import nl.hro.infsen212.neo4j.util.RelationTypes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.impl.util.FileUtils;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;

public class ManagerClassDELETE {
	private static ManagerClassDELETE instance = null;
	private static final String dataBasePath = "/tmp/nlhroinfsen212";
	private GraphDatabaseService graphDb;
	private static List<String> cityNames = new ArrayList<String>();
	private static List<String> relationList = new ArrayList<String>();
	private static TreeSet<String> sortedCityList = new TreeSet<String>();
	private String selectedStart;
	private String selectedFinish;
	private String selectedRelationStart;
	private String selectedRelationFinish;
	private String selectedDeleteCity;

	protected  ManagerClass() {

		try {
			FileUtils.deleteRecursively(new File(dataBasePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		graphDb = new EmbeddedGraphDatabase(dataBasePath);
		registerShutdownHook(graphDb);
		addCityToDatabase();
		loadCityInList();
	}

	private void printCityList() {
		sortedCityList.addAll(cityNames);
		Iterator<String> cityIterator = sortedCityList.iterator();
		while (cityIterator.hasNext()) {
			// System.out.println(cityIterator.next());
		}
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

	private void addCity(String name) {
		Transaction tx = graphDb.beginTx();
		try {
			Index<Node> nodeIndex = graphDb.index().forNodes("nodes");
			Node node = graphDb.createNode();
			node.setProperty("name", name);
			nodeIndex.add(node, "name", name);
			tx.success();
		} finally {
			tx.finish();
			loadCityInList();
		}
	}

	private void addRoute(Node start, Node finish, double distanceKm, int time) {
		Relationship relationship = start.createRelationshipTo(finish, RelationTypes.LEADS_TO);
		relationship.setProperty("distance", distanceKm);
		relationship.setProperty("time", time);
	}

	private void removeRoute(String start, String finish) {
		Transaction tx = graphDb.beginTx();
		try {
			Node node = getNode(start);
			relationList.clear();
			for (Relationship relationType : node.getRelationships()) {
				if (relationType.getEndNode().getProperty("name").equals(finish)) {
					relationType.delete();
				}
			}
		} finally {
			tx.finish();
		}
	}

	private Node getNode(String name) {
		Index<Node> nodeIndex = graphDb.index().forNodes("nodes");
		Node node = nodeIndex.get("name", name).getSingle();
		return node;

	}

	private void addCityToDatabase() {
		GraphDatabaseService graphDb = new EmbeddedGraphDatabase(dataBasePath);
		registerShutdownHook(graphDb);
		Index<Node> nodeIndex = graphDb.index().forNodes("nodes");

		Transaction tx = graphDb.beginTx();
		try {

			// clean database
			cleanUp(graphDb, nodeIndex);
			// System.out.println("Cleaning Database");
			// add all cities to database

			// Monster
			Node MonsterNode = graphDb.createNode();
			MonsterNode.setProperty("name", "Monster");
			nodeIndex.add(MonsterNode, "name", "Monster");

			// Naaldwijk
			Node NaaldwijkNode = graphDb.createNode();
			NaaldwijkNode.setProperty("name", "Naaldwijk");
			nodeIndex.add(NaaldwijkNode, "name", "Naaldwijk");

			// 's Gravenzende
			Node sGravenzandeNode = graphDb.createNode();
			sGravenzandeNode.setProperty("name", "sGravenzande");
			nodeIndex.add(sGravenzandeNode, "name", "sGravenzande");

			// Honselerdijk
			Node HonselersdijkNode = graphDb.createNode();
			HonselersdijkNode.setProperty("name", "Honserlesdijk");
			nodeIndex.add(HonselersdijkNode, "name", "Honselersdijk");

			// Kwinsheul
			Node KwintsheulNode = graphDb.createNode();
			KwintsheulNode.setProperty("name", "Kwintsheul");
			nodeIndex.add(KwintsheulNode, "name", "Kwintsheul");

			// De Lier
			Node DeLierNode = graphDb.createNode();
			DeLierNode.setProperty("name", "DeLier");
			nodeIndex.add(DeLierNode, "name", "DeLier");

			// Poeldijk
			Node PoeldijkNode = graphDb.createNode();
			PoeldijkNode.setProperty("name", "Poeldijk");
			nodeIndex.add(PoeldijkNode, "name", "Poeldijk");

			// Wateringen
			Node WateringenNode = graphDb.createNode();
			WateringenNode.setProperty("name", "Wateringen");
			nodeIndex.add(WateringenNode, "name", "Wateringen");

			// HoekvanHolland
			Node HoekvanHollandNode = graphDb.createNode();
			HoekvanHollandNode.setProperty("name", "HoekvanHolland");
			nodeIndex.add(HoekvanHollandNode, "name", "HoekvanHolland");

			// Delft
			Node DelftNode = graphDb.createNode();
			DelftNode.setProperty("name", "Delft");
			nodeIndex.add(DelftNode, "name", "Delft");

			// Schipluiden
			Node SchipluidenNode = graphDb.createNode();
			SchipluidenNode.setProperty("name", "Schipluiden");
			nodeIndex.add(SchipluidenNode, "name", "Schipluiden");

			// DenHaag
			Node DenHaagNode = graphDb.createNode();
			DenHaagNode.setProperty("name", "DenHaag");
			nodeIndex.add(DenHaagNode, "name", "DenHaag");

			// Rijswijk
			Node RijswijkNode = graphDb.createNode();
			RijswijkNode.setProperty("name", "Rijswijk");
			nodeIndex.add(RijswijkNode, "name", "Rijswijk");

			// Maassluis
			Node MaassluisNode = graphDb.createNode();
			MaassluisNode.setProperty("name", "Maassluis");
			nodeIndex.add(MaassluisNode, "name", "Maassluis");

			// Maasdijk
			Node MaasdijkNode = graphDb.createNode();
			MaasdijkNode.setProperty("name", "Maasdijk");
			nodeIndex.add(MaasdijkNode, "name", "Maasdijk");

			// Maasland
			Node MaaslandNode = graphDb.createNode();
			MaaslandNode.setProperty("name", "Maasland");
			nodeIndex.add(MaaslandNode, "name", "Maasland");

			// Vlaardingen
			Node VlaardingenNode = graphDb.createNode();
			VlaardingenNode.setProperty("name", "Vlaardingen");
			nodeIndex.add(VlaardingenNode, "name", "Vlaardingen");

			// Schiedam
			Node SchiedamNode = graphDb.createNode();
			SchiedamNode.setProperty("name", "Schiedam");
			nodeIndex.add(SchiedamNode, "name", "Schiedam");

			// Rotterdam
			Node RotterdamNode = graphDb.createNode();
			RotterdamNode.setProperty("name", "Rotterdam");
			nodeIndex.add(RotterdamNode, "name", "Rotterdam");

			// ADD RELATIONS TO NODE
			// (Monster)-[:RoadTo {distance:5.5,time:12}]->(Naaldwijk),
			Relationship MonsterToNaaldwijk = MonsterNode.createRelationshipTo(NaaldwijkNode, RelationTypes.LEADS_TO);
			MonsterToNaaldwijk.setProperty("distance", 5.6);
			MonsterToNaaldwijk.setProperty("time", 11);

			// (Monster)-[:RoadTo {distance:1.0,time:1}]->(sGravenzande),
			Relationship MonsterTosGravenzande = MonsterNode.createRelationshipTo(sGravenzandeNode,
					RelationTypes.LEADS_TO);
			MonsterTosGravenzande.setProperty("distance", 4.5);
			MonsterTosGravenzande.setProperty("time", 7);

			// (Monster)-[:RoadTo {distance:12.5,time:25}]->(DenHaag),
			Relationship MonsterToDenHaag = MonsterNode.createRelationshipTo(DenHaagNode, RelationTypes.LEADS_TO);
			MonsterToDenHaag.setProperty("distance", 11.2);
			MonsterToDenHaag.setProperty("time", 25);

			// (Monster)-[:RoadTo {distance:3.6,time:7}]->(Poeldijk),
			Relationship MonsterToPoeldijk = MonsterNode.createRelationshipTo(PoeldijkNode, RelationTypes.LEADS_TO);
			MonsterToPoeldijk.setProperty("distance", 3.6);
			MonsterToPoeldijk.setProperty("time", 7);

			// (Monster)-[:RoadTo {distance:7.8,time:14}]->(HoekvanHolland),
			Relationship MonsterToHoekvanHolland = MonsterNode.createRelationshipTo(HoekvanHollandNode,
					RelationTypes.LEADS_TO);
			MonsterToHoekvanHolland.setProperty("distance", 7.8);
			MonsterToHoekvanHolland.setProperty("time", 13);

			// (Naaldwijk)-[:RoadTo {distance:4.9,time:9}]->(sGravenzande),
			Relationship NaaldwijkTosGravenzande = NaaldwijkNode.createRelationshipTo(sGravenzandeNode,
					RelationTypes.LEADS_TO);
			NaaldwijkTosGravenzande.setProperty("distance", 4.9);
			NaaldwijkTosGravenzande.setProperty("time", 9);

			// (Naaldwijk)-[:RoadTo {distance:1.0,time:1}]->(Poeldijk),
			Relationship NaaldwijkToPoeldijk = NaaldwijkNode.createRelationshipTo(PoeldijkNode, RelationTypes.LEADS_TO);
			NaaldwijkToPoeldijk.setProperty("distance", 5.0);
			NaaldwijkToPoeldijk.setProperty("time", 9);

			// (Naaldwijk)-[:RoadTo {distance:6.3,time:13}]->(Wateringen),
			Relationship NaaldwijkToWateringen = NaaldwijkNode.createRelationshipTo(WateringenNode,
					RelationTypes.LEADS_TO);
			NaaldwijkToWateringen.setProperty("distance", 6.3);
			NaaldwijkToWateringen.setProperty("time", 13);

			// (Naaldwijk)-[:RoadTo {distance:5.0,time:10}]->(DeLier),
			Relationship NaaldwijkDeLier = NaaldwijkNode.createRelationshipTo(DeLierNode, RelationTypes.LEADS_TO);
			NaaldwijkDeLier.setProperty("distance", 6.2);
			NaaldwijkDeLier.setProperty("time", 11);

			// (Naaldwijk)-[:RoadTo {distance:7.0,time:10}]->(Maasdijk),
			Relationship NaaldwijkToMaasdijk = NaaldwijkNode.createRelationshipTo(MaasdijkNode, RelationTypes.LEADS_TO);
			NaaldwijkToMaasdijk.setProperty("distance", 7.7);
			NaaldwijkToMaasdijk.setProperty("time", 13);

			// (Naaldwijk)-[:RoadTo {distance:2.3,time:4}]->(Honselersdijk),
			Relationship NaaldwijkToHonselersdijk = NaaldwijkNode.createRelationshipTo(HonselersdijkNode,
					RelationTypes.LEADS_TO);
			NaaldwijkToHonselersdijk.setProperty("distance", 2.3);
			NaaldwijkToHonselersdijk.setProperty("time", 5);

			// (sGravenzande)-[:RoadTo {distance:4.5,time:8}]->(HoekvanHolland),
			Relationship sGravenzandeToHoekvanHolland = sGravenzandeNode.createRelationshipTo(HoekvanHollandNode,
					RelationTypes.LEADS_TO);
			sGravenzandeToHoekvanHolland.setProperty("distance", 4.5);
			sGravenzandeToHoekvanHolland.setProperty("time", 8);

			// (sGravenzande)-[:RoadTo {distance:7.6,time:12}]->(Maasdijk),
			Relationship sGravenzandeToMaasdijk = sGravenzandeNode.createRelationshipTo(MaasdijkNode,
					RelationTypes.LEADS_TO);
			sGravenzandeToMaasdijk.setProperty("distance", 7.6);
			sGravenzandeToMaasdijk.setProperty("time", 12);

			// (Kwintsheul)-[:RoadTo {distance:2.3,time:5}]->(Honselersdijk),
			Relationship KwinsheulToHonserlersdijk = KwintsheulNode.createRelationshipTo(HonselersdijkNode,
					RelationTypes.LEADS_TO);
			KwinsheulToHonserlersdijk.setProperty("distance", 2.3);
			KwinsheulToHonserlersdijk.setProperty("time", 5);

			// (Kwintsheul)-[:RoadTo {distance:4.1,time:8}]->(Poeldijk),
			Relationship KwinsheulToPoeldijk = KwintsheulNode.createRelationshipTo(PoeldijkNode,
					RelationTypes.LEADS_TO);
			KwinsheulToPoeldijk.setProperty("distance", 4.1);
			KwinsheulToPoeldijk.setProperty("time", 9);

			// (Kwintsheul)-[:RoadTo {distance:1.8,time:4}]->(Wateringen),
			Relationship KwinsheulToWateringen = KwintsheulNode.createRelationshipTo(WateringenNode,
					RelationTypes.LEADS_TO);
			KwinsheulToWateringen.setProperty("distance", 1.8);
			KwinsheulToWateringen.setProperty("time", 4);

			// (DeLier)-[:RoadTo {distance:10.0,time:18}]->(Delft),
			Relationship DeLierToDelft = DeLierNode.createRelationshipTo(DelftNode, RelationTypes.LEADS_TO);
			DeLierToDelft.setProperty("distance", 10.0);
			DeLierToDelft.setProperty("time", 18);

			// (DeLier)-[:RoadTo {distance:5.8,time:10}]->(Maasdijk),
			Relationship DeLierToMaasdijk = DeLierNode.createRelationshipTo(MaasdijkNode, RelationTypes.LEADS_TO);
			DeLierToMaasdijk.setProperty("distance", 5.5);
			DeLierToMaasdijk.setProperty("time", 11);

			// (DeLier)-[:RoadTo {distance:6.9,time:11}]->(Maasland),
			Relationship DeLierToMaasland = DeLierNode.createRelationshipTo(MaaslandNode, RelationTypes.LEADS_TO);
			DeLierToMaasland.setProperty("distance", 6.9);
			DeLierToMaasland.setProperty("time", 12);

			// (DeLier)-[:RoadTo {distance:10.8,time:13}]->(Maassluis),
			Relationship DeLierToMaassluis = DeLierNode.createRelationshipTo(MaassluisNode, RelationTypes.LEADS_TO);
			DeLierToMaassluis.setProperty("distance", 10.8);
			DeLierToMaassluis.setProperty("time", 13);

			// (DeLier)-[:RoadTo {distance:8.3,time:13}]->(Schipluiden),
			Relationship DeLierToSchipluiden = DeLierNode.createRelationshipTo(SchipluidenNode, RelationTypes.LEADS_TO);
			DeLierToSchipluiden.setProperty("distance", 8.3);
			DeLierToSchipluiden.setProperty("time", 12);

			// (Poeldijk)-[:RoadTo {distance:1.0,time:1}]->(DenHaag),
			Relationship PoeldijkToDenHaag = PoeldijkNode.createRelationshipTo(DenHaagNode, RelationTypes.LEADS_TO);
			PoeldijkToDenHaag.setProperty("distance", 10.1);
			PoeldijkToDenHaag.setProperty("time", 23);

			// (Poeldijk)-[:RoadTo {distance:10.1,time:20}]->(Wateringen),
			Relationship PoeldijkToWateringen = PoeldijkNode.createRelationshipTo(WateringenNode,
					RelationTypes.LEADS_TO);
			PoeldijkToWateringen.setProperty("distance", 4.7);
			PoeldijkToWateringen.setProperty("time", 10);

			// (Wateringen)-[:RoadTo {distance:11.0,time:23}]->(Delft),
			Relationship WateringenToDelft = WateringenNode.createRelationshipTo(DelftNode, RelationTypes.LEADS_TO);
			WateringenToDelft.setProperty("distance", 11.1);
			WateringenToDelft.setProperty("time", 20);

			// (Wateringen)-[:RoadTo {distance:7.4,time:17}]->(DenHaag),
			Relationship WateringenToDenHaag = WateringenNode.createRelationshipTo(DenHaagNode, RelationTypes.LEADS_TO);
			WateringenToDenHaag.setProperty("distance", 7.4);
			WateringenToDenHaag.setProperty("time", 19);

			// (Wateringen)-[:RoadTo {distance:5.8,time:12}]->(Rijswijk),
			Relationship WateringenToRijswijk = WateringenNode.createRelationshipTo(RijswijkNode,
					RelationTypes.LEADS_TO);
			WateringenToRijswijk.setProperty("distance", 5.8);
			WateringenToRijswijk.setProperty("time", 12);

			// (Delft)-[:RoadTo {distance:2.0,time:5}]->(Rijswijk),
			Relationship DelftToRijswijk = DelftNode.createRelationshipTo(RijswijkNode, RelationTypes.LEADS_TO);
			DelftToRijswijk.setProperty("distance", 5.7);
			DelftToRijswijk.setProperty("time", 16);

			// (Delft)-[:RoadTo {distance:17.0,time:21}]->(Schiedam),
			Relationship DelftToSchiedam = DelftNode.createRelationshipTo(SchiedamNode, RelationTypes.LEADS_TO);
			DelftToSchiedam.setProperty("distance", 17.0);
			DelftToSchiedam.setProperty("time", 21);

			// (Delft)-[:RoadTo {distance:15.9,time:21}]->(Rotterdam),
			Relationship DelftToRotterdam = DelftNode.createRelationshipTo(RotterdamNode, RelationTypes.LEADS_TO);
			DelftToRotterdam.setProperty("distance", 15.9);
			DelftToRotterdam.setProperty("time", 21);

			// (Delft)-[:RoadTo {distance:6.6,time:15}]->(Schipluiden),
			Relationship DelftToSchipluiden = DelftNode.createRelationshipTo(SchipluidenNode, RelationTypes.LEADS_TO);
			DelftToSchipluiden.setProperty("distance", 6.6);
			DelftToSchipluiden.setProperty("time", 15);

			// (Schipluiden)-[:RoadTo {distance:10.5,time:19}]->(Vlaardingen),
			Relationship SchipluidenToVlaardingen = SchiedamNode.createRelationshipTo(VlaardingenNode,
					RelationTypes.LEADS_TO);
			SchipluidenToVlaardingen.setProperty("distance", 10.5);
			SchipluidenToVlaardingen.setProperty("time", 19);

			// (DenHaag)-[:RoadTo {distance:1.0,time:1}]->(Rijswijk),
			Relationship DenHaagToRijswijk = DenHaagNode.createRelationshipTo(PoeldijkNode, RelationTypes.LEADS_TO);
			DenHaagToRijswijk.setProperty("distance", 6.0);
			DenHaagToRijswijk.setProperty("time", 17);

			// (Maassluis)-[:RoadTo {distance:7.8,time:10}]->(Maasdijk),
			Relationship MaassluisToMaasdijk = MaaslandNode.createRelationshipTo(MaasdijkNode, RelationTypes.LEADS_TO);
			MaassluisToMaasdijk.setProperty("distance", 2.4);
			MaassluisToMaasdijk.setProperty("time", 7);

			// (Maassluis)-[:RoadTo {distance:2.4,time:6}]->(Maasland),
			Relationship MaassluisToMaasland = MaassluisNode.createRelationshipTo(MaaslandNode, RelationTypes.LEADS_TO);
			MaassluisToMaasland.setProperty("distance", 2.4);
			MaassluisToMaasland.setProperty("time", 6);

			// (Maassluis)-[:RoadTo {distance:7.8,time:11}]->(Vlaardingen),
			Relationship MaassluisToVlaardingen = MaassluisNode.createRelationshipTo(VlaardingenNode,
					RelationTypes.LEADS_TO);
			MaassluisToVlaardingen.setProperty("distance", 7.8);
			MaassluisToVlaardingen.setProperty("time", 11);

			// (Maasland)-[:RoadTo {distance:7.8,time:11}]->(Vlaardingen),
			Relationship MaaslandToVlaardingen = MaaslandNode.createRelationshipTo(VlaardingenNode,
					RelationTypes.LEADS_TO);
			MaaslandToVlaardingen.setProperty("distance", 7.8);
			MaaslandToVlaardingen.setProperty("time", 11);

			// (Vlaardingen)-[:RoadTo {distance:5.4,time:12}]->(Schiedam),
			Relationship VlaardingenToSchiedam = VlaardingenNode.createRelationshipTo(SchiedamNode,
					RelationTypes.LEADS_TO);
			VlaardingenToSchiedam.setProperty("distance", 5.4);
			VlaardingenToSchiedam.setProperty("time", 12);

			// (Schiedam)-[:RoadTo {distance:6.4,time:15}]->(Rotterdam)
			Relationship SchiedamToRotterdam = SchiedamNode.createRelationshipTo(RotterdamNode, RelationTypes.LEADS_TO);
			SchiedamToRotterdam.setProperty("distance", 6.4);
			SchiedamToRotterdam.setProperty("time", 15);

			tx.success();
		} finally {
			tx.finish();
		}
		graphDb.shutdown();
	}

	public static ManagerClass getInstance() {
		if (instance == null) {
			instance = new ManagerClass();
			System.out.println("Mangerclass is constricted");
		}
		return instance;
	}

}
*/