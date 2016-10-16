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
		Odds northOdds = new Odds(1,Direction.North);
		Odds eastOdds = new Odds(1,Direction.East);
		Odds southOdds = new Odds(1,Direction.South);
		Odds westOdds = new Odds(1,Direction.West);
		currentPosition = start;
		SurroundingPheromone sp;
		double total = 0;
		
		while (!currentPosition.equals(end)) {
			
			sp = maze.getSurroundingPheromone(currentPosition);
			
			if (prevDir != null) {
				if (Direction.dirToInt(prevDir) == 1) {
					southOdds.oddValue = 0;
					sp.setSouth(0);
				}
				else if (Direction.dirToInt(prevDir) == 0) {
					westOdds.oddValue = 0;
					sp.setWest(0);
				}
				else if (Direction.dirToInt(prevDir) == 3) {
					northOdds.oddValue = 0;
					sp.setNorth(0);
				}
				else {
					eastOdds.oddValue = 0;
					sp.setEast(0);
				}
			}
			
			total = sp.getTotalSurroundingPheromone();
			northOdds.oddValue = sp.get(Direction.North) / total;
			eastOdds.oddValue = sp.get(Direction.East) / total;
			southOdds.oddValue = sp.get(Direction.South) / total;
			westOdds.oddValue = sp.get(Direction.West) / total;
			
			if(total != 0){
				double r = rand.nextDouble();
				while (!((r < northOdds.oddValue) | (r < eastOdds.oddValue) | (r < southOdds.oddValue) | (r < westOdds.oddValue))) {
					r = rand.nextDouble();
				}

				if (r < northOdds.oddValue) {
					prevDir = Direction.North;
					northOdds.active = true;
				} if (r < eastOdds.oddValue) {
					prevDir = Direction.East;
					eastOdds.active = true;
				} if (r < southOdds.oddValue) {
					prevDir = Direction.South;
					southOdds.active = true;
				} if (r < westOdds.oddValue) {
					prevDir = Direction.West;
					westOdds.active = true;
				}
				
				ArrayList<Odds> odds = new ArrayList<Odds>();
				odds.add(northOdds);
				odds.add(eastOdds);
				odds.add(southOdds);
				odds.add(westOdds);
				
				prevDir = Odds.randomActiveOdd(odds).getDirection();
			}
			else{
				prevDir = prevDir.reverseDirection();
			}
			
			

			route.add(prevDir);
			currentPosition = currentPosition.add(prevDir);
		}
		return route;
	}
}
