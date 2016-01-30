
package Toolpkg;

import Toolpkg.Chain;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author Mats
 */
public class Bom {
    
    private final PartedBlank partedBlank;            // Delat runt ämne med längd och dia delat på hälften
    
    private final DoubleProperty noseRadius;          // Spetsens nosradie
    private final DoubleProperty clearance;           // Frigång från spetsen i x-led
    private final DoubleProperty clearanceLength;     // Längd på frigång i z-led
    private final DoubleProperty radiusAtTip;         // Spetsens läge i x-led
    private final DoubleProperty straightFrontLength; // Längd på den raka delen framtill
    
    private Chain partingChain ;
    private Chain cutGeoChain ;
    
    private static final double START_LENGTH = 0.5;

    public Bom() {
        // Set up default values
        partedBlank = new PartedBlank(12, 50, 10);
        noseRadius = new SimpleDoubleProperty(0.2);
        clearance = new SimpleDoubleProperty(0.5);
        clearanceLength = new SimpleDoubleProperty(10);
        radiusAtTip = new SimpleDoubleProperty(5.8);
        straightFrontLength = new SimpleDoubleProperty(6);
    }

    public double getStockDia() {
        return partedBlank.getStockDia();
    }

    public DoubleProperty getStockDiaProperty() {
        return partedBlank.getStockDiaProperty();
    }
    
    public double getStockLength() {
        return partedBlank.getStockLength();
    }

//    public void setLength(double length) {
//        partedBlank.getStockLengthProperty().set( length );
//    }
//    
    
    public DoubleProperty getLengthProperty() {
        return partedBlank.getStockLengthProperty();
    }
    

    public double getHalfLength() {
        return partedBlank.getHalfLength();
    }

//    public void setHalfLength(double halfLength) {
//        this.halfLength.set( halfLength );
//    }
//
    public DoubleProperty getHalfLengthProperty() {
        return partedBlank.getHalfLengthProperty();
    }
    
    public double getNoseRadius() {
        return noseRadius.get();
    }

//    public void setNoseRadius(double noseRadius) {
//        this.noseRadius.set( noseRadius );
//    }
//
    public DoubleProperty getNoseRadiusProperty() {
        return noseRadius;
    }
    
    public double getClearance() {
        return clearance.get();
    }

//    public void setClearance(double clearance) {
//        this.clearance.set( clearance );
//    }
//
    public DoubleProperty getClearanceProperty() {
        return clearance;
    }
    
    public double getClearanceLength() {
        return clearanceLength.get();
    }

//    public void setClearanceLength(double clearanceLength) {
//        this.clearanceLength.set( clearanceLength );
//    }
//
    public DoubleProperty getClearanceLengthProperty() {
        return clearanceLength;
    }
    
    public double getRadiusAtTip() {
        return radiusAtTip.get();
    }

//    public void setRadiusAtTip(double radiusAtTip) {
//        this.radiusAtTip.set( radiusAtTip );
//    }
//
    public DoubleProperty getRadiusAtTipProperty() {
        return radiusAtTip;
    }
    
    
    public double getStraightFrontLength() {
        return straightFrontLength.get();
    }

//    public void setStraightFrontLength(double straightFrontLength) {
//        this.straightFrontLength.set( straightFrontLength );
//    }
//
    public DoubleProperty getStraightFrontLengthProperty() {
        return straightFrontLength;
    }
    

    
    public void calculateParting() {

        partingChain = partedBlank.calculatePartingChain();

        partingChain.saveChainToDXF();

    }

    public void calculateCutGeo() {
        // Starta länken
        cutGeoChain = new Chain();
        
        // Börja med två startsträckor
        double stockRadius = partedBlank.getStockDia()/2;
        double xStart = (stockRadius-radiusAtTip.get()) + clearance.get() + clearanceLength.get();
        double yStart = -stockRadius;
        cutGeoChain.add(new Line( xStart, yStart-1, xStart, yStart - 0.5 ));
        cutGeoChain.add(new Line( xStart, yStart-0.5, xStart, yStart ));
        
        // Sedan 45 grader mot clearanceLength till frigången
        double yClearance = - radiusAtTip.get() + clearance.get();
        cutGeoChain.add(new Line( xStart, yStart, clearanceLength.get(), yClearance ) );
        
        // Frigången fram till den möter 10-graderslinjen upp till skärspetsen
        double radianAngle = Math.toRadians( 10 );
        double yLength10 = radiusAtTip.get() + yClearance - noseRadius.get() * ( 1 - Math.cos( radianAngle ) );
        double xLength10 = yLength10 / Math.tan( radianAngle );
        double xClearStart = noseRadius.get() * ( 1 + Math.sin( radianAngle ) ) + xLength10 ;
        cutGeoChain.add(new Line( clearanceLength.get(), yClearance, xClearStart, yClearance));
        
        // Och så 10-graderslinjen
        cutGeoChain.add(new Line( xClearStart, yClearance, xClearStart - xLength10, yClearance - yLength10));
        
        // Nosradien
        double centerX = noseRadius.get();
        double centerY = -radiusAtTip.get() + noseRadius.get();
        cutGeoChain.add(new Arc( centerX, centerY, noseRadius.get(), 280, 180, Util.ArcDirection.CW));
        
        // Raksträcka för plansvarvning som angivet.
        double endOfFront = -radiusAtTip.get() + straightFrontLength.get();
        cutGeoChain.add(new Line(0, centerY, 0, endOfFront ));
        
        // 30 grader vidare ut till ämneskanten.
        double leftY = stockRadius - endOfFront;
        double xEnd = leftY * Math.tan(Math.toRadians(30));
        cutGeoChain.add( new Line( 0, endOfFront, xEnd, stockRadius ));
        
        // Två slutsträckor
        cutGeoChain.add(new Line( xEnd, stockRadius, xEnd, stockRadius + START_LENGTH));
        cutGeoChain.add(new Line( xEnd, stockRadius + START_LENGTH, xEnd, stockRadius + 2 * START_LENGTH));
        
        cutGeoChain.saveChainToDXF();
        
    }

    public void calculate() {
        
    }

        
        
    

    
    
}
