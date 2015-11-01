
import wheelsunh.users.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 *  A AsteroidApp class.
 * 
 * Assignment: Program9
 *  
 * @author Jeffrey Poegel
 * @version October 29, 2015
 * 
 */

public class AsteroidApp implements Animator
{
    private Rectangle background;
    private static ArrayList<Building> buildings = new ArrayList<Building>();
    private int buildingCount;
    private ArrayList<Star> stars;
    private int starCount;
    private Random random;
    private Shield shield;
    private AnimationTimer aTimer;
    private Asteroid currentAsteroid;
    private TextBox info;
    private boolean start = true;
    private boolean gameOver = false;
    private int startDelay = 3000;
    private TextBox countDown;

    /**
     * Constructor for AsteroidApp.
     */
    public AsteroidApp( int difficulty )
    {
        Frame frame = new Frame();
        background = new Rectangle( 0, 0 );
        background.setSize( 700, 500 );
        background.setColor( Color.GRAY.darker().darker().darker() );

        random = new Random();
        stars = new ArrayList<Star>();
        starCount = random.nextInt( 250 ) + 250;
        for( int i = 0; i < starCount; i++ )
        {
            int x = random.nextInt( 700 );
            int y = random.nextInt( 450 );

            stars.add( new Star( x, y ) );
        }

        while( !Building.isFinishedCity() )
        {
            new Building();
            buildingCount++;
        }
        shield = new Shield();
        currentAsteroid = new Asteroid();
        info = new TextBox( 500, 300 );
        countDown = new TextBox( 350, 250 );
        countDown.setFillColor( Color.GRAY );
        countDown.setWidth( 20 );
        countDown.setFrameColor( Color.WHITE );
        info.setText( "Buildings Destroyed: 0%" + "\nAsteroids Destroyed: " + currentAsteroid.getCount() );
        aTimer = new AnimationTimer( 5, this );
        //Adds a key Listener so the game can be played with the keyboard.
        KeyListener listener = new KeyListener() 
        {

            public void keyPressed( KeyEvent e )
            {
                int keyCode = e.getKeyCode();
                
                switch( keyCode ) 
                {
                    case KeyEvent.VK_LEFT:
                        shield.moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT :
                        shield.moveRight();
                        break;
                    case 80:
                        toggleAnimation();
                        break;
                    case 82:
                        resetGame();
                        break;
                }
            }
            
            public void keyReleased( KeyEvent e )
            {
            }

            public void keyTyped( KeyEvent e )
            {
            }
        };
        
        frame.addKeyListener( listener );
        aTimer.start();
    }

    /**
     * Default AsteroidApp constructor.
     */
    public AsteroidApp()
    {
        
    }
    
    /**
     * Main method.
     * 
     * @param args the command line.
     */
    public static void main( String[] args )
    {
        new AsteroidApp( 1 );
    }

