package noc_db;

public class Superlative_noc {

	private String root;
	private String superlative;
	
	public Superlative_noc(String r, String s){
		this.root = r;
		this.superlative = s;
	}
	
	public String getSuperlative(){
		return this.superlative;
	}
	
	public String getRoot(){
		return this.root;
	}
}
