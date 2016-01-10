
package bomcam;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author Mats
 */
public class Bom {
    
    private final DoubleProperty stockDia;      // Diameter på ämnet
    private final DoubleProperty length;        // Ämnets längd
    private final DoubleProperty halfLength;    // Till vilken längd ämnet skall delas på hälften
    
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
        stockDia = new SimpleDoubleProperty( 12);
        length = new SimpleDoubleProperty(50);
        halfLength = new SimpleDoubleProperty(10);
        noseRadius = new SimpleDoubleProperty(0.2);
        clearance = new SimpleDoubleProperty(0.5);
        clearanceLength = new SimpleDoubleProperty(10);
        radiusAtTip = new SimpleDoubleProperty(5.8);
        straightFrontLength = new SimpleDoubleProperty(6);
    }

    public double getStockDia() {
        return stockDia.get();
    }

    public void setStockDia(double stockDia) {
        this.stockDia.set(stockDia);
    }

    public DoubleProperty getStockDiaProperty() {
        return stockDia;
    }
    
    public double getLength() {
        return length.get();
    }

    public void setLength(double length) {
        this.length.set( length );
    }
    
    public DoubleProperty getLengthProperty() {
        return length;
    }
    

    public double getHalfLength() {
        return halfLength.get();
    }

    public void setHalfLength(double halfLength) {
        this.halfLength.set( halfLength );
    }

    public DoubleProperty getHalfLengthProperty() {
        return halfLength;
    }
    
    public double getNoseRadius() {
        return noseRadius.get();
    }

    public void setNoseRadius(double noseRadius) {
        this.noseRadius.set( noseRadius );
    }

    public DoubleProperty getNoseRadiusProperty() {
        return noseRadius;
    }
    
    public double getClearance() {
        return clearance.get();
    }

    public void setClearance(double clearance) {
        this.clearance.set( clearance );
    }

    public DoubleProperty getClearanceProperty() {
        return clearance;
    }
    
    public double getClearanceLength() {
        return clearanceLength.get();
    }

    public void setClearanceLength(double clearanceLength) {
        this.clearanceLength.set( clearanceLength );
    }

    public DoubleProperty getClearanceLengthProperty() {
        return clearanceLength;
    }
    
    public double getRadiusAtTip() {
        return radiusAtTip.get();
    }

    public void setRadiusAtTip(double radiusAtTip) {
        this.radiusAtTip.set( radiusAtTip );
    }

    public DoubleProperty getRadiusAtTipProperty() {
        return radiusAtTip;
    }
    
    
    public double getStraightFrontLength() {
        return straightFrontLength.get();
    }

    public void setStraightFrontLength(double straightFrontLength) {
        this.straightFrontLength.set( straightFrontLength );
    }

    public DoubleProperty getStraightFrontLengthProperty() {
        return straightFrontLength;
    }
    

    
    public void calculateParting() {
        double stockRadius = stockDia.get() / 2;
        double xStart = halfLength.get() + stockRadius ;
      
        // Starta länken
        partingChain = new Chain();
        
        // Börja med två startsträckor
        partingChain.add(new Line(xStart, -stockRadius - START_LENGTH * 2, xStart , -stockRadius - START_LENGTH ));
        partingChain.add(new Line(xStart, -stockRadius - START_LENGTH , xStart , -stockRadius ));
        
        // 45-graderslinjen fram till radien
        double cornDist = 2 * Math.atan(Math.PI/8);
        partingChain.add(new Line(xStart, -stockRadius, halfLength.get() - cornDist + Math.sqrt(2) , -2 + Math.sqrt(2)));
        
        // R2 vid övergången
        partingChain.add(new Arc(halfLength.get() - cornDist, -2, 2, 45, 90 ) );
        
        // Horisontell linje till spetsen
        partingChain.add(new Line( halfLength.get() - cornDist, 0, 0, 0 ));
        
        // Två slutsträckor
        partingChain.add(new Line( 0, 0, -START_LENGTH, 0 ));
        partingChain.add(new Line( -START_LENGTH, 0, -2 * START_LENGTH, 0 ));
        
        partingChain.saveChainToDXF();
    }

    void calculateCutGeo() {
        // Starta länken
        cutGeoChain = new Chain();
        
        // Börja med två startsträckor
        double stockRadius = stockDia.get()/2;
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
        cutGeoChain.add(new Arc( centerX, centerY, noseRadius.get(), 280, 180));
        
        // Raksträcka för plansvarvning som angivet.
        double endOfStraight = -radiusAtTip.get() + straightFrontLength.get();
        cutGeoChain.add(new Line(0, centerY, 0, endOfStraight ));
        
        // 30 grader vidare ut till ämneskanten.
        double leftY = stockRadius - endOfStraight;
        double xEnd = leftY * Math.tan(Math.toRadians(30));
        cutGeoChain.add( new Line( 0, endOfStraight, xEnd, stockRadius ));
        
        // Två slutsträckor
        cutGeoChain.add(new Line( xEnd, stockRadius, xEnd, stockRadius + START_LENGTH));
        cutGeoChain.add(new Line( xEnd, stockRadius + START_LENGTH, xEnd, stockRadius + 2 * START_LENGTH));
        
        cutGeoChain.saveChainToDXF();
        
    }

        
        
    

    
    
}
