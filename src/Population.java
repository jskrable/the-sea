import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.data.JSONObject;

public class Population {
	
	// initialize variables
	ArrayList<Guppy> guppies;
	ArrayList<Guppy> matingPool;
	ArrayList<Guppy> offspring;
	PApplet parent;
	
	
	// construct array of fish
	Population() {
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
					matingPool.add(g);
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
	
	public void writeDataFile(String filename) {
		
		// init JSON object
		JSONObject json = new JSONObject();
		
		// add fitness scores to file
		for (Guppy g : guppies) {
			json.put("Fitness", g.fitness);
		}
		String filepath = "/Users/jskra/Documents/" + filename;
		
		// write file
		try (FileWriter file = new FileWriter(filepath)) {
			file.write(json.toString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + json);
		} catch (IOException e) {
			// auto-generated catch block
			e.printStackTrace();
		}
	}
}
