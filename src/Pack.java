import java.util.ArrayList;

public class Pack {
	ArrayList<Predator> predators;
	// construct array of fish
	Pack() {
		predators = new ArrayList<Predator>();
	}

	// call fish run method for each in array
	public int run() {
		int popSize = 0; 
		for (Predator p : predators) {
			p.run();
			popSize++;
		}
		return popSize;
	}

	// method to add fish to array
	public void addPredator(Predator p) {
		predators.add(p);
	}
}
