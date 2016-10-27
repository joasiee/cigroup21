import java.util.concurrent.ThreadLocalRandom;

public class Chromosome {
	
	private int[] order;
	private double routeSize;
	private double fitness;
	
	public Chromosome(int[] o){
		this.order = o;
		this.routeSize = 0;
	}
	
	public double getFitness(){
		return this.fitness;
	}
	
	public void mutateChromosome(){
		int rand = ThreadLocalRandom.current().nextInt(0, this.getLength());
		int rand2 = ThreadLocalRandom.current().nextInt(1, this.getLength() + 1);
		
		this.setOrderIndex(rand, rand2);
	}
	
	public int getLength(){
		return order.length;
	}
	
	public int[] getOrder(){
		return this.order;
	}
	
	public void setRouteSize(double s){
		this.routeSize = s;
		this.fitness = 10000 / s;
	}
	
	public void setOrderIndex(int i, int y){
		this.order[i] = y;
	}

}
