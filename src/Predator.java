import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

public class Predator extends Fish{

	// Auto-generated constructor stub
	public Predator(PApplet p, int x, int y) {
		super(p, x, y);
	}
	
	@Override
	public void render() {
		parent.pushMatrix();
		parent.fill(230);
		parent.ellipse(position.x,position.y,30,30);
		parent.popMatrix();
	}
	
	// run method for predators
	public void run(School s, float sniffDist) {
		applyForce(hunt(s, sniffDist).mult(4.0f));
		update();
		borders();
		render();
	}
	
	// hunt method to aim for guppies
	private PVector hunt(School s, float sniffDist) {
		// pull in school of guppies
		ArrayList<Guppy> guppies;
		guppies = s.guppies;
		// initialize variables;
		PVector steer = new PVector();
		PVector attack = new PVector();
		float min = sniffDist;
		float d = 0;
		// loop thru guppies to attack
		for (Guppy g : guppies) {
			// get distance between guppy and pred
			d = PVector.dist(position, g.position);
			// if distance 
			if ((d > 0) && (d < min)) {
				//min = d;
				attack = PVector.sub(g.position,velocity);
			}
		}
		attack.normalize();
		//attack.div(d);
		steer.add(attack);
		if (steer.mag() > 0) {
			// steering = desired - velocity
			steer.normalize();
			steer.mult(maxspeed);
			steer.sub(velocity);
			steer.limit(maxforce);
			return steer; 
		} else {
		return new PVector(0,0);
		}
	}
}
