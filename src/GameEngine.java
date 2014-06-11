import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Filmmakernow
 * Date: 10/4/13
 * Time: 8:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameEngine extends JFrame implements KeyListener {
    final private static int windowSizeDefault=400;
    private static double windowSize=400;
    private static double zoomSize=1.0;
    private static double to=1.0;
    private static double offsetX=0.0;
    private static double offsetY=0.0;
    private static double targetX=0.0;
    private static double targetY=0.0;

    public GameEngine(){
        setTitle("  METEOROIDS ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(windowSizeDefault, windowSizeDefault);
        setResizable(false);
        setLocationRelativeTo(null);
        addKeyListener(this);
    }


    public int random(int x){
        Random movement = new Random();
        int y = (movement.nextInt(x));
        return (y);
    }
    public void move(ArrayList<Double> position, ArrayList<Double> destination, int number, int size, double speed){
        if(position.get(number)<destination.get(number)-speed){
            position.set(number, (position.get(number) + speed));
        } else if (position.get(number)>destination.get(number)+speed){
            position.set(number, (position.get(number)-speed));
        } else {
            destination.set(number, (double)random(size));
        }
    }
    public void move(ArrayList<Double> a, ArrayList<Double> b,int number){
          a.set(number,a.get(number)+b.get(number));

    }
    public static boolean collision(int x, int y, int width, int height, int x2, int y2, int width2, int height2){

        Shape Main = new Rectangle2D.Float(x,y,width,height);
        Shape Enemy = new Rectangle2D.Float(x2,y2,width2,height2);

        Area mainArea = new Area(Main);
        mainArea.intersect(new Area(Enemy));

        return !mainArea.isEmpty();
    }
    public boolean collision(int x, int y, int diameter, int x2, int y2, int diameter2){

        Shape Main = new Ellipse2D.Float(x,y,diameter,diameter);
        Shape Enemy = new Rectangle2D.Float(x2,y2,diameter2,diameter2);

        Area mainArea = new Area(Main);
        mainArea.intersect(new Area(Enemy));

        return !mainArea.isEmpty();
    }

    public static double getWindowSize() {
        return windowSize;
    }

    public static void setWindowSize(double windowSize) {
        GameEngine.windowSize = windowSize;
    }


    public static double getZoomSize() {
        return zoomSize;
    }


    public static void setZoomSize(double zoomSize) {
        to = zoomSize;
    }

    public static double getOffsetX() {
        return offsetX;
    }
    public static double getOffsetY() {
        return offsetY;
    }

    public static void setOffsetX(double x) {
        GameEngine.offsetX = 200-10*zoomSize-x;
    }
    public static void setOffsetY(double y) {
        GameEngine.offsetY = 200-10*zoomSize-y;
    }


    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            // REGULAR (ORIGINAL) W,A,S,D FUNCTIONALITY
            case KeyEvent.VK_W:
                Player.stopUp();
                break;
            case KeyEvent.VK_S:
                Player.stopDown();
                break;
            case KeyEvent.VK_A:
                Player.stopLeft();
                break;
            case KeyEvent.VK_D:
                Player.stopRight();
                break;
            // NEW ARROW KEY FUNCTIONALITY
            case KeyEvent.VK_UP:
                Player.stopUp();
                break;
            case KeyEvent.VK_DOWN:
                Player.stopDown();
                break;
            case KeyEvent.VK_LEFT:
                Player.stopLeft();
                break;
            case KeyEvent.VK_RIGHT:
                Player.stopRight();
                break;
        }
    }

    public void keyPressed(KeyEvent e) {

        //**************** NON-GAME RELATED KEYS FOR DEBUG **************

        if (e.getKeyCode() == KeyEvent.VK_Y && e.isControlDown() )
        {
            Highscore.clearScore();
        }

        //*************** GAME KEYS *************************************

        switch (e.getKeyCode()){

            // REGULAR (ORIGINAL) W,A,S,D FUNCTIONALITY
            case KeyEvent.VK_W:
                if(Game.getState()==0){
                    if(Game.getMenu()==0){
                        Game.setMenu(2);
                    } else {
                        Game.setMenu(Game.getMenu() - 1);
                    }
                } else if(Game.getState()==1){
                    Player.up();
                }
                break;
            case KeyEvent.VK_S:
                if(Game.getState()==0){

                    if(Game.getMenu()==2){
                        Game.setMenu(0);
                    } else {
                        Game.setMenu(Game.getMenu()+1);
                    }
                }else if(Game.getState()==1){
                    Player.down();
                }
                break;
            case KeyEvent.VK_A:

                Player.left();

                break;
            case KeyEvent.VK_D:
                Player.right();

                break;


            // NEW ARROW KEY FUNCTIONALITY (SAME CODE AS W,A,S,D)
            case KeyEvent.VK_UP:
                if(Game.getState()==0){
                    if(Game.getMenu()==0){
                        Game.setMenu(2);
                    } else {
                        Game.setMenu(Game.getMenu()-1);
                    }
                } else if(Game.getState()==1){
                    Player.up();
                }
                break;
            case KeyEvent.VK_DOWN:
                if(Game.getState()==0){

                    if(Game.getMenu()==2){
                        Game.setMenu(0);
                    } else {
                        Game.setMenu(Game.getMenu()+1);
                    }
                }else if(Game.getState()==1){
                    Player.down();
                }
                break;
            case KeyEvent.VK_LEFT:

                Player.left();
                break;
            case KeyEvent.VK_RIGHT:

                Player.right();
                break;

            // OTHER KEYS
            case KeyEvent.VK_ESCAPE:
                if(Game.getState()==0&&Game.getTotal()>0){
                    Game.setState(1);
                } else {
                    Game.setState(0);
                }
                break;

            case KeyEvent.VK_ENTER:
                if(Game.getState()==0){
                switch (Game.getMenu()){
                    case 0:
                        Game.setState(1);
                        break;
                    case 1:
                        Game.setState(2);
                        break;
                    case 2:
                        Game.setState(3);
                        break;
                }
                } else {
                    Game.classic^=true;
                }
                break;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void update(double x, double y){
        if(zoomSize<to)
        zoomSize+=.1;
       setOffsetY(y);
       setOffsetX(x);
    }


}
