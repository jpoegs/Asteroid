
import wheelsunh.users.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class AsteroidApp implements Animator
{
    private Rectangle background;
    public static ArrayList<Building> buildings = new ArrayList<Building>();
    public static ArrayList<ArrayList<Explosion>> explosionSites = new ArrayList<ArrayList<Explosion>>();
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
    
    public AsteroidApp()
    {
        Frame frame = new Frame();
        background = new Rectangle(0, 0);
        background.setSize(700, 500);
        background.setColor(Color.GRAY.darker().darker().darker());
        
        random = new Random();
        stars = new ArrayList<Star>();
        starCount = random.nextInt(250) + 250;
        for(int i = 0; i < starCount; i++)
        {
            int x = random.nextInt(700);
            int y = random.nextInt(450);
            
            stars.add(new Star(x, y));
        }
        
        while(!Building.isFinishedCity())
        {
            new Building();
        }
        shield = new Shield();
        currentAsteroid = new Asteroid();
        info = new TextBox(500, 300);
        countDown = new TextBox(350, 250);
        countDown.setFillColor(Color.GRAY);
        countDown.setWidth(20);
        countDown.setFrameColor(Color.WHITE);
        info.setText("Buildings Destroyed: 0%" + "\nAsteroids Destroyed: " + Asteroid.count);
        aTimer = new AnimationTimer( 5, this );
        KeyListener listener = new KeyListener() 
        {

            public void keyPressed(KeyEvent e)
            {
                int keyCode = e.getKeyCode();
                System.out.println(keyCode);
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
                 }
            }

            public void keyReleased(KeyEvent arg0)
            {
            }

            public void keyTyped(KeyEvent arg0)
            {
            }
        };       
        frame.addKeyListener(listener);
        aTimer.start();
    }
    
    public static void main(String[] args)
    {
        new AsteroidApp();
    }
    
    
    public boolean isGameOver()
    {
        if((double)AsteroidApp.buildings.size() / Building.count <= 0.5)
        {
            int buildingDest = 100 - (int)((double)AsteroidApp.buildings.size() / Building.count * 100);
            info.setText("Buildings Destroyed: " + buildingDest + "%\nAsteroids Destroyed: " + Asteroid.count);
            currentAsteroid.hide();
            currentAsteroid.getSmoke().hide();
            for(ArrayList<Explosion> e: explosionSites)
            {
               hideAll(e);
            }
            explosionSites.clear();
            
            if(!gameOver)
            {
                currentAsteroid = new Asteroid(400);
                gameOver = true;
                Button reset = new Button(this);
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

    public void animate()
    {
        if(start)
        {
            if(startDelay == 0)
            {
                System.out.println("Play!");
                countDown.hide();
                start = false;
            }
            else if(startDelay % 1000 == 0)
            {
                int x = startDelay / 1000;
                countDown.setText(Integer.toString(x));
                System.out.println(x);
                startDelay -= 5;
            }
            else
            {
                startDelay -= 5;
            }
        }
        
        if(!isGameOver() && !start)
        {
            currentAsteroid.addShield(shield);
            currentAsteroid.fall();
            int buildingDest = 100 - (int)((double)AsteroidApp.buildings.size() / Building.count * 100);
            info.setText("Buildings Destroyed: " + buildingDest + "%\nAsteroids Destroyed: " + Asteroid.count);
            
            if(currentAsteroid.hitBuilding() && !isGameOver())
            {
                currentAsteroid.getExplosions().add(new Explosion(currentAsteroid.getXLocation(), currentAsteroid.getYLocation()));
                explosionSites.add(currentAsteroid.getExplosions());
                currentAsteroid.hide();
                currentAsteroid.getSmoke().hide();
                currentAsteroid = new Asteroid();
            }
            else if(currentAsteroid.isCollision() && !isGameOver())
            {
                currentAsteroid.hide();
                currentAsteroid.getSmoke().hide();
                currentAsteroid = new Asteroid();
            }
            if(!explosionSites.isEmpty())
            {

                for(ArrayList<Explosion> e: explosionSites)
                {
                    int c = random.nextInt(3);
                    
                    if(c == 1)
                    {
                        e.add(new Explosion(e.get(0).getLocation().x, e.get(0).getLocation().y));
                    }
                    
                    for(Explosion x: e)
                    {
                        if(x.getParticleLife() > 200)
                        {
                            hideAll(e);
                            e.remove(this);
                            break;
                        }
                        else
                        {
                            x.explode();
                        }
                    }
                }
            }
            
        }
        if(gameOver)
        {
            currentAsteroid.addShield(shield);
            currentAsteroid.fall();;
            int buildingDest = 100 - (int)((double)AsteroidApp.buildings.size() / Building.count * 100);
            info.setText("Buildings Destroyed: " + buildingDest + "%\nAsteroids Destroyed: " + Asteroid.count);
            currentAsteroid.hitBuilding();
            
            if(currentAsteroid.hitShield())
            {
                shield.hide();
            }
        }
    }
    
    public void hideAll(ArrayList<Explosion> al)
    {
        for(Explosion e: al)
        {
            e.hide();
        }
    }
    
    public void resetGame()
    {
        for(Building b: buildings)
        {
            b.hide();
        }
        explosionSites.clear();
        start = true;
        gameOver = false;
        startDelay = 3000;
        Asteroid.speed = 2;
        Asteroid.count = 0;
        Building.count = 0;
        buildings.clear();
        while(!Building.isFinishedCity())
        {
            new Building();
        }
        shield.hide();
        shield = new Shield();
        currentAsteroid.hide();
        currentAsteroid = new Asteroid();
        info.show();
        info.setText("Buildings Destroyed: 0%" + "\nAsteroids Destroyed: " + Asteroid.count);
        countDown.show();
    }
    
    public void toggleAnimation()
    {
        if(aTimer.isRunning())
        {
            aTimer.stop();
        }
        else
        {
            aTimer.start();
        }
    }

}
