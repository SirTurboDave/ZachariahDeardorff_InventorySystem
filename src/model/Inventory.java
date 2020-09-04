/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 
public class Inventory {
 
    public static Part selectedPart;
 
    public static Product selectedProduct;
 
    public void addPart(int id, String name, double price, int stock, int min, int max, int machine) {
        Part newPart = new InhousePart(id, name, price, stock, min, max, machine);
        Inventory.parts.add(newPart);
    }
    
    public void addProduct(int id, String name, double price, int stock, int min, int max) {
        Product newProduct = new Product(id, name, price, stock, min, max);
        Inventory.products.add(newProduct);
    }
 
    public static ObservableList<Part> parts = FXCollections.observableArrayList();
    
    public static ObservableList<Part> getParts() {
        return parts;
    }
 
    public static ObservableList<Product> products = FXCollections.observableArrayList();
    
    public static ObservableList<Product> getProducts() {
        return products;
    }
}
