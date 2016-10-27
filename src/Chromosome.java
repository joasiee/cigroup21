
public class Chromosome {
	
	private int[] order;
	private int routeSize;
	
	public Chromosome(int[] o, int r){
		this.order = o;
		this.routeSize = r;
	}
	
	public double getFitness(){
		return this.routeSize;
	}

}
