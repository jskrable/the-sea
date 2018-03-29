import processing.core.PApplet;

public class Lir extends PApplet {
	// initialize objects
	School s;
	Predator p;
	DNA dna;
	// variable set
	int schoolPopSize = 50;
	float aC = 1.0f;
	float cC = 1.4f;
	float sC = 2.0f;
	float fC = 2.6f;
	int pullDist = 150;
	int desiredSep = 40;
	int scareDist = 100;
	int sniffDist = 200;

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
		p = new Predator(this, 0,0);
	}

	// draw canvas and run
	public void draw() {
		background(255);
		// add school of guppies
		int schoolPopSize = s.run(aC, cC, sC, fC, pullDist, desiredSep, scareDist, p);
		// add a predator
		p.run(s, sniffDist);
		fill(0);
		text(("Population Size: " + schoolPopSize + " Alignment: " + aC + " Cohesion: " 
				+ cC + " Separation: " + sC + " Flight: " + fC + " Scare Distance: "
				+ scareDist + " Attraction Distance: " + pullDist
				+ " Personal Space: " + desiredSep + " Sniff Distance: "
				+ sniffDist), 12, this.height - 16);
		text("Click and drag to add new fish", 12, 16);
		
	}
	
	// add new fish to school on mouse
	public void mouseDragged() {
		  s.addGuppy(new Guppy(this, mouseX, mouseY, dna));
		}
}
