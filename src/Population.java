import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class Population {

	protected ArrayList<Chromosome> chromosomes;
	
	public Population(){
		this.chromosomes = new ArrayList<Chromosome>();
	}
	
	public void addChromosome(Chromosome c){
		this.chromosomes.add(c);
	}
	
	public Population findNextPopulation(int popSize){
		Population res = new Population();
		while(res.chromosomes.size() < popSize){
			Chromosome currChromosomeA = rouletteWheelSelect();
			Chromosome currChromosomeB = rouletteWheelSelect();
			double rand = ThreadLocalRandom.current().nextDouble(0, 1 + 1);
			
			//70% chance to crossover
			if(rand < 0.70){
				Stack<Chromosome> crossedChromosomes = crossoverChromosomes(currChromosomeA,currChromosomeB);
				res.addChromosome(crossedChromosomes.pop());
				res.addChromosome(crossedChromosomes.pop());
			} else{
				res.addChromosome(currChromosomeA);
				res.addChromosome(currChromosomeB);
			}
		}
		
		/*for(Chromosome c : res.chromosomes){
			double rand = ThreadLocalRandom.current().nextDouble(0, 1 + 1);
			if(rand < 0.08){
				c.mutateChromosome();
			}
		}*/
		
		return res;
	}
	
	public Chromosome rouletteWheelSelect(){
		float totalScore = 0;
	    float runningScore = 0;
	    for (Chromosome c : this.chromosomes)
	    {
	        totalScore += c.getFitness();
	    }

	    float rnd = (float) (Math.random() * totalScore);

	    for (Chromosome c : this.chromosomes)
	    {   
	        if (    rnd>=runningScore &&
	                rnd<=runningScore+c.getFitness())
	        {
	            return c;
	        }
	        runningScore+=c.getFitness();
	    }

	    return null;
	}
	
	private void shuffle(int[] chromosome) {
		int n = chromosome.length;
		for (int i = 0; i < n; i++) {
			int r = i + (int) (Math.random() * (n - i));
			int swap = chromosome[r];
			chromosome[r] = chromosome[i];
			chromosome[i] = swap;
		}
	}
	
	public Stack<Chromosome> crossoverChromosomes(Chromosome c1, Chromosome c2){
		int rand = ThreadLocalRandom.current().nextInt(1, c1.getLength());
		int c1Order[] = c1.getOrder();
		int c2Order[] = c2.getOrder();
		
		int[] sliceC1a = Arrays.copyOfRange(c1Order, 0, rand);
		int[] sliceC1b = Arrays.copyOfRange(c1Order, rand, c1.getLength());
		int[] sliceC2a = Arrays.copyOfRange(c2Order, 0, rand);
		int[] sliceC2b = Arrays.copyOfRange(c2Order, rand, c2.getLength());
		
		double rand2 = ThreadLocalRandom.current().nextDouble(1, 1 + 1);
		if(rand2<0.50){
			shuffle(sliceC1a);
			shuffle(sliceC2a);
		}else{
			shuffle(sliceC1b);
			shuffle(sliceC2b);
		}
		
		c1Order = concat(sliceC1a,sliceC1b);
		c2Order = concat(sliceC2a,sliceC2b);
		
		Stack<Chromosome> res = new Stack<Chromosome>();
		Chromosome res1 = new Chromosome(c1Order);
		Chromosome res2 = new Chromosome(c2Order);
		
		res.push(res1);
		res.push(res2);
		
		return res;
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
		double bestSize = this.chromosomes.get(0).getFitness();
		Stack<Chromosome> bestC = new Stack<Chromosome>();
		for(Chromosome c : this.chromosomes){
			if(c.getFitness() < bestSize){
				bestC.push(c);
			}
		}
		if(bestC.size() > 0){
			return bestC.pop();
		}else{
			return this.chromosomes.get(0);
		}
	}
}
