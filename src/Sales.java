import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Sales {

    private static int salesID;
    private static Product product;
    private static int quantitySold;
    private static int totalPrice;
    private static Date saleDate;

    public Sales(int salesID, Product product, int quantitySold) {
        this.salesID = salesID;
        this.product = product;
        this.quantitySold = quantitySold;
        this.totalPrice = product.getPrice() * quantitySold;
        this.saleDate = new Date();
    }

    public static boolean processSale(Product product, int amount) {
        if (product.getQuantity() >= amount) {
            product.reduceStock(amount);
            recordSale(product, amount);
            return true;
        } else {
            System.out.println("Out of stock");
            return false;
        }
    }

    public static void recordSale(Product product, int amount) {

        int revenue = amount * product.getPrice();
        DatabaseManager.connect();

        String query = "INSERT INTO sales (product_name, quantity, revenue) VALUES (?,?,?)";

        try (PreparedStatement statement = DatabaseManager.connection.prepareStatement(query)) {

            statement.setString(1, product.getName());
            statement.setInt(2, amount);
            statement.setInt(3, revenue);

            statement.executeUpdate();
            System.out.println("Sale has been recorded");
            System.out.println(generateReceipt(product.getName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String generateReceipt(String productName) {
        return "Sale Receipt\n" +
                "Sale ID: " + salesID + "\n" +
                "Item: " + productName + "\n" +
                "Quantity Sold: " + quantitySold + "\n" +
                "Total Price: $" + totalPrice + "\n" +
                "Date: " + saleDate + "\n";
    }

    public int getSalesID() {return salesID;}
    public Product getProduct() {return product;}
    public int getQuantitySold() {return quantitySold;}
    public int getTotalPrice() {return totalPrice;}
    public Date getSaleDate() {return saleDate;}

    @Override
    public String toString() {
        return  "Sale ID: " + salesID + "\n" +
                "Item: " + product.getName() + "\n" +
                "Quantity Sold: " + quantitySold + "\n" +
                "Total Price: $" + totalPrice + "\n" +
                "Date: " + saleDate + "\n";
    }
}
