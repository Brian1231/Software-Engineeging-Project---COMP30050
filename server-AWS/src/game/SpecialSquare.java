package game;

import game_interfaces.Activatable;

public class SpecialSquare extends NamedLocation implements Activatable{

	public SpecialSquare(String name) {
		super(name);
		this.setType("special");
	}
	
	@Override
	public String activate(Player player){
		StringBuilder res = new StringBuilder();
		switch (this.getLocation()){
		//Go
		case 0:
			res.append("\n"+player.getCharName() + " arrived at the galactic core.");
			res.append("\nThe fuel for " + player.getPossessive().toLowerCase() + " " + player.getCharacter().getVehicle()+" was topped up.");
			player.topUpFuel();
			return res.toString();
			//Go to jail
		case 10:
			player.sendToJail();
			return "\n" + player.getCharName() + " was sent to intergalactic prison!\n"+
			"Attempt to break free by rolling doubles or pay the fee of 500 SHM.";
			//Jail
		case 29:
			return "You arrive at intergalactic prison and decide to have a relaxing stroll around.";
		default:
			return "";
		}
	}

	
}
