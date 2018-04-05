import java.util.HashMap;

public class DNA {

	private HashMap<String, Double> genes() {
		HashMap<String, Double> DNA = new HashMap<String, Double>();
		DNA.put("align", (Math.random()*2));
		DNA.put("separate", (Math.random()*2));
		DNA.put("cohesion", (Math.random()*2));
		DNA.put("flight", (Math.random()*2));
		DNA.put("pull", (Math.random()*200));
		DNA.put("space", (Math.random()*200));
		DNA.put("scare", (Math.random()*200));
		return DNA;
	}
	
	public HashMap<String, Double> getGenes() {
		return this.genes();	
	}
	
	public float getGene(String key) {
		return (float) this.getGene(key);
	}
	

}
