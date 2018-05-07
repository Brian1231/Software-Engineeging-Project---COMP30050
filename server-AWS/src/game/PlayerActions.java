package game;

import java.util.ArrayList;

import game_models.*;
import main.Main;

/*
 * Class used to hold methods to process player commands
 *
 * */
class PlayerActions {

    public String roll(Player player, ArrayList<NamedLocation> locations) {
        if (!player.hasRolled()) {
            Main.dice.roll();
            StringBuilder res = new StringBuilder();
            int[] dice_values = Main.dice.getDiceValues();
            if (!player.isInJail()) {

                if (locations.get(player.getPos()) instanceof RentalProperty) {
                    RentalProperty prop = (RentalProperty) locations.get(player.getPos());
                    if (!prop.isOwned()) {
                        //Rolling away from unowned property
                        Main.gameState.addPendingAction(player.getID(), "roll"); //Save roll action to be executed after auction
                        Main.gameState.startAuction(prop, null, 1); //Initiate auction
                        Main.clientUpdater.updateDesktopAuction();
                        return player.getCharName() + " didn't buy " + prop.getId()
                            + " so it's goes to the highest bidder!";
                    }

                }
                //Roll to move player around board
                int spaces = Main.dice.getRollResult();
                res.append(player.moveForward(spaces)).append(" and arrived at ").append(locations.get(player.getPos()).getId()).append(".");
                res.append(landedOn(player, locations.get(player.getPos()), spaces));
                res.append(Main.gameState.villainGangCheck(player));
                //Check for doubles. If so roll again
                if (dice_values[0] == dice_values[1])
                    res.append(player.getCharName()).append(" rolled doubles! Roll again!");
                else
                    player.useRoll();
                return res.toString();
            }

            //Roll to escape prison
            res.append(player.getCharName()).append(" attempts to escape prison...\n");
            if (dice_values[0] == dice_values[1]) {
                res.append(player.getCharName()).append(" has successfully escaped prison!");
                player.releaseFromJail();
                return res.toString();
            }
            player.useRoll();
            res.append(player.getCharName()).append("'s escape plan fails miserably.");
            //Check how long the player has spent in jail
            if (player.incrementJailTurns()) {
                res.append(player.getCharName()).append(" has spent their time in prison and will be released next turn.");
            }
            return res.toString();
        }
        return player.getCanName() + " has already rolled this turn.";
    }

    public String buy(Player player, NamedLocation tile) {

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

    //Put a property up for auction
    public String sell(Player player, NamedLocation loc, int price) {
        if (loc instanceof RentalProperty) {
            RentalProperty property = (RentalProperty) loc;
            if (property.isOwned()) {
                if (property.getOwner().equals(player)) {
                    if (property.isMortgaged()) {
                        return "You can't auction a mortgaged property.";
                    }
                    if (property instanceof InvestmentProperty) {
                        InvestmentProperty investmentProperty = (InvestmentProperty) property;
                        if (investmentProperty.getNumHouses() > 0) {
                            return "You cant sell a house that is upgraded!";
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

    public String mortgage(Player player, NamedLocation loc) {
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

    public String redeem(Player player, NamedLocation loc) {
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
        if (!player.isInJail()) {
            if (player.hasRolled()) {
                if (!player.hasBought()) {
                    if (!player.hasBoosted()) {
                        if (!player.isInDebt()) {
                            if (player.getFuel() > 0) {
                                if (locations.get(player.getPos()) instanceof RentalProperty) {
                                    RentalProperty prop = (RentalProperty) locations.get(player.getPos());
                                    if (!prop.isOwned()) {
                                        //Boosting away from an unowned property. Start an auction first
                                        Main.gameState.addPendingAction(player.getID(), "boost");
                                        Main.gameState.startAuction(prop, null, 1);
                                        Main.clientUpdater.updateDesktopAuction();
                                        return player.getCharName() + " didn't buy " + prop.getId()
                                            + " so it's goes to the highest bidder!";
                                    }
                                }
                                return (player.useBoost() + " and landed on " + locations.get(player.getPos()).getId()
                                    + ".") +
                                    landedOn(player, locations.get(player.getPos()), 1) +
                                    Main.gameState.villainGangCheck(player);
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
        return "You can't boost your way out of jail!";
    }

    public String build(Player player, NamedLocation loc, int numToBuild) {
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

    public String demolish(Player player, NamedLocation loc, int numToDemolish) {
        if (loc instanceof InvestmentProperty) {
            InvestmentProperty property = (InvestmentProperty) loc;
            if (property.isOwned()) {
                if (property.getOwner().equals(player)) {
                    if (!property.isMortgaged()) {
                        if (property.demolish(numToDemolish)) {
                            player.receiveMoney(property.getHouseSellValue());
                            return player.getCharName() + " downgraded " + property.getId() + " and received " + property.getHouseSellValue() + " SHM.";
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
        return " You cant downgrade on " + loc.getId();
    }

    public String done(Player player, NamedLocation location) {
        if (!player.isInDebt()) {
            if (player.hasRolled()) {
                if (location instanceof RentalProperty) {
                    RentalProperty prop = (RentalProperty) location;
                    if (!prop.isOwned()) {
                        //Ending turn on an unowned property. Auction it off first
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

    //Checks for player landing on a new tile
    private String landedOn(Player player, NamedLocation location, int spaces) {
        if (location instanceof RentalProperty) {
            RentalProperty property = (RentalProperty) location;
            if (property.isOwned()) {
                if (!property.getOwner().equals(player)) {
                    if (!property.isMortgaged()) {
                        StringBuilder res = new StringBuilder();
                        res.append("\n").append(property.getId()).append(" is owned by ").append(property.getOwner().getCharName()).append(".");
                        if (property instanceof InvestmentProperty) {
                            InvestmentProperty p = (InvestmentProperty) property;
                            player.setDebt(p.getRentalAmount(), property.getOwner());
                            res.append(player.getCharName()).append(" owes ").append(property.getOwner().getCharName()).append(" ").append(p.getRentalAmount()).append(" SHM. ");
                            if (p.hasTrap())
                                res.append(p.activateTrap(player));
                            return res.toString();
                        } else if (property instanceof Station) {
                            // Station
                            Station s = (Station) property;
                            player.setDebt(s.getRentalAmount(), property.getOwner());
                            res.append(player.getCharName()).append(" owes ").append(property.getOwner().getCharName()).append(" ").append(s.getRentalAmount()).append(" SHM. ");
                            if (s.hasTrap())
                                res.append(s.activateTrap(player));
                            return res.toString();
                        } else {
                            // Utility
                            Utility u = (Utility) property;
                            player.setDebt(u.getRentalAmount(spaces), property.getOwner());
                            res.append(player.getCharName()).append(" owes ").append(property.getOwner().getCharName()).append(" ").append(u.getRentalAmount(spaces)).append(" SHM. ");
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
                        if (!property.hasTrap()) {
                            if (player.getBalance() >= property.getTrapSetPrice()) {
                                player.payMoney(property.getTrapSetPrice());
                                return property.setTrap();
                            } else
                                return "You can't afford the cost of " + property.getTrapSetPrice() + " SHM to set a trap.";
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
