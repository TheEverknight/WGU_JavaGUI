package BrainStem.Brain.LeftBrain;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import BrainStem.Brain.Cerebellum.InHousePart;
import BrainStem.Brain.Cerebellum.OutsourcedPart;
import BrainStem.Brain.Cerebellum.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPart implements Initializable {







  @FXML
  private RadioButton _In;
  @FXML
  private RadioButton _Out;
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
  private Label _DynamicLabel;
  @FXML
  private TextField _Dynamic;

        private String error = "";

        private int ID;

        private boolean Out;


        /**
         * this initializes the Add part screen, sets the desired attributes of the window and retrieves
         * necessary information
         * @param url
         * @param rb
         */
        public void initialize(URL url, ResourceBundle rb) {
        ID = Inv.GetPartCount();
        System.out.println(""+ID);
        _IDLabel.setText("" + ID);
        }

        /**
         * This handles the actions taken in the application when the InHouse Radio button has been selected, changing the
         * Dynamic label, and deselecting the Outsourced Radio Button if selected
         * @param event
         */
        //Radio Button handler
        @FXML
        void addInHouse(ActionEvent event) {
            Out = false;
            _DynamicLabel.setText("Machine ID");
            _Dynamic.setPromptText("...");

            if(_In.isSelected()) {

                    _In.setSelected(false);
            }
        }

        /**
         * This handles the actions taken in the application when the Outsourced InHouse Radio button has been selected,
         * changing the Dynamic label, and deselecting the InHouse Radio Button if selected
         * @param event
         */
        //Radio Button handler
        @FXML
        void AddOutsource(ActionEvent event) {
            Out = true;

            _DynamicLabel.setText("Company");
            _Dynamic.setPromptText("...");

            if(_In.isSelected()) {
                    _In.setSelected(false);
            }

        }

        /**
         * this Handles the insertion of New parts back into the list, and actively looks for any exceptions, or
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
        //Handle Product validation, and append to arraylist
        @FXML
        void Save(ActionEvent event) throws IOException {
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
                    alert.setHeaderText("Error Adding Part");
                    alert.setContentText(error);
                    alert.showAndWait();
                    error = "";
                }


                else {
                    if (Out == false) {
                        InHousePart inPart = new InHousePart();
                        inPart.setPartID(ID);
                        inPart.setPartName(partName);
                        inPart.setPartPrice(Double.parseDouble(partPrice));
                        inPart.setPartInStock(Integer.parseInt(partInv));
                        inPart.setPartMin(Integer.parseInt(partMin));
                        inPart.setPartMax(Integer.parseInt(partMax));
                        inPart.setPartMachineID(Integer.parseInt(partDyn));
                        Inv.addPart(inPart);
                    } else {
                        OutsourcedPart outPart = new OutsourcedPart();
                        outPart.setPartID(ID);
                        outPart.setPartName(partName);
                        outPart.setPartPrice(Double.parseDouble(partPrice));
                        outPart.setPartInStock(Integer.parseInt(partInv));
                        outPart.setPartMin(Integer.parseInt(partMin));
                        outPart.setPartMax(Integer.parseInt(partMax));
                        outPart.setPartCompanyName(partDyn);
                        Inv.addPart(outPart);
                    }

                    Parent addPartSave = FXMLLoader.load(getClass().getResource("..//RightBrain//Face.fxml"));
                    Scene scene = new Scene(addPartSave);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();
                }
            }
            catch(NumberFormatException e) {
                Alert LocalError = new Alert(Alert.AlertType.INFORMATION);
                LocalError.setTitle("Error");
                LocalError.setHeaderText("Part could not be added");
                LocalError.setContentText("Missing Information.");
                LocalError.showAndWait();
            }
        }

        /**
         * handles the Cancellation dialogue for Adding parts, looking for exceptions
         * @param event
         * @throws IOException
         */
        //create Cancellation confirmation dialogue
        @FXML
        private void Cancel(ActionEvent event) throws IOException {
            Alert LocalError = new Alert(Alert.AlertType.CONFIRMATION);
            LocalError.initModality(Modality.NONE);

            LocalError.setTitle("Cancel");
            LocalError.setHeaderText("Confirm");
            LocalError.setContentText("Are you sure?");
            Optional<ButtonType> result = LocalError.showAndWait();

            if (result.get() == ButtonType.OK) {
                Inv.ReducePartCount();
                Parent addPartCancel = FXMLLoader.load(getClass().getResource("..//RightBrain//Face.fxml"));
                Scene scene = new Scene(addPartCancel);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }

        }

    }


