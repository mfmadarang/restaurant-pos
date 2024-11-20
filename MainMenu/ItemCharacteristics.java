package MainMenu;

import java.util.HashMap;
import java.util.Map;

public abstract class ItemCharacteristics {
    private String itemCode;
    private String itemName;
    private String itemCategory;
    private Map<String, Float> sizesAndPricesMap = new HashMap<String, Float>();

    public ItemCharacteristics(String itemCode, String itemName, String itemCategory, Map<String, Float> sizesAndPricesMap) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.sizesAndPricesMap = sizesAndPricesMap;
    }

}







