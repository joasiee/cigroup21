import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Chromosome {
	
	private int[] order;
	private int routeSize;
	private double fitness;
	
	public Chromosome(int[] o){
		this.order = o;
		this.routeSize = 0;
	}
	
	@Override
	public boolean equals(Object o) {
	    // self check
	    if (this == o)
	        return true;
	    // null check
	    if (o == null)
	        return false;
	    // type check and cast
	    if (getClass() != o.getClass())
	        return false;
	    Chromosome c = (Chromosome) o;
	    // field comparison
	    return Objects.equals(routeSize, c.routeSize)
	            && Objects.equals(fitness, c.fitness)
	            && Arrays.equals(order, c.order);
	}
	
	public void scrambleMutation(){
		int[] array = this.getOrder().clone();
		int l = array.length;
		for(int k = 0; k < 5; k++){
			int r1 = ThreadLocalRandom.current().nextInt(0, l);
			int r2 = ThreadLocalRandom.current().nextInt(0, l);
			
			while(r1 >= r2) {
				r1 = ThreadLocalRandom.current().nextInt(0, l);
				r2 = ThreadLocalRandom.current().nextInt(0, l);
			}
			
			for(int i = 0; i < 12; i++){
				int i1 = ThreadLocalRandom.current().nextInt(r1, r2+1);
				int i2 = ThreadLocalRandom.current().nextInt(r1, r2+1);
				int a = array[i1];
				array[i1] = array[i2];
				array[i2] = a;
			}
			
		}
		this.setOrder(array);
	}
	
	public double getFitness(){
		return this.fitness;
	}
	
	public void setOrder(int[] o){
		this.order = o;
	}
	
	public int getLength(){
		return order.length;
	}
	
	public int[] getOrder(){
		return this.order;
	}
	
	public void setRouteSize(int s){
		this.routeSize = s;
		this.fitness = 15000 / s;
	}
	
	public int getRouteSize(){
		return this.routeSize;
	}
	
	public void setOrderIndex(int i, int y){
		this.order[i] = y;
	}

}
