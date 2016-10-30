import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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
    public Route findShortestRoute(PathSpecification spec, GUI gui) {
        maze.reset();
        //GUI gui = new GUI();
        Ant a;
        int bestSize = 0;
        ArrayList<Coordinate> bestRoute = null;
        for (int i = 0; i < generations; i++) {
			gui.updateGen(i);
        	ArrayList<ArrayList<Coordinate>> antRoutes = new ArrayList<ArrayList<Coordinate>>();
			for (int j = 0; j < antsPerGen; j++) {
				gui.updateAnt(j);
				a = new Ant(maze, spec);
				Route r = a.findRoute();
				ArrayList<Coordinate> coordinateRoute = r.removeLoops();
				antRoutes.add(coordinateRoute);
				if(coordinateRoute.size()<bestSize || bestSize == 0){
					bestRoute = coordinateRoute;
					bestSize = coordinateRoute.size();
					gui.updateSize(bestSize);
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
        return toDirRoute(bestRoute);
        /*a = new Ant(maze, spec);
        return a.findRoute();*/
    }

    private Route toDirRoute(ArrayList<Coordinate> bestRoute) {
		Route out = new Route(bestRoute.get(0));
		for (int i = 1; i < bestRoute.size(); i++) {
			Direction dir = null;
			
			if (bestRoute.get(i-1).getX() == bestRoute.get(i).getX()) {
				if (bestRoute.get(i-1).getY() < bestRoute.get(i).getY()) {
					dir = Direction.South;
				} else {
					dir = Direction.North;
				}
			} else if(bestRoute.get(i-1).getX() < bestRoute.get(i).getX()) {
				dir = Direction.East;
			} else {
				dir = Direction.West;
			}
			out.add(dir);
			/*if (i<40){
				System.out.println(dir);
				System.out.println(bestRoute.get(i).toString());
			}*/
		}
		return out;
	}

	/**
     * Driver function for Assignment 1
     */
    public static void main(String[] args) throws FileNotFoundException {
        //runOnce();
    	getOptimalParameters();
    }
    
    
    public static void runOnce() throws FileNotFoundException{
    	GUI gui = new GUI();
        int gen = 150;
        int noGen = 20;
        double Q = 1000;
        double evap = .05;
        Maze maze = Maze.createMaze("./data/medium maze.txt");
        PathSpecification spec = PathSpecification.readCoordinates("./data/medium coordinates.txt");
        AntColonyOptimization aco = new AntColonyOptimization(maze, gen, noGen, Q, evap);
        long startTime = System.currentTimeMillis();
        Route shortestRoute = aco.findShortestRoute(spec, gui);
        System.out.println("Time taken: " + ((System.currentTimeMillis() - startTime) / 1000.0));
        shortestRoute.writeToFile("./data/insane_solution.txt");
        System.out.println(shortestRoute.size());
    }
    
    public static void getOptimalParameters() throws FileNotFoundException{
    	int noGen =  5;
    	// int gen =  150;
    	// double Q = 1000;
    	//double evap = .05;
    	
    	double bestTime = -1;
    	int bestSize = -1;
    	
    	int bestGen = -1;
    	double bestQ = -1;
    	double bestEvap = -1;
    	
    	
    	
    	for(int gen = 20; gen<200; gen = gen + 20){
    		for(double Q = 200; Q<2000; Q = Q + 200){
    			for(double evap = 0.1; evap<0.5; evap = evap + 0.1){
    				GUI gui = new GUI();
    				Maze maze = Maze.createMaze("./data/medium maze.txt");
    		        PathSpecification spec = PathSpecification.readCoordinates("./data/medium coordinates.txt");
    		        AntColonyOptimization aco = new AntColonyOptimization(maze, gen, noGen, Q, evap);
    		        long startTime = System.currentTimeMillis();
    		        Route shortestRoute = aco.findShortestRoute(spec, gui);
    		        gui.close();

    		        double newTime = ((System.currentTimeMillis() - startTime) / 1000.0);
    		        int newSize = shortestRoute.size();
    		        if(newSize<bestSize || bestSize == -1){
    		        	bestTime = newTime;
    		        	bestSize = newSize;
    		        	bestGen = gen;
    		        	bestQ = Q;
    		        	bestEvap = evap;
    		        }
    		        
    			}
    		}
    	}
    	
    	System.out.println("Best Size: " + bestSize);
    	System.out.println("Corresponding Time: " + bestTime);
    	System.out.println("Parameters:"
    			+ "\nNumber of ants: " + bestGen
    			+ "\nNumber of steps: " + bestQ
    			+ "\nEvap constant: " + bestEvap);
    	
    	
    	
    	
    	
    	
    }
}
