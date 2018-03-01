import processing.core.PApplet;

public class Lir extends PApplet {
	// initialize objects
	School s;
	Predator p;
	// variable set
	int schoolPopSize = 50;
	float aC = 1.0f;
	float cC = 1.4f;
	float sC = 2.0f;
	float fC = 2.2f;
	float pullDist = 150;
	float desiredSep = 40;
	float scareDist = 100;

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
			Guppy g = new Guppy(this, 0, 0);
			s.addGuppy(g);
		}
		// add one predator
		p = new Predator(this, 0,0);
	}

	// draw canvas and run
	public void draw() {
		background(255);
		int schoolPopSize = s.run(aC, cC, sC, fC, pullDist, desiredSep, scareDist, p);
		p.run();
		fill(0);
		text(("Population Size: " + schoolPopSize + " Alignment: " + aC + " Cohesion: " 
				+ cC + " Separation: " + sC + " Flight: " + fC + " Scare Distance: "
				+ scareDist + " Attraction Distance: " + pullDist
				+ " Personal Space: " + desiredSep), 12, this.height - 16);
		text("Click and drag to add new fish", this.width - 200, this.height - 16);
		
	}
	
	// add new fish to school on mouse
	public void mouseDragged() {
		  s.addGuppy(new Guppy(this, mouseX,mouseY));
		}
}
