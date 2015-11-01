
import wheelsunh.users.*;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;
import java.awt.event.MouseEvent;

/**
 *  A Button class.
 * 
 * Assignment: Program9
 *  
 * @author Jeffrey Poegel
 * @version October 29, 2015
 * 
 */

public class Button extends TextBox
{
    
    private AsteroidApp app;
    
    /**
     * Button Constructor.
     */
    public Button( AsteroidApp a ) 
    {
        super();
        setSize( 100, 80 );
        setColor( Color.GRAY.brighter() );
        setText( "Reset" );
        app = a;
    }
    
    /**
     * Mouse Pressed.
     * 
     * @param e MouseEvent
     */
    @Override
    public void mousePressed( MouseEvent e )
    {
        setColor( Color.LIGHT_GRAY );
        app.resetGame();
    }
    
    /**
     * Mouse Released.
     * 
     * @param e MouseEvent
     */
    @Override
    public void mouseReleased( MouseEvent e )
    {
        setColor( Color.RED );
        this.hide();
    }

}
