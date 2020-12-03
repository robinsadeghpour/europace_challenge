class StockPrice {
    private final long date;
    private final float open;
    private final float high;
    private final float low;
    private final float close;

    public StockPrice(long date, float open, float high, float low, float close) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    public long getDate() {
        return date;
    }

    public float getHigh() {
        return high;
    }

    public float getLow() {
        return low;
    }

    public float getClose() {
        return close;
    }

    /**
     * @return difference between close price and open price
     */
    public float getDifference() {
        return Math.abs(close - open);
    }
}
