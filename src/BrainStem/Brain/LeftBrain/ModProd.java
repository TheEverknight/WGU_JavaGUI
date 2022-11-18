package BrainStem.Brain.LeftBrain;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import BrainStem.Brain.Cerebellum.Part;
import BrainStem.Brain.Cerebellum.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModProd implements Initializable {


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
   private TextField _Search;
   @FXML
   private TableView<Part> _AddPart;
   @FXML
   private TableColumn<Part, Integer> _AddID;
   @FXML
   private TableColumn<Part, String> _AddName;
   @FXML
   private TableColumn<Part, Integer> _AddInv;
   @FXML
   private TableColumn<Part, Double> _AddPrice;
   @FXML
   private TableView<Part> _DeletePart;
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

    private final int productIndex = Controller.GetModProdIndex();

    private int ID;

    /**
     * this initializes the Modify Product screen, sets the desired attributes of the window and retrieves
     * necessary information
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        Product product = Inv.getProdInv().get(productIndex);
        parts = product.getProductParts();

        ID = Inv.getProdInv().get(productIndex).getProID();
        _IDLabel.setText("" + ID);
        _Name.setText(product.getProductName());
        _Inv.setText(Integer.toString(product.getProductInStock()));
        _Price.setText(Double.toString(product.getProductPrice()));
        _Min.setText(Integer.toString(product.getProductMin()));
        _Max.setText(Integer.toString(product.getProductMax()));
        _AddID.setCellValueFactory(cellData -> cellData.getValue().partIDproperty().asObject());
        _AddName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        _AddInv.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        _AddPrice.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        _DeleteID.setCellValueFactory(cellData -> cellData.getValue().partIDproperty().asObject());
        _DeleteName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        _DeleteInv.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        _DeletePrice.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        UpdateAvailableParts();
        UpdateUsedParts();
    }

    /**
         * this handles the clearing of the search bar, and updating the Table view in the modify product dialogue
         * @param event
         */
    @FXML
    void Clear(ActionEvent event) {
            UpdateAvailableParts();
            _Search.setText("");
        }

    /**
         * this handles the searching of products in the modify product dialogue, handling errors, and updating the table view
         * using the lookupPart method in the Inventory class.
         * @param event
         */
    @FXML
    void Search(ActionEvent event) {
            String query = _Search.getText();
            List<Integer> partIndex = new ArrayList<Integer>();
            if (Inv.findPart(query).size() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("not found");
                alert.setContentText("The query was not found.");
                alert.showAndWait();
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
                _AddPart.setItems(tempPartList);
            }
        }

    /**
     * updates the Add Parts Table view for the modify Product dialogue
     b     */
    public void UpdateAvailableParts() {
        _AddPart.setItems(Inv.GetPartInv());
    }

    /**
     * updates the Delete Parts Table view for the modify Product dialogue
     */
    public void UpdateUsedParts() {
        _DeletePart.setItems(parts);
    }

    /**
         * handles the adding of parts to a product, and updates the table view accordingly, adding the part to the list of
         * parts within the given product
         * @param event
         */
    @FXML
    void Add(ActionEvent event) {
            Part part = _AddPart.getSelectionModel().getSelectedItem();
            parts.add(part);
            UpdateUsedParts();
        }

    /**
         * handles the deletion of parts from a product, removing them from the parts list of the given product
         * @param event
         */
    @FXML
    void Delete(ActionEvent event) {
            Part part = _DeletePart.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Part Deletion");
            alert.setHeaderText("Confirm");
            alert.setContentText("Are you sure you want to delete " + part.getPartName() + " from parts?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                parts.remove(part);
            }
            else {
                System.out.println("You clicked cancel.");
            }
        }

    /**
     * this Handles the insertion of modified products back into the list, and actively looks for any exceptions, or
     * missing information
     * @param event
     * @throws IOException NumberFormatExceotion In the event that input is not the correct format, or blank the program
     * will throw an IOException, if this happens the program will inform the user there was an error. and informs them
     * of the error
     *
     * RUNTIME ERROR: Without this function being implemented the Lists of Products would be added to with missing values, or
     * inconsistent information, This checks for these various problems, such as missing information, or information that conflicts, like the
     * Min being greater than the Max.
     *
     */
    @FXML
    private void Save(ActionEvent event) throws IOException {
            String productName = _Name.getText();
            String productInv = _Inv.getText();
            String productPrice = _Price.getText();
            String productMin = _Min.getText();
            String productMax = _Max.getText();

            try {
                error = Product.validation(productName, Integer.parseInt(productMin), Integer.parseInt(productMax), Integer.parseInt(productInv), Double.parseDouble(productPrice), parts, error);
                if (error.length() > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Modifying Product");
                    alert.setContentText(error);
                    alert.showAndWait();
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
                    Inv.updateProd(productIndex, newProduct);

                    Parent modifyProductSaveParent = FXMLLoader.load(getClass().getResource("..//RightBrain//Face.fxml"));
                    Scene scene = new Scene(modifyProductSaveParent);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();
                }
            }

            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Product");
                alert.setContentText("Form contains blank fields.");
                alert.showAndWait();
            }
        }

    /**
         * handles the Cancellation dialogue for Modifying products, looking for exceptions
         * @param event
         * @throws IOException
         */
    @FXML
    private void Cancel(ActionEvent event) throws IOException {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Confirm Cancel");
            alert.setHeaderText("Confirm Cancel");
            alert.setContentText("Are you sure you want to cancel modifying the product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                Parent modifyProductCancelParent = FXMLLoader.load(getClass().getResource("..//RightBrain//Face.fxml"));
                Scene scene = new Scene(modifyProductCancelParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
            else {
                System.out.println("You clicked cancel.");
            }
        }

   }

