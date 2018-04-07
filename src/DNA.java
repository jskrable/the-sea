import java.util.HashMap;

public class DNA {

	// initialize empty map of genes
	HashMap<String, Float> genes = new HashMap<String, Float>();
	
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

}
