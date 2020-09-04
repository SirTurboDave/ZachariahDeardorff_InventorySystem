/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import model.Part;
import model.Product;

public class AddProductController implements Initializable {
    
    @FXML private TableView<Part> availablePartsTableView;
    @FXML private TableColumn<Part, Integer> idAvailablePartsColumn;
    @FXML private TableColumn<Part, String> nameAvailablePartsColumn;
    @FXML private TableColumn<Part, Integer> stockAvailablePartsColumn;
    @FXML private TableColumn<Part, Double> priceAvailablePartsColumn;
    
    @FXML private TableView<Part> associatedPartsTableView;    
    @FXML private TableColumn<Part, Integer> idAssociatedPartsColumn;
    @FXML private TableColumn<Part, String> nameAssociatedPartsColumn;
    @FXML private TableColumn<Part, Integer> stockAssociatedPartsColumn;
    @FXML private TableColumn<Part, Double> priceAssociatedPartsColumn;
    
    @FXML private TextField partSearchTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField stockTextField;
    @FXML private TextField priceOrCostTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;    
    
    Product selectedProduct = new Product();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        idAvailablePartsColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameAvailablePartsColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockAvailablePartsColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceAvailablePartsColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        idAssociatedPartsColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameAssociatedPartsColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockAssociatedPartsColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceAssociatedPartsColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        availablePartsTableView.setItems(Inventory.parts);
        
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
        sortedParts.comparatorProperty().bind(availablePartsTableView.comparatorProperty());
        availablePartsTableView.setItems(sortedParts);
        
    }
    
    public void clearButtonPushed(ActionEvent event) throws IOException {
        partSearchTextField.setText("");
    }
    
    public void addPartButtonPushed(ActionEvent event) throws IOException {
        Part selectedPart = availablePartsTableView.getSelectionModel().getSelectedItem();
        
        if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Part Selected");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part.");
            alert.showAndWait();
        } else {
            selectedProduct.associatedParts.add(selectedPart);
            associatedPartsTableView.setItems(selectedProduct.getAllAssociatedParts());
        }
    }
    
    public void removePartButtonPushed(ActionEvent event) throws IOException {
        Part selectedPart = associatedPartsTableView.getSelectionModel().getSelectedItem();
        
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Part Selected");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Confirm Removal");
            alert.setHeaderText("Removing Part");
            alert.setContentText("Would you like to remove " + selectedPart.getName() + "?");
            alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> selectedProduct.getAllAssociatedParts().remove(selectedPart));
            
            associatedPartsTableView.setItems(selectedProduct.getAllAssociatedParts());
        }
    }
    
    public void cancelButtonPushed(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("Inventory.fxml"));
        Scene addPartScene = new Scene(addPartParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(addPartScene);
        window.show();
    }
    
    public void saveButtonPushed(ActionEvent event) throws IOException {
        
        Integer id = 0;
        String name = "";
        Integer stock = 0;
        Double price = 0.0;
        Integer max = 0;
        Integer min = 0;
        
        try {
            id = Inventory.products.size();
            name = nameTextField.getText();
            stock = Integer.valueOf(stockTextField.getText());
            price = Double.valueOf(priceOrCostTextField.getText());
            max = Integer.valueOf(maxTextField.getText());
            min = Integer.valueOf(minTextField.getText());
 
            
            Double partSum = 0.0;

            for (int i = 0; i < selectedProduct.associatedParts.size(); i++) {
                partSum += selectedProduct.associatedParts.get(i).getPrice();
            }

            if (stock > max || stock < min || min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Inventory");
                alert.setHeaderText("Incorrect Inventory");
                alert.setContentText("The quantity entered for inventory was incorrect. Please check and try again.");
                alert.showAndWait();

            } else if (associatedPartsTableView.getItems().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Associated Part Missing");
                alert.setHeaderText("Associated Part Missing");
                alert.setContentText("There are no parts associated with this product. Select at least one part to be associated with this product to continue.");
                alert.showAndWait();

            } else if (price < partSum) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Price Too Low");
                alert.setHeaderText("Price Too Low");
                alert.setContentText("Product price must be higher than sum of associated parts.");
                alert.showAndWait();

            } else if (id < 1 || id > Inventory.products.size()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Product ID");
                alert.setHeaderText("Incorrect Product ID");
                alert.setContentText("Please enter a valid product ID.");
                alert.showAndWait();

            } else {
                Product newProduct = new Product(id, name, price, stock, max, min);
                Inventory.products.add(newProduct);
                newProduct.getAllAssociatedParts().addAll(selectedProduct.getAllAssociatedParts());    

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Product Added");
                alert.setContentText("A new product has been added.");
                alert.showAndWait();
                
                Parent addPartParent = FXMLLoader.load(getClass().getResource("Inventory.fxml"));
                Scene addPartScene = new Scene(addPartParent);
            
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                window.setScene(addPartScene);
                window.show();
                
            }
        } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Data");
                alert.setHeaderText("Incorrect Data");
                alert.setContentText("Data was entered incorrectly. Please check and try again.");
                alert.showAndWait();
            }
    }
}