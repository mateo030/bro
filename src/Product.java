import java.sql.*;

public class Product {

    private String name;
    private String description;
    private int price;
    private int quantity;

    public Product(String name, String description, int price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.description = desc;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void updateStock() {
        InventoryManager.updateProduct(name, "productQuantity", quantity);
        System.out.println(name + "Stock succesfully updated");
    }

    public void reduceStock(int amount) {
        if (amount <= quantity) {
            this.quantity -= amount;
            updateStock();
        } else {
            System.out.println("Insufficient stock");
        }
    }

    @Override
    public String toString() {
        return "Name: " + name + " Description: " + description + " Price: " + price + " Quantity: " + quantity;
    }
}


