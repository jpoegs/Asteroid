
import wheelsunh.users.*;

import java.util.Random;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class Shield extends RoundedRectangle
{

    private Point lastMousePosition;
    
    public Shield()
    {
        super();
        setLocation( 350, 375 );
        setSize(100, 20);
        setFrameColor(Color.BLACK);
        setFillColor(Color.ORANGE);
    }
    
    @Override
    public void mousePressed(MouseEvent e)
    {
        lastMousePosition = e.getPoint();
    }
    
    @Override
    public void mouseDragged(MouseEvent e)
    {
        int dx = e.getPoint().x - lastMousePosition.x;
        
        if(getXLocation() + dx < 0)
        {
            setLocation(0, getYLocation());
        }
        else if(getXLocation() + dx > 600)
        {
            setLocation(600, getYLocation());
        }
        else
        {
            setLocation(getXLocation() + dx, getYLocation());
        }
        lastMousePosition = e.getPoint();
    }
    
    public void moveLeft()
    {
        setLocation(getXLocation() - 30, getYLocation());
    }
    
    public void moveRight()
    {
        setLocation(getXLocation() + 30, getYLocation());
    }
}
