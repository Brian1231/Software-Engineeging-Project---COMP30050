package game;

public class SpecialSquare extends NamedLocation{

	public SpecialSquare(String name) {
		super(name);
	}
	
	
	public String activate(Player player){
		String res = "";
		switch (this.getLocation()){
		//Go
		case 0:
			res+="\n"+player.getCharName() + " arrived at the galactic core.";
			res+="\nThe fuel for " + player.getPossessive().toLowerCase() + " " + player.getCharacter().getVehicle()+" was topped up.";
			player.topUpFuel();
			return res;
			//Go to jail
		case 10:
			player.sendToJail();
			return "\n" + player.getCharName() + " was sent to intergalactic prison!\n"+
			"Attempt to break free by rolling doubles or pay the fee of $1000.";
			//Jail
		case 29:
			return "You arrive at intergalactic prison and decide to have a relaxing stroll around.";
		default:
			return "";
		}
	}

	
}
