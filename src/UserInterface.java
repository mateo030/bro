import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Scanner scanner;
    private InventoryManager manager;
    private DatabaseManager database;

    public UserInterface(Scanner scanner, InventoryManager manager, DatabaseManager database) {
        this.scanner = scanner;
        this.manager = manager;
        this.database = database;
    }

    public void run() {

        while (true) {
            System.out.println("===SALES MANAGEMENT SYSTEM===");
            System.out.println("1. Add product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Display Products");
            System.out.println("5. Process Sale");
            System.out.println("6. Exit");

            int command = scanner.nextInt();
            scanner.nextLine();

            switch(command) {
                case 1 -> addProduct(scanner, manager);
                case 2 -> updateProduct(scanner, manager);
                case 3 -> deleteProduct(scanner, manager);
                case 4 -> manager.displayProduct();
                case 5 -> confirmSale(manager);
                case 6 -> {
                    System.out.println("Exiting...");
                    closeResources(scanner, database);
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

    }

    public void addProduct(Scanner scanner, InventoryManager manager) {
        System.out.println("Product Name:");
        String pName = scanner.nextLine();
        System.out.println("Description:");
        String pDesc = scanner.nextLine();
        System.out.println("Price:");
        int pPrice = scanner.nextInt();
        System.out.println("Quantity:");
        int pQuantity = scanner.nextInt();

        Product product = new Product(pName, pDesc, pPrice, pQuantity);
        manager.addProduct(product);
    }

    public void updateProduct(Scanner scanner, InventoryManager manager) {
        System.out.println("Product Name:");
        String pName = scanner.nextLine();
        System.out.println("productPrice / productQuantity");
        String key = scanner.nextLine();
        System.out.println("Value:");
        int value = scanner.nextInt();

        manager.updateProduct(pName, key, value);
    }

    public void deleteProduct(Scanner scanner, InventoryManager manager) {
        System.out.println("Product Name:");
        String pName = scanner.nextLine();

        manager.deleteProduct(pName);
    }

    public void closeResources(Scanner scanner, DatabaseManager database) {
        scanner.close();
        database.disconnect();
    }

    public void confirmSale(InventoryManager manager) {

        System.out.println("Product Name:");
        String pName = scanner.nextLine();
        List<Product> productData = manager.returnProductData(pName);

        Product soldProduct = null;

        for (Product product : productData) {
            String name = product.getName();
            String desc = product.getDesc();
            int price = product.getPrice();
            int quantity = product.getQuantity();

            System.out.println("Product ID: " + name);
            System.out.println("Product Name: " + desc);
            System.out.println("Price: " + price);
            System.out.println("Quantity: " + quantity);
            soldProduct = new Product(name, desc, price, quantity);
        }

        System.out.println("Sold Amount:");
        int amount = scanner.nextInt();
        Sales sale = new Sales(99, soldProduct, amount);
        Sales.processSale(soldProduct, amount);
    }
}
