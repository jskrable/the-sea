import java.util.ArrayList;

public class School {
	ArrayList<Guppy> guppies;
	// construct array of fish
	School() {
		guppies = new ArrayList<Guppy>();
	}

	// call fish run method for each in array
	public int run(float aC, float cC, float sC, float fC, int pullDist, int desiredSep, 
			int scareDist, Pack p) {
		int popSize = 0; 
		for (Guppy g : guppies) {
			g.run(guppies, aC, cC, sC, fC, pullDist, desiredSep, scareDist, p);
			popSize++;
		}
		return popSize;
	}

	// method to add guppies to array
	public void addGuppy(Guppy g) {
		guppies.add(g);
	}
}
