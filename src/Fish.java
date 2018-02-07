import java.util.ArrayList;
import processing.core.*;

public class Fish {
	// variable declarations
	PVector position;
	PVector velocity;
	PVector acceleration;
	float r;
	float maxforce;
	float maxspeed;
	PApplet parent;
	float pullDist = 25;

	// Fish constructor
	Fish(PApplet p) {
		// PApplet to reference canvas
		parent = p;
		// random start position
		position = new PVector(p.random(p.width),p.random(p.height));
		// random velocity vector
		velocity = new PVector(p.random(-1,1),p.random(-1,1));
		acceleration = new PVector();
		// WHAT IS THIS
		r = (float) 3.0;
		// maximums
		maxspeed = 2;
		maxforce = (float) 0.05;
	}

	// run method for fish, takes array of fishes
	public void run(ArrayList<Fish> fishes) {
		school(fishes);
		update();
		borders();
		render();
	}

	// applies a PVector acceleration
	private void applyForce(PVector force) {
		acceleration.add(force);
	}

	// applies various swarm forces
	private void school(ArrayList<Fish> fishes) {
		// init. each force
		PVector a = align(fishes, pullDist);
		// apply weights
		a.mult(1);
		// apply each force
		applyForce(a);

	}

	// method to change direction
	private void update() {
		velocity.add(acceleration);
		position.add(velocity);
	}

	// bounces fish off borders of canvass
	// CHANGE??
	private void borders() {
		if ((position.x > parent.width) || (position.x < 0)) {
			velocity.x = velocity.x * -1;
		}
		if ((position.y > parent.height) || (position.y < 0)) {
			velocity.y = velocity.y * -1;
		}
	}

	// draws the fish
	private void render() {
		parent.stroke(0);
		parent.strokeWeight(1);
		parent.fill(127);
		parent.ellipse(position.x,position.y,10,25);
	}

	// keeps fish moving in similar direction
	private PVector align(ArrayList<Fish> fishes, float pullDist) {
		// distance of pull
		//float pullDist = 50;
		// PVector to hold sum of direction
		PVector sum = new PVector(0,0);
		// counter to divide by
		int count = 0;
		// loop through all fish
		for (Fish other : fishes) {
			// measure distance between current fish and others
			float d = PVector.dist(position,other.position);
			// if between 0 and pull distance
			if ((d > 0) && (d < pullDist)) {
				// add vector of neighbor to sum
				sum.add(other.velocity);
				count++;
			}
		}
		// if other fish w/in pull distance
		if (count > 0) {
			// divide sum by count
			sum.div((float)count);
			sum.normalize();
			sum.mult(maxspeed);
			// get new steering vector
			PVector steer = PVector.sub(sum,velocity);
			steer.limit(maxforce);
			return steer;
		} else {
			return new PVector(0,0);
		}
	}
}
