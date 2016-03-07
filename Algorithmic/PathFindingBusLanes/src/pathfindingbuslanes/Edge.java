//This class represents an edge of the graph.
public class Edge {

	private Node firstEndpoint;
	private Node secondEndpoint;
	private String busLine;

	// The constructor for the class
	public Edge(Node u, Node v, String busLine) {
		this.firstEndpoint = u;
		this.secondEndpoint = v;
		this.busLine = busLine;
	}

	// Returns the first endpoint of the edge.
	public Node firstEndpoint() {
		return this.firstEndpoint;
	}

	// Returns the second endpoint of the edge.
	public Node secondEndpoint() {
		return this.secondEndpoint;
	}

	// Returns the busLine to which the edge belongs.
	public String getBusLine() {
		return this.busLine;
	}
}
