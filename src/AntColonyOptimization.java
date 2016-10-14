import java.io.FileNotFoundException;
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
        int bestSize = 0;
        Stack<Route> routes = new Stack<Route>();
        for (int i = 0; i < generations; i++) {
			for (int j = 0; j < antsPerGen; j++) {
				Ant a = new Ant(maze, spec);
				Route r = a.findRoute();
				maze.addPheromoneRoute(r, Q);
				if(r.getRoute().size()<bestSize | bestSize == 0){
					routes.push(r);
					bestSize = r.getRoute().size();
				}
			}
			maze.evaporate(evaporation);
		}
        return routes.pop();
    }

    /**
     * Driver function for Assignment 1
     */
    public static void main(String[] args) throws FileNotFoundException {
        int gen = 30;
        int noGen = 15;
        double Q = 1;
        double evap = 0.1;
        Maze maze = Maze.createMaze("./data/easy maze.txt");
        PathSpecification spec = PathSpecification.readCoordinates("./data/easy coordinates.txt");
        AntColonyOptimization aco = new AntColonyOptimization(maze, gen, noGen, Q, evap);
        long startTime = System.currentTimeMillis();
        Route shortestRoute = aco.findShortestRoute(spec);
        System.out.println("Time taken: " + ((System.currentTimeMillis() - startTime) / 1000.0));
        shortestRoute.writeToFile("./data/easy_solution.txt");
        System.out.println(shortestRoute.size());
    }
}
