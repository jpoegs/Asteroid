
import wheelsunh.users.*;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.awt.Point;

/**
 *  A Asteroid class.
 * 
 * Assignment: Program9
 *  
 * @author Jeffrey Poegel
 * @version October 29, 2015
 * 
 */

public class Asteroid extends Ellipse
{
    private static ArrayList<Explosion> explosions = new ArrayList<Explosion>();;
    private ArrayList<Ellipse> craters;
    private ArrayList<Point> craterPoint;
    private Line smoke;
    private Random random;
    private double xLocation;
    private double yLocation;
    private int size;
    private static int difficulty = 1;
    private static int speed = 2;
    private double dx;
    private double dy;
    private static int count;
    private static Shield shield;
    private boolean endGameAsteroid;
    private static AsteroidApp astApp = new AsteroidApp();

    /**
     * Asteroid constructor.
     */
    public Asteroid()
    {
        super();
        random = new Random();
        craters = new ArrayList<Ellipse>();
        craterPoint = new ArrayList<Point>();
        setLocation(random.nextInt( 700 ), -50);
        size = random.nextInt( 25 ) + 20;
        setSize( size, size );
        smoke = new Line( getCenter(), getCenter() );
        smoke.setThickness( 3 );
        setFrameThickness( 2 );
        setFrameColor( Color.BLACK );
        setFillColor( Color.GRAY );
        smoke.setColor( Color.GRAY );
        xLocation = getCenterX();
        yLocation = getCenterY();
        dx = random.nextDouble() * (random.nextInt( 6 ) - 3 );
        dy = random.nextDouble() * random.nextInt( 5 ) + speed * difficulty;
        endGameAsteroid = false;
        createCraters();
    }

    /**
     * End game asteroid constructor.
     * 
     * @param s
     */
    public Asteroid( int s )
    {
        super();
        this.size = s;
        craters = new ArrayList<Ellipse>();
        craterPoint = new ArrayList<Point>();
        random = new Random();
        setSize( size, size );
        setLocation( 350, -size );
        setFrameThickness( 2 );
        setFrameColor( Color.BLACK );
        setFillColor( Color.GRAY );
        smoke = new Line( getCenter(), getCenter() );
        smoke.setThickness( size / 5 );
        smoke.setColor( Color.GRAY );
        xLocation = getXLocation();
        yLocation = getYLocation();
        dx = 0.0;
        dy = 1.0;
        endGameAsteroid = true;
        createCraters();
    }

    /**
     * Gets xLocation.
     * 
     * @return xLocation
     */
    public double getxLocation()
    {
        return xLocation;
    }

    /**
     * Gets yLocation.
     * 
     * @return yLocation
     */
    public double getyLocation()
    {
        return yLocation;
    }

    /**
     * Gets dx.
     * 
     * @return dx
     */
    public double getDx()
    {
        return dx;
    }

    /**
     * Gets dy.
     * 
     * @return dy
     */
    public double getDy()
    {
        return dy;
    }
    
    /**
     * Gets the number of Asteroids destroyed by the shield.
     * 
     * @return count
     */
    public int getCount()
    {
        return count;
    }
    
    /**
     * Resets the number of Asteroids destroyed by the shield to 0.
     */
    public void resetCount()
    {
        count = 0;
    }
    
    /**
     * Resets the speed of the Asteroid to 2.
     */
    public void resetSpeed()
    {
        speed = 2;
    }
    
    /**
     * Sets the speed multiplier / difficulty of the game.
     * 
     * @param d
     */
    public void setDifficulty( int d )
    {
        difficulty = d;
    }

    /**
     * Creates craters on the asteroid using the equation of a circle.
     */
    public void createCraters()
    { 
        int amountOfCraters = random.nextInt( 6 ) + 4;

        for( int i = 0; i < amountOfCraters; i++ )
        {
            int size = random.nextInt( this.size / 5 ) + 2;
            int x = random.nextInt( getWidth() - size ) + getXLocation() + size / 2;
            // y = h +- sqrt( r^2 - (x-k)^2 )

            int b = getXLocation() + getWidth();

            int y1 = (int)( getCenterY() + Math.sqrt( Math.pow( ( getWidth() - size) / 2, 2 ) - Math.pow( x - getCenterX(), 2 ) ) );
            int y2 = (int)( getCenterY() - Math.sqrt( Math.pow( ( getWidth() - size) / 2, 2 ) - Math.pow( x - getCenterX(), 2 ) ) );

            int y;

            try
            {
                y = random.nextInt( y1 - y2 ) + y2;
            }
            catch( IllegalArgumentException e )
            {
                y = getCenterY();
            }

            Ellipse e = new Ellipse();
            e.setSize( size, size );
            e.setCenter( new Point( x, y ) );
            e.setColor( Color.BLACK );
            craterPoint.add( new Point( ((int)xLocation - e.getCenterX() ), ( (int)yLocation - e.getCenterY() ) ) );
            craters.add( e );
        }
    }

    /**
     * Causes the asteroid to fall.
     */
    public void fall()
    {
        
        for( int i = 0; i < craters.size(); i++ )
        {
            craters.get( i ).setCenter( new Point( (int)( xLocation + dx + craterPoint.get( i ).x ), (int)( yLocation + dy + craterPoint.get( i ).y ) ) );
        }
        xLocation += dx;
        yLocation += dy;
        setCenter( new Point( (int)xLocation, (int)yLocation ) );
        smoke.setP2( getCenter() );
    }
    
    /**
     * Out of bounds of frame detection.
     * 
     * @return true if the asteroid is off the screen, false otherwise
     */
    public boolean isCollision()
    {
        if( this.getXLocation() < 0 || this.getXLocation() > 700 || this.getYLocation() > 500 )
        {
            return true;
        }
        if( this.boundsIntersects( shield ) )
        {
            count++;
            if( count % 10 == 0 )
            {
                speed += 1;
            }
            return true;
        }

        return false;
    }

    /**
     * Collision detection for the shield.
     * 
     * @return true if asteroid hits the shield, false otherwise
     */
    public boolean hitShield()
    {
        if( this.boundsIntersects( shield ) )
        {
            return true;
        }

        return false;
    }

    /**
     * Collision detection for the buildings.
     * 
     * @return true if asteroid hits a building, false otherwise
     */
    public boolean hitBuilding()
    {
        for( int i = 0; i < astApp.getBuildings().size(); i++ )
        {
            if( this.boundsIntersects( astApp.getBuildings().get( i ) ) )
            {
                astApp.getBuildings().get( i ).hide();
                astApp.getBuildings().remove( i );
                return true;
            }
        }
        return false;
    }

    /**
     * Gets explosions.
     * 
     * @return explosions
     */
    public ArrayList<Explosion> getExplosions()
    {
        return explosions;
    }
    
    /**
     * Allows the Asteroid to manipulate the shield.
     * 
     * @param s
     */
    public void addShield(Shield s)
    {
        shield = s;
    }
    
    /**
     * Gets the smoke line.
     * 
     * @return smoke
     */
    public Line getSmoke()
    {
        return smoke;
    }
    
    /**
     * Hides the Asteroid and hides the craters.
     */
    @Override
    public void hide()
    {
        super.hide();
        smoke.hide();
        for( Ellipse e: craters )
        {
            e.hide();
        }
    }
    /*
    public void show()
    {
        if( smoke != null )
        {
            smoke.show();
        }
        if( craters != null )
        {
            for( Ellipse s: craters )
            {
                s.show();
            }
        }
    }*/
}
