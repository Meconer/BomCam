
package SickelCam;

import Toolpkg.Sickel;
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
    private TextField clearanceLength;

    @FXML
    private TextField radiusAtTip;

    @FXML
    private TextField straightFrontLength;

    @FXML
    void calculateCutGeo(ActionEvent event) {
        
        sickel.calculateCutGeo();
        
    }
    
    @FXML
    private void calculateParting(ActionEvent event) {
        sickel.calculateParting();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sickel = new Sickel();

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
        
        
        Bindings.bindBidirectional(stockDia.textProperty(), sickel.getStockDiaProperty(), converter );
        Bindings.bindBidirectional(stockLength.textProperty(), sickel.getLengthProperty(), converter );
        Bindings.bindBidirectional(halfLength.textProperty(), sickel.getHalfLengthProperty(), converter );
        Bindings.bindBidirectional(tipDia.textProperty(), sickel.getTipDiaProperty(), converter );
        Bindings.bindBidirectional(sideAngle.textProperty(), sickel.getSideAngleProperty(), converter );
    }    
    
}
