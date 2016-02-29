
package SodickCam;

import SodickBomProgram.Bom;
import Toolpkg.Borr;
import SodickSickelProgram.Sickel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author Mats
 */
public class FXMLDocumentController implements Initializable {
    
    Sickel sickel;
    Bom bom;
    Borr borr;
    
    @FXML
    private TextField sickelStockDia;

    @FXML
    private TextField sickelStockLength;

    @FXML
    private TextField sickelHalfLength;
    
    @FXML
    private TextField sickelTipDia;

    @FXML
    private TextField sickelSideAngle;

    @FXML
    private TextField bomStockDia;

    @FXML
    private TextField bomStockLength;

    @FXML
    private TextField bomHalfLength;
    
    @FXML
    private TextField bomNoseRadius;

    @FXML
    private TextField bomViperLength;

    @FXML
    private TextField bomClearance;

    @FXML
    private TextField bomClearanceLength;

    @FXML
    private TextField bomRadiusAtTip;

    @FXML
    private TextField bomStraightFrontLength;
    
    @FXML
    private TextField drillStockDiameter;

    @FXML
    private TextField drillTipThickness;

    @FXML
    private TextField drillAngle;
    
    @FXML 
    private TextArea drillSpecTextArea;

    @FXML
    void sickelCalculate(ActionEvent event) {
        sickel.calculate();
    }
    
    @FXML
    void bomCalculate(ActionEvent event) {
        bom.calculate();
    }
    
    @FXML
    void borrCalculate(ActionEvent event) {
        borr.setSpecText( drillSpecTextArea.getText() );
        borr.calculate();
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
        bom = new Bom();
        borr = new Borr();

        StringConverter<Number> converter = new NumberStringConverter();
        
        Bindings.bindBidirectional(sickelStockDia.textProperty(), sickel.getStockDiaProperty(), converter );
        Bindings.bindBidirectional(sickelStockLength.textProperty(), sickel.getLengthProperty(), converter );
        Bindings.bindBidirectional(sickelHalfLength.textProperty(), sickel.getHalfLengthProperty(), converter );
        Bindings.bindBidirectional(sickelTipDia.textProperty(), sickel.getTipDiaProperty(), converter );
        Bindings.bindBidirectional(sickelSideAngle.textProperty(), sickel.getSideAngleProperty(), converter );
        
        Bindings.bindBidirectional(bomStockDia.textProperty(), bom.getStockDiaProperty(), converter );
        Bindings.bindBidirectional(bomStockLength.textProperty(), bom.getLengthProperty(), converter );
        Bindings.bindBidirectional(bomHalfLength.textProperty(), bom.getHalfLengthProperty(), converter );
        Bindings.bindBidirectional(bomNoseRadius.textProperty(), bom.getNoseRadiusProperty(), converter );
        Bindings.bindBidirectional(bomViperLength.textProperty(), bom.getViperLengthProperty(), converter );
        Bindings.bindBidirectional(bomClearance.textProperty(), bom.getClearanceProperty(), converter );
        Bindings.bindBidirectional(bomClearanceLength.textProperty(), bom.getClearanceLengthProperty(), converter );
        Bindings.bindBidirectional(bomRadiusAtTip.textProperty(), bom.getRadiusAtTipProperty(), converter );
        Bindings.bindBidirectional(bomStraightFrontLength.textProperty(), bom.getStraightFrontLengthProperty(), converter );
        
        Bindings.bindBidirectional(drillTipThickness.textProperty(), borr.getStockDiameterProperty(), converter);
        Bindings.bindBidirectional(drillAngle.textProperty(), borr.getAngleProperty(), converter);
        Bindings.bindBidirectional(drillTipThickness.textProperty(), borr.getTipThicknessProperty(), converter);
        
    }    

}
