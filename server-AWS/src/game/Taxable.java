package game;

public interface Taxable {

    // get % of income to be charged as tax
    int getIncomePercentage();
    void setIncomePercentage(int percentage);

    // get flat figure to be charged as tax
    int getFlatAmount();
    void setFlatAmount(int flatAmount);
}
