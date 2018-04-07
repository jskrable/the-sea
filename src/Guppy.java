import java.util.ArrayList;
import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PVector;

public class Guppy extends Fish {
	
	// Auto-generated constructor stub
	public Guppy(PApplet p, int x, int y, DNA dna) {
		super(p, x, y);
		
		// if incoming dna is null, assign random genes
		if (dna == null) {
			HashMap<String, Float> geneMap = new HashMap<String, Float>();
			geneMap.put("align", (float) Math.random()*2);
			geneMap.put("separate", (float) Math.random()*2);
			geneMap.put("cohesion", (float) Math.random()*2);
			geneMap.put("flight", (float) Math.random()*2);
			geneMap.put("pull", (float) Math.random()*200);
			geneMap.put("space", (float) Math.random()*(160)+40);
			geneMap.put("scare", (float) Math.random()*200);
			this.setGenes(geneMap);
		}
	}
	// calculates fitness score for guppy
	protected void getFitness(Predator p) {
		float d = PVector.dist(position, p.position);
		this.fitness = d;
		if (d == 0) {
			fitness /= 75;
		}
		
	}
	
	// applies various swarm forces
	protected void school(ArrayList<Guppy> guppies, Predator p) {
		// init. each force
		PVector a = align(guppies);
		PVector s = separate(guppies);
		PVector c = cohesion(guppies);
		PVector f = flight(p);
		// apply weights
		a.mult(this.getGene("align"));
		s.mult(this.getGene("separate"));
		c.mult(this.getGene("cohesion"));
		f.mult(this.getGene("flight"));
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
	private PVector align(ArrayList<Guppy> guppies) {
		// PVector to hold sum of direction
		PVector sum = new PVector(0,0);
		// counter to divide by
		int count = 0;
		// get pull gene for comparison
		float pullDist = this.getGene("pull");
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
	private PVector separate(ArrayList<Guppy> guppies) {
		PVector steer = new PVector();
		int count = 0;
		float desiredSep = this.getGene("space");
		// loop through all fish
		for (Guppy other : guppies) {
			// check distance like in align()
			float d = PVector.dist(position, other.position);
			// if d is within range
			if ((d > 0) && (d < desiredSep)) {
				// get vector away from neighbor 
				PVector diff = PVector.sub(position,  other.position);
				diff.normalize();
				// weight by distance
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
	private PVector cohesion(ArrayList<Guppy> guppies) {
		PVector sum = new PVector(0,0);
		int count = 0;
		float pullDist = this.getGene("pull");
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

	// steers fish away that are too close to predator
	private PVector flight(Predator p) {
		// create empty PVector to hold steering
		PVector steer = new PVector();
		float scareDist = this.getGene("scare");
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
