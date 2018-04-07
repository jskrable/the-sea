import java.util.ArrayList;

public class School {
	ArrayList<Guppy> guppies;
	
	// construct array of fish
	School() {
		guppies = new ArrayList<Guppy>();
	}
	
	// method to add guppies to array
	public void addGuppy(Guppy g) {
		guppies.add(g);
	}


	// call fish run method for each in array
	public int run(Predator p) {
		int popSize = 0; 
		for (Guppy g : guppies) {
			g.run(guppies, p);
			popSize++;
		}
		return popSize;
	}
}
