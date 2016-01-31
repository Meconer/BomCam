/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SodickSickelProgram;

import SodickCNCProgram.CNCProgram;
import Toolpkg.Chain;
import Toolpkg.Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Mats
 */
class SickelMainProgram extends CNCProgram {

    private final double stockDia;
    private final double firstTiltMeasurePoint;
    private Chain partChain = null;
    private Chain cutGeoChain = null;
    private Chain firstReleifChain = null;
    private Chain secondReliefChain = null;

    private final String subsFileName = "SodickSickelProgram/mainend.txt";

    public SickelMainProgram(double stockDia, double firstTiltMeasurePoint) {
        this.stockDia = stockDia;
        this.firstTiltMeasurePoint = firstTiltMeasurePoint;
    }

    public void build(String fileName) {
        this.fileName = fileName;

        buildHeader();

        buildOP1();
        buildOP2();
        buildOP3();
        buildOP4();

        buildSubs();
    }

    private void buildHeader() {
        program.add("( SICKEL " + Util.cncRound(stockDia, 1) + " )");
        program.add("( TRAD 0.25 ) ");
        program.add("G56");
        program.add("H110 = " + Util.cncRound(stockDia, 4) + " ( AEMNETS DIAMETER )");
        program.add("H111 = " + Util.cncRound(firstTiltMeasurePoint, 4) + " ( LAEGE FOER RIKTPUNKT )");
        program.add("H112 = 8.0 ( AVSTAND MELLAN RIKTPUNKTER ");
        program.add("H113 = 1.0+H110/2 ( FRIGANGSLAGE I Y ) ");
        program.add("M98P0010 ( RIKTA AEMNET )");
        program.add("M98P0011 ( CENTRERA AEMNET )");
        program.add("M98P0012 ( MAET 0-LAEGET I X )");
        program.add("G0 X-0.9");
        program.add("G92 X-1.0 (FLYTTA IN 0.1)");
        program.add("");
    }

    private void buildOP1() {
        if (partChain != null) {
            program.add("( OP1 DELNING HALVA B0 ) ");
            program.add("G00 Y" + Util.cncRound(partChain.getStartPoint().getyPoint(), 4));
            program.add("X" + Util.cncRound(partChain.getStartPoint().getxPoint(), 4));
            program.add("QF0S0()");
            program.add("G56");
            program.add("G00 X-1.0");
            program.add("");
        } else {
            program.add("partChain är null");
        }
    }

    private void buildOP2() {
        if (cutGeoChain != null) {
            program.add("( VRID -90 GRADER OCH RIKTA ) ");
            program.add("H114=6");
            program.add("M98P0009");
            program.add("M98P0012 ( MAET 0-LAEGET I X )");
            program.add("G0 X-0.9");
            program.add("G92 X-1.0 (FLYTTA IN 0.1)");
            program.add("");

            program.add("( OP2 SKAER B-90 ) ");
            program.add("G00 Y" + Util.cncRound(cutGeoChain.getStartPoint().getyPoint(), 4));
            program.add("X" + Util.cncRound(cutGeoChain.getStartPoint().getxPoint(), 4));
            program.add("QF90S10()");
            program.add("G56");
            program.add("G00 X-1.0");
            program.add("");
        } else {
            program.add("cutGeoChain är null");
        }
    }

    private void buildOP3() {
        if (firstReleifChain != null) {
            program.add("( VRID -30 GRADER OCH RIKTA ) ");
            program.add("H114=2");
            program.add("M98P0009");

            program.add("( OP3 SLAEPP 1 B-120 ) ");
            program.add("G00 Y" + Util.cncRound(firstReleifChain.getStartPoint().getyPoint(), 4));
            program.add("X" + Util.cncRound(firstReleifChain.getStartPoint().getxPoint(), 4));
            program.add("QG120S5()");
            program.add("G56");
            program.add("G00 X-1.0");
            program.add("");
        } else {
            program.add("firstReleifChain är null");
        }
    }

    private void buildOP4() {
        if (secondReliefChain != null) {
            program.add("( OP4 SLAEPP 2 B-150,-180,-210,-240,-270 )");
            program.add("M98 P0001 L5");
            program.add("");
            program.add("M02");
            program.add("");
            program.add("");
            program.add("N0001");
            program.add("( VRID -30 GRADER OCH RIKTA ) ");
            program.add("H114=2");
            program.add("M98P0009");

            program.add("G00 Y" + Util.cncRound(secondReliefChain.getStartPoint().getyPoint(), 4));
            program.add("X" + Util.cncRound(secondReliefChain.getStartPoint().getxPoint(), 4));
            program.add("QG150S0N5()");
            program.add("G56");
            program.add("G00 X-1.0");
            program.add("M99");
            program.add("");
        } else {
            program.add("secondReliefChain är null");
        }
    }

    void setChains(Chain partChain, Chain cutGeoChain, Chain firstReleifChain, Chain secondReliefChain) {
        this.partChain = partChain;
        this.cutGeoChain = cutGeoChain;
        this.firstReleifChain = firstReleifChain;
        this.secondReliefChain = secondReliefChain;
    }

    private void buildSubs() {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream is = cl.getResourceAsStream(subsFileName);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                program.add(line);
            }
        } catch (IOException ex) {
            System.err.println("Fel vid läsning av resource : " + subsFileName);
            System.err.println(ex.getMessage());
        }
    }

}
