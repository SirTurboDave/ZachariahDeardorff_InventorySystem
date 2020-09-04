/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author sirtu
 */
public class OutsourcedPart extends Part {
    
    public SimpleStringProperty company;
 
    public SimpleStringProperty companyProperty() {
        return company;
    }
    
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String company) {
        super(id, name, price, stock, min, max);
        this.company = new SimpleStringProperty(company);
    }
    
    public static void addOutsourcedPart(int id, String name, double price, int stock, int min, int max, String company) {
        Part outsourcedPart = new OutsourcedPart(id, name, price, stock, min, max, company);
        Inventory.parts.add(outsourcedPart);
    }
    
    public void setCompany(String company) {
        this.company.set(company);
    }
    
    public String getCompany() {
        return company.get();
    }
}
