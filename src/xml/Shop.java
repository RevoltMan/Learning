package xml;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    public static class Goods {
        public List<String> names = new ArrayList<>();

        public Goods() {

        }
    }
    public Goods goods = new Goods();
    public int count;
    public double profit;
    public String[] secretData;

    public Shop () {

    }
}
