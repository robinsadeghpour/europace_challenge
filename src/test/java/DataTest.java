import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {
    private String path =  FileSystems.getDefault().getPath("src/test/resources/test.json").toString();


    @org.junit.jupiter.api.Test
    void deserializeJSON() {
        Data data = new Data(path);
        ArrayList<StockPrice> list1 = initializePriceCollection().getPriceList();
        ArrayList<StockPrice> list2 = data.getPrices();

        assertEquals(list1.size(), list2.size(), "lists not same size");
        list1.sort((o1, o2) -> -Float.compare(o1.getClose(), o2.getClose()));
        list2.sort((o1, o2) -> -Float.compare(o1.getClose(), o2.getClose()));
        StockPrice item1;
        StockPrice item2;

        for (int i = 0; i < list1.size(); i++) {
            item1 = list1.get(i);
            item2 = list2.get(i);

            assertEquals(item1.getDate(), item2.getDate());
        }
    }

    @org.junit.jupiter.api.Test
    void getDayMinPrice() {
        Data data = new Data(path);
        Date date1 = data.getDayMinPrice();
        Date date2 = data.convertEpochToDate(1605772800);

        assertTrue(compareDates(date1, date2), "not correct min date");
    }

    @org.junit.jupiter.api.Test
    void getDayMaxPrice() {
        Data data = new Data(path);
        Date date1 = data.getDayMaxPrice();
        Date date2 = data.convertEpochToDate(1605686400);

        assertTrue(compareDates(date1, date2), "not correct max date");
    }

    @org.junit.jupiter.api.Test
    void getDayMaxDifference() {
        Data data = new Data(path);
        Date date1 = data.getDayMaxDifference();
        Date date2 = data.convertEpochToDate(1605686400);

        assertTrue(compareDates(date1, date2), "not correct max difference date");
    }

    @org.junit.jupiter.api.Test
    void getAverageClosePrice() {
        Data data = new Data(path);
        float avgPrice = (float) (433.5 + 436.5) / 2;
        assertEquals(data.getAverageClosePrice(), avgPrice, "not correct max difference date");
    }

    /**
     * This functions compares two Dates
     *
     * @param date1 date to be compared
     * @param date2 date to be compared
     * @return true if date, month and year are the same; otherwise false
     */
    private Boolean compareDates(Date date1, Date date2) {
        /* to extract date, month and year create calender objects,
            because the functions which extract the properties from date object are deprecated
         */
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        int day1 = cal1.get(Calendar.DATE);
        int month1 = cal1.get(Calendar.MONTH);
        int year1 = cal1.get(Calendar.YEAR);
        int day2 = cal2.get(Calendar.DATE);
        int month2 = cal2.get(Calendar.MONTH);
        int year2 = cal2.get(Calendar.YEAR);

        return day1 == day2 && year1 == year2 && month1 == month2;
    }

    private PriceCollection initializePriceCollection() {
        ArrayList<StockPrice> prices = new ArrayList<>();
        StockPrice price1 = new StockPrice(1605772800, 427, 439, 427, (float) 436.5, 7232, (float) 436.5);
        StockPrice price2 = new StockPrice(1605686400, 439, 450, (float) 428.5, (float) 433.5, 5604, (float) 433.5);

        prices.add(price1);
        prices.add(price2);

        return new PriceCollection(prices);
    }
}