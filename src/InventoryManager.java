import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryManager {

    private static DatabaseManager database;

    public InventoryManager(DatabaseManager database) {
        this.database = database;
    }

    public void addProduct(Product product) {
        database.connect();
        database.insert(product.getName(), product.getDesc(), product.getPrice(), product.getQuantity());
        database.disconnect();
    }

    public static void updateProduct(String pName, String key, int value) {
        database.connect();
        database.update(pName, key, value);
        database.disconnect();
    }

    public void deleteProduct(String pName) {
        database.connect();
        database.remove(pName);
        database.disconnect();
    }

    public void displayProduct() {
        database.connect();
        database.fetchProducts();
        database.disconnect();
    }

    public List<Product> returnProductData(String key) {

        DatabaseManager.connect();
        List<Product> productData = new ArrayList<>();
        String query = "SELECT * FROM products WHERE productName = '" + key + "'";

        try (PreparedStatement preparedStatement = DatabaseManager.connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery();) {

            while (resultSet.next()) {
                int id = resultSet.getInt("productId");
                String name = resultSet.getString("productName");
                String desc = resultSet.getString("productDesc");
                int price = resultSet.getInt("productPrice");
                int quantity = resultSet.getInt("productQuantity");
                productData.add(new Product(name, desc, price, quantity));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return productData;
    }
}
