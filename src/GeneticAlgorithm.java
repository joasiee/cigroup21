import java.io.IOException;
import java.util.ArrayList;

/**
 * TSP problem solver using genetic algorithms
 */
public class GeneticAlgorithm {

	private int generations;
	private int popSize;
	private ArrayList<int[]> chromosomes;

	public GeneticAlgorithm(int generations, int popSize) {
		this.generations = generations;
		this.popSize = popSize;
		chromosomes = new ArrayList<int[]>();
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

	public int[] solveTSP(TSPData pd) {

		// initial population creation using shuffler method
		int[] firstPop = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 };
		shuffle(firstPop);
		for (int n = 0; n < this.popSize; n++) {
			shuffle(firstPop);
			int[] temp = firstPop.clone();
			this.chromosomes.add(temp);
		}

		int[] currChromosome;
		int totalRouteSize;
		Population pop = new Population();

		// genetic algorithm
		for (int i = 0; i < this.generations; i++) {
			for (int j = 0; j < this.popSize; j++) {
				
				totalRouteSize = 0;

				currChromosome = this.chromosomes.get(j);
				int currProduct = currChromosome[0];
				totalRouteSize += pd.getStartDistances()[currProduct - 1];

				for (int x = 1; x < currChromosome.length; x++) {
					int nextProduct = currChromosome[x];
					totalRouteSize += pd.getDistances()[currProduct - 1][nextProduct - 1];
					currProduct = nextProduct;
				}

				totalRouteSize += pd.getEndDistances()[currProduct - 1];
				Chromosome toAdd = new Chromosome(currChromosome, totalRouteSize);
				pop.addChromosome(toAdd);
			}
		}
		return null;
	}

	/**
	 * Assignment 2.b
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		int populationSize = 20;
		int generations = 20;
		String persistFile = "./tmp/productMatrixDist";
		TSPData tspData = TSPData.readFromFile(persistFile);
		GeneticAlgorithm ga = new GeneticAlgorithm(generations, populationSize);
		int[] solution = ga.solveTSP(tspData);
		tspData.writeActionFile(solution, "./data/TSP solution.txt");
	}
}
