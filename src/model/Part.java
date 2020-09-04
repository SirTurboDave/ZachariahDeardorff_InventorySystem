package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class Part {
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty price;
    private final SimpleIntegerProperty id, stock, min, max;

    public Part(int id, String name, double price, int stock, int max, int min) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.stock = new SimpleIntegerProperty(stock);
        this.price = new SimpleDoubleProperty(price);
        this.max = new SimpleIntegerProperty(max);
        this.min = new SimpleIntegerProperty(min);
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
}