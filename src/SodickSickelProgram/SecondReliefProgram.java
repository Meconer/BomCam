/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SodickSickelProgram;

/**
 *
 * @author Mats
 */
public class SecondReliefProgram extends StraightProgram {

    void build(String fileName) {
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
