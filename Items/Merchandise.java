package Items;

import java.util.Map;

public class Merchandise extends ItemCharacteristics {
    private String merchandiseType; // e.g., T-Shirts, Bags, Mugs, Others

    public Merchandise(String itemCode, String itemName, String itemCategory, Map<String, Float> sizesAndPricesMap, String merchandiseType) {
        super(itemCode, itemName, itemCategory, sizesAndPricesMap);
        this.merchandiseType = merchandiseType;
    }

    public String getMerchandiseType() {
        return merchandiseType;
    }

    public void setMerchandiseType(String merchandiseType) {
        this.merchandiseType = merchandiseType;
    }
}
