import java.io.IOException;
import java.util.Stack;

/**
 * TSP problem solver using genetic algorithms
 */
public class GeneticAlgorithm {

	private int generations;
	private int popSize;

	public GeneticAlgorithm(int generations, int popSize) {
		this.generations = generations;
		this.popSize = popSize;
	}

	/**
	 * Knuth-Yates shuffle, reordering a array randomly
	 * 
	 * @param chromosome
	 *            array to shuffle.
	 */
	private void shuffle(int[] chromosome) {
		int n = chromosome.length;
		for (int i = 0; i < n; i++) {
			int r = i + (int) (Math.random() * (n - i));
			int swap = chromosome[r];
			chromosome[r] = chromosome[i];
			chromosome[i] = swap;
		}
	}

	public int[] solveTSP(TSPData pd,GUI gui) {	
		Population pop = new Population();

		// initial population creation using shuffler method
		int[] firstOrder = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 };
		for (int n = 0; n < this.popSize; n++) {
			shuffle(firstOrder);
			int[] temp = firstOrder.clone();
			Chromosome tempC = new Chromosome(temp);
			pop.addChromosome(tempC);
		}

		int[] currChromosomeOrder;
		int totalRouteSize;
		int bestSize = 9999999;
		Chromosome bestCurrentChromosome = new Chromosome(new int[2]);
		bestCurrentChromosome.setRouteSize(9999999);

		// genetic algorithm
		for (int i = 0; i < this.generations; i++) {
			gui.updategenG(i);
			for (int j = 0; j < this.popSize; j++) {
				gui.updategenC(j);
				
				totalRouteSize = 0;

				currChromosomeOrder = pop.chromosomes.get(j).getOrder();
				int currProduct = currChromosomeOrder[0];
				totalRouteSize += pd.getStartDistances()[currProduct - 1];

				for (int x = 1; x < currChromosomeOrder.length; x++) {
					int nextProduct = currChromosomeOrder[x];
					totalRouteSize += pd.getDistances()[currProduct - 1][nextProduct - 1];
					currProduct = nextProduct;
				}

				totalRouteSize += pd.getEndDistances()[currProduct - 1];
				pop.chromosomes.get(j).setRouteSize(totalRouteSize);
			}
			Chromosome tempFittest = pop.returnBest();
			if(tempFittest.getFitness() > bestCurrentChromosome.getFitness()){
				bestCurrentChromosome = tempFittest;
				bestSize = bestCurrentChromosome.getRouteSize();
				gui.updateSize(bestSize);
			}
			if(i != this.generations - 1){
				pop = pop.findNextPopulation(this.popSize);
			}
		}
		
		//return pop.returnBest().getOrder();
		
		return bestCurrentChromosome.getOrder();
	}

	/**
	 * Assignment 2.b
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		GUI gui = new GUI();
		int populationSize = 400;
		int generations = 3000;
		String persistFile = "./tmp/productMatrixDist";
		TSPData tspData = TSPData.readFromFile(persistFile);
		GeneticAlgorithm ga = new GeneticAlgorithm(generations, populationSize);
		int[] solution = ga.solveTSP(tspData,gui);
		tspData.writeActionFile(solution, "./data/TSP solution.txt");
		System.out.println("Finished!");
	}
}
