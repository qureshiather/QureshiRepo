import java.util.*;
import java.io.*;

public class Solve {

	public static void main(String[] args) {
		Node u = null, v;
		DrawMap display;
		int delay = 0;
		if (args.length == 2)
			delay = Integer.parseInt(args[1]); // Delay between
		// drawing edges
		display = new DrawMap(args[0]); // Draw the map
		try {
			Map streetMap = new Map("src/"+ args[0]);
			//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			//System.out.println("Press a key to continue");
			//String line = in.readLine();
			//if (line.length() != 0)
			display.drawRoads(); // Re-draw the map

			/* Here is where the solution is computed */
			Iterator<Node> solution = streetMap.findPath();

			// Display the solution
			if (solution.hasNext())
				u = (Node) solution.next();
				while (solution.hasNext()) {
					v = (Node) solution.next();
					Thread.sleep(delay);
					display.drawEdge(u, v);
					u = v;
				}
		} catch (MapException e) {
			System.out.println("Error reading input file");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		try {
		    Thread.sleep(10000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		//display.dispose();
		//System.exit(0);
	}
}

