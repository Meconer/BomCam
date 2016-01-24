/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Toolpkg;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author Mats
 */
public class PartedBlank extends Blank {
    
    private final DoubleProperty halfLength;

    public PartedBlank( double diameter, double length, double halfLength) {
        super(diameter, length);
        this.halfLength = new SimpleDoubleProperty( halfLength );
    }

    public double getHalfLength() {
        return halfLength.get();
    }
    
    public DoubleProperty getHalfLengthProperty() {
        return halfLength;
    }
    
    public Chain calculatePartingChain() {
        double stockRadius = getStockDia() / 2;
        double xStart = halfLength.get() + stockRadius ;
      
        // Starta länken
        Chain partingChain = new Chain();
        
        // Börja med två startsträckor
        partingChain.add(new Line(xStart, -stockRadius - Constants.START_LENGTH * 2, xStart , -stockRadius - Constants.START_LENGTH ));
        partingChain.add(new Line(xStart, -stockRadius - Constants.START_LENGTH , xStart , -stockRadius ));
        
        // 45-graderslinjen fram till radien
        double cornDist = 2 * Math.atan(Math.PI/8);
        partingChain.add(new Line(xStart, -stockRadius, halfLength.get() - cornDist + Math.sqrt(2) , -2 + Math.sqrt(2)));
        
        // R2 vid övergången
        partingChain.add(new Arc(halfLength.get() - cornDist, -2, 2, 45, 90, Util.ArcDirection.CCW ) );
        
        // Horisontell linje till spetsen
        partingChain.add(new Line( halfLength.get() - cornDist, 0, 0, 0 ));
        
        // Två slutsträckor
        partingChain.add(new Line( 0, 0, -Constants.START_LENGTH, 0 ));
        partingChain.add(new Line( -Constants.START_LENGTH, 0, -2 * Constants.START_LENGTH, 0 ));
        
        return partingChain;
    }

    
    
}
