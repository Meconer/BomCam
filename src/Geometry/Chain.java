
package Geometry;

import Toolpkg.Constants;
import Toolpkg.DxfFile;
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
        // Om det inte är första elementet så kontrollera att startpunkten är
        // samma som slutpunkten på elementet innan
        if ( !chainList.isEmpty() ) {
            
            Point lastEndPoint = chainList.get( chainList.size()-1 ).getEndPoint();
            Point newStartPoint = geo.getStartPoint();
            double distance = lastEndPoint.pointDistance(newStartPoint);
            if ( distance  > Constants.TOLERANCE )
                throw new IllegalArgumentException("Startpunkt för nytt element != slutpunkt på sista elementet vid chainList.add");
            
        }
        chainList.add(geo);
        
    }
    
    public Iterator<Geometry> getIterator() {
        return chainList.iterator();
    }
    
    public void saveChainToDXF() {
        saveChainToDXF("");
    }
    public void saveChainToDXF(String chainName) {
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
        
        dxfFile.saveFile(chainName);
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

    // Sätt in en hörnradie mellan de två senaste elementen som måste vara linjer.
    public void insertFillet(double filletRadius) {
        if ( chainList.size() < 2 ) throw new IllegalArgumentException("Måste vara minst två element i chainlist");
        Geometry l2 = chainList.get(chainList.size()-1);
        if ( !(l2 instanceof Line )) throw new IllegalArgumentException("Sista elementet inte en Line");
        Geometry l1 = chainList.get(chainList.size()-2);
        if ( !( l1 instanceof Line )) throw new IllegalArgumentException("Näst sista elementet inte en Line");
        Arc fillet = Arc.getFillet( (Line) l1, (Line) l2, filletRadius);

        // Skapa nya linjer med hörnradien imellan
        Line nl1 = new Line( l1.getStartPoint(), fillet.getStartPoint());
        Line nl2 = new Line(fillet.getEndPoint(), l2.getEndPoint());
        
        // Tag bort de två sista linjerna
        chainList.remove(chainList.size()-1);
        chainList.remove(chainList.size()-1);
        
        chainList.add(nl1);
        chainList.add(fillet);
        chainList.add(nl2);
    }
}
