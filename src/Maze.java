import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Class that holds all the maze data. This means the pheromones, the open and
 * blocked tiles in the system as well as the starting and end coordinates.
 */
public class Maze {
	private int width;
	private int length;
	private int[][] walls;
	private double[][] pheromones;
	private Coordinate start;
	private Coordinate end;

	/**
	 * Constructor of a maze
	 * 
	 * @param walls
	 *            int array of tiles accessible (1) and non-accessible (0)
	 * @param width
	 *            width of Maze (horizontal)
	 * @param length
	 *            length of Maze (vertical)
	 */
	public Maze(int[][] walls, int width, int length) {
		this.walls = walls;
		this.length = length;
		this.width = width;
		this.pheromones = new double[this.length][this.width];
		initializePheromones();
	}

	/**
	 * Initialize pheromones to a start value.
	 */
	private void initializePheromones() {
		for (int i = 0; i < pheromones.length; i++) {
			for (int j = 0; j < pheromones[i].length; j++) {
				if(this.walls[i][j] == 1){
					pheromones[i][j] = 1;
				}
				else{
					pheromones[i][j] = 0;
				}
			}
		}
	}

	/**
	 * Reset the maze for a new shortest path problem.
	 */
	public void reset() {
		initializePheromones();
	}

	/**
	 * Update the pheromones along a certain route according to a certain Q
	 * 
	 * @param r
	 *            The route of the ants
	 * @param Q
	 *            Normalization factor for amount of dropped pheromone
	 */
	public void addPheromoneRoute(Route r, double Q) {
		int size = r.getRoute().size();
		double pheromoneToAdd = Q / size;
		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
		Coordinate curr = r.getStart();

		// Create iterator for whole route
		Iterator<Direction> routeIter = r.getRoute().iterator();
		// For every coordinate update the pheromone
		while (routeIter.hasNext()) {
			curr = curr.add((Direction) routeIter.next());
			if(!coords.contains(curr)){
				coords.add(curr);
			}
		}
		
		for(Coordinate c : coords){
			if(this.pheromones[c.getY()][c.getX()] < 500){
				this.pheromones[c.getY()][c.getX()] = this.pheromones[c.getY()][c.getX()] + pheromoneToAdd;
			}
		}

	}

	/**
	 * Update pheromones for a list of routes
	 * 
	 * @param routes
	 *            A list of routes
	 * @param Q
	 *            Normalization factor for amount of dropped pheromone
	 */
	public void addPheromoneRoutes(List<Route> routes, double Q) {
		for (Route r : routes) {
			addPheromoneRoute(r, Q);
		}
	}

	/**
	 * Evaporate pheromone
	 * 
	 * @param rho
	 *            evaporation factor
	 */
	public void evaporate(double rho) {
		for (int i = 0; i < pheromones.length; i++) {
			for (int j = 0; j < pheromones[i].length; j++) {
				pheromones[i][j] = pheromones[i][j] * (1 - rho);
			}
		}
	}

	/**
	 * Width getter
	 * 
	 * @return width of the maze
	 */
	public int getWidth() {
		return width;
	}
	
	public void writePheromones() throws IOException{
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < pheromones.length; i++)//for each row
		{
		   for(int j = 0; j < pheromones[i].length; j++)//for each column
		   {
			   BigDecimal bd = new BigDecimal(pheromones[i][j]);
			   bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		      builder.append("[" + bd.doubleValue() +"] ");//append to the output string
		      if(j < pheromones.length - 1)//if this is not the last row element
		         builder.append(",");//then add comma (if you don't like commas you can use spaces)
		   }
		   builder.append("\n");//append new line at the end of the row
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter("pheromones.txt"));
		writer.write(builder.toString());//save the string representation of the board
		writer.close();
	}

	/**
	 * Length getter
	 * 
	 * @return length of the maze
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Returns a the amount of pheromones on the neighbouring positions
	 * (N/S/E/W).
	 * 
	 * @param position
	 *            The position to check the neighbours of.
	 * @return the pheromones of the neighbouring positions.
	 */
	public SurroundingPheromone getSurroundingPheromone(Coordinate position) {
		if (inBounds(position)) {
			int posX = position.getX();
			int posY = position.getY();
			double north = 0;
			double east = 0;
			double south = 0;
			double west = 0;

			Coordinate n = new Coordinate(posX, posY - 1);
			Coordinate e = new Coordinate(posX + 1, posY);
			Coordinate s = new Coordinate(posX, posY + 1);
			Coordinate w = new Coordinate(posX - 1, posY);

			if (inBounds(n)) {
				north = this.pheromones[n.getY()][n.getX()];
			}
			if (inBounds(e)) {
				east = this.pheromones[e.getY()][e.getX()];
			}
			if (inBounds(s)) {
				south = this.pheromones[s.getY()][s.getX()];
			}
			if (inBounds(w)) {
				west = this.pheromones[w.getY()][w.getX()];
			}

			return (new SurroundingPheromone(north, east, south, west));
		}
		return (new SurroundingPheromone(0, 0, 0, 0));
	}

	/**
	 * Pheromone getter for a specific position. If the position is not in
	 * bounds returns 0
	 * 
	 * @param pos
	 *            Position coordinate
	 * @return pheromone at point
	 */
	private double getPheromone(Coordinate pos) {
		if (inBounds(pos)) {
			return (this.pheromones[pos.getY()][pos.getX()]);
		}
		return 0;
	}

	/**
	 * Check whether a coordinate lies in the current maze.
	 * 
	 * @param position
	 *            The position to be checked
	 * @return Whether the position is in the current maze
	 */
	private boolean inBounds(Coordinate position) {
		return position.xBetween(0, width) && position.yBetween(0, length);
	}
	
	/**
	 * Returns if the coordinate is accessible or not in the maze.
	 * @param pos position to check
	 * @return true or false
	 */
	public boolean coordAccessible(Coordinate pos){
		if(inBounds(pos)){
			return(this.walls[pos.getY()][pos.getX()] == 1);
		}
		return false;
	}

	/**
	 * Representation of Maze as defined by the input file format.
	 * 
	 * @return String representation
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(width);
		sb.append(' ');
		sb.append(length);
		sb.append(" \n");
		for (int y = 0; y < length; y++) {
			for (int x = 0; x < width; x++) {
				sb.append(walls[x][y]);
				sb.append(' ');
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * Method that builds a mze from a file
	 * 
	 * @param filePath
	 *            Path to the file
	 * @return A maze object with pheromones initialized to 0's inaccessible and
	 *         1's accessible.
	 */
	public static Maze createMaze(String filePath) throws FileNotFoundException {
		Scanner scan = new Scanner(new FileReader(filePath));
		int width = scan.nextInt();
		int length = scan.nextInt();
		int[][] mazeLayout = new int[length][width];
		for (int y = 0; y < length; y++) {
			for (int x = 0; x < width; x++) {
				mazeLayout[y][x] = scan.nextInt();
			}
		}
		scan.close();
		return new Maze(mazeLayout, width, length);
	}
}
