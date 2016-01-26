
package Toolpkg;

import SodickSickelProgram.SodickCNCProgram;
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
    

    public Sickel() {
        // Set up default values
        partedBlank = new PartedBlank(4, 30, 4);
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
    
    

    // Beräknar geometri för att dela ämnet på hälften
    private Chain calculateParting() {

        Chain partingChain = partedBlank.calculatePartingChain();

        return partingChain;
    }

    // Beräknar geometri för skäret. 
    private Chain calculateCutGeo() {
        // Starta länken
        Chain cutGeoChain = new Chain();
        
        // Börja med två startsträckor
        double stockRadius = partedBlank.getStockDia()/2;
        double tipRadius = tipDia.get() / 2;
        double xStart = (stockRadius -  tipRadius )/Math.tan(Math.toRadians( sideAngle.get() ) );
        double yStart = -stockRadius;
        cutGeoChain.add(new Line( xStart, yStart - 2 * Constants.START_LENGTH, xStart, yStart - Constants.START_LENGTH ));
        cutGeoChain.add(new Line( xStart, yStart - Constants.START_LENGTH, xStart, yStart ));
        
        // Sedan till spets
        cutGeoChain.add(new Line( xStart, yStart, 0, -tipRadius ) );
        
        // Till centrum
        cutGeoChain.add(new Line( 0, -tipRadius, 0, 0 ) );
        
        // 10 grader snett till 2*tipRadius, rakt fram till 4*tipRadius och sedan till vänster
        double yNext = tipRadius * 2;
        double xNext = yNext * Math.tan( Math.toRadians(10) );
        cutGeoChain.add(new Line( 0, 0, xNext, yNext ));
        double yLast = yNext;
        double xLast = xNext;
        yNext = tipRadius * 4;
        
        cutGeoChain.add(new Line( xLast, yLast, xLast, yNext ));
        cutGeoChain.add(new Line( xLast, yNext, 0, yNext ));
        
        // 2 startsträckor
        cutGeoChain.add( new Line(0, yNext, -Constants.START_LENGTH, yNext));
        cutGeoChain.add( new Line(-Constants.START_LENGTH, yNext, - 2*Constants.START_LENGTH, yNext));
        
        return cutGeoChain;
        
    }

    // Beräknar geometri för första släppningen.
    private Chain calculateFirstReliefChain() {
        Chain firstReliefChain = new Chain();

        // Radieberäkningar vid xStart för cutGeoChain
        double stockRadius = partedBlank.getStockDia()/2;
        double tipRadius = tipDia.get() / 2;
        double xCutStart = (stockRadius-tipRadius )/Math.tan(Math.toRadians( sideAngle.get() ) );
        double yNegAtTip = tipRadius * Constants.FIRST_RELIEF_FACTOR;
        double yNegAtCutGeoStart = Constants.FIRST_RELIEF_FACTOR * stockRadius ;
        double reliefSideAngle = Math.atan( (yNegAtCutGeoStart - yNegAtTip ) / xCutStart ); // In radians
        double xStart = (stockRadius - yNegAtTip) / Math.tan(reliefSideAngle);
        // Börja med två startsträckor
        double yStart = -stockRadius;
        firstReliefChain.add(new Line( xStart, yStart - 2 * Constants.START_LENGTH, xStart, yStart - Constants.START_LENGTH ));
        firstReliefChain.add(new Line( xStart, yStart - Constants.START_LENGTH, xStart, yStart ));
        
        // Sedan till spets
        firstReliefChain.add(new Line( xStart, yStart, 0, -yNegAtTip ) );
        
        // Till centrum
        firstReliefChain.add(new Line( 0, -yNegAtTip, 0, 0 ) );
        
        // Två slutsträckor y+
        firstReliefChain.add(new Line( 0, 0, 0, Constants.START_LENGTH ));
        firstReliefChain.add(new Line( 0, Constants.START_LENGTH, 0, 2 * Constants.START_LENGTH ));
        
        
        return firstReliefChain;
    }

    // Beräknar geometri för första släppningen.
    private Chain calculateSecondReliefChain() {
        Chain secondReliefChain = new Chain();

        // Radieberäkningar vid xStart för cutGeoChain
        double stockRadius = partedBlank.getStockDia()/2;
        double tipRadius = tipDia.get() / 2;
        double xCutStart = (stockRadius-tipRadius )/Math.tan(Math.toRadians( sideAngle.get() ) );
        double yNegAtTip = tipRadius * Constants.SECOND_RELIEF_FACTOR;
        double yNegAtCutGeoStart = Constants.SECOND_RELIEF_FACTOR * stockRadius ;
        double reliefSideAngle = Math.atan( (yNegAtCutGeoStart - yNegAtTip ) / xCutStart ); // In radians
        double xStart = (stockRadius - yNegAtTip) / Math.tan(reliefSideAngle);
        // Börja med två startsträckor
        double yStart = -stockRadius;
        secondReliefChain.add(new Line( xStart, yStart - 2 * Constants.START_LENGTH, xStart, yStart - Constants.START_LENGTH ));
        secondReliefChain.add(new Line( xStart, yStart - Constants.START_LENGTH, xStart, yStart ));
        
        // Sedan till spets
        secondReliefChain.add(new Line( xStart, yStart, 0, -yNegAtTip ) );
        
        // Till centrum
        secondReliefChain.add(new Line( 0, -yNegAtTip, 0, 0 ) );
        
        // Två slutsträckor y+
        secondReliefChain.add(new Line( 0, 0, 0, Constants.START_LENGTH ));
        secondReliefChain.add(new Line( 0, Constants.START_LENGTH, 0, 2 * Constants.START_LENGTH ));
        
        
        return secondReliefChain;
    }


    
    // Skapa cncprogram
    public void calculate() {
        Chain partChain = calculateParting();
        Chain cutGeoChain = calculateCutGeo();
        Chain firstReleifChain = calculateFirstReliefChain();
        Chain secondReliefChain = calculateSecondReliefChain();
//        partChain.saveChainToDXF();
//        cutGeoChain.saveChainToDXF();
//        firstReleifChain.saveChainToDXF();
//        secondReliefChain.saveChainToDXF();
        
        SodickCNCProgram program = new SodickCNCProgram(partedBlank.getStockDia(),
            partedBlank.getHalfLength() + partedBlank.getStockDia() / 2 + 1.0);
        program.setChains( partChain, cutGeoChain, firstReleifChain, secondReliefChain );
        program.buildProgram();
    }
    
    

    
}
