package BrainStem.Brain.Cerebellum;

import javafx.beans.property.*;

/**
 * Abstract class, Blueprint for the InHouse Parts, and Outsourced Parts.
 */

public abstract class Part {

    private final IntegerProperty partID;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inStock;
    private final IntegerProperty min;
    private final IntegerProperty max;



    public Part() {
        partID = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();
        inStock = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
    }


    // Getters
    public IntegerProperty partIDproperty() {
        return partID;
    }

    public StringProperty partNameProperty() {
        return name;
    }

    public DoubleProperty partPriceProperty() {
        return price;
    }

    public IntegerProperty partInvProperty() {
        return inStock;
    }


    /**
     * returns the Part ID Number
     * @return
     */
    public int getPartID() {
        return this.partID.get();
    }

    /**
     * returns the part Name
     * @return
     */
    public String getPartName() {
        return this.name.get();
    }

    /**
     * returns the part price as a decimal
     * @return
     */
    public double getPartPrice() {
        return this.price.get();
    }

    /**
     * returns the number of parts in stock
     * @return
     */
    public int getPartInStock() {
        return this.inStock.get();
    }

    /**
     * returns the minimum number of products in inventory
     * @return
     */
    public int getPartMin() {
        return this.min.get();
    }

    /**
     * returns the Maximum number of parts in the number
     * @return
     */
    public int getPartMax() {
        return this.max.get();
    }

    /**
     * sets the Part ID Number
     * @param partID
     */

    public void setPartID(int partID) {
        this.partID.set(partID);
    }

    /**
     *  sets the name of the Part
     * @param name
     */
    public void setPartName(String name) {
        this.name.set(name);
    }

    /**
     * Sets the price of the Part
     * @param price
     */
    public void setPartPrice(double price) {
        this.price.set(price);
    }

    /**
     * sets the number of parts in stock
     * @param inStock
     */
    public void setPartInStock(int inStock) {
        this.inStock.set(inStock);
    }

    /**
     * sets the minimum number of parts in inventory
     * @param min
     */
    public void setPartMin(int min) {
        this.min.set(min);
    }

    /**
     * sets the maximum number of parts in inventory
     * @param max
     */
    public void setPartMax(int max) {
        this.max.set(max);
    }

    /**
     * checks on the validity of the part for error handling
     * @param name name of the part
     * @param min minimum number of parts
     * @param max maximum number of parts
     * @param inv number of parts in inventory
     * @param price price of part
     * @param error Error message
     * @return returns error message
     */

    public static String validation(String name, int min, int max, int inv, double price, String error){
        if (name == null) {
            error = error + "name field is empty ";
        }
        if (inv < 1) {
            error = error + "stock can't be less than 1. ";
        }
        if (price <= 0) {
            error = error + "price must be greater than 0.00 ";
        }
        if (max < min) {
            error = error + "maximum number of parts cannot be less than the minimum ";
        }
        if (inv < min || inv > max) {
            error = error + "inventory needs to be between the Min and Max. ";
        }
       return error;
    }
}
