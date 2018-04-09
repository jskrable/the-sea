import java.util.HashMap;

public class DNA {

	float mutationRate = 0.05f;
	// initialize empty map of genes
	HashMap<String, Float> genes = new HashMap<String, Float>();
	
	// sets random genes within range
	public void randomGenes() {
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
	// setter for full gene map
	public void setGenes(HashMap<String, Float> geneMap) {
		this.genes = geneMap;
	}
	
	// getter for full gene map
	public HashMap<String, Float> getGenes() {
		return this.genes;	
	}
	
	// getter for single gene value
	public Float getGene(String key) {
		return genes.get(key);
	}

	// function for mating two guppies
	public DNA mating(Guppy mate) {
		// init empty DNA for child
		DNA childDNA = new DNA();
		// flip a coin
		double coin = Math.random();
		// create new map for child genes
		HashMap<String, Float> childGenes = new HashMap<String, Float>();
		// iterate thru parent genes
		for (HashMap.Entry<String, Float> entry : mate.genes.entrySet()) {	
			// get gene values
			String key = entry.getKey();
			Float val = entry.getValue();
			// 50% chance of gene from parent 1
			if (coin >= 0.5) {
				childGenes.put(key, val);
			} else {
				// 50% chance from parent 2
				childGenes.put(key, mate.getGene(key));
			}
			// re-randomize
			coin = Math.random();
		}
		// add genes to child DNA
		childDNA.setGenes(childGenes);
		return childDNA;
	}
	
	public void mutation() {
		// iterate thru genes
		for (HashMap.Entry<String, Float> entry : this.genes.entrySet()) {	
			// for a small percentage of genes
			if (Math.random() < mutationRate) {
				// get keys
				String key = entry.getKey();
				// assign random values within range
				if (key == "pull" || key == "scare") {
					this.genes.put(key, (float) Math.random()*200);
				} else if (key == "space") {
					this.genes.put(key, (float) Math.random()*(160)+40);
				} else {
					this.genes.put(key, (float) Math.random()*2);
				}
			}
		}
	}
}
