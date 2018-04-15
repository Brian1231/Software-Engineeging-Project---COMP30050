package game_interfaces;

public interface Taxable {

    // get % of income to be charged as tax
    int getIncomePercentage(Playable player, double percentage);

    // get flat figure to be charged as tax
    int getFlatAmount();
}
