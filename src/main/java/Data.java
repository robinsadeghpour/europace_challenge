import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;


public class Data {
    private ArrayList<StockPrice> prices;

    public Data() {
        deserializeJSON();
    }

    /**
     * This fucntion reads the JSON from hypoport.json and deserialize it to a PriceCollection Object
     */
    public void deserializeJSON() {
        try {
            Gson gson = new Gson();
            String path = Paths.get("").toAbsolutePath()+ "\\src\\main\\resources\\hypoport.json";
            Reader reader = Files.newBufferedReader(Paths.get(path));

            // deserialize with gson.fromJson
            PriceCollection priceCollection = gson.fromJson(reader, PriceCollection.class);
            prices = priceCollection.getPriceList();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return date of the day with the lowest price
     */
    public Date getDayMinPrice() {
        prices.sort((o1, o2) -> -Float.compare(o1.getLow(), o2.getLow()));
        return convertEpochToDate(prices.get(0).getDate());
    }

    /**
     *
     * @return date of the day with the highest price
     */
    public Date getDayMaxPrice() {
        prices.sort((o1, o2) -> -Float.compare(o1.getHigh(), o2.getHigh()));
        return convertEpochToDate(prices.get(0).getDate());
    }

    /**
     *
     * @return date of the day with the biggest difference between closing price and opening price
     */
    public Date getDayMaxDifference() {
        prices.sort((o1, o2) -> -Float.compare(o1.getDifference(), o2.getDifference()));
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

        return  sum / ((float) size);
    }

    /**
     *
     * @param epoch : date in epoch format
     *
     * @return date in java date format
     */
    public Date convertEpochToDate(long epoch) {
        return new Date(epoch * 1000);
    }
}

