import java.util.ArrayList;
import processing.core.PApplet;

public class Population extends School {
	
	// initialize variables
	ArrayList<Guppy> matingPool;
	ArrayList<Guppy> offspring;
	PApplet parent;
	
	// evaluate a population of guppies
	public void eval(Predator p) {
		float maxFitness = 0;
		for (Guppy g : guppies) {
			g.getFitness(p);
			if (g.fitness > 0) {
				maxFitness = g.fitness;
			}
		}
		
		for (Guppy g : guppies) {
			if (maxFitness != 0) {
				g.fitness /= maxFitness;
			}
		}
		
		for (Guppy g : guppies) {
			if (g.fitness > 0) {
				float n = g.fitness * 100;
				for (int i = 0; i < n; i++) {
					this.matingPool.add(g);
				}
			}
		}
	}
	
	// create a new generation of guppies
	public void naturalSelection() {
		// iterate thru mating pool
		for (Guppy m : matingPool) {
			// get a random guppy
			int randomMate = (int)(Math.random()*matingPool.size());
			Guppy parent1 = this.matingPool.get(randomMate);
			// find a mate
			randomMate = (int)(Math.random()*matingPool.size());
			Guppy parent2 = this.matingPool.get(randomMate);
			// create a new child guppy
			Guppy child = new Guppy(parent, 0, 0, parent1.mating(parent2));
			child.mutation();
			// add to offspring pool
			offspring.add(child);
		}
		// replace current population with new one
		this.guppies = offspring;
	}
}
