
package bomcam;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
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
    private TextField clearance;

    @FXML
    private TextField clearanceLength;

    @FXML
    private TextField radiusAtTip;

    @FXML
    private TextField straightFrontLength;

    @FXML
    void calculateCutGeo(ActionEvent event) {
        
        bom.calculateCutGeo();
        
    }
    
    @FXML
    private void calculateParting(ActionEvent event) {
        bom.calculateParting();
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
        Bindings.bindBidirectional(clearance.textProperty(), bom.getClearanceProperty(), converter );
        Bindings.bindBidirectional(clearanceLength.textProperty(), bom.getClearanceLengthProperty(), converter );
        Bindings.bindBidirectional(radiusAtTip.textProperty(), bom.getRadiusAtTipProperty(), converter );
        Bindings.bindBidirectional(straightFrontLength.textProperty(), bom.getStraightFrontLengthProperty(), converter );
    }    
    
}
