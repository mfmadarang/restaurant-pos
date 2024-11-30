package Items;

import java.util.Map;

public class Merchandise extends ItemCharacteristics {
    private String merchandiseCategory; // e.g., T-Shirts, Bags, Mugs, Others

    public Merchandise(String itemCode, String itemName, String itemType, Map<String, Float> sizesAndPricesMap, String merchandiseCategory) {
        super(itemCode, itemName, itemType, sizesAndPricesMap);
        this.merchandiseCategory = merchandiseCategory;
    }

    public String getMerchandiseCategory() {
        return merchandiseCategory;
    }

    public void setMerchandiseCategory(String merchandiseCategory) {
        this.merchandiseCategory = merchandiseCategory;
    }
}
