import java.util.ArrayList;
import java.util.Random;

public class Odds {
	
	protected double oddValue;
	protected Direction dir;
	protected boolean active;
	
	public Odds(double val, Direction dir){
		this.oddValue = val;
		this.dir = dir;
		this.active = false;
	}
	
	public double getValue(){
		return this.oddValue;
	}
	
	public Direction getDirection(){
		return this.dir;
	}
	
	public static Odds randomActiveOdd(ArrayList<Odds> odds){
		
		ArrayList<Odds> res = new ArrayList<Odds>();
		
		for(Odds odd : odds){
			if (odd.active == true){
				res.add(odd);
			}
		}
		
		Random rand = new Random();
		int index = rand.nextInt(res.size());
		
		return res.get(index);
		
	}

}
