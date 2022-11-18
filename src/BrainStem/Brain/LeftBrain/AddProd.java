package BrainStem.Brain.LeftBrain;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import BrainStem.Brain.Cerebellum.Part;
import BrainStem.Brain.Cerebellum.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProd implements Initializable {




    @FXML
    private Label _IDLabel;
    @FXML
    private TextField _Name;
    @FXML
    private TextField _Inv;
    @FXML
    private TextField _Price;
    @FXML
    private TextField _Min;
    @FXML
    private TextField _Max;
    @FXML
    private TextField _Query;
    @FXML
    private TableView<Part> _AddPartTable;
    @FXML
    private TableColumn<Part, Integer> _AddID;
    @FXML
    private TableColumn<Part, String> _AddName;
    @FXML
    private TableColumn<Part, Integer> _AddInv;
    @FXML
    private TableColumn<Part, Double> _AddPrice;
    @FXML
    private TableView<Part> _DeletePartTable;
    @FXML
    private TableColumn<Part, Integer> _DeleteID;
    @FXML
    private TableColumn<Part, String> _DeleteName;
    @FXML
    private TableColumn<Part, Integer> _DeleteInv;
    @FXML
    private TableColumn<Part, Double> _DeletePrice;

    private String error = "";

    private ObservableList<Part> parts = FXCollections.observableArrayList();

    private int ID;

    /**
     * this initializes the Modify product Dialogue, sets the desired attributes of the window and retrieves
     * necessary information
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ID = Inv.GetProdCount();

        _IDLabel.setText("" + ID);

        _AddID.setCellValueFactory(cellData -> cellData.getValue().partIDproperty().asObject());

        _AddName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());

        _AddInv.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());

        _AddPrice.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());

        _DeleteID.setCellValueFactory(cellData -> cellData.getValue().partIDproperty().asObject());

        _DeleteName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());

        _DeleteInv.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());

        _DeletePrice.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());




        updateAvailableParts();
        updateUsedParts();
    }

    /**
     * this handles the clearing of the search bar, and updating the Table view in the Add product dialogue
     * @param event
     */
    @FXML
    //Table Search handlers
    void Clear(ActionEvent event) {
        updateAvailableParts();
        _Query.setText("");
    }

    /**
     * this handles the searching of products in the Add product dialogue, handling errors, and updating the table view
     * using the lookupPart method in the Inventory class.
     * @param event
     */
    @FXML
    void Search(ActionEvent event) {
        String searchPart = _Query.getText();
        List<Integer> partIndex = new ArrayList<Integer>();
        if (Inv.findPart(searchPart).size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Part not found");
            alert.showAndWait();
        }
        else {
            int x = 0;

            partIndex = Inv.findPart(searchPart);
            ObservableList<Part> tempPartList = FXCollections.observableArrayList();
            while (x < partIndex.size()) {
                Part tempPart = Inv.GetPartInv().get(partIndex.get(x));

                tempPartList.add(tempPart);
                x++;
            }
            _AddPartTable.setItems(tempPartList);
        }
    }

    /**
     * updates the Add Parts Table view for the Add Product dialogue
     */
    //adds items to table view
    public void updateAvailableParts() {
        _AddPartTable.setItems(Inv.GetPartInv());
    }

    /**
     * updates the Delete Parts Table view for the Add Product dialogue
     */
    //removes items from table view
    public void updateUsedParts() {
        _DeletePartTable.setItems(parts);
    }

    /**
     * handles the adding of parts to a product, and updates the table view accordingly, adding the part to the list of
     * parts within the given product
     * @param event
     */
    @FXML
    void Add(ActionEvent event) {
        Part part = _AddPartTable.getSelectionModel().getSelectedItem();
        parts.add(part);

        updateUsedParts();
    }

    /**
     * handles the deletion of parts from a product, removing them from the parts list of the given product
     * @param event
     */
    @FXML
    void Delete(ActionEvent event) {
        Part part = _DeletePartTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("DELETE");
        alert.setHeaderText("Delete?");
        alert.setContentText("delete Part: " + part.getPartName() + " from parts?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.out.println("Part deleted.");
            parts.remove(part);
        }
        else {
            System.out.println("You clicked cancel.");
        }
    }

    /**
     * this Handles the insertion of new products into the list, and actively looks for any exceptions, or
     * missing information
     * @param event
     * @throws IOException NumberFormatExceotion In the event that input is not the correct format, or blank the program
     * will throw an IOException, if this happens the program will inform the user there was an error. and informs them
     * of the error
     *
     * RUNTIME ERROR: Without this function being implemented the Lists of Products would be added to with missing values, or
     * inconsistent information, This checks for these various problems, such as missing information, or information that conflicts, like the
     * Min being greater than the Max.
     */
    @FXML
    void Save(ActionEvent event) throws IOException {
        String productName = _Name.getText();
        String productMin = _Min.getText();
        String productMax = _Max.getText();
        String productInv = _Inv.getText();
        String productPrice = _Price.getText();


        try{
            error = Product.validation(productName, Integer.parseInt(productMin), Integer.parseInt(productMax), Integer.parseInt(productInv), Double.parseDouble(productPrice), parts, error);
            if (error.length() > 0) {
                Alert LocalError = new Alert(Alert.AlertType.INFORMATION);
                LocalError.setTitle("Error");
                LocalError.setHeaderText("well, that didn't work...");
                LocalError.setContentText(error);
                LocalError.showAndWait();
                error = "";
            }
            else {
                System.out.println("Product name: " + productName);
                Product newProduct = new Product();
                newProduct.setProID(ID);
                newProduct.setProductName(productName);
                newProduct.setProductInStock(Integer.parseInt(productInv));
                newProduct.setProductPrice(Double.parseDouble(productPrice));
                newProduct.setProductMin(Integer.parseInt(productMin));
                newProduct.setProductMax(Integer.parseInt(productMax));
                newProduct.setProductParts(parts);
                Inv.addProd(newProduct);

                Parent addProductSaveParent = FXMLLoader.load(getClass().getResource("..//RightBrain//Face.fxml"));
                Scene scene = new Scene(addProductSaveParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
        catch (NumberFormatException e) {
            Alert LocalError = new Alert(Alert.AlertType.INFORMATION);
            LocalError.setTitle("Error");
            LocalError.setHeaderText("Product could not be added");
            LocalError.setContentText("Form contains blank fields.");
            LocalError.showAndWait();
        }
    }

    /**
     * handles the Cancellation dialogue for Adding products, looking for exceptions
     * @param event
     * @throws IOException
     */
    //creates product cancellation dialogue
    @FXML
    private void Cancel(ActionEvent event) throws IOException {
        Alert LocalError = new Alert(Alert.AlertType.CONFIRMATION);
        LocalError.initModality(Modality.NONE);

        LocalError.setTitle("Cancel");
        LocalError.setHeaderText("Confirm");
        LocalError.setContentText("Are you sure?");
        Optional<ButtonType> result = LocalError.showAndWait();

        if (result.get() == ButtonType.OK) {
            Inv.ReduceProdCount();
            Parent addProductCancel = FXMLLoader.load(getClass().getResource("..//RightBrain//Face.fxml"));
            Scene scene = new Scene(addProductCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else {
            System.out.println("You clicked cancel.");
        }
    }




}


