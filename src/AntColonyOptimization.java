import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Class representing the first assignment. Finds shortest path between two points in a maze according to a specific
 * path specification.
 */
public class AntColonyOptimization {
    private int antsPerGen;
    private int generations;
    private double Q;
    private double evaporation;
    private Maze maze;

    public AntColonyOptimization(Maze maze, int antsPerGen, int generations, double Q, double evaporation) {
        this.maze = maze;
        this.antsPerGen = antsPerGen;
        this.generations = generations;
        this.Q = Q;
        this.evaporation = evaporation;
    }

    /**
     * Loop that starts the shortest path process
     * @param spec Spefication of the route we wish to optimize
     * @return ACO optimized route
     */
    public Route findShortestRoute(PathSpecification spec) {
        maze.reset();
        Ant a;
        int bestSize = 0;
        Stack<Route> routes = new Stack<Route>();
        for (int i = 0; i < generations; i++) {
        	System.out.println("Gen#: " + i);
        	ArrayList<ArrayList<Coordinate>> antRoutes = new ArrayList<ArrayList<Coordinate>>();
			for (int j = 0; j < antsPerGen; j++) {
	        	System.out.println("Ant#: " + j);
				a = new Ant(maze, spec);
				Route r = a.findRoute();
				ArrayList<Coordinate> coordinateRoute = r.removeLoops();
				antRoutes.add(coordinateRoute);
				if(r.getRoute().size()<bestSize | bestSize == 0){
					routes.push(r);
					bestSize = r.getRoute().size();
				}
			}
			//System.out.println("evap");
			maze.evaporate(evaporation);
			//System.out.println("add Pheromone routes");
			maze.addPheromoneRoutes(antRoutes, Q);

		}
        try {
			maze.writePheromones();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return routes.pop();
        /*a = new Ant(maze, spec);
        return a.findRoute();*/
    }

    /**
     * Driver function for Assignment 1
     */
    public static void main(String[] args) throws FileNotFoundException {
        int gen = 50;
        int noGen = 400;
        double Q = 150;
        double evap = 0.3;
        Maze maze = Maze.createMaze("./data/medium maze.txt");
        PathSpecification spec = PathSpecification.readCoordinates("./data/medium coordinates.txt");
        AntColonyOptimization aco = new AntColonyOptimization(maze, gen, noGen, Q, evap);
        long startTime = System.currentTimeMillis();
        Route shortestRoute = aco.findShortestRoute(spec);
        System.out.println("Time taken: " + ((System.currentTimeMillis() - startTime) / 1000.0));
        shortestRoute.writeToFile("./data/medium_solution.txt");
        System.out.println(shortestRoute.size());
    }
}
