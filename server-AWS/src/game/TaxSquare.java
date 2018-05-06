package game;

import java.util.Random;

import game_interfaces.Activatable;
import game_interfaces.JSONable;
import game_interfaces.Playable;
import game_interfaces.Taxable;
import main.Main;
import noc_db.Character_noc;
import org.json.JSONException;
import org.json.JSONObject;

public class TaxSquare extends NamedLocation implements Taxable, JSONable, Activatable {

	private final Random random = new Random();
	private String type;

	public TaxSquare(String name) {
		super(name);
		this.setType("tax");
	}

	@Override
	public String activate(Player player){
		int type = random.nextInt(2);
		String res = "\n"+this.getText(Main.noc.getOpponent(player.getCharacter()), type);
		
		switch (type){
		case 0:
			int flatAmount = this.getFlatAmount();
			player.setDebt(flatAmount, null);
			res+="\n"+player.getCharName()+" owes the flat amount of "+flatAmount+" SHM.";
			return res;
		case 1:
			//Percent in range 5% - 30%
			double percentage = (0.05 + (random.nextInt(26)*0.01));
			int amount = this.getIncomePercentage(player, percentage);
			res+="\n"+player.getCharName()+" owes "+Math.round(percentage*100)+"% of their net worth. That's "+amount+" SHM.";
			player.setDebt(amount, null);
			return res;
		}
		return res;
	}
	
	@Override
	public int getIncomePercentage(Playable player, double percentage) {
		return (int) (player.getNetWorth() * percentage);
	}

	public String getText(Character_noc ch, int taxType){
		return new TaxTemplate(ch, taxType).getTemplate();
	}
	
	@Override
	public int getFlatAmount() {
		return 50 + 10*random.nextInt(26);
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public JSONObject getInfo() throws JSONException {
		JSONObject info = super.getInfo();
		info.put("type", this.getType());
		return info;
	}
}
