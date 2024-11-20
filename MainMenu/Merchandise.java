package MainMenu;

import java.util.Map;

public class Merchandise extends ItemCharacteristics{
    private String Tshirts;
    private String Bags;
    private String Mugs;
    private String Others;

    public Merchandise(String itemCode, String itemName, String itemCategory, Map<String, Float> sizesAndPricesMap
    , String Tshirts, String Bags, String Mugs, String Others) {
        super(itemCode, itemName, itemCategory, sizesAndPricesMap);
        this.Tshirts = Tshirts;
        this.Bags = Bags;
        this.Mugs = Mugs;
        this.Others = Others;
    }
}
