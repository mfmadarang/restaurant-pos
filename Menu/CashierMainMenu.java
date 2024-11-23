package Menu;
import java.util.Scanner;

public class CashierMainMenu {
    private Scanner scanner = new Scanner(System.in);

    public void selectCashierItemType() {
        System.out.println("Select Items.Item Type:");
        System.out.println("(1) - Drinks");
        System.out.println("(2) - Food");
        System.out.println("(3) - Merchandise");
        System.out.println("(4) - Cancel Transaction");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                selectDrinkType();
                break;
            case 2:
                selectFoodType();
                break;
            case 3:
                selectMerchandiseType();
                break;
            case 4:
                System.out.println("Transaction cancelled. Returning to main menu.");
                break;
            default:
                System.out.println("Invalid input. Please try again.");
                selectCashierItemType();
        }
    }

    public void selectDrinkType() {
        System.out.println("Select Drink Type:");
        System.out.println("(1) Espresso Drinks");
        System.out.println("(2) Blended Drinks");
        System.out.println("(3) Tea");
        System.out.println("(4) Others");
        // needs logic for selecting food type
    }

    public void selectFoodType() {
        System.out.println("Select Food Type:");
        System.out.println("(1) Pastries");
        System.out.println("(2) Cakes");
        System.out.println("(3) Sandwiches");
        System.out.println("(4) Pastas");
        System.out.println("(5) Others");
        // needs logic for selecting food type
    }

    public void selectMerchandiseType() {
        System.out.println("Select Merchandise Category:");
        System.out.println("(1) T-Shirts");
        System.out.println("(2) Bags");
        System.out.println("(3) Mugs");
        System.out.println("(4) Others");
        // needs logic for selecting merchandise type
    }
}
