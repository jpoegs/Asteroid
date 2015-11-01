
import wheelsunh.users.*;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

public class Building extends ShapeGroup
{
    private Random random;
    public static int count = 0;
    private Rectangle building;
    
    public Building()
    {
        super();
        random = new Random();
        AsteroidApp.buildings.add(this);
        building = new Rectangle();
        building.setLocation(0, 0);
        building.setFrameColor(Color.YELLOW);
        building.setFillColor(Color.BLACK);
        building.setSize(random.nextInt(20) + 40, random.nextInt(50) + 60);
        add(building);
        if(AsteroidApp.buildings.size() > 1)
        {
            Building x = AsteroidApp.buildings.get(AsteroidApp.buildings.size() - 2);
            setLocation(x.getBuilding().getXLocation() + x.getBuilding().getWidth() + random.nextInt(5), 
                    500 - building.getHeight());
        }
        else
        {
            setLocation(0, 500 - building.getHeight());
        }
        
        makeWindows();
        
        count++;
    }
    
    public void makeWindows()
    {
        int spacingX = random.nextInt(3) + 3;
        int spacingY = random.nextInt(5) + 3;
        int windowsX = random.nextInt(3) + 4;
        int windowsY = random.nextInt(3) + 5;
        int sizeX = (building.getWidth() - spacingX * (windowsX + 1)) / windowsX;
        int sizeY = (building.getHeight() - spacingY * (windowsY + 1)) / windowsY;
        
        
        for(int i = 1; i <= windowsX; i++)
        {
            for(int k = 1; k <= windowsY; k++)
            {
                
                Rectangle r = new Rectangle(building.getXLocation() + (spacingX / 2 + spacingX * i) + sizeX * (i - 1), 
                        building.getYLocation() + (spacingY / 2 + spacingY * k) + sizeY * (k - 1));
                r.setSize(sizeX, sizeY);
                r.setColor(Color.YELLOW);
                add(r);
            }
        }
    }
    
    public static boolean isFinishedCity()
    {
        try
        {
            Building x = AsteroidApp.buildings.get(AsteroidApp.buildings.size() - 1);
            if(x.getBuilding().getXLocation() + x.getBuilding().getWidth() >= 700)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            return false;
        }
    }
    
    public Rectangle getBuilding()
    {
        return building;
    }
    
    public void hide()
    {
        for(AbstractShape s: getShapes())
        {
            ((Rectangle)s).hide();
        }
    }

}
