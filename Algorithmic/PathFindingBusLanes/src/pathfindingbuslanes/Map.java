import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

//This class represents the map of bus lines
public class Map {

	private static final String String = null;
	private Graph graph;
	private Node startNode;
	private Node endNode;
	private int maxBusChanges;
	private Stack<Node> thePath;
	// this is -1 because when we get on the bus the first time, my algorithm
	// will
	// count that as a busChange.
	private int busChanges = -1;

	// Constructor for building a map from the content of the input file.
	// If the input file does not exist, this method should throw a
	// MapException.
	public Map(String inputFile) throws MapException {
		// make the linkedList to store the path, I will use this in later
		// methods
		this.thePath = new Stack<Node>();
		if (inputFile == null)
			throw new MapException("inputFile = " + inputFile
					+ "is not defined");
		// constructor building a map from the contents of the input file
		try {
			BufferedReader input = new BufferedReader(new FileReader(inputFile));
			Integer.parseInt(input.readLine());
			// how many nodes are in each row
			int mapWidth = Integer.parseInt(input.readLine());
			int mapLength = Integer.parseInt(input.readLine());
			this.maxBusChanges = Integer.parseInt(input.readLine());
			int numNodes = mapWidth * mapLength;
			this.graph = new Graph(numNodes);
			int i, row = 0;
			String line;
			// make 2D array to store characters
			// I got this from drawMap!
			char[][] map = new char[2 * mapLength - 1][2 * mapWidth - 1];
			// Read the map from the file
			for (;;) {
				line = input.readLine();
				if (line == null) { // End of file
					input.close();
					break;
				}
				for (i = 0; i < line.length(); ++i)
					map[row][i] = line.charAt(i);
				++row;
			}
			// NodeLevel will keep track at which Node I am at. At every 0,1,+ I
			// will increment it.
			int nodeLevel = 0;
			// now all characters from the file are in a 2d Character Array
			// the map[a][b], a is the row and b the character on that row
			for (row = 0; row < (2 * mapLength - 1); ++row) {
				// Horizontal Street
				if ((row % 2) == 0) {
					// go through the row
					for (i = 0; i < (2 * mapWidth - 1); ++i)
						switch (map[row][i]) {
						// Start Node
						case '0':
							this.startNode = this.graph.getNode(nodeLevel);
							nodeLevel++;
							break;
						// End/Destination Node
						case '1':
							this.endNode = this.graph.getNode(nodeLevel);
							nodeLevel++;
							break;
						// block of houses (do nothing)
						case ' ':
							break;
						// Regular Node (do nothing) increment Node Level
						case '+':
							nodeLevel++;
							break;
						// letter (make an edge between node behind and after it
						default:
							String currBusLine = Character
									.toString(map[row][i]);
							this.graph.insertEdge(
									this.graph.getNode(nodeLevel - 1),
									this.graph.getNode(nodeLevel), currBusLine);
							break;
						}
				}
				// vertical street
				else {
					// go through the row
					for (i = 0; i < (2 * mapWidth - 1); ++i)
						switch (map[row][i]) {
						// block of houses (do nothing)
						case ' ':
							break;
						// letter (make an edge between node above it and below
						// it)
						default:
							// nodes in each row
							String currBusLine = Character
									.toString(map[row][i]);
							// mistake here
							int nodeAboveName;
							int nodeBelowName;
							int numCol = 2 * mapWidth - 1;
							// this edge is at the very end of the row
							if (i == numCol - 1) {
								nodeAboveName = nodeLevel - 1;
								nodeBelowName = nodeLevel + mapWidth - 1;
							}
							// this edge is at the start of the row
							else if (i == 0) {
								nodeAboveName = nodeLevel - mapWidth;
								nodeBelowName = nodeLevel;
							}
							// node is in the middle of the row somewhere
							else {
								// math formulas I derived from drawing out that
								// will give me the node above
								// i is the current length of the row, and I
								// know that there is a node
								// on every odd character on horizontal streets
								// (/2)
								nodeAboveName = nodeLevel
										- ((numCol - i) / 2 + 1);
								nodeBelowName = nodeLevel + (i + 1) / 2;
							}
							this.graph.insertEdge(
									this.graph.getNode(nodeAboveName),
									this.graph.getNode(nodeBelowName),
									currBusLine);
							break;
						}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage()
					+ "Failed to make Graph of inputFile");
		}
	}

	// Returns the graph representing the map
	// if graph is not defined, throws map exception.
	public Graph getGraph() throws MapException {
		if (this.graph == null)
			throw new MapException("Graph is not defined");
		return this.graph;
	}

	// Returns a Java Iterator containing the nodes along the path
	// Modified Depth First Search Traversal
	// from the starting point to the destination, if such a path exists.
	// If the path does not exist, this method returns the value null
	public Iterator<Node> findPath() throws GraphException {
		boolean isPath = findPath(this.startNode, this.endNode);
		if (isPath == true)
			return this.thePath.iterator();
		else
			return null;
	}

	// finds the path between Node b and e.
	// b is the currentNode and e is the destinationNode
	// if a path is found, returns true
	// if no path is found, returns false
	// thePath is stored in a stack instance variable called thePath.
	// it makes sure bus changes is less than maxBusChanges
	private boolean findPath(Node b, Node e) throws GraphException {
		String currentBus;
		// if you are at the start, the currentBus will be null.
		if (b == this.startNode)
			currentBus = null;
		else
			currentBus = this.graph.getEdge(this.thePath.peek(), b)
					.getBusLine();
		b.setMark(true);
		thePath.push(b);
		// you have found the endNode!
		if (b == e)
			return true;
		Iterator<Edge> incidentEdges = this.graph.incidentEdges(b);
		// for every edge (b, u) incident on b do
		while (incidentEdges.hasNext()) {

			// opposite
			Node u = incidentEdges.next().secondEndpoint();

			// if the edge on this is not marked!
			if (u.getMark() != true) {

				// get the bus line between b and the potential new node u
				String newBus = this.graph.getEdge(u, b).getBusLine();

				// check if we are switching buses by going to u
				if (!newBus.equals(currentBus)) {
					busChanges++;
					if (busChanges > this.maxBusChanges) {
						busChanges--;
						// go to next incident node in iteration as going here
						// will hit your maxBusChanges
						continue;
					}
				}

				// keep searching within this path
				if (findPath(u, e) == true)
					return true;
			}
			// check another incidentNode that is not marked
			else
				continue;
		}
		// un-mark when backtrack
		// pops b
		// if you did on the Path directly before you must decrement!
		b.setMark(false);
		thePath.pop(); // this is node b
		// do this operation as long as b is not start node!
		try{
		if (this.thePath.peek() != this.startNode) {
			Node s = thePath.pop(); // the node right Before b
			Node d = thePath.peek();// node before even that
			thePath.push(s); // you want to put it back
			// this gives you the bus line before the current bus line.
			// if these are different! then you did a bus change! Decrement bus
			// Change as you go back
			String pastBus = this.graph.getEdge(s, d).getBusLine();
			if (!(currentBus.equals(pastBus)))
				this.busChanges--;
		}
		}
		//if any time I do an illegal operation with the container, It means my
		//stack was empty all ready, and I can return false.
		catch(Exception ex){
			
		}
		return false;
	}

	// end of class
}
