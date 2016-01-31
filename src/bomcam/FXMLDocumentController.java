
package bomcam;

import Toolpkg.Bom;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author Mats
 */
public class FXMLDocumentController implements Initializable {
    
    Bom bom = new Bom();
    @FXML
    private TextField stockDia;

    @FXML
    private TextField stockLength;

    @FXML
    private TextField halfLength;
    
    @FXML
    private TextField noseRadius;

    @FXML
    private TextField viperLength;

    @FXML
    private TextField clearance;

    @FXML
    private TextField clearanceLength;

    @FXML
    private TextField radiusAtTip;

    @FXML
    private TextField straightFrontLength;

    @FXML
    void calculate(ActionEvent event) {
        
        bom.calculate();
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bom = new Bom();

//        Pattern validDoubleText = Pattern.compile("-?((\\d*)|(\\d+\\.\\d*))");
//        
//        TextFormatter<Double> stockDiaFmt = new TextFormatter<Double>(new DoubleStringConverter(), 0.0, 
//            change -> {
//                String newText = change.getControlNewText() ;
//                if (validDoubleText.matcher(newText).matches()) {
//                    return change ;
//                } else return null ;
//            });
//        
//        stockDia.setTextFormatter(stockDiaFmt);
//        stockDia.setText( String.valueOf( bom.getStockDia() ) );
//        stockDiaFmt.valueProperty().addListener( (obs, oldValue, newValue ) -> {
//            bom.setStockDia( newValue );
//        });


        StringConverter<Number> converter = new NumberStringConverter();
        
        
        Bindings.bindBidirectional(stockDia.textProperty(), bom.getStockDiaProperty(), converter );
        Bindings.bindBidirectional(stockLength.textProperty(), bom.getLengthProperty(), converter );
        Bindings.bindBidirectional(halfLength.textProperty(), bom.getHalfLengthProperty(), converter );
        Bindings.bindBidirectional(noseRadius.textProperty(), bom.getNoseRadiusProperty(), converter );
        Bindings.bindBidirectional(viperLength.textProperty(), bom.getViperLengthProperty(), converter );
        Bindings.bindBidirectional(clearance.textProperty(), bom.getClearanceProperty(), converter );
        Bindings.bindBidirectional(clearanceLength.textProperty(), bom.getClearanceLengthProperty(), converter );
        Bindings.bindBidirectional(radiusAtTip.textProperty(), bom.getRadiusAtTipProperty(), converter );
        Bindings.bindBidirectional(straightFrontLength.textProperty(), bom.getStraightFrontLengthProperty(), converter );
    }    
    
}
