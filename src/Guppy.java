import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

public class Guppy extends Fish {

	// Auto-generated constructor stub
	public Guppy(PApplet p, int x, int y, DNA d) {
		super(p, x, y, d);
		new DNA();
	}
	
	// applies various swarm forces
	protected void school(ArrayList<Guppy> guppies, Predator p) {
		// init. each force
		PVector a = align(guppies, this.dna.getGene("pull"));
		PVector s = separate(guppies, this.dna.getGene("space"));
		PVector c = cohesion(guppies, this.dna.getGene("pull"));
		PVector f = flight(this.dna.getGene("scare"), p);
		// apply weights
		a.mult(this.dna.getGene("align"));
		s.mult(this.dna.getGene("separate"));
		c.mult(this.dna.getGene("cohesion"));
		f.mult(this.dna.getGene("flight"));
		// apply each force
		applyForce(c);
		applyForce(s);
		applyForce(a);
		applyForce(f);

	}
	
	// run method for guppies
	public void run(ArrayList<Guppy> guppies, Predator p) {
		school(guppies, p);
		update();
		borders();
		render();
	}
	
	// render override to draw guppy
	@Override
	public void render() {
		parent.pushMatrix();
		parent.fill(127);
		parent.ellipse(position.x,position.y,10,10);
		parent.popMatrix();
	}

	// keeps guppies moving in similar direction
	private PVector align(ArrayList<Guppy> guppies, float pullDist) {
		// PVector to hold sum of direction
		PVector sum = new PVector(0,0);
		// counter to divide by
		int count = 0;
		// loop through all fish
		for (Guppy other : guppies) {
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
	private PVector separate(ArrayList<Guppy> guppies, float desiredSep) {
		PVector steer = new PVector();
		int count = 0;
		// loop through all fish
		for (Guppy other : guppies) {
			// check distance like in align()
			float d = PVector.dist(position, other.position);
			// if d is within range
			if ((d > 0) && (d < desiredSep)) {
				// get vector away from neighbor 
				// USE THIS FOR FLEE METHOD??
				PVector diff = PVector.sub(position,  other.position);
				diff.normalize();
				// weight by distance ???
				diff.div(d*2);
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

	// steers fish towards center of mass
	private PVector cohesion(ArrayList<Guppy> guppies, float pullDist) {
		PVector sum = new PVector(0,0);
		int count = 0;
		// loop thru all fish
		for (Guppy other : guppies) {
			// if within pull distance (
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

	// steers away from fish that are too close to predator
	private PVector flight(float scareDist, Predator p) {
		// create empty PVector to hold 
		PVector steer = new PVector();
		// check distance like in align()
		float d = PVector.dist(position, p.position);
		// if d is within range
		if ((d > 0) && (d < scareDist)) {
			// get vector away from mouse 
			PVector diff = PVector.sub(position, p.position);
			diff.normalize();
			// weight by distance ???
			diff.div(d);
			steer.add(diff);	
		}
		if (steer.mag() > 0) {
			// steering = desired - velocity
			// Reynolds
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
