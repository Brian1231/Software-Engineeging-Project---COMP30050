package game;

public class Player {

	private int id;
	
	public Player(int playerId){
		id = playerId;
	}
	
	public String toString(){
		return "ID: " + this.id;
	}
}
