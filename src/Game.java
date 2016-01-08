

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author lalim9800
 */


public class Game extends JComponent implements KeyListener, MouseMotionListener, MouseListener { 

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 900;
    
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000)/desiredFPS;
    
    
    //create player 
     Rectangle player = new Rectangle(60, 795, 50, 50);
     //ceate a integer for the y value 
     int movey=0;
     //create gravity integer 
     int gravity=1;
    
    
    
    //create jump variable (keyboard variables)
    boolean jump=false;
    boolean inair=false;
    //previous jump boolean 
    boolean pjump=false;
    

    
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g)
    {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);
        Color ground=new Color(232,205,190);
        // GAME DRAWING GOES HERE 
        //set color to yellow 
        g.setColor(Color.yellow);
        //draw the flappy cube 
        g.fillRect(player.x, player.y, player.width,player.height);        
        //make ground
        g.setColor(Color.GREEN);
        g.drawRect(0, 847, 800, 1);
        g.setColor(ground);
        g.fillRect(0, 850, 800, 50);
        
        
        
        // GAME DRAWING ENDS HERE
    }
    
    
    // The main game loop
    // In here is where all the logic for my game will go
    public void run(){
    
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;
         
        // the main game loop section
        // game will end if you set done = false;
        boolean done = false; 
        while(!done)
        {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();
            
            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            
             
            
            //gravity pulling player down
            movey = movey + gravity;
            //jumping 
            if (jump && !inair&&!pjump) {
                //make a big change in y direction
                movey = -20;
                inair = true;
            }
                
                //move player in y direction 
                 player.y = player.y + movey;
            //if feet of playet become lower than floor
            if (player.y + player.height > 847) {
                player.y = 847 - player.height;
                movey = 0;
                inair = false;
            }
            // GAME LOGIC ENDS HERE 
            
            // update the drawing (calls paintComponent)
            repaint();
            
            
            
            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if(deltaTime > desiredTime)
            {
                //took too much time, don't wait
            }else
            {
                try
                {
                    Thread.sleep(desiredTime - deltaTime);
                }catch(Exception e){};
            }
        
    }
    }   
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");
       
        // creates an instance of my game
        Game game = new Game();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        // adds the game to the window
        frame.add(game);
         
        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        frame.addKeyListener(game);
        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key==KeyEvent.VK_SPACE){
            jump=true;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
         int key = e.getKeyCode();
        if(key==KeyEvent.VK_SPACE){
            jump=false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
