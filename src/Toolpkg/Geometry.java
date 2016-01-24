
package Toolpkg;

import SodickSickelProgram.CNCCodeLine;
import Toolpkg.Util;

/**
 *
 * @author Mats
 */
public abstract class Geometry {

    public abstract Point getStartPoint();
    public abstract Point getEndPoint();

    public abstract CNCCodeLine geoToCNCCode(Point lastPoint, Util.GCode lastGCode);
    
    
}
