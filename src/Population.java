import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class Population {
	
	// initialize variables
	ArrayList<Guppy> guppies;
	ArrayList<Guppy> matingPool;
	ArrayList<Guppy> offspring;
	PApplet parent;
	
	
	// construct array of fish
	public Population(PApplet p) {
		guppies = new ArrayList<Guppy>();
		parent = p;
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
		// init mating pool
		matingPool = new ArrayList<Guppy>();
		// init max fitness for normalization
		float maxFitness = 0;
		// loop thru guppies and get fitness score
		for (Guppy g : guppies) {
			g.getFitness(guppies, p);
			// make sure max is highest
			if (g.fitness > maxFitness) {
				maxFitness = g.fitness;
			}
		}
		// loop thru guppies and normalize by max
		for (Guppy g : guppies) {
			if (maxFitness != 0) {
				g.fitness /= maxFitness;
			}
		}
		// loop thru guppies and add to mating pool
		for (Guppy g : guppies) {
			if (g.fitness > 0) {
				// higher fitness scores get more entries in mating pool
				int n = Math.round(g.fitness * 100);
				for (int i=1; i<n; i++) {
					matingPool.add(g);
				}
			}
		}
	}
	
	// create a new generation of guppies
	public void naturalSelection(int popsize) {
		// init offspring list
		offspring = new ArrayList<Guppy>();
		// iterate thru population
		int n = popsize;
		// use standard for loop to avoid concurrent modification
		for (int i=0; i < n; i++) {
			// get a random guppy from mating pool
			int randomMate = (int)(Math.random()*matingPool.size());
			Guppy parent1 = this.matingPool.get(randomMate);
			// find a mate
			randomMate = (int)(Math.random()*matingPool.size());
			Guppy parent2 = this.matingPool.get(randomMate);
			// create a new child guppy
			DNA childDNA = parent1.mating(parent2);
			Guppy child = new Guppy(parent, 0, 0, childDNA);
			child.mutation();
			// add to offspring pool
			offspring.add(child);
		} 
		// replace current population with new one
		this.guppies.clear();
		this.guppies.addAll(offspring);
	}
	
	public void writeDataFile(String filepath) {
		
		// init JSON object
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject entry = new JSONObject();
		
		// add fitness scores to file
		for (Guppy g : guppies) {
			entry.put("fitness", g.fitness);
			entry.put("dna", g.genes);
			array.append(entry);
		}
		json.put("records", array);
		
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
