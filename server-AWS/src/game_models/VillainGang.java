package game_models;

import java.util.Random;

import game_interfaces.VillainGangable;
import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.JSONable;
import noc_db.Character_noc;
import noc_db.Weapon_noc;

/*
 * This class represents the gang of villains which may be summoned in our game.
 * This object will move around the board and attack any players that land on the same tile as it.
 * 
 * */

public class VillainGang implements JSONable, VillainGangable {

	private int turnsToLive;
	private int position;
	private boolean isActive;
	private boolean isOnGo;

	public VillainGang(){
		this.isActive = false;
		this.position = 0;
		this.turnsToLive = 10;
	}

	@Override
	public boolean isActive() {
		return this.isActive;
	}

	@Override
    public int position(){
		return this.position;
	}

	@Override
	public int turnsToLive() {
		return this.turnsToLive;
	}

	//Villain gang has been summoned
	@Override
    public void activate(int location){
		this.isActive = true;
		this.position = location;
		this.turnsToLive = 10;
		//If we land on go going backwards
		if(this.position == 20) {
			this.position = 0;
			this.isOnGo = true;
		}
	}

	@Override
    public void deactivate(){
		this.isActive = false;
	}

	//Move around the board and decrement time to live
	@Override
    public void update(){
		if(this.isActive){
			this.turnsToLive--;
			if(this.turnsToLive == 0 ) this.deactivate();
			if(this.isOnGo){
				this.position = 19;
				this.isOnGo = false;
				this.position = (this.position+=1)%39;
			}
			else{
				this.position = (this.position+=1)%39;
				//If we land on go going backwards
				if(this.position == 20) {
					this.position = 0;
					this.isOnGo = true;
				}
			}
		}
	}

	//Player lands on same tile as villain gang
	@Override
    public String attackPlayer(Player player){
		Random random = new Random();
		int amount = 50 + 10*random.nextInt(26);
		player.setDebt(amount, null);
		Character_noc playerCharacter = player.getCharacter();
		Character_noc villain = player.getVillain();
		Weapon_noc villainWeapon = villain.getWeaponObject();
		
		return villain.getName() + " steps forward from the gang of villains and immediately begins " +
		villainWeapon.getAffordanceWithTarget(playerCharacter.getName()) + " " + villainWeapon.getDeterminer() + " " + villainWeapon.getWeapon() + ". "+
		playerCharacter.getName() + " loses " + amount + " SHM. ";
	}
	
	@Override
	public JSONObject getInfo() throws JSONException {
		JSONObject info = new JSONObject();
		info.put("position", this.position);
		info.put("is_active", this.isActive);
		return info;
	}
}

