import java.util.ArrayList;

public class Population {

	private ArrayList<Chromosome> chromosomes;
	
	public Population(){
		this.chromosomes = new ArrayList<Chromosome>();
	}
	
	public void addChromosome(Chromosome c){
		this.chromosomes.add(c);
	}
	
	public Population findNextPopulation(){
		
		return null;
	}
}
