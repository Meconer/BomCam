/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SodickSickelProgram;

import Toolpkg.Point;

/**
 *
 * @author Mats
 */
class AngleFineProgram extends AngleProgram {
    
    AngleFineProgram() {
        this.headerFileName = "SodickSickelProgram/angle6.txt";
    }
    
    @Override
    void addSubs() throws Exception {
        
        addSubSection( chain, "N0001");
        addSubSection( chain.getReversedChain(), "N0002");
    }

    @Override
    protected void buildMain(Point startPoint, Point secondPoint, Point lastPoint, Point nextToLastPoint) {
        program.add("G92 " + startPoint.toCNCString( "X", "Y"));
        program.add("G29");
        program.add("T94");
        program.add("T84");
        addForwardSection("C001","H001", secondPoint);
        program.add("T85");
        program.add("G149 G249");
        addBackwardSection("C002", "H002", nextToLastPoint);
        addForwardSection("C900","H003", secondPoint);
        addBackwardSection("C901", "H004", nextToLastPoint);
        addForwardSection("C902","H005", secondPoint);
        addBackwardSection("C903", "H006", nextToLastPoint);
        program.add("M199");
    }

    protected void addBackwardSection( String condition, String offset, Point nextToLastPoint) {
        program.add(condition);
        program.add("G52 A0 G42 H000 G01 " + nextToLastPoint.toCNCString("X", "Y"));
        program.add(sideAngleCode());
        program.add(offset);
        program.add("M98 P0002" ) ;
    }

}
