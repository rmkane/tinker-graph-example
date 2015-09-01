package com.github.tinkerpop.graph;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;

/**
 * http://stackoverflow.com/questions/32308987/algorithm-to-group-objects#32309268
 * 
 * https://github.com/tinkerpop/blueprints/wiki/Code-Examples
 */
public class SportRelationships {
	private static Map<String, Person> people;
	private static Map<String, Sport> sports;
	
	static {
		people = getPeople("Sam", "Dylan", "Tyler", "John", "Carter", "Kane", "Michael", "Frank");
		sports = getSports("Football", "Basketball", "Baseball", "Hockey", "Soccer");
	}
	
	public static void main(String[] args) {
		Graph graph = new TinkerGraph();
		
		addVerticies(graph, people);
		
		linkPeopleToSport(graph, 1, "Sam", "Dylan", "Football");
		linkPeopleToSport(graph, 2, "Tyler", "John", "Basketball");
		linkPeopleToSport(graph, 3, "Carter", "Dylan", "Baseball");
		linkPeopleToSport(graph, 4, "Kane", "Michael", "Hockey");
		linkPeopleToSport(graph, 5, "Carter", "Frank", "Soccer");

		System.out.println("Vertices of " + graph);
		printVerticies(graph);
		
		System.out.println("\nEdges of " + graph);
		printEdges(graph);
		
	}
	
	private static void printVerticies(Graph graph) {
		for (Vertex vertex : graph.getVertices()) {
			System.out.println(formatVertex(vertex));
		}
	}
	
	private static void printEdges(Graph graph) {
		for (Edge edge : graph.getEdges()) {
			System.out.println(formatEdge(edge, "name"));
		}
	}
	
	private static String formatVertex(Vertex vertex) {
		return String.format("- %s (%s) - %s", vertex.getProperty("name"),
				vertex.getProperty("uniqueId"), countEdges(vertex));
	}
	
	private static String countEdges(Vertex vertex) {
		int count = countEdges(vertex, Direction.OUT);
		
		return String.format("%d edge%s", count, count != 1 ? "s" : "");
	}
	
	private static int countEdges(Vertex vertex, Direction direction) {
		Iterable<Edge> it = vertex.getEdges(direction);

		if (it instanceof Collection)
			return ((Collection<?>) it).size();

		int i = 0;
		for (@SuppressWarnings("unused") Edge edge : it) {
			i++;
		}
		return i;
	}
	
	private static String formatEdge(Edge edge, String property) {
		return String.format("- %s <-- %s --> %s",
				edge.getVertex(Direction.OUT).getProperty(property),
				edge.getLabel(),
				edge.getVertex(Direction.IN).getProperty(property));
	}
	
	private static void addVerticies(Graph graph, Map<String, Person> people2) {
		for (Person person : people.values()) {
			Vertex v = graph.addVertex(person);
			
			v.setProperty("uniqueId", person.getUniqueId());
			v.setProperty("name", person.getName());
		}
	}

	private static Edge linkPeopleToSport(Graph graph, long id, String personA, String personB, String sportName) {
		Vertex v1 = getPerson(graph, personA);
		Vertex v2 = getPerson(graph, personB);
		
		addIfNotFound(sportName, personA);
		addIfNotFound(sportName, personB);
		
		return graph.addEdge(id, v1, v2, sportName);
	}
	
	private static void addIfNotFound(String sportName, String personName) {
		Sport sport = sports.get(sportName);
		List<Person> plays = sport.getPeopleWhoPlayThisSport();
		Person person = people.get(personName);
		
		if (!plays.contains(person)) {
			plays.add(person);
		}
	}
	
	private static Vertex getPerson(Graph graph, String name) {
		return graph.getVertex(people.get(name));
	}

	private static Map<String, Sport> getSports(String... names) {
		Map<String, Sport> sports = new LinkedHashMap<String, Sport>();
		
		for (String name : names) {
			sports.put(name, new Sport(name));
		}
		
		return sports;
	}

	private static Map<String, Person> getPeople(String... names) {
		Map<String, Person> people = new LinkedHashMap<String, Person>();
		
		for (int i = 0; i < names.length; i++) {
			people.put(names[i],  new Person(i, names[i]));
		}
		
		return people;
	}
}
