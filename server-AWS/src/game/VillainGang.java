package game;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import game_interfaces.JSONable;
import main.Main;
import noc_db.Character_noc;
import noc_db.Weapon_noc;

public class VillainGang implements JSONable{

	private boolean isActive;
	private int position;
	private boolean isOnGo;
	private int turnsToLive;

	public VillainGang(){
		this.isActive = false;
		this.position = 0;
		this.turnsToLive = 10;
	}

	public boolean isActive(){
		return this.isActive;
	}

	public int position(){
		return this.position;
	}
	
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

	public void deactivate(){
		this.isActive = false;
	}

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
				System.out.println("here");
				this.position = (this.position+=1)%39;

				//If we land on go going backwards
				if(this.position == 20) {
					this.position = 0;
					this.isOnGo = true;
				}
			

			}
		}
	}

	public String attackPlayer(Player player){
		Random random = new Random();
		int amount = 50 + 10*random.nextInt(26);
		player.setDebt(amount);
		Character_noc pchar = player.getCharacter();
		Character_noc villain = Main.noc.getOpponent(pchar);
		Weapon_noc villWeapon = Main.noc.getWeapon(villain.getWeapon());
		
		return villain.getName() + " steps forward from the gang of villains and immediately begins " +
		villWeapon.getAffordanceWithTarget(pchar.getName()) + " " + villWeapon.getDeterminer() + " " + villWeapon.getWeapon() + ". "+
		pchar.getName() + " loses " + amount + ". ";
	}
	
	@Override
	public JSONObject getInfo() throws JSONException {
		JSONObject info = new JSONObject();
		info.put("position", this.position);
		info.put("is_active", this.isActive);
		return info;
	}
}

