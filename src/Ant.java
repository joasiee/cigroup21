import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
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
	 * 
	 * @param maze
	 *            Maze the ant will be running in.
	 * @param spec
	 *            The path specification consisting of a start coordinate and an
	 *            end coordinate.
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
	 * 
	 * @return The route the ant found through the maze.
	 */
	public Route findRoute() {
		Route route = new Route(start);
		Direction prevDir = null;
		currentPosition = start;
		while (currentPosition != end) {
			SurroundingPheromone sp = maze.getSurroundingPheromone(currentPosition);
			double total = 0;
			double northOdds = 1;
			double eastOdds = 1;
			double southOdds = 1;
			double westOdds = 1;
			
			total = sp.getTotalSurroundingPheromone();
			northOdds = sp.get(Direction.North) / total;
			eastOdds = sp.get(Direction.East) / total;
			southOdds = sp.get(Direction.South) / total;
			westOdds = sp.get(Direction.West) / total;
			
			if(prevDir != null){
				if(Direction.dirToInt(prevDir) == 1){
					southOdds = 0;
				}
				if(Direction.dirToInt(prevDir) == 0){
					westOdds = 0;
				}
				if(Direction.dirToInt(prevDir) == 3){
					northOdds = 0;
				}
				if(Direction.dirToInt(prevDir) == 2){
					eastOdds = 0;
				}
			}

			double r = rand.nextDouble();
			while(!((r<northOdds) | (r<eastOdds) | (r<southOdds) | (r<westOdds))){
				r = rand.nextDouble() * total;
			}
			
			if(r<northOdds){
				prevDir = Direction.North;
			}
			else if(r<eastOdds){
				prevDir = Direction.East;
			}
			else if(r<southOdds){
				prevDir = Direction.South;
			}
			else{
				prevDir = Direction.West;
			}
			
			route.add(prevDir);
			currentPosition = currentPosition.add(prevDir);
		}
		return route;
	}
}
