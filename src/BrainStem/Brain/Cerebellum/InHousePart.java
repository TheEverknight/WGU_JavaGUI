package BrainStem.Brain.Cerebellum;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Implementation of Abstract class Part, with added parameter of a MachineID
 */
public class InHousePart extends Part {

    private final IntegerProperty partMachineID;

    /**
     * Constructor for the InHouse Part
     */

    public InHousePart() {
        super();
        partMachineID = new SimpleIntegerProperty();
    }

    /**
     * Returns the Machine ID
     * @return
     */
    public int getPartMachineID() {
        return this.partMachineID.get();
    }

    /**
     * Sets the Machine ID
     * @param partMachineID
     */
    public void setPartMachineID(int partMachineID) {
        this.partMachineID.set(partMachineID);
    }
}
