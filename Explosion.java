
import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;

/**
 *  A Explosion class is a collection of particles.
 * 
 * Assignment: Program9
 *  
 * @author Jeffrey Poegel
 * @version November 1, 2015
 * 
 */

public class Explosion
{
    private ArrayList<Particle> explosion = new ArrayList<Particle>();
    private Random random;
    private int explosionLife;
    private Point location;
    private int time = 0;

    /**
     * Explosion constructor.
     * 
     * @param x location
     * @param y location
     */
    public Explosion( int x, int y )
    {
        location = new Point( x, y );
        random = new Random();
        explosionLife = random.nextInt( 50 ) + 150;
        explosion.add( new Particle( location.x, location.y ) );
    }

    /**
     * Causes the particles to move slightly when called by animate.
     */
    public void explode()
    {   
        explosion.add( new Particle( location.x, location.y ) );
        for(Particle p: explosion)
        {
            if( time > p.getParticleLife() )
            {
            p.hide();
        }
        else
        {
            p.explode();
        }
        p.explode();
    }

    time++;
    }

    /**
     * Gets how long the explosion will last.
     * 
     * @return explosionLife
     */
    public int getExplosionLife()
    {
        return explosionLife;
    }

    /**
     * Gets the location where the explosion originated from.
     * 
     * @return location
     */
    public Point getLocation()
    {
        return location;
    }

    /**
     * Gets the amount of time passed since the first particle created.
     * 
     * @return time
     */
    public int getTime()
    {
        return time;
    }

    /**
     * Hides all the particles in the explosion.
     */
    public void hide()
    {
        for( Particle p: explosion )
        {
            p.hide();
        }
    }
}
