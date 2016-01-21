
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author lalim9800
 */
public class Game extends JComponent implements KeyListener, MouseMotionListener, MouseListener {

    // Height and Width of our game
    static final int WIDTH = 600;
    static final int HEIGHT = 700;

    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    //create a int to stote the first pipe x value 

    ArrayList<Rectangle> blocks = new ArrayList<>();

    //create player 
   Rectangle player = new Rectangle(60, 100, 50, 50);

    //ceate a integer for the y value 
    int movey = 0;
    //create gravity integer 
    int gravity = 1;
    int thispipe = 0;
    int counter = 0;
    //create jump variable (keyboard variables)
    boolean jump = false;
    //previous jump boolean 
    boolean pjump = false;
    //make done varible stops 
    boolean done = false;
    //make second screen
    int screen = 0;

    //create new font
    Font newFont = new Font("Helvetica", Font.BOLD, 50);
    Font small = new Font("Helvetica", Font.BOLD, 25);

    BufferedImage title = loadImage("flappy bird.jpg");
    

    public BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (Exception e) {
            System.out.println("Error loading " + filename);
        }
        return img;
    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {

// always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        if (screen == 0) {
            g.setFont(newFont);
            g.drawImage(title, 0, 0, WIDTH, HEIGHT, null);
            g.drawString("Click Enter To Start", 50, 350);

        }

        if (screen == 1) {
            

            Color ground = new Color(232, 205, 190);
            Color Backround = new Color(144, 215, 245);
            // GAME DRAWING GOES HERE 

            g.setColor(Backround);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.GREEN);

            for (Rectangle block : blocks) {
                // draw the block
                g.fillRect(block.x, block.y, block.width, block.height);

            }

            //set color to yellow
            g.setColor(Color.yellow);
            //draw the flappy cube 
            g.fillRect(player.x, player.y, player.width, player.height);
            //make ground 

            g.setColor(Color.GREEN); //set color to green 
            g.fillRect(0, 647, 600, HEIGHT - 647); //fill ground
            //SET COLOR TO BLACK
            g.setColor(Color.BLACK);
            //DRAW GROUND OUTLINE
            g.fillRect(0, 647, 600, 3);
            

          //SET COLOR TO BLACK 
            g.setColor(Color.black);
            g.setFont(newFont);
            //out put the pipe counter to the screen
            g.drawString("" + counter / 2, 500, 100);
           
            

            if (done) { //if game ends 
                //set new font created 
                g.setFont(newFont);
                //output gameover (tell user game is over )
                g.drawString("GAMEOVER", 125, 200);
                //set custom font "small"
                g.setFont(small);
                //when game is over tell user there score
                g.drawString(" SCORE: " + counter / 2, HEIGHT / 4, 275);
                //game drawing ends 
            }

        }
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void run() {

        //do intail things 
        //draw top pipes 
        blocks.add(new Rectangle(1000, 0, 100, 200));
        blocks.add(new Rectangle(1500, 0, 100, 300));
        blocks.add(new Rectangle(2000, 0, 100, 100));
        blocks.add(new Rectangle(2500, 0, 100, 300));
        blocks.add(new Rectangle(3000, 0, 100, 350));
        blocks.add(new Rectangle(3500, 0, 100, 100));
        //draw bottom pipes 
        blocks.add(new Rectangle(1000, 450, 100, 647 - 450));
        blocks.add(new Rectangle(1500, 550, 100, 647 - 550));
        blocks.add(new Rectangle(2000, 350, 100, 647 - 350));
        blocks.add(new Rectangle(2500, 550, 100, 647 - 550));
        blocks.add(new Rectangle(3000, 600, 100, 647 - 600));
        blocks.add(new Rectangle(3500, 350, 100, 647 - 350));

        //end initail things 
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime = 0;
        long deltaTime;

        // the main game loop section
        // game will end if you set done = false;
        while (!done) {

            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            //if screen is 1 then start game 
            if (screen == 1) {

//gravity pulling player down
                movey = movey + gravity;
                //jumping 
                if (jump && !pjump) {
                    //make a big change in y direction
                    movey = -15;
                    pjump = true;

                }

                //move player in y direction 
                player.y = player.y + movey;
                //if feet of playet become lower than floor
                if (player.y + player.height > 647) {
                    player.y = 647 - player.height;
                    movey = 0;

                }

                // go through all blocks and decrase the x value by 5 (moving function)
                for (Rectangle block : blocks) {
                    block.x = block.x - 5;
                    if (player.intersects(block)) {
                        done = true;

                    }

                }
                 //when the block gets to -2500 respawn at 500
                for (Rectangle block : blocks) {
                    if (block.x == -2500) {
                        block.x = 500;

                    }
                }

                //if player hits the ground end game 
                if (player.y == 597) {
                    done = true;
                }
                //if player hits the roof end game 
                if (player.y <= 0) {
                    done = true;
                }
                for (Rectangle block : blocks) {
                    if (block.x + 50 == player.x) {
                        counter++;

                    }
                }

            }

            // GAME LOGIC ENDS HERE 
            //
            // update the drawing (calls paintComponent)
            repaint();

            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if (deltaTime > desiredTime) {
                //took too much time, don't wait
            } else {
                try {
                    Thread.sleep(desiredTime - deltaTime);
                } catch (Exception e) {
                };
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
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
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
        if (key == KeyEvent.VK_SPACE) {
            jump = true;
        }
        //if enter is clicked change to screen 1 
        if (key == KeyEvent.VK_ENTER) {
            screen = 1;
        }
        if (key == KeyEvent.VK_R) {

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        //if space bar is pressed player jumps 
        if (key == KeyEvent.VK_SPACE) {
            jump = false;
            pjump = false;
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
