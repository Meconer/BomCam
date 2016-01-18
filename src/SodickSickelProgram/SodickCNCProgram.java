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
    private final MainProgram mainProgram;
    private final CutProgram cutProgram;
    private final PartProgram partProgram;
    private final FirstReliefProgram firstReliefProgram;
    private final SecondReliefProgram secondReliefProgram;
    

    public SodickCNCProgram() {
        mainProgram = new MainProgram();
        cutProgram = new CutProgram();
        partProgram = new PartProgram();
        firstReliefProgram = new FirstReliefProgram();
        secondReliefProgram = new SecondReliefProgram();
    }

    public void setChains(Chain partChain, Chain cutGeoChain, Chain firstReleifChain, Chain secondReliefChain) {
        partProgram.setChain(partChain);
        cutProgram.setChain(cutGeoChain);
        firstReliefProgram.setChain(firstReleifChain);
        secondReliefProgram.setChain(secondReliefChain);
        
        
    }
    
    public void buildProgram() {
        partProgram.build("F0S0.NC");
        cutProgram.build("F90S10.NC");
        firstReliefProgram.build("G120S5.NC");
        secondReliefProgram.build("G120S5.NC");
    
    }   
    
       
}
