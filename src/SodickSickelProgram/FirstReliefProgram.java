/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SodickSickelProgram;

import SodickCNCProgram.StraightProgram;

/**
 *
 * @author Mats
 */
public class FirstReliefProgram extends StraightProgram {
    
    public FirstReliefProgram() {
        //sideAngle = 5.0;
    }

    public void build(String fileName) {
        this.fileName = fileName;
        addHeader();
        addMainProgram();
        try {
            addSubs();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        
    }
   
}