package BrainStem.Brain.Cerebellum;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Implementation of Abstract class Part, with added parameter of a company name
 */
// Definition of an object using an abstract class as a base
public class OutsourcedPart extends Part {

    private final StringProperty partCompanyName;
    //Constructor

    /**
     * constructor for the Outsourced part
     */
    public OutsourcedPart() {
        super();
        partCompanyName = new SimpleStringProperty();
    }

    /**
     * Returns the company name of the outsourced part
     * @return company name of part
     */
    //getter
    public String getPartCompanyName() {
        return this.partCompanyName.get();
    }

    /**
     * Sets the company name of the outsourced part
     * @param partCompanyName the name of the company
     */

    //setter
    public void setPartCompanyName(String partCompanyName) {
        this.partCompanyName.set(partCompanyName);
    }
}
