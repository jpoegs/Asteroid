
import wheelsunh.users.*;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

public class Asteroid extends Ellipse
{
    private ArrayList<Explosion> explosions;
    private ArrayList<Ellipse> craters;
    private Line smoke;
    private Random random;
    private double x;
    private double y;
    private int size;
    public static int speed = 2; 
    private double dx;
    private double dy;
    public static int count;
    private static Shield shield;
    private boolean endGameAsteroid;
    
    public Asteroid()
    {
        super();
        random = new Random();
        explosions = new ArrayList<Explosion>();
        craters = new ArrayList<Ellipse>();
        setLocation( random.nextInt( 700 ), -50 );
        size = random.nextInt(25) + 20;
        setSize(size, size);
        smoke = new Line( getCenter(), getCenter() );
        smoke.setThickness(3);
        setFrameThickness(2);
        setFrameColor(Color.BLACK);
        setFillColor(Color.GRAY);
        smoke.setColor(Color.GRAY);
        x = getXLocation();
        y = getYLocation();
        dx = random.nextDouble() * (random.nextInt(8) - 4);
        dy = random.nextDouble() * random.nextInt(10) + speed;
        //createCraters();
    }
    
    
    public Asteroid(int s)
    {
        super();
        this.size = s;
        craters = new ArrayList<Ellipse>();
        setLocation(350 - size / 2, -size);
        setSize(size, size);
        smoke = new Line( getCenter(), getCenter() );
        smoke.setThickness(size / 5);
        setFrameThickness(2);
        setFrameColor(Color.BLACK);
        setFillColor(Color.GRAY);
        smoke.setColor(Color.GRAY);
        x = getXLocation();
        y = getYLocation();
        dx = 0.0;
        dy = 1.0;
        endGameAsteroid = true;
    }
    
    public void createCraters()
    { 
        int amountOfCraters = random.nextInt(6) + 4;
        
        for(int i = 0; i < amountOfCraters; i++)
        {
            int size = random.nextInt(5) + 2;
            int x = random.nextInt(getWidth() - size) + getXLocation();
            double yC = Math.sqrt(Math.pow(getWidth() / 2, 2) - Math.pow(x - getCenterX(), 2)) + getCenterY();
            double _yC = -Math.sqrt(Math.pow(getWidth() / 2, 2) - Math.pow(x - getCenterX(), 2)) + getCenterY();
            System.out.println(yC + ", " + _yC);
            int y = random.nextInt((int)(yC - _yC)) + (int)_yC;
            System.out.println(y);
            Ellipse e = new Ellipse((int)x, (int)y);
            e.setColor(Color.BLACK);
            e.setSize(size, size);
            craters.add(e);
        }
    }
    
    public void fall()
    {
        x += dx;
        y += dy;
        setLocation((int)x, (int)y);
        smoke.setP2(getCenter());
        /*
        for(Ellipse e: craters)
        {
            e.setLocation(e.getCenterX() + (int)dx, e.getCenterY() + (int)dy);
        }*/
    }
    
    public boolean isCollision()
    {
        if(this.getXLocation() < 0 || this.getXLocation() > 700 || this.getYLocation() > 500)
        {
            return true;
        }
        if(this.boundsIntersects(shield))
        {
            count++;
            if(count % 10 == 0)
            {
                speed += 3;
            }
            return true;
        }
        
        return false;
    }
    
    public boolean hitShield()
    {
        if(this.boundsIntersects(shield))
        {
            return true;
        }
        
        return false;
    }
    
    public boolean hitBuilding()
    {
        for(int i = 0; i < AsteroidApp.buildings.size(); i++)
        {
            if(this.boundsIntersects(AsteroidApp.buildings.get(i)))
            {
                AsteroidApp.buildings.get(i).hide();
                AsteroidApp.buildings.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<Explosion> getExplosions()
    {
        return explosions;
    }
    
    public void addShield(Shield s)
    {
        shield = s;
    }
    
    public Line getSmoke()
    {
        return smoke;
    }

	@Override
	public void hide() 
	{
		super.hide();
		for(Ellipse e: craters)
		{
			e.hide();
		}
	}
    
    
}
