package BrainStem.Brain.LeftBrain;

import BrainStem.Brain.Cerebellum.Part;
import BrainStem.Brain.Cerebellum.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Inv {
    private static ObservableList<Product> prodInv = FXCollections.observableArrayList();
    private static ObservableList<Part> partInv = FXCollections.observableArrayList();
    private static int ProductCount = 0;
    private static int PartCount = 0;

    /**
     * returns the list of parts in the inventory
     * @return
     */
    public static ObservableList<Part> GetPartInv() {
        return partInv;
    }

    /**
     * returns the list of products in inventory
     * @return
     */
    public static ObservableList<Product> getProdInv() {
        return prodInv;
    }

    /**
     * adds the newPart to the Parts Inventory list
     * @param NewPart
     */
    public static void addPart(Part NewPart) {partInv.add(NewPart);}
    /**
     * adds the NewProd to the Products Inventory list
     * @param NewProd
     */
    public static void addProd(Product NewProd) {prodInv.add(NewProd);}

    /**
     * Uodates the value at index i with the values of Referenced part values in the part inventory
     * @param i
     * @param Reference
     */
    public static void updatePart(int i, Part Reference){
        partInv.set(i, Reference);
    }
    /**
     * Uodates the value at index i with the values of Referenced Product values in the Product inventory
     * @param i
     * @param Reference
     */
    public static void updateProd(int i, Product Reference) {prodInv.set(i, Reference);}

    /**
     * Removes Part Delete from the Part Inventory
     * @param Delete
     */
    public static void DeletePart(Part Delete){partInv.remove(Delete); }


    /**
     * Removes Product Delete from the Product Inventory
     * @param Delete
     */
    public static void DeleteProduct(Product Delete){prodInv.remove(Delete);}

    /**
     * Performs search operations on the list of parts that have been created, Does numerical searches, Partial searches,
     * and Complete searches
     * @param query
     * @return
     *
     * RUNTIME ERROR: When implementing this originally it would not find search queries with partial information, and even when it did only displayed the first
     * result, changing this to return a list of found objects allowed for multiple results to be displayed, and using the Contains() function
     * allowed me to make this work with partial information.
     */
    public static List<Integer> findPart(String query) {
        boolean isFound = false;
        List<Integer> index = new ArrayList<Integer>();
        int test;
        try {
            test = Integer.parseInt(query);
        }catch (NumberFormatException e){
            test = -1;
        }
        if (test >= 0) {
            for (int i = 0; i < partInv.size(); i++) {
                if (test == partInv.get(i).getPartID()) {
                    index.add(i);
                    isFound = true;
                }
            }
        } else {
            for (int i = 0; i < partInv.size(); i++) {
                query = query.toLowerCase();
                if (query.equals(partInv.get(i).getPartName().toLowerCase()) || partInv.get(i).getPartName().toLowerCase().contains(query)) {
                    index.add(i);
                    isFound = true;
                }
            }
        }
        if (isFound == true) {
            return index;
        }
        else {
            System.out.println("No products found.");
            return index;
        }

    }

    /**
     * Performs search operations on the list of parts that have been created, Does numerical searches, Partial searches,
     * and Complete searches
     * @param query
     * @return
     *
     * RUNTIME ERROR: When implementing this originally it would not find search queries with partial information, and even when it did only displayed the first
     * result, changing this to return a list of found objects allowed for multiple results to be displayed, and using the Contains() function
     * allowed me to make this work with partial information.
     */
    public static List<Integer> findProd(String query) {
        boolean isFound = false;
        List<Integer> index = new ArrayList<Integer>();
        int test;
        try {
            test = Integer.parseInt(query);
        }catch (NumberFormatException e){
            test = -1;
        }
        if (test >= 0) {
            for (int i = 0; i < prodInv.size(); i++) {
                if (Integer.parseInt(query) == prodInv.get(i).getProID()) {
                    index.add(i);
                    isFound = true;
                }
            }
        } else {
            for (int i = 0; i < prodInv.size(); i++) {
                query = query.toLowerCase();
                if (query.equals(prodInv.get(i).getProductName().toLowerCase()) || prodInv.get(i).getProductName().toLowerCase().contains(query)) {
                    index.add(i);
                    isFound = true;
                }
            }
        }
            if (isFound == true) {
                return index;
            }
            else {
                System.out.println("No products found.");
                return index;
            }

        }

    /**
     * returns the active number of parts to accurately ID the newly created parts without repeats
     * @return
     *
     * FUTURE IMPLEMENTATION: When parts are deleted the counter is decremented, but this can still lead to duplicate IDs, in the future making this
     * detect the first available unused number would be useful to reduce the number of errors.
     */
    public static int GetPartCount(){ PartCount++;  return PartCount;}

    /**
     * returns the active number of products to accurately ID the newly created parts without repeats
     * @return
     *
     * FUTURE IMPLEMENTATION: When products are deleted the counter is decremented, but this can still lead to duplicate IDs, in the future making this
     * detect the first available unused number would be useful to reduce the number of errors.
     */

    public static int GetProdCount(){ProductCount++; return ProductCount;}

    /**
     * reduces the active number of parts to accurately ID the newly created parts without repeats
     * @return
     *
     * FUTURE IMPLEMENTATION: When parts are deleted the counter is decremented, but this can still lead to duplicate IDs, in the future making this
     * detect the first available unused number would be useful to reduce the number of errors.
     */

    public static void ReducePartCount(){if (PartCount > 0) { PartCount -=1;}}

    /**
     * reduces the active number of products to accurately ID the newly created parts without repeats
     * @return
     *
     * FUTURE IMPLEMENTATION: When parts are deleted the counter is decremented, but this can still lead to duplicate IDs, in the future making this
     * detect the first available unused number would be useful to reduce the number of errors.
     */

    public static void ReduceProdCount(){if (ProductCount > 0) { ProductCount -=1;} }

    /**
     * Validates the safe deletion of parts from the list by checking if it is in use
     * @param Reference
     * @return
     */

    public static boolean PartDelValidation(Part Reference){
        boolean inUse = false;
        for (int i = 0; i < prodInv.size(); i++) {
            if (prodInv.get(i).getProductParts().contains(Reference)) {
                inUse = true;
            }
        }
        return inUse;
    }

    /**
     * validates the safe deletion of parts from the list by checking if any parts are still being used by it, and thus if
     * it is in use.
     *
     * @param Reference
     * @return
     */

    public static boolean ProdDelValidation(Product Reference){
    boolean inUse = false;
    int productID = Reference.getProID();
        for (int i=0; i < prodInv.size(); i++) {
            if (prodInv.get(i).getProID() == productID) {
                if (!prodInv.get(i).getProductParts().isEmpty()) {
                    inUse = true;
                }
            }
        }
        return inUse;
    }


}
