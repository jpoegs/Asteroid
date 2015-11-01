
import wheelsunh.users.*;

import java.util.Random;
import java.awt.Color;
import java.awt.Point;

public class Explosion extends Ellipse
{
    private Random random;
    private Point location;
    private int particleLife;
    private double x, y;
    private double dx, dy;
    private Color color;
    
    public Explosion(int x, int y)
    {
        super(x, y);
        random = new Random();
        setSize(random.nextInt(10) + 2, random.nextInt(10) + 2);
        
        location = new Point(x, y);
        
        this.x = x;
        this.y = y;
        
        dx = random.nextDouble() - 0.5;
        dy = random.nextDouble() - 0.5;
        
        int c = random.nextInt(3);
        switch (c)
        {
            case 0:
                setColor(Color.RED);
                break;
            case 1:
                setColor(Color.ORANGE);
                break;
            case 2:
                setColor(Color.BLACK);
                break;
            default:
                setColor(Color.RED);
                break;
                
        }
    }
    
    public void explode()
    {
        x += dx;
        y += dy;
        setLocation((int)x, (int)y);
        particleLife++;
    }
    
    public int getParticleLife()
    {
        return particleLife;
    }
    
    public Point getLocation()
    {
        return location;
    }

}
