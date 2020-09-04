/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static model.Inventory.parts;

/**
 *
 * @author sirtu
 */
public class main extends Application {

    public main() {
    Inventory.parts.add(new OutsourcedPart(0, "1000 Processor", 100.0, 12, 100, 1, "Lintel"));   
    Inventory.parts.add(new OutsourcedPart(1, "3000 Processor", 150.0, 3, 100, 1, "A. Mandy"));
    Inventory.parts.add(new InhousePart(2, "Case", 40.0, 22, 100, 1, 1));
    Inventory.parts.add(new InhousePart(3, "Lighting Fixture", 20.0, 18, 100, 1, 2));
    Inventory.parts.add(new OutsourcedPart(4, "Low Graphics Card", 119.0, 8, 100, 1, "A. Mandy"));
    Inventory.parts.add(new OutsourcedPart(5, "High Graphics Card", 450.0, 2, 100, 1, "Lintel"));
    Inventory.parts.add(new OutsourcedPart(6, "Motherboard", 75.0, 15, 100, 1, "Lintel"));
        
    Inventory.products.add(new Product(0, "Base", 500.0, 12, 1, 5));   
    Inventory.products.add(new Product(1, "Base w/ Processor Upgrade", 650.0, 3, 100, 1));
    Inventory.products.add(new Product(2, "Base w/ Graphics Upgrade", 800.0, 22, 100, 1));
    Inventory.products.add(new Product(3, "High Performance", 1200.0, 8, 100, 1));
    Inventory.products.add(new Product(4, "Dual High Graphics Pack", 850.0, 2, 100, 1));
    Inventory.products.add(new Product(5, "Quad High Graphics Pack", 1600.0, 15, 100, 1));
    
    //Product.associatedParts.add();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Inventory.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}