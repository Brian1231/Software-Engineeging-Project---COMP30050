package game;

import java.util.ArrayList;
import java.util.Random;

import main.Main;

public class PlayerActions {

	Random random = new Random();

	public String roll(Player player, int id, ArrayList<NamedLocation> locations) {
		if(!player.hasRolled()) {
			Main.dice.roll();
			StringBuilder res = new StringBuilder();
			int[] dice_values = Main.dice.getDiceValues();
			if (!player.isInJail()) {

				if (locations.get(player.getPos()) instanceof RentalProperty) {
					RentalProperty prop = (RentalProperty) locations.get(player.getPos());
					if (!prop.isOwned()) {
						Main.gameState.addPendingAction(player.getID(), "roll");
						Main.gameState.startAuction(prop, null, 1);
						Main.clientUpdater.updateDesktopAuction();
						return player.getCharName() + " didn't buy " + prop.getId()
								+ " so it's goes to the highest bidder!";
					}

				}
				int spaces = Main.dice.getRollResult();
				res.append(
						player.moveForward(spaces) + " and arrived at " + locations.get(player.getPos()).getId() + ".");
				res.append(landedOn(player, locations.get(player.getPos()), spaces));
				res.append(Main.gameState.villainGangCheck(player));
				if (dice_values[0] == dice_values[1])
					res.append(player.getCharName() + " rolled doubles! Roll again!");
				else
					player.useRoll();
				return res.toString();
			}

			res.append(player.getCharName() + " attempts to escape prison...\n");
			if (dice_values[0] == dice_values[1]) {
				res.append(player.getCharName() + " has successfully escaped prison!");
				player.releaseFromJail();
				return res.toString();
			}
			player.useRoll();
			res.append(player.getCharName() + "'s escape plan fails miserably.");
			if (player.incrementJailTurns()) {
				res.append(player.getCharName() + " has spent their time in prison and will be released next turn.");
			}
			return res.toString();
		}
		return player.getCanName() + " has already rolled this turn.";
	}

	public String buy(Player player, NamedLocation tile, int id) {

		if (tile instanceof RentalProperty) {
			RentalProperty prop = (RentalProperty) tile;
			if (!prop.isOwned()) {
				if (player.getBalance() >= prop.getPrice()) {
					prop.setOwner(player);
					player.addNewPropertyBought(prop, prop.getPrice());
					return player.getCharName() + " bought " + prop.getId() + " for " + prop.getPrice() + " SHM.";
				}
				return "You can't afford this property.";
			}
			if (prop.getOwner().equals(player))
				return "You already own " + prop.getId() + ".";
			else
				return prop.getId() + " is already owned by " + prop.getOwner().getCharName() + ".";
		}
		return "You can't buy that.";
	}

	public String sell(Player player, NamedLocation loc, int price) {
		if (loc instanceof RentalProperty) {
			RentalProperty property = (RentalProperty) loc;
			if (property.isOwned()) {
				if (property.getOwner().equals(player)) {
					if (property instanceof RentalProperty) {
						RentalProperty rental = (RentalProperty) property;
						if (rental.isMortgaged()) {
							return "You can't auction a mortgaged property.";
						}
					}
					Main.gameState.startAuction(property, player, price);
					Main.clientUpdater.updateDesktopAuction();
					return player.getCharName() + " is auctioning " + property.getId() + " for " + property.getPrice()
							+ " SHM!";
				}
				return "You don't own that property.";
			}
			return "That property is unowned.";
		}
		return "You can't auction that.";
	}

