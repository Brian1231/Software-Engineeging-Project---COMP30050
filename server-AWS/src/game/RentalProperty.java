package game;

import game_interfaces.JSONable;
import game_interfaces.Mortgageable;
import game_interfaces.Ownable;
import game_interfaces.Trapable;
import game_interfaces.Colourable;
import game_interfaces.Rentable;
import game_interfaces.Playable;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.Color;

public class RentalProperty extends NamedLocation implements Ownable, Rentable, Mortgageable, JSONable, Trapable, Colourable {

	private int price;
	private int mortgageAmount;
	private int[] rentAmounts;
	private boolean isMortgaged = false;
	private boolean hasTrap;
	private boolean isOwned;
	private Player owner;
	private String type;
	private Color colour;

	public RentalProperty(String name, int price) {
		super(name);
		this.price = price;
		this.hasTrap = false;
		this.setType("Rental");
		this.colour = Color.RED;
	}

	@Override
	public Player getOwner() {
		return this.owner;
	}

	@Override
	public void setOwner(Playable player) {
		this.isOwned = true;
		this.owner = (Player) player;
	}

	public void setUnOwned() {
		this.owner = null;
		this.isOwned = false;
	}

	@Override
	public boolean isOwned() {
		return this.isOwned;
	}

	@Override
	public int getPrice() {
		return this.price;
	}

	@Override
	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int getMortgageAmount() {
		return this.mortgageAmount;
	}

	@Override
	public void setMortgageAmount(int mortgageAmount) {
		this.mortgageAmount = mortgageAmount;
	}

	@Override
	public void mortgage(Playable player) {
		player.receiveMoney(this.mortgageAmount);
		setMortgageStatus(true);
	}

	@Override
	public void redeem(Playable player) {
		player.payMoney(getRedeemAmount());
		setMortgageStatus(false);
	}

	@Override
	public int getRedeemAmount() {
		return Math.round(this.mortgageAmount + (this.mortgageAmount * 0.1f));
	}

	@Override
	public boolean isMortgaged() {
		return isMortgaged;
	}

	@Override
	public void setMortgageStatus(boolean mortgageStatus) {
		this.isMortgaged = mortgageStatus;
	}

	@Override
	public int getBaseRentAmount() {
		if (!this.isMortgaged()) {
			return rentAmounts[0];
		} else {
			System.out.println("Cant claim rent on rental property that is mortgaged");
			return 0;
		}
	}

	@Override
	public void setRentAmounts(int[] rentAmounts) {
		this.rentAmounts = rentAmounts;
	}

	@Override
	public int[] getAllRentAmounts() {
		return rentAmounts;
	}

	@Override
	public boolean hasTrap() {
		return this.hasTrap;
	}

	@Override
	public String setTrap() {
		int trapCost = this.getPrice() / 5;
		if (this.getOwner().getBalance() > trapCost) {
			this.hasTrap = true;
			this.getOwner().payMoney(trapCost);
			return this.getOwner().getCharName() + " paid " + trapCost + " and set a trap at " + this.getId() + ". ";
		} else
			return "You can't afford the cost of " + trapCost + " to set a trap.";
	}

	@Override
	public String activateTrap(Player player) {
		if (!player.equals(this.getOwner())) {
			int trapAmount = this.getPrice() / 3;
			player.setDebt(trapAmount, this.getOwner());
			return player.getCharName() + " activated " + this.getOwner().getCharName() + "'s trap and now owes them an additional " + trapAmount + ". ";
		}
		return "";
	}

	@Override
	public JSONObject getInfo() throws JSONException {
		JSONObject info = super.getInfo();
		info.put("price", this.price);
		if(this.owner != null)
			info.put("owner", this.owner.getID());
		else
			info.put("owner", 0);
		info.put("color", this.colour.getRGB());
		info.put("hasTrap", this.hasTrap);
		info.put("is_mortgaged", this.isMortgaged());
		return info;
	}

	@Override
	public void setColour(Color colour) {
		this.colour = colour;
	}

	@Override
	public Color getColour() {
		return this.colour;
	}
}
