/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Product;

public class InventoryController implements Initializable {
    
    @FXML private TableView<Product> productsTableView;
    @FXML private TableColumn<Part, Integer> idProductColumn;
    @FXML private TableColumn<Part, String> nameProductColumn;
    @FXML private TableColumn<Part, Integer> stockProductColumn;
    @FXML private TableColumn<Part, Double> priceProductColumn;
    
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> idPartColumn;
    @FXML private TableColumn<Part, String> namePartColumn;
    @FXML private TableColumn<Part, Integer> stockPartColumn;
    @FXML private TableColumn<Part, Double> pricePartColumn;
    

    @FXML private TextField productSearchTextField;
    @FXML private TextField partSearchTextField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        idProductColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameProductColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockProductColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceProductColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productsTableView.setItems(Inventory.products);
        
        FilteredList<Product> filteredProducts = new FilteredList<>(Inventory.products, p -> true);
        productSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProducts.setPredicate((Product product) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return product.getName().toLowerCase().contains(lowerCaseFilter); 
            });
        });
        SortedList<Product> sortedProducts = new SortedList<>(filteredProducts);
        sortedProducts.comparatorProperty().bind(productsTableView.comparatorProperty());
        productsTableView.setItems(sortedProducts);
        
        idPartColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        namePartColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockPartColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePartColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        partsTableView.setItems(Inventory.parts);
        
        FilteredList<Part> filteredParts = new FilteredList<>(Inventory.parts, p -> true);
        partSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredParts.setPredicate((Part part) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return part.getName().toLowerCase().contains(lowerCaseFilter); 
            });
        });
        SortedList<Part> sortedParts = new SortedList<>(filteredParts);
        sortedParts.comparatorProperty().bind(partsTableView.comparatorProperty());
        partsTableView.setItems(sortedParts);
    }    
    
    public void addPartButtonPushed(ActionEvent event) throws IOException {
        
        Parent addPartParent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(addPartScene);
        window.show();
    }
    
    public void modifyPartButtonPushed(ActionEvent event) throws IOException {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        
        
        if (selectedPart != null) {
            
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyPart.fxml"));
        Parent modifyPartParent = loader.load();
        Scene modifyPartScene = new Scene(modifyPartParent);
        
        ModifyPartController controller = loader.getController();
        controller.initData(selectedPart);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(modifyPartScene);
        window.show();
        } else {
 
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part.");
            alert.showAndWait();
        }        
    }
    
    public void deletePartButtonPushed(ActionEvent event) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        
        if(selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Deleting Part");
            alert.setContentText("Would you like to delete " + selectedPart.getName() + "?");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> Inventory.getParts().remove(selectedPart));
            
            partsTableView.setItems(Inventory.getParts());
        } else {
 
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part.");
            alert.showAndWait();
        }
    }
    
    public void addProductButtonPushed(ActionEvent event) throws IOException {
        
        Parent addProductParent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(addProductScene);
        window.show();
    }
    
    public void modifyProductButtonPushed(ActionEvent event) throws IOException {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
                
        if (selectedProduct != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyProduct.fxml"));
            Parent modifyProductParent = loader.load();
            Scene modifyProductScene = new Scene(modifyProductParent);

            ModifyProductController controller = loader.getController();
            controller.initData(selectedProduct);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(modifyProductScene);
            window.show();
        } else {
 
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select a product.");
            alert.showAndWait();
        }
    }
    
    public void deleteProductButtonPushed(ActionEvent event) {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            if (selectedProduct.associatedParts.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Delete");
                alert.setHeaderText("Deleting Part");
                alert.setContentText("Would you like to delete " + selectedProduct.getName() + "?");
                alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> Inventory.getProducts().remove(selectedProduct));
                
                productsTableView.setItems(Inventory.getProducts());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Associated Part Found");
                alert.setHeaderText("Associated Part Found");
                alert.setContentText("Can't delete a product with an associated part. Delete all associated parts first.");
                alert.showAndWait();
            }
        } else {
 
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select an product.");
            alert.showAndWait();
        }
    }

    public void clearProductSearchButtonPushed() {
        productSearchTextField.setText("");
    }
    
    public void clearPartSearchButtonPushed() {
        partSearchTextField.setText("");
    }
    
    public void exitButtonPushed() {
        Platform.exit();
    }
}