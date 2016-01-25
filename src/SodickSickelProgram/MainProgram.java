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
class MainProgram extends CNCProgram {
    private final double stockDia;
    private final double firstTiltMeasurePoint;

    public MainProgram(double stockDia, double firstTiltMeasurePoint) {
        this.stockDia = stockDia;
        this.firstTiltMeasurePoint = firstTiltMeasurePoint;
    }
    
    
    public void build(String fileName ) {
        this.fileName = fileName;
        
        program.add("( SICKEL ) ");
        program.add("G56");
        program.add("");
    }
}
