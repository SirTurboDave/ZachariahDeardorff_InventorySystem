/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author sirtu
 */
public class InhousePart extends Part {
    public SimpleIntegerProperty machine;
 
    public SimpleIntegerProperty machine() {
        return machine;
    }
    
    public InhousePart(int id, String name, double price, int stock, int min, int max, int machine) {
        super(id, name, price, stock, min, max);
        this.machine = new SimpleIntegerProperty(machine);
    }
    
    public static void addInhousePart(int id, String name, double price, int stock, int min, int max, int machine) {
        Part inhousePart = new InhousePart(id, name, price, stock, min, max, machine);
        Inventory.parts.add(inhousePart);
    }
    
    public void setMachine(Integer machineID) {
        this.machine.set(machineID);
    }
 
    public int getMachine() {
        return machine.get();
    }
    
}
