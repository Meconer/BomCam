
package Toolpkg;

import Toolpkg.Chain;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author Mats
 */
public class Sickel {
    
    private final PartedBlank partedBlank;            // Delat runt ämne med längd och dia delat på hälften
    
    private final DoubleProperty tipDia;              // Diameter i spetsen
    private final DoubleProperty sideAngle;           // Halva spetsvinkeln
    
    private Chain partingChain ;
    private Chain cutGeoChain ;
    
    private static final double START_LENGTH = 0.5;

    public Sickel() {
        // Set up default values
        partedBlank = new PartedBlank(12, 50, 10);
        tipDia = new SimpleDoubleProperty(0.2);
        sideAngle = new SimpleDoubleProperty(30);
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
    
    public double getTipDia() {
        return tipDia.get();
    }

//    public void setNoseRadius(double noseRadius) {
//        this.noseRadius.set( noseRadius );
//    }
//
    public DoubleProperty getTipDiaProperty() {
        return tipDia;
    }
    
    public double getSideAngle() {
        return sideAngle.get();
    }

//    public void setClearance(double clearance) {
//        this.clearance.set( clearance );
//    }
//
    public DoubleProperty getSideAngleProperty() {
        return sideAngle;
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
        double xStart = (stockRadius-tipDia.get())/Math.tan(Math.toRadians( sideAngle.get() ) );
        double yStart = -stockRadius;
        cutGeoChain.add(new Line( xStart, yStart - 2 * Constants.START_LENGTH, xStart, yStart - Constants.START_LENGTH ));
        cutGeoChain.add(new Line( xStart, yStart - Constants.START_LENGTH, xStart, yStart ));
        
        // Sedan till spets
        cutGeoChain.add(new Line( xStart, yStart, 0, -tipDia.get()/2 ) );
        
        // Till centrum
        cutGeoChain.add(new Line( 0, -tipDia.get()/2, 0, 0 ) );
        
        // 15 grader snett till 2*tipdia och sedan till vänster
        double yNext = tipDia.get() * 2;
        double xNext = yNext * Math.tan( Math.toRadians(15) );
        cutGeoChain.add(new Line( 0, 0, xNext, yNext ));
        cutGeoChain.add(new Line( xNext, yNext, 0, yNext ));
        
        // 2 startsträckor
        cutGeoChain.add( new Line(0, yNext, -Constants.START_LENGTH, yNext));
        cutGeoChain.add( new Line(-Constants.START_LENGTH, yNext, - 2*Constants.START_LENGTH, yNext));
        
        cutGeoChain.saveChainToDXF();
        
    }

        
        
    

    
    
}
