import java.util.Iterator;
import java.util.*;

//This class represents an undirected graph
//I will be using an Adjacency Matrix
public class Graph implements GraphADT {

	private int numNodes;
	private Node[] vertexList;
	private Edge[][] adjacencyMatrix;

	// Creates a graph with n nodes and no edges.
	// This is the constructor for the class. The names of the nodes are
	// 0,1,...,n-1.
	public Graph(int n) {
		this.numNodes = n;
		this.vertexList = new Node[n];
		this.adjacencyMatrix = new Edge[n][n];
		for (int i = 0; i < n; i++) {
			// nodes will be named from 0 to n-1
			vertexList[i] = new Node(i);
		}
	}

	// Adds an edge connecting u and v and belonging to the specified bus line.
	// This method throws a GraphException if either
	// node does not exist or if in the graph there is already an
	// edge connecting the given nodes.
	public void insertEdge(Node nodeu, Node nodev, String busLine)
			throws GraphException {
		checkNode(nodeu);
		checkNode(nodev);
		// check if there is an edge already between two vertices
		if (this.adjacencyMatrix[nodeu.getName()][nodev.getName()] == null) {
			// put these edges in the matrix
			this.adjacencyMatrix[nodeu.getName()][nodev.getName()] = new Edge(
					nodeu, nodev, busLine);
			this.adjacencyMatrix[nodev.getName()][nodeu.getName()] = new Edge(
					nodev, nodeu, busLine);
		} else
			// there is already an edge there
			throw new GraphException("Edge is already present between nodes "
					+ nodeu.getName() + " and " + nodev.getName());
	}

	// Returns the node with the specified name.
	// If no node with this name exists, the method should throw a
	// GraphException.
	public Node getNode(int name) throws GraphException {
		if (name >= this.numNodes)
			throw new GraphException("No Node with name " + name + " exists");
		return this.vertexList[name];
	}

	// The last three methods throw a GraphException if u or v are not nodes of
	// the graph.
	// this private helper method will quickly do that for me
	private void checkNode(Node z) throws GraphException {
		if (z.getName() >= this.numNodes)
			throw new GraphException("No Node with name " + z.getName()
					+ " exists");
	}

	// Returns a Java Iterator storing all the edges incident on node u.
	// It returns null if u does not have any edges incident on it.
	public Iterator<Edge> incidentEdges(Node u) throws GraphException {
		// linkedList to store the incidentEdges
		LinkedList<Edge> incidentEdges = new LinkedList<Edge>();
		// check if u is in the graph
		checkNode(u);
		for (int i = 0; i < this.numNodes; i++) {
			if (this.adjacencyMatrix[u.getName()][i] != null)
				incidentEdges.add(this.adjacencyMatrix[u.getName()][i]);
		}
		if (incidentEdges.isEmpty())
			return null;
		else
			return incidentEdges.iterator();
	}

	// Returns the edge connecting nodes u and v.
	// This method throws a GraphException if there is no edge between u and v.
	public Edge getEdge(Node u, Node v) throws GraphException {
		checkNode(u);
		checkNode(v);
		// return edge if one is there, else, it's not there, throw exception.
		if (this.adjacencyMatrix[u.getName()][v.getName()] != null)
			return this.adjacencyMatrix[u.getName()][v.getName()];
		else
			throw new GraphException("No Edge between nodes " + u.getName()
					+ " and " + v.getName());
	}

	// Returns true if nodes u and v are adjacent; it returns false otherwise.
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		checkNode(u);
		checkNode(v);
		// there is an edge between them, they are adjacent.
		if (this.adjacencyMatrix[u.getName()][v.getName()] != null)
			return true;
		else
			return false;
	}

}
