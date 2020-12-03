import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;


class DataTest {
    private final String path1 =  FileSystems.getDefault().getPath("src/test/resources/test.json").toString();
    private final String path2 =  FileSystems.getDefault().getPath("src/test/resources/empty_list.json").toString();



    @org.junit.jupiter.api.Test
    void deserializeJSON() {
        assertTrue(checkDeserializeEqual(new Data(path1)), "correct data set failed");
        assertFalse(checkDeserializeEqual(new Data(path2)), "empty list failed");
    }

    /**
     * this is a helpfer function for deserializeJSON test
     *
     * @param data : data object which should be tested
     *
     * @return true if the data object is correctly deserialized
     */
    Boolean checkDeserializeEqual (Data data) {
        ArrayList<StockPrice> list1 = initializePriceCollection().getPriceList();
        ArrayList<StockPrice> list2 = data.getPrices();

        boolean isSizeEqual = list1.size() == list2.size();
        boolean areItemsEqual = false;

        list1.sort((o1, o2) -> -Float.compare(o1.getClose(), o2.getClose()));
        list2.sort((o1, o2) -> -Float.compare(o1.getClose(), o2.getClose()));
        StockPrice item1;
        StockPrice item2;

        for (int i = 0; i < list2.size() && isSizeEqual; i++) {
            item1 = list1.get(i);
            item2 = list2.get(i);

            areItemsEqual = item1.getDate() == item2.getDate();
        }

        return isSizeEqual && areItemsEqual;
    }

    @org.junit.jupiter.api.Test
    void getDayMinPrice() {
        Data data = new Data(path1);
        Date date1 = data.getDayMinPrice();
        Date date2 = data.convertEpochToDate(1605772800);

        assertTrue(compareDates(date1, date2));
    }

    @org.junit.jupiter.api.Test
    void getDayMaxPrice() {
        Data data = new Data(path1);
        Date date1 = data.getDayMaxPrice();
        Date date2 = data.convertEpochToDate(1605686400);

        assertTrue(compareDates(date1, date2), "not correct max date");
    }

    @org.junit.jupiter.api.Test
    void getDayMaxDifference() {
        Data data = new Data(path1);
        Date date1 = data.getDayMaxDifference();
        Date date2 = data.convertEpochToDate(1605686400);

        assertTrue(compareDates(date1, date2), "not correct max difference date");
    }

    @org.junit.jupiter.api.Test
    void getAverageClosePrice() {
        Data data = new Data(path1);
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
        StockPrice price1 = new StockPrice(1605772800, 427, 439, 427, (float) 436.5);
        StockPrice price2 = new StockPrice(1605686400, 439, 450, (float) 428.5, (float) 433.5);

        prices.add(price1);
        prices.add(price2);

        return new PriceCollection(prices);
    }
}