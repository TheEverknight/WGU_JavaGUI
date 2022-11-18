package BrainStem.Brain.Cerebellum;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product Base class, implemented to create new products
 */
public class Product {

    private ObservableList<Part> parts = FXCollections.observableArrayList();
    private IntegerProperty proID;
    private StringProperty name;
    private DoubleProperty price;
    private IntegerProperty inStock;
    private IntegerProperty min;
    private IntegerProperty max;


    // Constructor

    /**
     * constructor for new product
     */
    public Product() {
        proID = new SimpleIntegerProperty();
        name        = new SimpleStringProperty();
        price       = new SimpleDoubleProperty();
        inStock     = new SimpleIntegerProperty();
        min         = new SimpleIntegerProperty();
        max         = new SimpleIntegerProperty();
    }


    /**
     * returns the Product ID as an integer property
      * @return
     */
    public IntegerProperty proIDProperty() {
        return proID;
    }

    /**
     * returns the product name as a string property
     * @return
     */
    public StringProperty proNameProperty() {
        return name;
    }

    /**
     * returns the Product Price as a double property
     * @return
     */
    public DoubleProperty proPriceProperty() {
        return price;
    }

    /**
     * returns the number of products in stock as an integer property
     * @return
     */
    public IntegerProperty proInvProperty() {
        return inStock;
    }

    /**
     * returns the Product ID number
     * @return
     */
    public int getProID() {
        return this.proID.get();
    }
    /**
     * returns the product name
     * @return
     */
    public String getProductName() {
        return this.name.get();
    }
    /**
     * returns the Product Price
     * @return
     */
    public double getProductPrice() {
        return this.price.get();
    }
    /**
     * returns the number of products in stock
     * @return
     */
    public int getProductInStock() {
        return this.inStock.get();
    }
    /**
     * returns the minimum number of products in stock
     * @return
     */
    public int getProductMin() {
        return this.min.get();
    }
    /**
     * returns the Maximum number of products
     * @return
     */
    public int getProductMax() {
        return this.max.get();
    }

    /**
     * returns a list of parts in the product
     * @return
     */
    public ObservableList getProductParts() {
        return parts;
    }


    /**
     * sets the Id of the product
     * @param proID
     */
    public void setProID(int proID) {
        this.proID.set(proID);
    }

    /**
     * sets the name of the product
     * @param name
     */
    public void setProductName(String name) {
        this.name.set(name);
    }

    /**
     * sets the product price
     * @param price
     */
    public void setProductPrice(double price) {
        this.price.set(price);
    }

    /**
     * sets the number of products in stock
     * @param inStock
     */
    public void setProductInStock(int inStock) {
        this.inStock.set(inStock);
    }

    /**
     * sets the minimum number of products in inventory
     * @param min
     */
    public void setProductMin(int min) {
        this.min.set(min);
    }

    /**
     * sets the maximum number of products
     * @param max
     */
    public void setProductMax(int max) {
        this.max.set(max);
    }

    /**
     * sets the parts included in the product
     * @param parts
     */
    public void setProductParts(ObservableList<Part> parts) {
        this.parts = parts;
    }

    /**
     * checks that the product is valid before the data is manipulated
     * @param name the name of the product
     * @param min the minimum number of products
     * @param max the maximum number of products
     * @param inv the number of products in stock
     * @param price the price per product
     * @param parts the parts in the product
     * @param error error message
     * @return error message returned
     */
    public static String validation(String name, int min, int max, int inv, double price, ObservableList<Part> parts, String error) {
         double sum = 0;
         for (int i = 0; i < parts.size(); i++) {
             sum = sum + parts.get(i).getPartPrice();
         }
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
            error = error + "maximum number of products cannot be less than the minimum ";
        }
        if (inv < min || inv > max) {
            error = error + "inventory needs to be between the Min and Max. ";
        }
        if (sum > price) {
            error = error + "product price has to be greater than the cost of the parts. ";
        }
        return error;
    }
}
