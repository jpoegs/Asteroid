
import wheelsunh.users.*;

import java.util.Random;
import java.awt.Color;
import java.awt.Point;

/**
 *  A Particle class.
 * 
 * Assignment: Program9
 *  
 * @author Jeffrey Poegel
 * @version October 29, 2015
 * 
 */

public class Particle extends Ellipse
{
    private Random random;
    private Point location;
    private int particleLife;
    private double x, y;
    private double dx, dy;
    
    /**
     * Particle Constructor.
     * 
     * @param x location
     * @param y location
     */
    public Particle( int x, int y )
    {
        super( x, y );
        random = new Random();
        setSize( random.nextInt( 10 ) + 2, random.nextInt( 10 ) + 2 );
        
        location = new Point( x, y );
        
        this.x = x;
        this.y = y;
        
        dx = ( random.nextDouble() - 0.5 );
        dy = ( random.nextDouble() - 0.5 );
        
        int c = random.nextInt( 3 );
        switch( c )
        {
            case 0:
                setColor( Color.RED );
                break;
            case 1:
                setColor( Color.ORANGE );
                break;
            case 2:
                setColor( Color.BLACK );
                break;
            default:
                setColor( Color.RED );
                break;        
        }
        
        particleLife = random.nextInt( 100 ) + 50;
    }
    
    /**
     * Causes the particle to move slightly.
     */
    public void explode()
    {
        x += dx;
        y += dy;
        setLocation( (int)x, (int)y );
        particleLife++;
    }
    
    /**
     * Gets how long the particle will last for.
     * 
     * @return particleLife
     */
    public int getParticleLife()
    {
        return particleLife;
    }
    
    /**
     * Gets the location of the particle.
     */
    public Point getLocation()
    {
        return location;
    }

}
