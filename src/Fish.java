import processing.core.*;

abstract class Fish extends DNA{
	// variable declarations
	PVector position;
	PVector velocity;
	float r;
	float maxforce;
	float maxspeed;
	PApplet parent;

	// Fish constructor
	public Fish(PApplet p, int x, int y) {
		// PApplet to reference canvas
		parent = p;
		if ((x==0) && (y==0)) {
			// random start position if no input variables
			position = new PVector(p.random(p.width),p.random(p.height));
		} else {
			position = new PVector(x,y);
		}
		// random velocity vector
		velocity = new PVector(p.random(-1,1),p.random(-1,1));
		r = 3.0f;
		// maximums
		maxspeed = 2.5f;
		maxforce = 0.1f;
	}

	// applies a PVector acceleration
	protected void applyForce(PVector force) {
		velocity.add(force);
	}
	
	// method to change direction
	protected void update() {
		position.add(velocity);
	}

	// border behavior
	protected void borders() {
		// wraps fish around borders
		if (position.x < -r) position.x = parent.width+r;
		if (position.y < -r) position.y = parent.height+r;
		if (position.x > parent.width+r) position.x = -r;
		if (position.y > parent.height+r) position.y = -r;
	}

	// draws the fish
	// NEED TO ADD ROTATION
	protected void render() {
		//float theta = (float) (velocity.heading() + (Math.PI/2)) ;
		parent.pushMatrix();
		//parent.stroke(0);
		parent.fill(127);
		//parent.translate(position.x, position.y);
		//parent.rotate(theta);
		parent.ellipse(position.x,position.y,10,10);
		parent.popMatrix();
	}
	
	// applies a steering force towards a target
	// STEER = DESIRED MINUS VELOCITY
	public PVector seek(PVector target) {
		// A vector pointing from the position to the target
		PVector desired = PVector.sub(target,position);  
		// Normalize desired and scale to maximum speed
		desired.normalize();
		desired.mult(maxspeed);
		// Steering = Desired minus Velocity
		PVector steer = PVector.sub(desired,velocity);
		// Limit to maximum steering force
		steer.limit(maxforce);  
		return steer;
	}
}
