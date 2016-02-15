/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SodickCNCProgram;

import Geometry.Chain;

/**
 *
 * @author matsandersson
 */
public class GeoProgram extends CNCProgram {

    Chain chain;

    public void setChain(Chain chain) {
        this.chain = chain;
    }

}
