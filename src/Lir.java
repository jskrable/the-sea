import processing.core.PApplet;

public class Lir extends PApplet {
	// initialize objects
	Population pop;
	Predator pred;
	DNA dna;
	// variable set
	int schoolPopSize = 50;
	int lifespan = 500;
	int epoch = 0;
	int timer = 0;

	// PApplet extension
	public static void main(String[] args) {
		PApplet.main("Lir");
	}

	// canvas size
	public void settings() {
		size(1200,800); 
	}

	// add guppies to the school
	public void setup() {
		pop = new Population(this);
		for (int i = 0; i < schoolPopSize; i++) {
			Guppy g = new Guppy(this, 0, 0, dna);
			pop.addGuppy(g);
		}
		// add one predator
		pred = new Predator(this, 0, 0, dna);
	}

	// draw canvas and run
	public void draw() {
		background(255);
		// add school of guppies
		int displayPop = pop.run(pred);
		// add a predator
		pred.run(pop);
		// increment timer
		timer++;
		// process generation
		if (timer == lifespan) {
			pop.eval(pred);
			String filename = "population_summary_" + epoch + ".json";
			pop.writeDataFile(filename);
			pop.naturalSelection(schoolPopSize);
			timer = 0;
			epoch++;
		}
		// help text
		fill(0);
		text(("Population Size: " + displayPop + "      Timer: " + timer + "      Epoch: " + epoch),
				12, this.height - 16);
		text("Click and drag to add new fish", 12, 16);
		
	}
	
	// add new fish to school on mouse
	public void mouseDragged() {
		  pop.addGuppy(new Guppy(this, mouseX, mouseY, dna));
		}
}
