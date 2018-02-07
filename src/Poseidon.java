import processing.core.PApplet;

public class Poseidon extends PApplet {
	// init. school object
	School s;
	// variable set
	int popSize = 50;

	// PApplet extension
	public static void main(String[] args) {
		PApplet.main("Poseidon");
	}

	// canvas size
	public void settings() {
		size(1200,800); 
	}

	// add fish to the school
	public void setup() {
		s = new School();
		for (int i = 0; i < popSize; i++) {
			Fish f = new Fish(this);
			s.addFish(f);
		}
	}

	// draw canvas and run
	public void draw() {
		background(255);
		s.run();
	}
}
