import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;


public class Data {
    private ArrayList<StockPrice> prices;

    public Data(String path) {
        deserializeJSON(path);
    }

    public ArrayList<StockPrice> getPrices() {
        return prices;
    }

    /**
     * This fucntion reads the test.json from hypoport.json and deserialize it to a PriceCollection Object
     *
     * @param path : string containing path to json file
     */
    public void deserializeJSON(String path) {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(path));

            // deserialize with gson.fromJson
            PriceCollection priceCollection = gson.fromJson(reader, PriceCollection.class);
            prices = priceCollection.getPriceList();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return date of the day with the lowest price
     */
    public Date getDayMinPrice() {
        prices.sort((o1, o2) -> Float.compare(o1.getLow(), o2.getLow()));

        System.out.println(prices.get(0).getDate() + " " + prices.get(0).getLow());
        return convertEpochToDate(prices.get(0).getDate());
    }

    /**
     * @return date of the day with the highest price
     */
    public Date getDayMaxPrice() {
        prices.sort((o1, o2) -> Float.compare(o2.getHigh(), o1.getHigh()));
        return convertEpochToDate(prices.get(0).getDate());
    }

    /**
     * @return date of the day with the biggest difference between closing price and opening price
     */
    public Date getDayMaxDifference() {
        prices.sort((o1, o2) -> -Float.compare(o2.getDifference(), o1.getDifference()));
        return convertEpochToDate(prices.get(0).getDate());
    }

    /**
     * This functions calculates the average closing price
     *
     * @return average closing price
     */
    public float getAverageClosePrice() {
        float sum = 0;
        int size = prices.size();
        int i = 0;

        while (i < size) {
            sum += prices.get(i).getClose();
            i++;
        }

        return sum / ((float) size);
    }

    /**
     * @param epoch : date in epoch format
     * @return date in java date format
     */
    public Date convertEpochToDate(long epoch) {
        return new Date(epoch * 1000);
    }
}

