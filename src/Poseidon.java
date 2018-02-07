import processing.core.PApplet;

public class Poseidon extends PApplet {
	// init. school object
	School s;
	// variable set
	int popSize = 50;
	float aC = 1.0f;
	float cC = 1.6f;
	float sC = 1.5f;
	float pullDist = 175;
	float desiredSep = 30;

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
			Fish f = new Fish(this, 0, 0);
			s.addFish(f);
		}
	}

	// draw canvas and run
	public void draw() {
		background(255);
		int popSize = s.run(aC, cC, sC, pullDist, desiredSep);
		fill(0);
		text(("Population Size: " + popSize + " Alignment: " + aC + " Cohesion: " 
				+ cC + " Separation: " + sC + " Attraction Distance: " + pullDist
				+ " Personal Space: " + desiredSep), 12, this.height - 16);
		
	}
	
	public void mouseDragged() {
		  s.addFish(new Fish(this, mouseX,mouseY));
		}
}
