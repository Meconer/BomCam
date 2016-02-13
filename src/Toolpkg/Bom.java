
package Toolpkg;

import SodickBomProgram.BomProgram;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author Mats
 */
public class Bom {
    
    private final PartedBlank partedBlank;            // Delat runt ämne med längd och dia delat på hälften
    
    private final DoubleProperty noseRadius;          // Spetsens nosradie
    private final DoubleProperty viperLength;         // Längd på viperdel
    private final DoubleProperty clearance;           // Frigång från spetsen i x-led
    private final DoubleProperty clearanceLength;     // Längd på frigång i z-led
    private final DoubleProperty radiusAtTip;         // Spetsens läge i x-led
    private final DoubleProperty straightFrontLength; // Längd på den raka delen framtill
    
    
    private static final double START_LENGTH = 0.5;

    public Bom() {
        // Set up default values
        partedBlank = new PartedBlank(12, 50, 10);
        noseRadius = new SimpleDoubleProperty(0.2);
        clearance = new SimpleDoubleProperty(0.5);
        clearanceLength = new SimpleDoubleProperty(10);
        radiusAtTip = new SimpleDoubleProperty(5.8);
        straightFrontLength = new SimpleDoubleProperty(6);
        viperLength = new SimpleDoubleProperty(0.1);
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
    
    public double getViperLength() {
        return viperLength.get();
    }

    public DoubleProperty getViperLengthProperty() {
        return viperLength;
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
    

    
    public Chain calculateParting() {

        return partedBlank.calculatePartingChain();

    }

    public Chain calculateCutGeo() {
        // Starta länken
        Chain cutGeoChain = new Chain();
        
        // Börja med två startsträckor
        double stockRadius = partedBlank.getStockDia()/2;
        double xStart = (stockRadius-radiusAtTip.get()) + clearance.get() + clearanceLength.get();
        double yStart = -stockRadius;
        cutGeoChain.add(new Line( xStart, yStart-1, xStart, yStart - 0.5 ));
        cutGeoChain.add(new Line( xStart, yStart-0.5, xStart, yStart ));
        
        // Sedan 45 grader mot clearanceLength till frigången med R1 mellan.
        double yClearance = - radiusAtTip.get() + clearance.get();
        double r1Lengt45 = 1 / Math.sqrt(2);
        double xNext = clearanceLength.get() + r1Lengt45 - Math.tan( Math.toRadians(22.5));
        double yNext = yClearance - 1 + r1Lengt45;
        cutGeoChain.add(new Line( xStart, yStart, xNext, yNext ) );
        
        // radien.
        xNext = clearanceLength.get() - Math.tan(Math.toRadians(22.5));
        yNext = yClearance;
        double xCenter = xNext;
        double yCenter = yNext - 1.0; // Radie 1
        cutGeoChain.add(new Arc(xCenter, yCenter, 1.0, 45, 90, Util.ArcDirection.CCW ));
        xStart = xNext;
        yStart = yNext;
        
        // Frigången fram till den möter 10-graderslinjen upp till skärspetsen
        double radianAngle = Math.toRadians( 10 );
        double yLength10 = radiusAtTip.get() + yClearance - noseRadius.get() * ( 1 - Math.cos( radianAngle ) );
        double xLength10 = yLength10 / Math.tan( radianAngle );
        xNext = viperLength.get() + noseRadius.get() * ( 1 + Math.sin( radianAngle ) ) + xLength10 ;
        cutGeoChain.add(new Line( xStart, yStart, xNext, yNext));
        xStart = xNext;
        yStart = yNext;
        
        // Och så 10-graderslinjen
        xNext = xStart - xLength10;
        yNext = yStart - yLength10;
        cutGeoChain.add(new Line( xStart, yStart, xNext, yNext));
        
        // Nosradien fram till viperstarten.
        xCenter = noseRadius.get() + viperLength.get();
        yCenter = -radiusAtTip.get() + noseRadius.get();
        xNext = xCenter;
        yNext = -radiusAtTip.get();
        cutGeoChain.add(new Arc( xCenter, yCenter, noseRadius.get(), 280, 270, Util.ArcDirection.CW));
        xStart = xNext;
        yStart = yNext;
        
        // Viperdelen
        xNext = noseRadius.get();
        yNext = yStart;
        cutGeoChain.add(new Line(xStart, yStart, xNext, yNext));
        xStart = xNext;

        // yCenter är samma som tidigare.
        // Nosradien efter viperdelen.
        xCenter = xStart;
        xNext = 0;
        yNext = yCenter;
        cutGeoChain.add(new Arc(xCenter, yCenter, noseRadius.get(), 270, 180, Util.ArcDirection.CW));
        xStart = xNext;
        yStart = yNext;
        
        // Raksträcka för plansvarvning som angivet.
        xNext = 0;
        yNext = -radiusAtTip.get() + straightFrontLength.get();
        cutGeoChain.add(new Line(xStart, yStart, xNext, yNext ));
        xStart = xNext;
        yStart = yNext;
        
        // 30 grader vidare ut till ämneskanten.
        double leftY = stockRadius - yStart;
        xNext = leftY * Math.tan(Math.toRadians(30));
        yNext = stockRadius;
        cutGeoChain.add( new Line( xStart, yStart, xNext, yNext ));
        xStart = xNext;
        yStart = yNext;
        
        // Två slutsträckor
        yNext = yStart + START_LENGTH;
        cutGeoChain.add(new Line( xStart, yStart, xStart, yNext));
        xStart = xNext;
        yStart = yNext;
        yNext = yStart + START_LENGTH;
        cutGeoChain.add(new Line( xStart, yStart, xStart, yNext));
        
        return cutGeoChain;
        
    }
    
    
    
    private Chain calculateFirstReliefChain() {
        // Starta länken
        Chain firstReliefChain = new Chain();
        
        // Faktor för släppningsmått
        double yRelFact = Constants.FIRST_RELIEF_FACTOR;
        // Börja med två startsträckor
        double stockRadius = partedBlank.getStockDia()/2;
        double xStart = (stockRadius-radiusAtTip.get()) + clearance.get() + clearanceLength.get();
        double yStart = -stockRadius;
        firstReliefChain.add(new Line( xStart, yStart-1, xStart, yStart - 0.5 ));
        firstReliefChain.add(new Line( xStart, yStart-0.5, xStart, yStart ));
        
        // Sedan snett mot clearanceLength till frigången med R1 mellan.
        double yClearance = - radiusAtTip.get() + clearance.get();
        double xNext = clearanceLength.get();
        double yNext = yClearance;
        
        // Och så skalas yvärdet ned till 86% av ursprungsvärdet för att få rätt släppning.
        // Beräkningar gjorda i cad:en
        yNext *= yRelFact;
        firstReliefChain.add(new Line( xStart, yStart, xNext, yNext ) );
        xStart = xNext;
        yStart = yNext;
        

        
        // Frigången fram till den möter 10-graderslinjen upp till skärspetsen
        double radianAngle = Math.toRadians( 10 );
        double yLength10 = radiusAtTip.get() + yClearance - noseRadius.get() * ( 1 - Math.cos( radianAngle ) );
        double xLength10 = yLength10 / Math.tan( radianAngle );
        xNext = viperLength.get() + noseRadius.get() * ( 1 + Math.sin( radianAngle ) ) + xLength10 ;
        firstReliefChain.add(new Line( xStart, yStart, xNext, yNext));
        xStart = xNext;
        yStart = yNext;
        
        firstReliefChain.insertFillet( 1.0 ); // Insert a fillet radius before last line
        
        // Och så 10-graderslinjen
        xNext = xStart - xLength10;
        yNext = yStart - yLength10;
        firstReliefChain.add(new Line( xStart, yStart, xNext, yNext));
        
        xStart = xNext;
        yStart = yNext;
        
        // Go to x0
        xNext = 0;
        firstReliefChain.add(new Line( xStart, yStart, xNext, yNext));
        xStart = xNext;
        
        // Two end lines
        xNext = -START_LENGTH;
        firstReliefChain.add(new Line( xStart, yStart, xNext, yNext));
        xStart = xNext;
        xNext = xStart - START_LENGTH;
        firstReliefChain.add(new Line( xStart, yStart, xNext, yNext));
        
        return firstReliefChain;
        
    }

    private Chain calculateSecondReliefChain() {
        // Starta länken
        Chain secondReliefChain = new Chain();
        
        // Faktor för släppningsmått
        double yRelFact = Constants.FIRST_RELIEF_FACTOR;
        // Börja med två startsträckor
        double stockRadius = partedBlank.getStockDia()/2;
        double xStart = (stockRadius-radiusAtTip.get()) + clearance.get() + clearanceLength.get();
        double yStart = -stockRadius;
        secondReliefChain.add(new Line( xStart, yStart-1, xStart, yStart - 0.5 ));
        secondReliefChain.add(new Line( xStart, yStart-0.5, xStart, yStart ));
        
        // Sedan snett mot clearanceLength till frigången med R1 mellan.
        double yClearance = - radiusAtTip.get() + clearance.get();
        double xNext = clearanceLength.get();
        double yNext = yClearance;
        
        // Och så skalas yvärdet ned till 86% av ursprungsvärdet för att få rätt släppning.
        // Beräkningar gjorda i cad:en
        yNext *= yRelFact;
        secondReliefChain.add(new Line( xStart, yStart, xNext, yNext ) );
        xStart = xNext;
        yStart = yNext;
        

        
        // Frigången fram till den möter 10-graderslinjen upp till skärspetsen
        double radianAngle = Math.toRadians( 10 );
        double yLength10 = radiusAtTip.get() + yClearance - noseRadius.get() * ( 1 - Math.cos( radianAngle ) );
        double xLength10 = yLength10 / Math.tan( radianAngle );
        xNext = viperLength.get() + noseRadius.get() * ( 1 + Math.sin( radianAngle ) ) + xLength10 ;
        secondReliefChain.add(new Line( xStart, yStart, xNext, yNext));
        xStart = xNext;
        yStart = yNext;
        
        secondReliefChain.insertFillet( 1.0 ); // Insert a fillet radius before last line
        
        // Och så 10-graderslinjen
        xNext = xStart - xLength10;
        yNext = yStart - yLength10;
        secondReliefChain.add(new Line( xStart, yStart, xNext, yNext));
        
        xStart = xNext;
        yStart = yNext;
        
        // Go to x0
        xNext = 0;
        secondReliefChain.add(new Line( xStart, yStart, xNext, yNext));
        xStart = xNext;
        
        // Two end lines
        xNext = -START_LENGTH;
        secondReliefChain.add(new Line( xStart, yStart, xNext, yNext));
        xStart = xNext;
        xNext = xStart - START_LENGTH;
        secondReliefChain.add(new Line( xStart, yStart, xNext, yNext));
        
        return secondReliefChain;
        
    }

        


    public void calculate() {
        Chain partChain = calculateParting();
        //partChain.saveChainToDXF("PartChain.dxf");
        Chain cutGeoChain = calculateCutGeo();
        //cutGeoChain.saveChainToDXF("CutGeoChain.dxf");
        Chain firstReleifChain = calculateFirstReliefChain();
        // firstReleifChain.saveChainToDXF("FirstReliefChain.dxf");
        Chain secondReliefChain = calculateSecondReliefChain();
        //secondReliefChain.saveChainToDXF("SecondReliefChain.dxf");
        BomProgram program = new BomProgram(partedBlank.getStockDia(),
            partedBlank.getHalfLength() + partedBlank.getStockDia() / 2 + 1.0);
        program.setChains( partChain, cutGeoChain, firstReleifChain, secondReliefChain );
        program.buildProgram();
    }
        
    

    
    
}
