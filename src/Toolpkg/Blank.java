
package Toolpkg;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Class representing a round carbide blank
 * @author Mats
 */
public class Blank {
    private final DoubleProperty stockDia;
    private final DoubleProperty stockLength;

    public Blank(double diameter, double length) {
        stockDia = new SimpleDoubleProperty( diameter );
        stockLength = new SimpleDoubleProperty( length );
    }

    public double getStockDia() {
        return stockDia.get();
    }

    public DoubleProperty getStockDiaProperty() {
        return stockDia;
    }

    public double getStockLength() {
        return stockLength.get();
    }

    public DoubleProperty getStockLengthProperty() {
        return stockLength;
    }
    
    
}