	public String mortgage(Player player, NamedLocation loc, int id) {
		if (loc instanceof RentalProperty) {
			RentalProperty property = (RentalProperty) loc;
			if (property.isOwned()) {
				if (property.getOwner().equals(player)) {
					if (!property.isMortgaged()) {
						property.mortgage(player);
						return player.getCharName() + " mortgaged " + property.getId() + " and received "
								+ property.getMortgageAmount() + " SHM.";
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
		if (loc instanceof RentalProperty) {
			RentalProperty property = (RentalProperty) loc;
			if (property.isOwned()) {
				if (property.getOwner().equals(player)) {
					if (property.isMortgaged()) {
						property.redeem(player);
						return player.getCharName() + " redeemed " + property.getId() + " for "
								+ property.getRedeemAmount() + " SHM.";
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
					if (!player.isInDebt()) {
						if (player.getFuel() > 0) {

							if (locations.get(player.getPos()) instanceof RentalProperty) {
								RentalProperty prop = (RentalProperty) locations.get(player.getPos());
								if (!prop.isOwned()) {
									Main.gameState.addPendingAction(player.getID(), "boost");
									Main.gameState.startAuction(prop, null, 1);
									Main.clientUpdater.updateDesktopAuction();
									return player.getCharName() + " didn't buy " + prop.getId()
											+ " so it's goes to the highest bidder!";
								}
							}
							StringBuilder res = new StringBuilder();
							res.append(player.useBoost() + " and landed on " + locations.get(player.getPos()).getId()
									+ ".");
							res.append(landedOn(player, locations.get(player.getPos()), 1));
							res.append(Main.gameState.villainGangCheck(player));
							return res.toString();
						}
						return "Your vehicle is out of fuel!";
					}
					return "You can't run away from your debt of " + player.getDebt() + " SHM!";
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
						if (player.getBalance() >= property.getHousePrice()) {
							if (player.ownsThree(property.getColour())) {
								if (property.build(numToBuild)) {
									player.payMoney(property.getHousePrice());
									return player.getCharName() + " upgraded " + property.getId() + " for " + property.getHousePrice() + " SHM.";
								} else {
									return property.getBuildDemolishError();
								}
							}
							int diff = (property.getHousePrice() - player.getBalance());
							return "You do not have enough money to upgrade the property! You need " + diff + " SHM more!";
						}
						return "You need to own the full colour group before you can upgrade a property!";
					}
					return property.getId() + " is mortgaged! ";
				}
				return "You don't own " + property.getId();
			}
			return property.getId() + " is unowned.";
		}
		return " You cant upgrade " + loc.getId();
	}

	public String demolish(Player player, NamedLocation loc, int numToDemolish, int id) {
		if (loc instanceof InvestmentProperty) {
			InvestmentProperty property = (InvestmentProperty) loc;
			if (property.isOwned()) {
				if (property.getOwner().equals(player)) {
					if (!property.isMortgaged()) {
						if (property.demolish(numToDemolish)) {
							player.receiveMoney(property.getHouseSellValue());
							return player.getCharName() + " downgraded " + property.getId() + " and received " + property.getHouseSellValue() + " SHM.";
						} 
						else {
							return property.getBuildDemolishError();
						}
					}
					return property.getId() + " is mortgaged! ";
				}
				return "You don't own " + property.getId();
			}
			return property.getId() + " is unowned.";
		}
		return " You cant downgrade on " + loc.getId();
	}

	public String done(Player player, NamedLocation location) {
		if (!player.isInDebt()) {
			if (player.hasRolled()) {
				if (location instanceof RentalProperty) {
					RentalProperty prop = (RentalProperty) location;
					if (!prop.isOwned()) {
						Main.gameState.addPendingAction(player.getID(), "done");
						Main.gameState.startAuction(prop, null, 1);
						Main.clientUpdater.updateDesktopAuction();
						return player.getCharName() + " didn't buy " + prop.getId()
								+ " so it's goes to the highest bidder!";
					}
				}
				player.reset();
				Main.gameState.incrementPlayerTurn();
				Main.gameState.updateVillainGang();
				return player.getCharName() + " finished their turn.";
			}
			return "You must roll the dice before ending your turn.";
		}
		return "You must pay your debt of " + player.getDebt() + " SMH before ending your turn.";
	}

	private String landedOn(Player player, NamedLocation location, int spaces) {
		if (location instanceof RentalProperty) {
			RentalProperty property = (RentalProperty) location;
			if (property.isOwned()) {
				if (!property.getOwner().equals(player)) {
					if (!property.isMortgaged()) {
						StringBuilder res = new StringBuilder();
						res.append("\n" + property.getId() + " is owned by " + property.getOwner().getCharName() + ".");
						if (property instanceof InvestmentProperty) {
							InvestmentProperty p = (InvestmentProperty) property;
							player.setDebt(p.getRentalAmount(), property.getOwner());
							res.append(player.getCharName() + " owes " + property.getOwner().getCharName() + " "
									+ p.getRentalAmount() + " SHM. ");
							if (p.hasTrap())
								res.append(p.activateTrap(player));
							return res.toString();
						} else if (property instanceof Station) {
							// Station
							Station s = (Station) property;
							player.setDebt(s.getRentalAmount(), property.getOwner());
							res.append(player.getCharName() + " owes " + property.getOwner().getCharName() + " "
									+ s.getRentalAmount() + " SHM. ");
							if (s.hasTrap())
								res.append(s.activateTrap(player));
							return res.toString();
						} else {
							// Utility
							Utility u = (Utility) property;
							player.setDebt(u.getRentalAmount(spaces), property.getOwner());
							res.append(player.getCharName() + " owes " + property.getOwner().getCharName() + " "
									+ u.getRentalAmount(spaces) + " SHM. ");
							if (u.hasTrap())
								res.append(u.activateTrap(player));
							return res.toString();
						}
					}
					return property.getId() + " is mortgaged";
				}
				return "\nYou own " + property.getId() + ".";
			}

			return "\n" + property.getId() + " is unowned. It can be purchased for " + property.getPrice() + " SHM.";
		} else if (location instanceof TaxSquare) {
			TaxSquare tax = (TaxSquare) location;
			return tax.activate(player);
		} else if (location instanceof ChanceSquare) {
			ChanceSquare chance = (ChanceSquare) location;
			return chance.activate(player);
		} else if (location instanceof SpecialSquare) {
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
							player.payMoney(property.getTrapPrice());
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
		for (RentalProperty p : player.getOwnedProperties()) {
			p.setUnOwned();
		}
		Main.gameState.removePlayer(player);

		return player.getCharName() + " has declared bankruptcy and any property they own has been released.";
	}

	public String bid(Player player, int price) {
		if (Main.gameState.auctionInProgress()) {
			if (Main.gameState.isValidBid(player)) {
				if (Main.gameState.updateAuction(player, price)) {
					Main.clientUpdater.updateDesktopAuction();
					return player.getCharName() + " bids " + price + " SHM.";
				}
				return "You need to bid more!";
			}
			return "You can't bid on your own property!";
		}
		return "There's no auction in progress.";
	}
}
