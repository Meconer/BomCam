
package Toolpkg;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Mats
 */
public class Chain {

    ArrayList<Geometry> chainList;

    public Chain() {
        chainList = new ArrayList<>();
    }
    
    public void add( Geometry geo ) {
        chainList.add(geo);
    }
    
    public Iterator<Geometry> getIterator() {
        return chainList.iterator();
    }
    
    public void saveChainToDXF() {
        DxfFile dxfFile = new DxfFile();
        dxfFile.addHeader();
        for (Geometry geo : chainList) {
            if ( geo instanceof Line ) {
                Point2D.Double startPoint = new Point2D.Double( ((Line) geo).getxStart(), ((Line) geo).getyStart());
                Point2D.Double endPoint = new Point2D.Double( ((Line) geo).getxEnd(), ((Line) geo).getyEnd());
                dxfFile.addLine( startPoint, endPoint );
            }
            if ( geo instanceof Arc) {
                Point2D.Double centerPoint = new Point2D.Double( ((Arc) geo).getCenterX(), ((Arc) geo).getCenterY());
                double sA = ((Arc) geo).getStartAngle();
                double eA = ((Arc) geo).getEndAngle();
                if ( eA > sA ) {
                    dxfFile.addArc(centerPoint, sA, eA, ((Arc) geo).getRadius());
                } else {
                    dxfFile.addArc(centerPoint, eA, sA, ((Arc) geo).getRadius());
                }
            }
        }
        dxfFile.addEnd();
        
        dxfFile.saveFile();
    }

    public Point getStartPoint() {
        if ( chainList.isEmpty() ) return null;
        return chainList.get(0).getStartPoint();
    }

    public Point getSecondPoint() {
        if ( chainList.isEmpty() ) return null;
        return chainList.get(0).getEndPoint();
    }

    public Point getLastPoint() {
        if ( chainList.isEmpty() ) return null;
        return chainList.get( chainList.size()-1).getEndPoint();
    }

    public Point getNextToLastPoint() {
        if ( chainList.isEmpty() ) return null;
        return chainList.get( chainList.size()-1).getStartPoint();
    }
    
    public Chain getReversedChain() {
        Chain reversedChain = new Chain();
        
        for ( int i = chainList.size()-1 ;  i >= 0 ; i-- ) {
            Geometry geo = chainList.get(i);
            if ( geo instanceof Line ) reversedChain.add( ( (Line) geo).getReversedLine() );
            if ( geo instanceof Arc ) reversedChain.add(  ((Arc) geo).getReversedArc() );
        }
        
        return reversedChain;
    }
}
