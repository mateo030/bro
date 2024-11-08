import java.sql.*;

public class DatabaseManager {

    private static final String url = "jdbc:mysql://localhost:3306/javaproducts";
    private static final String user = "root";
    private static final String password = "";
    static Connection connection;

    public DatabaseManager() {

    }

    public static Connection connect() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String pName, String pDesc, int pPrice, int pQuantity) {

        String query = "INSERT INTO products (productName, productDesc, productPrice, productQuantity) VALUES (?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, pName);
            preparedStatement.setString(2, pDesc);
            preparedStatement.setInt(3, pPrice);
            preparedStatement.setInt(4, pQuantity);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Inserted " + rowsAffected + " rows into the database");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void remove(String pName) {

        String query = "DELETE FROM products WHERE productName = '" + pName + "'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Removed " + rowsAffected + " rows from the database");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void update(String pName, String key, int value) {

        String query = "UPDATE products SET " + key + " = " + value + " WHERE productName = '" + pName + "'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Updated " + rowsAffected + " rows from the database");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void fetchProducts() {

        String query = "SELECT * FROM products";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery();) {

            while (resultSet.next()) {
                int productID = resultSet.getInt("productId");
                String productName = resultSet.getString("productName");
                String productDesc = resultSet.getString("productDesc");
                int productPrice = resultSet.getInt("productPrice");
                int productQuantity = resultSet.getInt("productQuantity");

                System.out.println("[ID] [NAME] [DESCRIPTION] [PRICE] [QUANTITY]");
                System.out.println(productID + " " + productName + " " + productDesc + " " + productPrice + " " + productQuantity);

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
