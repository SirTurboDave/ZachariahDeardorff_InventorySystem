package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty price;
    private final SimpleIntegerProperty id, stock, min, max;
    
    public Product(int id, String name, double price, int stock, int max, int min) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.stock = new SimpleIntegerProperty(stock);
        this.price = new SimpleDoubleProperty(price);
        this.max = new SimpleIntegerProperty(max);
        this.min = new SimpleIntegerProperty(min);        
    }
    
    public Product() {
        this.id = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty("");
        this.stock = new SimpleIntegerProperty(0);
        this.price = new SimpleDoubleProperty(0.0);
        this.max = new SimpleIntegerProperty(0);
        this.min = new SimpleIntegerProperty(0);
    }
    
    public final String getName() {
        return name.get();
    }

    public final void setName(String name) {
        this.name.set(name);
    }

    public final double getPrice() {
        return price.get();
    }

    public final void setPrice(double price) {
        this.price.set(price);
    }

    public final int getId() {
        return id.get();
    }

    public final void setId(int id) {
        this.id.set(id);
    }

    public final int getStock() {
        return stock.get();
    }

    public final void setStock(int stock) {
        this.stock.set(stock);
    }

    public final int getMin() {
        return min.get();
    }

    public final void setMin(int min) {
        this.min.set(min);
    }

    public final int getMax() {
        return max.get();
    }

    public final void setMax(int max) {
        this.max.set(max);
    }
    
    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();
 
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
