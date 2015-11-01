
import wheelsunh.users.*;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

/**
 *  A Building class.
 * 
 * Assignment: Program9
 *  
 * @author Jeffrey Poegel
 * @version October 29, 2015
 * 
 */

public class Building extends ShapeGroup
{
    private Random random;
    private Rectangle building;
    private static AsteroidApp astApp = new AsteroidApp();
    
    /**
     * Building Constructor.
     */
    public Building()
    {
        super();
        random = new Random();
        astApp.getBuildings().add( this );
        building = new Rectangle();
        building.setLocation( 0, 0 );
        building.setFrameColor( Color.YELLOW );
        building.setFillColor( Color.BLACK );
        building.setSize( random.nextInt( 20 ) + 40, random.nextInt( 50 ) + 60 );
        add( building );
        
        if( astApp.getBuildings().size() > 1 )
        {
            Building b = astApp.getBuildings().get( astApp.getBuildings().size() - 2 );
            setLocation( b.getBuilding().getXLocation() + b.getBuilding().getWidth() + random.nextInt( 5 ), 
                    500 - building.getHeight() );
        }
        else
        {
            setLocation( 0, 500 - building.getHeight() );
        }
        
        makeWindows();
    }
    /**
     * Randomly generates the windows on the buildings.
     */
    public void makeWindows()
    {
        int spacingX = random.nextInt( 3 ) + 3;
        int spacingY = random.nextInt( 5 ) + 3;
        int windowsX = random.nextInt( 3 ) + 4;
        int windowsY = random.nextInt( 3 ) + 5;
        int sizeX = ( building.getWidth() - spacingX * ( windowsX + 1 ) ) / windowsX;
        int sizeY = ( building.getHeight() - spacingY * ( windowsY + 1 ) ) / windowsY;
        
        
        for( int i = 1; i <= windowsX; i++ )
        {
            for( int k = 1; k <= windowsY; k++ )
            {
                
                Rectangle r = new Rectangle( building.getXLocation() + ( spacingX / 2 + spacingX * i ) + sizeX * ( i - 1 ), 
                        building.getYLocation() + ( spacingY / 2 + spacingY * k ) + sizeY * ( k - 1 ) );
                r.setSize( sizeX, sizeY );
                r.setColor( new Color( 254, 254, 34 ) );
                add( r );
            }
        }
    }
    
    /**
     * Determines when there are enough buildings to fill the screen.
     * 
     * @return true if there are enough buildings, false otherwise
     */
    public static boolean isFinishedCity()
    {
        try
        {
            Building b = astApp.getBuildings().get( astApp.getBuildings().size() - 1 );
            
            if( b.getBuilding().getXLocation() + b.getBuilding().getWidth() >= 700 )
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch( ArrayIndexOutOfBoundsException e )
        {
            return false;
        }
    }
    
    /**
     * Gets the rectangle that contains the windows.
     * 
     * @return building
     */
    public Rectangle getBuilding()
    {
        return building;
    }
    
    /**
     * Creates a hide method for the ShapeGroup.
     */
    public void hide()
    {
        for( AbstractShape s: getShapes() )
        {
            ( (Rectangle)s ).hide();
        }
    }

}
