import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

public class Guppy extends Fish {
	
	float fitness;
	boolean eaten;
	
	// Auto-generated constructor stub
	public Guppy(PApplet p, int x, int y, DNA dna, float fit) {
		super(p, x, y);
		
		// if incoming dna is null, assign random genes
		if (dna != null) {
			this.setGenes(dna.genes);
		} else {
			this.randomGenes();
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
	public void run(ArrayList<Guppy> guppies, Predator p, int gen) {
		school(guppies, p);
		update();
		fitness(guppies, p, gen);
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
	
	// continuously calculates a guppy's fitness score
	private void fitness(ArrayList<Guppy> guppies, Predator p, int year) {
		float safetyDist = PVector.dist(position, p.position);
		boolean closeCall = (safetyDist <= 30);

		if (!eaten) {
			eaten = (safetyDist <= 15);
			fitness += safetyDist;
			
			if (year == 2000) {
				float sep = Math.abs(2.3f - this.getGene("separate"));
				float ali = Math.abs(1.2f - this.getGene("align"));
				float coh = Math.abs(1.4f - this.getGene("cohesion"));
				float fli = Math.abs(2.6f - this.getGene("flight"));
				float space = Math.abs(40 - this.getGene("space"));
				float pull = Math.abs(150 - this.getGene("pull"));
				float scare = Math.abs(100 - this.getGene("scare"));
				float score = sep + ali + coh + fli + space + pull + scare;
				
				/*if (score < 1) {
					this.fitness *= 10^8;
				} else if (score < 5) {
					this.fitness *= 10^6;
				} else if (score < 10) {
					this.fitness *= 10^4;
				} else if (score < 20) {
					this.fitness *= 10^2;
				} else if (score < 30) {
					this.fitness *= 10;
				} else if (score < 50) {
					this.fitness *= 5;
				} else if (score < 75) {
					this.fitness *= 3;
				} else if (score < 100) {
					this.fitness *= 2;
				}*/
				
				fitness /= score;
				
				fitness = fitness / year;
			}
			// punish fish who are nearly eaten
			/*if (closeCall) {
				this.fitness /= 5;
			}*/
		} else if (eaten) {
			// punish fish who are eaten
			this.fitness = 0.01f;
			//guppies.remove(this);
		}		
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
