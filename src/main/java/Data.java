import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Data {

    public void deserializeJSON() {
        try {
            Gson gson = new Gson();
            String path = Paths.get("").toAbsolutePath()+ "\\src\\main\\resources\\hypoport.json";
            Reader reader = Files.newBufferedReader(Paths.get(path));

            PriceCollection prices = gson.fromJson(reader, PriceCollection.class);

            System.out.println(prices.getPriceList().get(0).getLow());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

