import processing.core.PApplet;

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
	public void run() {
		update();
		borders();
		render();
	}
	
}
