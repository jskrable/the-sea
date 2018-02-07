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
	// tweak for force fun
	float pullDist = 30;
	float desiredSep = 20;

	// Fish constructor
	Fish(PApplet p) {
		// PApplet to reference canvas
		parent = p;
		// random start position
		position = new PVector(p.random(p.width),p.random(p.height));
		// random velocity vector
		velocity = new PVector(p.random(-1,1),p.random(-1,1));
		acceleration = new PVector(0,0);
		// WHAT IS THIS
		r = (float) 3.0;
		// maximums
		maxspeed = (float) .5;
		maxforce = (float) 0.03;
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
	// SOMETHING HERE CAUSING UNCONTROLLABLE ACCELERATION
	private void school(ArrayList<Fish> fishes) {
		/*// init. each force
		PVector a = align(fishes, pullDist);
		PVector s = separate(fishes, desiredSep);
		PVector c = cohesion(fishes, pullDist);
		// apply weights
		a.mult(1);
		s.mult(1);
		c.mult((float) 1.5);
		// apply each force
		applyForce(a);
		applyForce(s);
		applyForce(c);*/

	}

	// method to change direction
	private void update() {
		velocity.add(acceleration);
		position.add(velocity);
	}
	
	// A method that calculates and applies a steering force towards a target
	// STEER = DESIRED MINUS VELOCITY
	public PVector seek(PVector target) {
		// A vector pointing from the position to the target
		PVector desired = PVector.sub(target,position);  
		// Normalize desired and scale to maximum speed
		desired.normalize();
		desired.mult(maxspeed);
		// Steering = Desired minus Velocity
		PVector steer = PVector.sub(desired,velocity);
		steer.limit(maxforce);  // Limit to maximum steering force
		return steer;
	}


	// border behavior
	private void borders() {
		// bounces fish off borders of canvass
		if ((position.x > parent.width) || (position.x < 0)) {
			velocity.x = velocity.x * -1;
		}
		if ((position.y > parent.height) || (position.y < 0)) {
			velocity.y = velocity.y * -1;
		}
		// wraps fish around borders
		/*
	    if (position.x < -r) position.x = parent.width+r;
	    if (position.y < -r) position.y = parent.height+r;
	    if (position.x > parent.width+r) position.x = -r;
	    if (position.y > parent.height+r) position.y = -r;*/
	}

	// draws the fish
	private void render() {
		float theta = (float) (velocity.heading() + (Math.PI/2)) ;
		parent.pushMatrix();
		parent.stroke(0);
		parent.fill(127);
		//parent.translate(position.x, position.y);
		parent.rotate(theta);
		parent.ellipse(position.x,position.y,10,25);
		parent.popMatrix();
	}

	// keeps fish moving in similar direction
	private PVector align(ArrayList<Fish> fishes, float pullDist) {
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
	
	// steers away from fish that are too close
	private PVector separate(ArrayList<Fish> fishes, float desiredSep) {
		PVector steer = new PVector(0,0,0);
		int count = 0;
		// loop through all fish
		for (Fish other : fishes) {
			// check distance like in align()
			float d = PVector.dist(position,other.position);
			// if d is within range
			if ((d < 0) && (d < desiredSep)) {
				// get vector away from neighbor 
				// USE THIS FOR FLEE METHOD??
				PVector diff = PVector.sub(position,  other.position);
				diff.normalize();
				// weight by distance
				diff.div(d);
				steer.add(diff);
				count++;
			}
		}
		// get average
		if (count > 0) {
			steer.div(count);
		}
		// if there is a direction
		if (steer.mag() > 0) {
			// steering = desired - velocity
			// Reynolds
			steer.normalize();
			steer.mult(maxspeed);
			steer.sub(velocity);
			steer.limit(maxforce);
		}
		return steer;
	}
	
	private PVector cohesion(ArrayList<Fish> fishes, float pullDist) {
		PVector sum = new PVector(0,0);
		int count = 0;
		for (Fish other : fishes) {
			float d = PVector.dist(position, other.position);
			if ((d > 0 ) && (d < pullDist)) {
				sum.add(other.position);
				count++;
			}
		}
		if (count > 0 ) {
			sum.div(count);
			return seek(sum);
		} else {
			return new PVector(0,0);
		}
	}
}
