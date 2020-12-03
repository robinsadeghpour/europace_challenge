import java.nio.file.FileSystems;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        String path = FileSystems.getDefault().getPath("src/main/resources/hypoport.json").toString();
        Data data = new Data(path);

        System.out.println(data.getDayMaxPrice());
        System.out.println(data.getDayMinPrice());
        System.out.println(data.getDayMaxDifference());
        System.out.println(data.getAverageClosePrice());
    }
}


