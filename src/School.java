import java.util.ArrayList;

public class School {
	ArrayList<Fish> fishes;
	// construct array of fish
	School() {
		fishes = new ArrayList<Fish>();
	}

	// call fish run method for each in array
	public int run(float aC, float cC, float sC, float fC, float pullDist, float desiredSep, 
			float scareDist) {
		int popSize = 0; 
		for (Fish f : fishes) {
			f.run(fishes, aC, cC, sC, fC, pullDist, desiredSep, scareDist);
			popSize++;
		}
		return popSize;
	}

	// method to add fish to array
	public void addFish(Fish f) {
		fishes.add(f);
	}
}
