package game_models;

import game_interfaces.Activatable;


/*
 * Class for Go, go to jail and jail tiles
 * 
 * */
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
			res.append("\n").append(player.getCharName()).append(" arrived at the galactic core.");
			res.append("\nThe fuel for ").append(player.getPossessive().toLowerCase()).append(" ").append(player.getCharacter().getVehicle()).append(" was topped up.");
			player.topUpFuel(); //Top up a players fuel if they land at the centre
			return res.toString();
			//Go to jail
		case 10:
			player.sendToJail();
			return "\n" + player.getCharName() + " was sent to intergalactic prison!\n"+
			"Attempt to break free by rolling doubles or pay the fee of 50 SHM.";
			//Jail
		case 29:
			return "You arrive at intergalactic prison and decide to have a relaxing stroll around.";
		default:
			return "";
		}
	}
}
