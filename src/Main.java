import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DatabaseManager database = new DatabaseManager();
        InventoryManager manager = new InventoryManager(database);
        UserInterface ui = new UserInterface(scanner, manager, database);

        ui.run();
    }
}
