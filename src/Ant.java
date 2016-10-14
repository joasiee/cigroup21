import java.util.EnumMap;
import java.util.Random;

/**
 * Class that represents the ants functionality.
 */
public class Ant {
    private Maze maze;
    private Coordinate start;
    private Coordinate end;
    private Coordinate currentPosition;
    private static Random rand;

    /**
     * Constructor for ant taking a Maze and PathSpecification.
     * @param maze Maze the ant will be running in.
     * @param spec The path specification consisting of a start coordinate and an end coordinate.
     */
    public Ant(Maze maze, PathSpecification spec) {
        this.maze = maze;
        this.start = spec.getStart();
        this.end = spec.getEnd();
        this.currentPosition = start;
        if (rand == null) {
            rand = new Random();
        }
    }

    /**
     * Method that performs a single run through the maze by the ant.
     * @return The route the ant found through the maze.
     */
    public Route findRoute() {
        Route route = new Route(start);
        Direction prevDir = null;
        currentPosition = start;
        while (currentPosition != end){
        	SurroundingPheromone sp = maze.getSurroundingPheromone(currentPosition);
        	double total = 0;
        	double northOdds = 1;
        	double eastOdds = 1;
        	double southOdds = 1;
        	double westOdds = 1;
        	if (prevDir == null){
            	total = sp.getTotalSurroundingPheromone();
            	northOdds = sp.get(Direction.North);
            	eastOdds = northOdds + sp.get(Direction.East);
            	southOdds = eastOdds + sp.get(Direction.South);
            	westOdds = southOdds + sp.get(Direction.West);
        	} else if (prevDir == Direction.North){
            	total = sp.getTotalSurroundingPheromone() - sp.get(Direction.North);
            	eastOdds = sp.get(Direction.East);
            	southOdds = eastOdds + sp.get(Direction.South);
            	westOdds = southOdds + sp.get(Direction.West);
        	} else if (prevDir == Direction.East){
            	total = sp.getTotalSurroundingPheromone() - sp.get(Direction.East);
            	northOdds = sp.get(Direction.North);
            	southOdds = northOdds + sp.get(Direction.South);
            	westOdds = southOdds + sp.get(Direction.West);
        	} else if (prevDir == Direction.South){
            	total = sp.getTotalSurroundingPheromone() - sp.get(Direction.South);
            	northOdds = sp.get(Direction.North);
            	eastOdds = northOdds + sp.get(Direction.East);
            	westOdds = eastOdds + sp.get(Direction.West);
        	} else if (prevDir == Direction.West){
            	total = sp.getTotalSurroundingPheromone() - sp.get(Direction.West);
            	northOdds = sp.get(Direction.North);
            	eastOdds = northOdds + sp.get(Direction.East);
            	southOdds = eastOdds + sp.get(Direction.South);
        	}
        	double r = rand.nextDouble() * total;
        	if (r > westOdds){
        		prevDir = Direction.West;
        	} else if (r > southOdds){
        		prevDir = Direction.South;
        	} else if (r > eastOdds){
        		prevDir = Direction.East;
        	} else {
        		prevDir = Direction.North;
        	}
    		route.add(prevDir);
    		currentPosition = currentPosition.add(prevDir);
        }
        return route;
    }
}

