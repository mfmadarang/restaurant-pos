package MainMenu;

import java.util.Map;

public class Drink extends ItemCharacteristics{
    private String ExpressoDrinks;
    private String BlendedDrinks;
    private String Tea;
    private String Others; /** Probably make a class where "OTHERS" have its own characteristics */

    public Drink(String itemCode, String itemName, String itemCategory, Map<String, Float> sizesAndPricesMap
    ,String ExpressoDrinks, String BlendedDrinks, String Tea, String Others) {
        super(itemCode, itemName, itemCategory, sizesAndPricesMap);
        this.ExpressoDrinks = ExpressoDrinks;
        this.BlendedDrinks = BlendedDrinks;
        this.Tea = Tea;
        this.Others = Others;
    }
}
