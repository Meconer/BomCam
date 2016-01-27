
package SickelCam;

import Toolpkg.Sickel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author Mats
 */
public class FXMLDocumentController implements Initializable {
    
    Sickel sickel = new Sickel();
    @FXML
    private TextField stockDia;

    @FXML
    private TextField stockLength;

    @FXML
    private TextField halfLength;
    
    @FXML
    private TextField tipDia;

    @FXML
    private TextField sideAngle;

    @FXML
    void calculateCutGeo(ActionEvent event) {
        sickel.calculate();
    }
    
    @FXML
    void closeProgram() {
        System.exit(0);
    }
    
    @FXML
    void aboutBox() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "SickelCam 0.1 by Mats Andersson", ButtonType.OK);
        alert.showAndWait();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sickel = new Sickel();

        StringConverter<Number> converter = new NumberStringConverter();
        
        Bindings.bindBidirectional(stockDia.textProperty(), sickel.getStockDiaProperty(), converter );
        Bindings.bindBidirectional(stockLength.textProperty(), sickel.getLengthProperty(), converter );
        Bindings.bindBidirectional(halfLength.textProperty(), sickel.getHalfLengthProperty(), converter );
        Bindings.bindBidirectional(tipDia.textProperty(), sickel.getTipDiaProperty(), converter );
        Bindings.bindBidirectional(sideAngle.textProperty(), sickel.getSideAngleProperty(), converter );
    }    
    
}
