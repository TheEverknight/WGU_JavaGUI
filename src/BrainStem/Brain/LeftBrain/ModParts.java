package BrainStem.Brain.LeftBrain;


import BrainStem.Brain.Cerebellum.InHousePart;
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
import BrainStem.Brain.Cerebellum.OutsourcedPart;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModParts implements Initializable {






    @FXML
    private RadioButton _In;
    @FXML
    private RadioButton _Out;
    @FXML
    private Label _ID;
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
    private Label _DynamicLabel;
    @FXML
    private TextField _Dynamic;

    int partIndex = Controller.GetModPartIndex();

    private String error = "";

    private int ID;

    private boolean Out;
    /**
     * this initializes the Modify part screen, sets the desired attributes of the window and retrieves
     * necessary information
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        Part part = Inv.GetPartInv().get(partIndex);
        ID = Inv.GetPartInv().get(partIndex).getPartID();
        _ID.setText("" + ID);
        _Name.setText(part.getPartName());
        _Inv.setText(Integer.toString(part.getPartInStock()));
        _Price.setText(Double.toString(part.getPartPrice()));
        _Min.setText(Integer.toString(part.getPartMin()));
        _Max.setText(Integer.toString(part.getPartMax()));
        if (part instanceof InHousePart) {
            _DynamicLabel.setText("Machine ID");
            _Dynamic.setText(Integer.toString(((InHousePart) Inv.GetPartInv().get(partIndex)).getPartMachineID()));
            _In.setSelected(true);
        }
        else {
            _DynamicLabel.setText("Company");
            _Dynamic.setText(((OutsourcedPart) Inv.GetPartInv().get(partIndex)).getPartCompanyName());
            _Out.setSelected(true);
        }
    }

    /**
     * This handles the actions taken in the application when the InHouse Radio button has been selected, changing the
     * Dynamic label, and deselecting the Outsourced Radio Button if selected
     * @param event
     */
    @FXML
    void modPartInhouse(ActionEvent event) {
        Out = false;
        _Out.setSelected(false);
        _DynamicLabel.setText("Machine ID");
        _Dynamic.setText("");
        _Dynamic.setPromptText("Machine ID");
    }

    /**
     * This handles the actions taken in the application when the Outsourced InHouse Radio button has been selected,
     * changing the Dynamic label, and deselecting the InHouse Radio Button if selected
     * @param event
     */
    @FXML
    void modPartOutsource(ActionEvent event) {
        Out = true;
        _In.setSelected(false);
        _DynamicLabel.setText("Company Name");
        _Dynamic.setText("");
        _Dynamic.setPromptText("Company Name");
    }
    //part validation, save/cancel---------------------------------------------------------------------------------------

    /**
     * this Handles the insertion of modified parts back into the list, and actively looks for any exceptions, or
     * missing information
     * @param event
     * @throws IOException NumberFormatExceotion In the event that input is not the correct format, or blank the program
     * will throw an IOException, if this happens the program will inform the user there was an error. and informs them
     * of the error
     *
     * RUNTIME ERROR: Without this function being implemented the Lists of Parts would be added to with missing values, or
     * inconsistent information, This checks for these various problems, such as missing information, or information that conflicts, like the
     * Min being greater than the Max.
     */

    @FXML
    void save(ActionEvent event) throws IOException {
        String partName = _Name.getText();
        String partInv = _Inv.getText();
        String partPrice = _Price.getText();
        String partMin = _Min.getText();
        String partMax = _Max.getText();
        String partDyn = _Dynamic.getText();

        try {
            error = Part.validation(partName, Integer.parseInt(partMin), Integer.parseInt(partMax), Integer.parseInt(partInv), Double.parseDouble(partPrice), error);
            if (error.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Part");
                alert.setContentText(error);
                alert.showAndWait();
                error = "";
            }
            else {
                if (Out == false) {
                    System.out.println("Part name: " + partName);
                    InHousePart inPart = new InHousePart();
                    inPart.setPartID(ID);
                    inPart.setPartName(partName);
                    inPart.setPartInStock(Integer.parseInt(partInv));
                    inPart.setPartMin(Integer.parseInt(partMin));
                    inPart.setPartMax(Integer.parseInt(partMax));
                    inPart.setPartPrice(Double.parseDouble(partPrice));
                    inPart.setPartMachineID(Integer.parseInt(partDyn));
                    Inv.updatePart(partIndex, inPart);
                }
                else {
                    System.out.println("Part name: " + partName);
                    OutsourcedPart outPart = new OutsourcedPart();
                    outPart.setPartID(ID);
                    outPart.setPartName(partName);
                    outPart.setPartInStock(Integer.parseInt(partInv));
                    outPart.setPartMin(Integer.parseInt(partMin));
                    outPart.setPartMax(Integer.parseInt(partMax));
                    outPart.setPartPrice(Double.parseDouble(partPrice));
                    outPart.setPartCompanyName(partDyn);
                    Inv.updatePart(partIndex, outPart);
                }

                Parent modifyProductSave = FXMLLoader.load(getClass().getResource("..//RightBrain//Face.fxml"));
                Scene scene = new Scene(modifyProductSave);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Part could not be modified");
            alert.setContentText("Missing Information");
            alert.showAndWait();
        }
    }

    /**
     * handles the Cancellation dialogue for Modifying parts, looking for exceptions
     * @param event
     * @throws IOException
     */
    @FXML
    private void cancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel");
        alert.setHeaderText("Confirm");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent modifyPartCancel = FXMLLoader.load(getClass().getResource("..//RightBrain//Face.fxml"));
            Scene scene = new Scene(modifyPartCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }

    }
    //Initial Values----------------------------------------------------------------------------------------------------


}
