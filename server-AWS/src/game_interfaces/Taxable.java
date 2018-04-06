package game_interfaces;

public interface Taxable {

    // get % of income to be charged as tax
    int getIncomePercentage(Playable player);
    void setIncomePercentage(int percentage);

    // get flat figure to be charged as tax
    int getFlatAmount(Playable player);
    void setFlatAmount(int flatAmount);
}
