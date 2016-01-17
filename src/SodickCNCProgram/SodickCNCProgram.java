/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SodickCNCProgram;

/**
 *
 * @author Mats
 */
public class SodickCNCProgram {
    private final MainProgram mainProgram;
    private final PartingProgram partingProgram;
    private final FirstReliefProgram firstReliefProgram;
    private final SecondReliefProgram secondReliefProgram;

    public SodickCNCProgram() {
        mainProgram = new MainProgram();
        partingProgram = new PartingProgram();
        firstReliefProgram = new FirstReliefProgram();
        secondReliefProgram = new SecondReliefProgram();
    }
    
    
    
}
