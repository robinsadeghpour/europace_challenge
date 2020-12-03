import java.util.ArrayList;

class PriceCollection {
    private ArrayList<StockPrice> prices;

    public PriceCollection(ArrayList<StockPrice> prices) {
        this.prices = prices;
    }

    public ArrayList<StockPrice> getPriceList() {
        return prices;
    }
}
