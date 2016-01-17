
package Toolpkg;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Mats
 */
class Chain {

    ArrayList<Geometry> list;

    public Chain() {
        list = new ArrayList<>();
    }
    
    public void add( Geometry geo ) {
        list.add(geo);
    }
    
    public Iterator<Geometry> getIterator() {
        return list.iterator();
    }
    
    public void saveChainToDXF() {
        DxfFile dxfFile = new DxfFile();
        dxfFile.addHeader();
        for ( Iterator<Geometry> it = list.iterator() ; it.hasNext(); ) {
            Geometry geo = it.next();
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
    
}
