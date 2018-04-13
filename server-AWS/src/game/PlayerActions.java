package game;

import java.util.ArrayList;

public class PlayerActions {

	public String roll(Player player, Dice dice, int id, ArrayList<NamedLocation> locations) {
		if(!player.hasRolled()){
			int spaces = dice.roll();
			player.useRoll();
			return player.moveForward(spaces) +" and arrived at "+ locations.get(player.getPos()).getId()+".";
		}
		else{
			return "Player " + id + " has already rolled this turn.";
		}
	}


	public String buy(Player player, NamedLocation tile, int id) {

		if (tile instanceof PrivateProperty) {
			PrivateProperty prop = (PrivateProperty) tile;
			if (!prop.isOwned()) {
				if (player.getBalance() >= prop.getPrice()) {
					(prop).setOwner(player);
					player.addNewPropertyBought(prop);
					player.useBuy();
					return "Player " + id + " bought " + prop.getId() + " for " + prop.getPrice() + ".";
				}
				return "You can't afford this property.";
			}
			return prop.getId() + " is already owned by " + prop.getOwner().getId() + ".";
		}
		return "You can't buy that.";
	}


	public String sell(Player player, NamedLocation loc, int id) {
		if (loc instanceof PrivateProperty) {
			PrivateProperty property = (PrivateProperty) loc;
			if (property.isOwned()) {
				if (property.getOwner().equals(player)) {
					if (property instanceof RentalProperty) {
						RentalProperty rental = (RentalProperty) property;
						if (!rental.isMortgaged()) {
							property.setOwner(null);
							player.removePropertySold(property);
							return "Player " + id + " sold " + property.getId() + " for " + property.getPrice() + ".";
						}
						return "You can't sell a mortgaged property.";
					}
					property.setOwner(null);
					player.removePropertySold(property);
					player.receiveMoney(property.getPrice());
					return "Player " + id + " sold " + property.getId() + " for " + property.getPrice() + ".";
				}
				return "You don't own that property.";
			}
			return "That property is unowned.";
		}
		return "You can't sell that.";
	}


	public String mortgage(Player player, NamedLocation loc, int id)  {
		if (loc instanceof PrivateProperty) {
			RentalProperty property = (RentalProperty) loc;
			if (property.isOwned()) {
				if (property.getOwner().equals(player)) {
					if (!property.isMortgaged()) {
						property.mortgage(player);
						return "Player " + id + " mortgaged " + property.getId() + " and received " + property.getMortgageAmount() + ".";
					}
					return property.getId() + " is already mortgaged! ";
				}
				return "You don't own that property.";
			}
			return "That property is unowned.";
		}
		return "You can't mortgage that.";
	}


	public String redeem(Player player, NamedLocation loc, int id) {
		if (loc instanceof PrivateProperty) {
			RentalProperty property = (RentalProperty) loc;
			if (property.isOwned()) {
				if (property.getOwner().equals(player)) {
					if (property.isMortgaged()) {
						property.redeem(player);
						return "Player " + id + " redeemed " + property.getId() + " for " + property.getRedeemAmount() + ".";
					}
					return property.getId() + " isn't mortgaged! ";
				}
				return "You don't own that property.";
			}
			return "That property is unowned.";
		}
		return "You can't redeem that.";
	}


	public String boost(Player player, ArrayList<NamedLocation> locations) {
		/*if (player.hasRolled()) {
			if (!player.hasBought()) {
				if (!player.hasBoosted()) {
					if (player.getFuel() > 0) {*/
						return player.useBoost()  +" and arrived at "+ locations.get(player.getPos()).getId()+".";
	/*				}
					return "Your vehicle is out of fuel!";
				}
				return "You've already used your vehicle this turn!";
			}
			return "You can't use your vehicle after buying a property!";
		}
		return "You must roll before using your vehicle.";*/
	}

	public String build(Player player, NamedLocation loc, int numToBuild, int id) {
		if (loc instanceof InvestmentProperty) {
			InvestmentProperty property = (InvestmentProperty) loc;
			if (property.isOwned()) {
				if (property.getOwner().equals(player)) {
					if (!property.isMortgaged()) {
						if (property.build(numToBuild)) {
							return "Player " + id + " built " + numToBuild + " houses on " + property.getId() + ".";
						} else {
							return property.getBuildDemolishError();
						}
					}
					return property.getId() + " is mortgaged! ";
				}
				return "You don't own " + property.getId();
			}
			return property.getId() + " is unowned.";
		}
		return " You cant build on " + loc.getId();
	}


	public String demolish(Player player, NamedLocation loc, int numToDemolish, int id) {
		if (loc instanceof InvestmentProperty) {
			InvestmentProperty property = (InvestmentProperty) loc;
			if (property.isOwned()) {
				if (property.getOwner().equals(player)) {
					if (!property.isMortgaged()) {
						if (property.build(numToDemolish)) {
							return "Player " + id + " demolished " + numToDemolish + " houses on " + property.getId() + ".";
						} else {
							return property.getBuildDemolishError();
						}
					}
					return property.getId() + " is mortgaged! ";
				}
				return "You don't own " + property.getId();
			}
			return property.getId() + " is unowned.";
		}
		return " You cant demolish on " + loc.getId();
	}

	public void done(Player player) {
		player.resetRoll();
		player.resetBought();
		player.resetBoost();
	}
}
