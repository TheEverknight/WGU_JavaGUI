package BrainStem.Brain.LeftBrain;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import BrainStem.Brain.Cerebellum.Part;
import BrainStem.Brain.Cerebellum.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {
    @FXML
    private TableView<Part> _PartsTable;
    @FXML
    private TableColumn<Part, Integer> _PartsId;
    @FXML
    private TableColumn<Part, String> _PartsName;
    @FXML
    private TableColumn<Part, Integer> _PartsInv;
    @FXML
    private TableColumn<Part, Double> _PartsPrice;
    @FXML
    private TableView<Product> _ProductsTable;
    @FXML
    private TableColumn<Product, Integer> _ProductsID;
    @FXML
    private TableColumn<Product, String> _ProductsName;
    @FXML
    private TableColumn<Product, Integer> _ProductsInv;
    @FXML
    private TableColumn<Product, Double> _ProductsPrice;
    @FXML
    private TextField _PartsSearch;
    @FXML
    private TextField _ProdSearch;


    private static Part modPart;
    private static Product modProd;


    private static int modPartIndex;
    private static int modProdIndex;


    /**
     * this initializes the Main Window, sets the desired attributes of the window and retrieves
     * necessary information
     *
     */
    public void initialize() {
        _PartsId.setCellValueFactory(cellData -> cellData.getValue().partIDproperty().asObject());
        _PartsName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        _PartsInv.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        _PartsPrice.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        _ProductsID.setCellValueFactory(cellData -> cellData.getValue().proIDProperty().asObject());
        _ProductsName.setCellValueFactory(cellData -> cellData.getValue().proNameProperty());
        _ProductsInv.setCellValueFactory(cellData -> cellData.getValue().proInvProperty().asObject());
        _ProductsPrice.setCellValueFactory(cellData -> cellData.getValue().proPriceProperty().asObject());

        updatePartsTable();
        updateProductsTable();
    }

    /**
     * updates the Parts Table view for the Main Window
     */
    public void updatePartsTable() { _PartsTable.setItems(Inv.GetPartInv());}

    /**
     * updates the Products Table view for the Main Window
     */
    public void updateProductsTable() {
        _ProductsTable.setItems(Inv.getProdInv());
    }

    /**
     * this handles the clearing of the search bar, and updating the Parts Table view in the main window of the program
     * @param event
     */
    @FXML
    private void ClearSearch(ActionEvent event) { updatePartsTable(); _PartsSearch.setText(""); }

    /**
     * this handles the clearing of the search bar, and updating the Product Table view in the main window of the program
     * @param event
     */
    @FXML
    private void ClearProdSearch(ActionEvent event) {updateProductsTable(); _ProdSearch.setText("");
    }

    /**
     * this handles the searching of Parts in the main window, handling errors, and updating the table view
     * using the lookupPart method in the Inventory class.
     * @param event Without active error checking this would cause crashes, implemented checks to make sure that the program
     * did not attempt to display null values
     */
    @FXML
    private void PartSearch(ActionEvent event) {
        String query = _PartsSearch.getText();
        List<Integer> partIndex = new ArrayList<Integer>();
        if (Inv.findPart(query).size() == 0) {
            Alert LocalError = new Alert(Alert.AlertType.INFORMATION);

            LocalError.setHeaderText("Part not found");
            LocalError.setContentText("Could not find part");
            LocalError.showAndWait();
        }
        else {
            int x = 0;

            partIndex = Inv.findPart(query);
            ObservableList<Part> tempPartList = FXCollections.observableArrayList();
            while (x < partIndex.size()) {
                Part tempPart = Inv.GetPartInv().get(partIndex.get(x));

                tempPartList.add(tempPart);
                x++;
            }
            _PartsTable.setItems(tempPartList);
        }
    }

    /**
     * this handles the searching of Products in the main window, handling errors, and updating the table view
     * using the lookupPart method in the Inventory class.
     * @param event
     *
     * RUNTIME ERROR: Without active error checking this would cause crashes, implemented checks to make sure that the program
     * did not attempt to display null values
     */
    @FXML
    private void ProdSearch(ActionEvent event) {
        String searchProduct = _ProdSearch.getText();
        List<Integer> prodIndex = new ArrayList<Integer>();
        if (Inv.findProd(searchProduct).size() == 0) {
            Alert LocalError = new Alert(Alert.AlertType.INFORMATION);

            LocalError.setHeaderText("Product not found");
            LocalError.setContentText("The search term entered does not match any known products.");
            LocalError.showAndWait();
        }
        else {
            int x = 0;
            prodIndex = Inv.findProd(searchProduct);
            ObservableList<Product> tempProductList = FXCollections.observableArrayList();

            while (x < prodIndex.size()) {
                Product tempProduct = Inv.getProdInv().get(prodIndex.get(x));
                tempProductList.add(tempProduct);
                x++;
            }
            _ProductsTable.setItems(tempProductList);
        }
    }

    /**
     * handles opening the Add Part Dialogue window
     * @param event
     * @throws IOException
     *
     * RUNTIME ERROR: Given the Project hierarchy set up the way that it is, Many of the FXMLLoader.Load() statements would return a
     * File not Found Error, Causing a large amount of the project to fail to load, This was resolved by telling the Loader to look outside of
     * the current directory and searching in the Formatting directory labeled RightBrain.
     */
    @FXML
    private void InitAddPart(ActionEvent event) throws IOException {


        Parent addPartParent = FXMLLoader.load(getClass().getResource("..//RightBrain//AddPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addPartStage.setScene(addPartScene);
        addPartStage.show();
    }

    /**
     * handles opening the Add Product Dialogue window
     * @param event
     * @throws IOException
     *
     * RUNTIME ERROR: Given the Project hierarchy set up the way that it is, Many of the FXMLLoader.Load() statements would return a
     * File not Found Error, Causing a large amount of the project to fail to load, This was resolved by telling the Loader to look outside of
     *  the current directory and searching in the Formatting directory labeled RightBrain.
     */
    @FXML
    private void InitAddProd(ActionEvent event) throws IOException {
        Parent addProductParent =  FXMLLoader.load(getClass().getResource("..//RightBrain//AddProd.fxml")) ;
        Scene addProductScene = new Scene(addProductParent);
        Stage addProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addProductStage.setScene(addProductScene);
        addProductStage.show();
    }

    /**
     * handles opening the Modify Part Dialogue window
     * @param event
     * @throws IOException
     *
     * RUNTIME ERROR: Given the Project hierarchy set up the way that it is, Many of the FXMLLoader.Load() statements would return a
     * File not Found Error, Causing a large amount of the project to fail to load, This was resolved by telling the Loader to look outside of
     * the current directory and searching in the Formatting directory labeled RightBrain.
     */
    @FXML
    private void InitModPart(ActionEvent event) throws IOException {
        modPart = _PartsTable.getSelectionModel().getSelectedItem();
        modPartIndex = Inv.GetPartInv().indexOf(modPart);
        Parent modifyPartParent = FXMLLoader.load(getClass().getResource("..//RightBrain//ModPart.fxml"));
        Scene modifyPartScene = new Scene(modifyPartParent);
        Stage modifyPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        modifyPartStage.setScene(modifyPartScene);
        modifyPartStage.show();
    }

    /**
     * handles opening the Modify Product Dialogue window
     * @param event
     * @throws IOException
     *
     * RUNTIME ERROR: Given the Project hierarchy set up the way that it is, Many of the FXMLLoader.Load() statements would return a
     * File not Found Error, Causing a large amount of the project to fail to load, This was resolved by telling the Loader to look outside of
     * the current directory and searching in the Formatting directory labeled RightBrain.
     *
     *
     */
    @FXML
    private void InitModProd(ActionEvent event) throws IOException {
        modProd = _ProductsTable.getSelectionModel().getSelectedItem();
        modProdIndex = Inv.getProdInv().indexOf(modProd);
        Parent modifyProductParent = FXMLLoader.load(getClass().getResource("..//RightBrain//ModProd.fxml"));
        Scene modifyProductScene = new Scene(modifyProductParent);
        Stage modifyProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        modifyProductStage.setScene(modifyProductScene);
        modifyProductStage.show();
    }

    /**
     * returns the index of the selected part in the table view
     * @return
     */
    public static int GetModPartIndex() {
        return modPartIndex;
    }

    /**
     * returns the index of the selected Product in the table view
     * @return
     */
    public static int GetModProdIndex() {
        return modProdIndex;
    }

    /**
     * handles the removal of parts from the Inventory, and error handling regarding parts actively in use with products
     * @param event
     *  RUNTIME ERROR: When implementing this method, it became clear that simply deleting the Part could cause problems if it actively
     * is being used by one or more products. the obvious solution is to make sure the product is not using any parts, and inform the user that it cannot be deleted
     * while these parts are in use
     *
     * FUTURE ENHANCEMENT: Having the user be prompted to see if they want to remove the active parts From the products using them,
     * and flag those products to be modified
     */
    @FXML
    private void DeletePart(ActionEvent event) {
        Part part = _PartsTable.getSelectionModel().getSelectedItem();
        if (Inv.PartDelValidation(part)) {
            Alert LocalError = new Alert(Alert.AlertType.INFORMATION);

            LocalError.setHeaderText("cannot be deleted");
            LocalError.setContentText("Part is in use.");
            LocalError.showAndWait();
        }
        else {
            Alert LocalError = new Alert(Alert.AlertType.CONFIRMATION);
            LocalError.initModality(Modality.NONE);

            LocalError.setHeaderText("Delete?");
            LocalError.setContentText("Are you sure?");

            Optional<ButtonType> result = LocalError.showAndWait();

            if (result.get() == ButtonType.OK) {
                Inv.DeletePart(part);
                updatePartsTable();
            }

        }
    }

    /**
     * handles the removal of Products from the Inventory, and error handling regarding parts actively in use with Parts
     * @param event
     *
     *  RUNTIME ERROR: When implementing this method, it became clear that simply deleting the product could cause problems if it actively
     * is using parts. the obvious solution is to make sure the product is not using any parts, and inform the user that it cannot be deleted
     * while these parts are in use
     *
     * FUTURE ENHANCEMENT: Having the user be prompted to see if they want to remove the active parts and put them back into the inventory and continue the delete safely
     * and decrementing the product ID from there.
     */
    @FXML
    private void DeleteProd(ActionEvent event) {
        Product product = _ProductsTable.getSelectionModel().getSelectedItem();
        if (Inv.ProdDelValidation(product)) {

            Alert LocalError = new Alert(Alert.AlertType.INFORMATION);

            LocalError.setHeaderText("cannot be deleted");
            LocalError.setContentText("Product is in use.");
            LocalError.showAndWait();
        } else {
            Alert LocalError = new Alert(Alert.AlertType.CONFIRMATION);

            LocalError.initModality(Modality.NONE);

            LocalError.setHeaderText("Delete?");
            LocalError.setContentText("Are you sure?");

            Optional<ButtonType> result = LocalError.showAndWait();

            if (result.get() == ButtonType.OK) {
                Inv.DeleteProduct(product);
                updateProductsTable();
            }
        }
    }

    /**
     * opens the Confirm exit Dialogue window and awaits input
     * @param event
     */
    @FXML
    private void exit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);

        alert.setHeaderText("Exit?");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }

    }

}
