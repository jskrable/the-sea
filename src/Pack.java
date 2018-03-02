import java.util.ArrayList;

public class Pack {
	ArrayList<Predator> preds;
	// construct array of fish
	Pack() {
		preds = new ArrayList<Predator>();
	}

	// call fish run method for each in array
	public int run(School s, int sniffDist) {
		int popSize = 0; 
		for (Predator p : preds) {
			p.run(s, sniffDist);
			popSize++;
		}
		return popSize;
	}

	// method to add guppies to array
	public void addPredator(Predator p) {
		preds.add(p);
	}
}
