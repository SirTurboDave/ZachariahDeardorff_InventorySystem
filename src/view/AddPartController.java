/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.InhousePart;
import model.Inventory;
import model.OutsourcedPart;
import model.Part;

public class AddPartController implements Initializable {
    
    @FXML private RadioButton inhousePartRadioButton;
    @FXML private RadioButton outsourcedPartRadioButton;
    
    @FXML private Label priceOrCostLabel;
    @FXML private Label companyOrMachineLabel;
    
    @FXML private TextField idTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField stockTextField;
    @FXML private TextField priceOrCostTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;
    @FXML private TextField companyOrMachineTextField;
    
    private ToggleGroup partSourceToggleGroup;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partSourceToggleGroup = new ToggleGroup();
        this.inhousePartRadioButton.setToggleGroup(partSourceToggleGroup);
        this.outsourcedPartRadioButton.setToggleGroup(partSourceToggleGroup);        
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
        Integer machine = 0;
        String company = "";
        
        try {
            id = Inventory.parts.size();
            name = nameTextField.getText();
            stock = Integer.valueOf(stockTextField.getText());
            price = Double.valueOf(priceOrCostTextField.getText());
            max = Integer.valueOf(maxTextField.getText());
            min = Integer.valueOf(minTextField.getText());
            if (inhousePartRadioButton.isSelected()) {            
                machine = Integer.valueOf(companyOrMachineTextField.getText());
            }
            company = companyOrMachineTextField.getText();
            
            if (stock > max || stock < min || min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Inventory");
                alert.setHeaderText("Incorrect Inventory");
                alert.setContentText("The quantity entered for inventory was incorrect. Please check and try again.");
                alert.showAndWait();
                
            } else if (id < 1 || id > Inventory.parts.size()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Part ID");
                alert.setHeaderText("Incorrect Part ID");
                alert.setContentText("Please enter a valid part ID.");
                alert.showAndWait();
                
            } else if (outsourcedPartRadioButton.isSelected()) {
            OutsourcedPart.addOutsourcedPart(id, name, price, stock, max, min, company);
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Part Added");
            alert.setContentText("A new part has been added.");
            alert.showAndWait();
            
            Parent addPartParent = FXMLLoader.load(getClass().getResource("Inventory.fxml"));
            Scene addPartScene = new Scene(addPartParent);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(addPartScene);
            window.show();
        
            } else if (inhousePartRadioButton.isSelected()) {
            InhousePart.addInhousePart(id, name, price, stock, max, min, machine);
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Part Added");
            alert.setContentText("A new part has been added.");
            alert.showAndWait();
            
            Parent inventoryParent = FXMLLoader.load(getClass().getResource("Inventory.fxml"));
            Scene inventoryScene = new Scene(inventoryParent);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(inventoryScene);
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
    
    public void radioButtonChanged() {
        if (this.partSourceToggleGroup.getSelectedToggle().equals(this.inhousePartRadioButton)) {
            companyOrMachineLabel.setText("Machine ID");
            priceOrCostLabel.setText("Cost");
        }
        
        if (this.partSourceToggleGroup.getSelectedToggle().equals(this.outsourcedPartRadioButton)) {
            companyOrMachineLabel.setText("Company Name");
            priceOrCostLabel.setText("Price");
        }
    }
}