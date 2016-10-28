import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class Population {

	protected ArrayList<Chromosome> chromosomes;
	private static double mutationRate = 0.05;
	private static double crossoverRate = 0.8;
	
	public Population(){
		this.chromosomes = new ArrayList<Chromosome>();
	}
	
	public void addChromosome(Chromosome c){
		this.chromosomes.add(c);
	}
	
	public Population findNextPopulation(int popSize){
		Population res = new Population();
		Chromosome a;
		Chromosome b;
		Chromosome winner;
		Chromosome loser;
		while(res.chromosomes.size() < popSize){
			do {a = rouletteWheelSelect(); b = rouletteWheelSelect();} while(a.equals(b));
			double rand = ThreadLocalRandom.current().nextDouble(0, 1 + 1);
			
			if (a.getFitness() > b.getFitness()){
				winner = a;
				loser = b;
			}
			else{
				winner = b;
				loser = a;
			}
			
			Chromosome temp = winner;
			
			//70% chance to crossover
			if(rand < crossoverRate){
				temp = crossoverChromosomes(winner,loser);
				
			}
			res.addChromosome(temp);
		}
		
		for(Chromosome c : res.chromosomes){
			double rand = ThreadLocalRandom.current().nextDouble(0, 1 + 1);
			if(rand < mutationRate){
				c.scrambleMutation();
			}
		}
		
		return res;
	}
	
	public Chromosome rouletteWheelSelect(){
		int totalSum = 0;
		for(Chromosome c : this.chromosomes){
			totalSum += c.getFitness();
		}
		int rand = ThreadLocalRandom.current().nextInt(0, totalSum + 1);
		int partialSum = 0;
		for(Chromosome c: this.chromosomes){
			partialSum += c.getFitness();
			if(partialSum >= rand){
				return c;
			}
		}
		return null;
	}
	
	public Chromosome crossoverChromosomes(Chromosome c1, Chromosome c2){
		int l = c1.getLength();
		int[] c1Order = c1.getOrder();
		int[] c2Order = c2.getOrder();
		int rand1 = ThreadLocalRandom.current().nextInt(0, l);
		int rand2 = ThreadLocalRandom.current().nextInt(0, l);
		
		while(rand1 >= rand2) {
			rand1 = ThreadLocalRandom.current().nextInt(0, l);
			rand2 = ThreadLocalRandom.current().nextInt(0, l);
		}
		
		int[] child = new int[l];
		for(int i = 0; i<l; i++){
			child[i] = -1;
		}
		
		for(int i = rand1; i<= rand2; i++){
			child[i] = c1Order[i];
		}
		
		int[] y = new int[l-(rand2-rand1)-1];
		int j = 0;
		for(int i = 0; i < l; i++){
			if(!arrayContains(child,c1Order[i])){
				y[j] = c1Order[i];
				j++;
			}
		}
		
		int[] copy = c2Order.clone();
		rotate(copy, l-rand2-1);
		
		int[] y1 = new int[l-(rand2-rand1)-1];
		j=0;
		for(int i = 0; i< l; i++){
			if(arrayContains(y,copy[i])){
				y1[j] = copy[i];
				j++;
			}
		}
		
		j = 0;
		for(int i = 0; i < y1.length; i++){
			int ci = (rand2 + i + 1) % l;
			child[ci] = y1[i];
		}
		
		Chromosome childC = new Chromosome(child);
		
		return childC;
	}
	
	public static boolean arrayContains(int[] arr, int targetValue) {
		for(int s: arr){
			if(s == targetValue){
				return true;
			}
		}
		return false;
	}
	
	public static void rotate(int[] arr, int order) {
		int offset = arr.length - order % arr.length;
		if (offset > 0) {
			int[] copy = arr.clone();
			for(int i = 0; i < arr.length; i++){
				int j = (i + offset) % arr.length;
				arr[i] = copy[j];
			}
		}
	}
	
	public int[] concat(int[] a, int[] b) {
		   int aLen = a.length;
		   int bLen = b.length;
		   int[] c= new int[aLen+bLen];
		   System.arraycopy(a, 0, c, 0, aLen);
		   System.arraycopy(b, 0, c, aLen, bLen);
		   return c;
		}
	
	public Chromosome returnBest(){
		Chromosome bestC = this.chromosomes.get(0);
		for(Chromosome c : this.chromosomes){
			if(c.getFitness() > bestC.getFitness()){
				bestC = c;
			}
		}
		return bestC;
	}
}
