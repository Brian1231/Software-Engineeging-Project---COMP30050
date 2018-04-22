package game;

import java.util.ArrayList;
import java.util.Random;

public class PlayerActions {

	Random random = new Random();

	public String roll(Player player, Dice dice, int id, ArrayList<NamedLocation> locations) {
		if(!player.hasRolled()){
			if(!player.isInJail()){
				int spaces = dice.roll();
				player.useRoll();
				String res = player.moveForward(spaces)  +" and arrived at "+ locations.get(player.getPos()).getId()+".";
				res += landedOn(player, locations.get(player.getPos()), spaces);
				return res;
			}
			String res = player.getCharName() +" attempts to escape prison...\n";
			if(dice.rollDoubles()){
				res+=player.getCharName()+" has succesfully escaped prison!";
				player.releaseFromJail();
				return res;
			}
			player.useRoll();
			res+=player.getCharName()+"'s escape plan fails miserably.";
			if(player.incrementJailTurns()){
				res+=player.getCharName()+" has spent their time in prison and will be released next turn.";
			}
			return res;
		}
		return player.getCanName() + " has already rolled this turn.";
	}


	public String buy(Player player, NamedLocation tile, int id) {

		if (tile instanceof PrivateProperty) {
			PrivateProperty prop = (PrivateProperty) tile;
			if (!prop.isOwned()) {
				if (player.getBalance() >= prop.getPrice()) {
					(prop).setOwner(player);
					player.addNewPropertyBought(prop);
					player.useBuy();
					return player.getCharName() + " bought " + prop.getId() + " for " + prop.getPrice() + ".";
				}
				return "You can't afford this property.";
			}
			return prop.getId() + " is already owned by " + prop.getOwner().getCharName() + ".";
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
							property.setUnOwned();
							player.removePropertySold(property);
							return player.getCharName() + " sold " + property.getId() + " for " + property.getPrice() + ".";
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
						return player.getCharName() + " mortgaged " + property.getId() + " and received " + property.getMortgageAmount() + ".";
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
						return player.getCharName() + " redeemed " + property.getId() + " for " + property.getRedeemAmount() + ".";
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
		if (player.hasRolled()) {
			if (!player.hasBought()) {
				if (!player.hasBoosted()) {
					if (player.getFuel() > 0) {
						String res = player.useBoost()  +" and landed on "+ locations.get(player.getPos()).getId()+".";
						res += landedOn(player, locations.get(player.getPos()), 1);
						return res;
					}
					return "Your vehicle is out of fuel!";
				}
				return "You've already used your vehicle this turn!";
			}
			return "You can't use your vehicle after buying a property!";
		}
		return "You must roll before using your vehicle.";
	}

	public String build(Player player, NamedLocation loc, int numToBuild, int id) {
		if (loc instanceof InvestmentProperty) {
			InvestmentProperty property = (InvestmentProperty) loc;
			if (property.isOwned()) {
				if (property.getOwner().equals(player)) {
					if (!property.isMortgaged()) {
						if(player.ownsThree(property.getColor())){
							if (property.build(numToBuild)) {
								if(numToBuild>1)
									return player.getCharName() + " built " + numToBuild + " houses on " + property.getId() + ".";
								else
									return player.getCharName() + " built " + numToBuild + " house on " + property.getId() + ".";
							} else {
								return property.getBuildDemolishError();
							}

						}
						return "You need to own 3 " + property.getColour() + "'s before you can improve a property!";
					}
					return property.getId() + " is mortgaged! ";
				}
				return "You don't own " + property.getId();
			}
			return property.getId() + " is unowned.";
		}
		return " You cant improve " + loc.getId();
	}


	public String demolish(Player player, NamedLocation loc, int numToDemolish, int id) {
		if (loc instanceof InvestmentProperty) {
			InvestmentProperty property = (InvestmentProperty) loc;
			if (property.isOwned()) {
				if (property.getOwner().equals(player)) {
					if (!property.isMortgaged()) {
						if (property.demolish(numToDemolish)) {
							if(numToDemolish>1)
								return player.getCharName() + " demolished " + numToDemolish + " houses on " + property.getId() + ".";
							else
								return player.getCharName() + " demolished " + numToDemolish + " house on " + property.getId() + ".";
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

	private String landedOn(Player player, NamedLocation location, int spaces){
		if(location instanceof PrivateProperty){
			PrivateProperty property = (PrivateProperty) location;
			if(property.isOwned()){
				if(!property.getOwner().equals(player)){

					String res = "\n"+ property.getId() + " is owned by " + property.getOwner().getCharName() + ".";
					if(property instanceof InvestmentProperty){
						InvestmentProperty p = (InvestmentProperty) property;
						player.setDebt(p.getBaseRentAmount(), property.getOwner());
						if(p.hasTrap()) res+= p.activateTrap(player);
						return res;
					}
					else if(property instanceof Station){
						//Station
						Station s = (Station) property;
						player.setDebt(s.getRentalAmount(), property.getOwner());
						if(s.hasTrap()) res += s.activateTrap(player);
						return res;
					}
					else{
						//Utility
						Utility u = (Utility) property;
						player.setDebt(u.getRentalAmount(spaces), property.getOwner());
						if(u.hasTrap()) res += u.activateTrap(player);
						return res;
					}
				}
				return "\nYou own "+ property.getId() + ".";	
			}

			return "\n"+ property.getId() + " is unowned. It can be purchased for $" + property.getPrice() + ".";
		}
		else if(location instanceof TaxSquare){
			TaxSquare tax = (TaxSquare) location;
			return tax.activate(player);
		}
		else if(location instanceof ChanceSquare){
			ChanceSquare chance = (ChanceSquare) location; 
			return chance.activate(player);
		}
		else if(location instanceof SpecialSquare){
			SpecialSquare square = (SpecialSquare) location; 
			return square.activate(player);
		}
		return "";
	}


	public String setTrap(Player player, NamedLocation loc) {
		if (loc instanceof RentalProperty) {
			RentalProperty property = (RentalProperty) loc;
			if (property.isOwned()) {
				if (property.getOwner().equals(player)) {
					if (!property.isMortgaged()) {
						if(!property.hasTrap()){
							return property.setTrap();
						}
						return property.getId() + " already has a trap set!";
					}
					return property.getId() + " is mortgaged! ";
				}
				return "You don't own " + property.getId();
			}
			return property.getId() + " is unowned.";
		}
		return " You cant demolish on " + loc.getId();
	}


	public String bankrupt(Player player) {
		for(PrivateProperty p : player.getOwnedProperties()){
			p.setUnOwned();
		}
		return player.getCharName() + " has declared bankruptcy and any property they own has been released. ";
	}


}
