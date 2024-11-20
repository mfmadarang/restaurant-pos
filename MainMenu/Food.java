package MainMenu;

import java.util.Map;

public class Food extends ItemCharacteristics {
    public String Pastries;
    public String Cakes;
    public String Sandwiches;
    public String Pastas;
    private String Others; /** Temporarily added since idk how to implement it yet */

    public Food(String itemCode, String itemName, String itemCategory, Map<String, Float> sizesAndPricesMap
    , String Pastries, String Cakes, String Sandwiches, String Pastas, String Others) {
        super(itemCode, itemName, itemCategory, sizesAndPricesMap);
        this.Pastries = Pastries;
        this.Cakes = Cakes;
        this.Sandwiches = Sandwiches;
        this.Pastas = Pastas;
        this.Others = Others;
    }


}
