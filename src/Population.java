import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import processing.core.PApplet;

public class Population {
	
	// initialize variables
	ArrayList<Guppy> guppies;
	ArrayList<Guppy> matingPool;
	ArrayList<Guppy> offspring;
	PApplet parent;
	float maxFitness;
	String filepath;
	String line;
	
	
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
	int run(Predator p) {
		int popSize = 0; 
		for (Guppy g : guppies) {
			g.run(guppies, p);
			popSize++;
		}
		return popSize;
	}
	
	// evaluate a population of guppies
	float eval(Predator p) {
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
		return maxFitness;
	}
	
	// create a new generation of guppies
	void naturalSelection(int popsize) {
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
			Guppy child = new Guppy(parent, 0, 0, childDNA, 0);
			child.mutation();
			// add to offspring pool
			offspring.add(child);
		} 
		// replace current population with new one
		this.guppies.clear();
		this.guppies.addAll(offspring);
	}
	
	void writeDataFile(int gen, float fit) {
		
		if (gen == 0) {
			String timestamp = new SimpleDateFormat("yyMMddHHmmss").format(new java.util.Date());
			filepath = System.getProperty("user.dir") + "/data/population_summary_" + timestamp + ".csv";
			line = "Max Fitness,Generation Number\n" + maxFitness + "," + gen;
		} else {
			line = "\n" + fit + "," + gen;
		}
		
		// write file
		try (FileWriter file = new FileWriter(filepath, true)) {
			file.write(line);
			System.out.println("\nRow: " + line);
		} catch (IOException e) {
			// auto-generated catch block
			e.printStackTrace();
		}
	}
}
