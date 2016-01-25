/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SodickSickelProgram;

import Toolpkg.Chain;

/**
 *
 * @author Mats
 */
public class SodickCNCProgram {
    
    private final double stockDia;
    private final double firstTiltMeasurePoint;
    
    private final MainProgram mainProgram;
    private final CutProgram cutProgram;
    private final PartProgram partProgram;
    private final FirstReliefProgram firstReliefProgram;
    private final SecondReliefProgram secondReliefProgram;
    

    public SodickCNCProgram(double stockDia, double firstTiltMeasurePoint) {
        mainProgram = new MainProgram( stockDia, firstTiltMeasurePoint );
        cutProgram = new CutProgram();
        partProgram = new PartProgram();
        firstReliefProgram = new FirstReliefProgram();
        secondReliefProgram = new SecondReliefProgram();
        this.stockDia = stockDia;
        this.firstTiltMeasurePoint = firstTiltMeasurePoint;
    }

    public void setChains(Chain partChain, Chain cutGeoChain, Chain firstReleifChain, Chain secondReliefChain) {
        partProgram.setChain(partChain);
        cutProgram.setChain(cutGeoChain);
        firstReliefProgram.setChain(firstReleifChain);
        secondReliefProgram.setChain(secondReliefChain);
        mainProgram.setChains( partChain, cutGeoChain, firstReleifChain, secondReliefChain );
    }
    
    public void buildProgram() {
        partProgram.build("F0S0.NC");
        partProgram.save();
        cutProgram.build("F90S10.NC");
        cutProgram.save();
        firstReliefProgram.build("G120S5.NC");
        firstReliefProgram.save();
        secondReliefProgram.build("G150S0N5.NC");
        secondReliefProgram.save();
        mainProgram.build("MAINS.NC");
        mainProgram.save();
    }   

       
}