    /**
     * Determines if the game is over.
     * 
     * @return true if the buildings are more than 50% destroyed, false otherwise
     */
    public boolean isGameOver()
    {
        if( (double)AsteroidApp.buildings.size() / buildingCount < 0.5 )
        {
            int buildingDest = 100 - ( int)( (double)AsteroidApp.buildings.size() / buildingCount * 100 );
            info.setText( "Buildings Destroyed: " + buildingDest + "%\nAsteroids Destroyed: " + currentAsteroid.getCount() );
            currentAsteroid.hide();
            currentAsteroid.getSmoke().hide();
            for( Explosion e: currentAsteroid.getExplosions() )
            {
                e.hide();
            }
            currentAsteroid.getExplosions().clear();

            if( !gameOver )
            {
                currentAsteroid = new Asteroid( 400 );
                gameOver = true;
                Button reset = new Button( this );
            }
            else
            {
                currentAsteroid.show();
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Animates the game.
     */
    public void animate()
    {
        //Runs a count down timer
        if( start )
        {
            if( startDelay == 0 )
            {
                System.out.println( "Play!" );
                countDown.hide();
                start = false;
            }
            else if( startDelay % 1000 == 0 )
            {
                int x = startDelay / 1000;
                countDown.setText( Integer.toString( x ) );
                System.out.println( x );
                startDelay -= 10;
            }
            else
            {
                startDelay -= 10;
            }
        }
        
        //Runs after start animation and not during end game animation
        if( !isGameOver() && !start )
        {
            currentAsteroid.addShield( shield );
            currentAsteroid.fall();
            int buildingDest = 100 - (int)( (double)AsteroidApp.buildings.size() / buildingCount * 100 );
            info.setText( "Buildings Destroyed: " + buildingDest + "%\nAsteroids Destroyed: " + currentAsteroid.getCount() );

            if( currentAsteroid.hitBuilding() && !isGameOver() )
            {   
                currentAsteroid.getExplosions().add( new Explosion( currentAsteroid.getCenterX(), currentAsteroid.getCenterY() ) );
                currentAsteroid.hide();
                currentAsteroid.getSmoke().hide();
                currentAsteroid = new Asteroid();
            }
            else if( currentAsteroid.isCollision() && !isGameOver() )
            {
                currentAsteroid.hide();
                currentAsteroid.getSmoke().hide();
                currentAsteroid = new Asteroid();
            }
            
            //Only runs when there are explosions on screen.
            if( !currentAsteroid.getExplosions().isEmpty() )
            {

                for( int i = 0; i < currentAsteroid.getExplosions().size(); i++ )
                {
                    
                    if( currentAsteroid.getExplosions().get(i).getTime() > currentAsteroid.getExplosions().get( i ).getExplosionLife() )
                    {
                        currentAsteroid.getExplosions().get( i ).hide();
                        currentAsteroid.getExplosions().remove( i );
                    }
                    else
                    {
                        currentAsteroid.getExplosions().get( i ).explode();
                    }

                }
            }

        }
        //Runs the end game animation
        if( gameOver )
        {
            currentAsteroid.addShield( shield );
            currentAsteroid.fall();;
            int buildingDest = 100 - (int)( (double)AsteroidApp.buildings.size() / buildingCount * 100 );
            info.setText( "Buildings Destroyed: " + buildingDest + "%\nAsteroids Destroyed: " + currentAsteroid.getCount() );
            if( currentAsteroid.hitBuilding() )
            {

            }
            if( currentAsteroid.hitShield() )
            {
                shield.hide();
            }
        }
    }

    /**
     * Hides all the explosions.
     * 
     * @param al
     */
    public void hideAll( ArrayList<Explosion> al )
    {
        for( Explosion e: al )
        {
            e.hide();
        }
    }

    /**
     * Resets the game.
     */
    public void resetGame()
    {
        for( Building b: buildings )
        {
            b.hide();
        }
        currentAsteroid.getExplosions().clear();
        start = true;
        gameOver = false;
        startDelay = 3000;
        buildingCount = 0;
        buildings.clear();
        while( !Building.isFinishedCity() )
        {
            new Building();
        }
        shield.hide();
        shield = new Shield();
        currentAsteroid.hide();
        currentAsteroid = new Asteroid();
        currentAsteroid.resetCount();
        currentAsteroid.resetSpeed();
        info.show();
        info.setText( "Buildings Destroyed: 0%" + "\nAsteroids Destroyed: " + currentAsteroid.getCount() );
        countDown.show();
        System.out.println( "Reset" );
    }
    /**
     * Pauses and Unpauses the game.
     */
    public void toggleAnimation()
    {
        if( aTimer.isRunning() )
        {
            aTimer.stop();
            System.out.println( "Pause" );
        }
        else
        {
            aTimer.start();
            System.out.println( "Unpause" );
        }
    }

    public ArrayList<Building> getBuildings()
    {
        return buildings;
    }
}
