/**
 * Class containing the pheromone information around a certain point in the maze
 */
public class SurroundingPheromone {
    private double north;
    private double south;
    private double east;
    private double west;

    public SurroundingPheromone(double north, double east, double south, double west) {
        this.north = north;
        this.south = south;
        this.west = west;
        this.east = east;
    }

    public double getTotalSurroundingPheromone() {
        return (this.north + this.south + this.west + this.east);
    }

    /**
     * Get a specific pheromone level
     * @param dir Direction of pheromone
     * @return Pheromone of dir
     */
    public double get(Direction dir) {
        switch (dir) {
            case North:
                return north;
            case East:
                return east;
            case West:
                return west;
            case South:
                return south;
        }
        throw new IllegalArgumentException("Invalid direction");
    }
    
    public void setNorth(double nw){
    	this.north = nw;
    }
    public void setEast(double nw){
    	this.east = nw;
    }
    public void setSouth(double nw){
    	this.south = nw;
    }
    public void setWest(double nw){
    	this.west = nw;
    }
}
