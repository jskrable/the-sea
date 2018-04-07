import processing.core.PApplet;

public class Lir extends PApplet {
	// initialize objects
	School s;
	Predator p;
	DNA dna;
	// variable set
	int schoolPopSize = 50;
	int lifespan = 1000;
	int epoch = 0;
	int counter = 0;

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
		s = new School();
		for (int i = 0; i < schoolPopSize; i++) {
			Guppy g = new Guppy(this, 0, 0, dna);
			s.addGuppy(g);
		}
		// add one predator
		p = new Predator(this, 0, 0, dna);
	}

	// draw canvas and run
	public void draw() {
		background(255);
		// add school of guppies
		int schoolPopSize = s.run(p);
		// add a predator
		p.run(s);
		fill(0);
		text(("Population Size: " + schoolPopSize + "      Timer: " + counter + "      Epoch: " + epoch),
				12, this.height - 16);
		text("Click and drag to add new fish", 12, 16);
		
		counter++;
		if (counter == lifespan) {
			// evaluate scores
			// natural selection
			counter = 0;
			epoch++;
		}
		
	}
	
	// add new fish to school on mouse
	public void mouseDragged() {
		  s.addGuppy(new Guppy(this, mouseX, mouseY, dna));
		}
}
