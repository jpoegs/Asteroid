
import wheelsunh.users.*;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 *  A Shield class.
 * 
 * Assignment: Program9
 *  
 * @author Jeffrey Poegel
 * @version October 29, 2015
 * 
 */

public class Shield extends RoundedRectangle
{

    private Point lastMousePosition;
    
    /**
     * Shield Constructor.
     */
    public Shield()
    {
        super();
        setLocation( 350, 375 );
        setSize( 100, 20 );
        setFrameColor( Color.BLACK );
        setFillColor( Color.ORANGE );
        
        TextBoxLabel tLable = new TextBoxLabel( "Shield", this )
        {
            public void mouseMoved( MouseEvent e )
            {
                if( getXLocation() < 0 )
                {
                    setLocation( 0, getYLocation() );
                }
                else if( getXLocation() > 600 )
                {
                    setLocation( 600, getYLocation() );
                }
                else
                {
                    setLocation( e.getX(), getYLocation() );
                }
            }
        };
    }
    
    /**
     * Mouses Pressed.
     * 
     * @param e MouseEvent
     */
    @Override
    public void mousePressed( MouseEvent e )
    {
        lastMousePosition = e.getPoint();
    }
    
    /**
     * Mouse Dragged.
     * 
     * @param e MouseEvent
     */
    @Override
    public void mouseDragged( MouseEvent e )
    {
        int dx = e.getPoint().x - lastMousePosition.x;
        
        if( getXLocation() + dx < 0 )
        {
            setLocation( 0, getYLocation() );
        }
        else if( getXLocation() + dx > 600 )
        {
            setLocation( 600, getYLocation() );
        }
        else
        {
            setLocation( getXLocation() + dx, getYLocation() );
        }
        lastMousePosition = e.getPoint();
    }
    
    /**
     * Moves the shield left.
     */
    public void moveLeft()
    {
        setLocation( getXLocation() - 30, getYLocation() );
    }
    
    /**
     * Moves the shield right.
     */
    public void moveRight()
    {
        setLocation( getXLocation() + 30, getYLocation() );
    }
    
    
}
