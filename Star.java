
import wheelsunh.users.*;

import java.util.Random;
import java.awt.Color;

/**
 *  A Star class.
 * 
 * Assignment: Program9
 *  
 * @author Jeffrey Poegel
 * @version October 29, 2015
 * 
 */

public class Star extends ShapeGroup
{
    private Random random;
    private Rectangle star1, star2;
    private int size;
    
    /**
     * Star Constructor.
     * 
     * @param x location
     * @param y location
     */
    public Star( int x, int y )
    {
        super();
        random = new Random();
        size = random.nextInt( 2 ) + 1;
        star1 = new Rectangle( x, y );
        star1.setColor( new Color( 250, 250, 210 ) );
        star1.setRotation( random.nextInt( 45 ));
        star1.setSize( size, size );
        add( star1 );
        
        star2 = new Rectangle( x, y );
        star2.setColor( new Color( 250, 250, 210 ) );
        star2.setRotation( star1.getRotation() + 45 );
        star2.setSize( size, size );
        add( star2 );
    }

}
