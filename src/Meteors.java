import java.awt.*;
import java.util.ArrayList;

public class Meteors {

    public static double speed=1;

    public static ArrayList<Meteroid> enemies = new ArrayList<Meteroid>();

    public Meteors(){

    }
    // **** GRAPHICS FOR THE METEOROIDS ****
    public void graphic(Graphics g){
        Graphics2D asteroids = (Graphics2D)g;
        asteroids.setColor(Color.ORANGE);
        for(int enemy=0; enemy<(enemies.size());enemy++){
            int x=(int)(Math.round(enemies.get(enemy).getX())+GameEngine.getOffsetX());
            int y=(int)(Math.round(enemies.get(enemy).getY())+GameEngine.getOffsetY());
            asteroids.fillOval(x,y,(int)(Math.round(enemies.get(enemy).getSize())*GameEngine.getZoomSize()),(int)(Math.round(enemies.get(enemy).getSize())*GameEngine.getZoomSize()));
        }

    }
    public void update(){
        for(int x=0; x<(enemies.size());x++){
            enemies.get(x).move();
            if(!enemies.get(x).isAlive()){
                enemies.remove(x);
                //x--;
            }
        }

    }
    public void add(double x, double y){
        enemies.add(new Meteroid(25,x,y));
    }
    public void reset(){
        enemies.clear();
    }
    public static int crash(int x, int y, int xsize, int ysize){ // Checks for collision, if there is, it returns which it crashed with, else, it returns -1;
        int check =-1;
        for(int enemy=0; enemy<(enemies.size());enemy++){
        if (enemies.get(enemy).collision(x,y,xsize)&&enemies.get(enemy).isAlive())
            check = enemy;
            //hit(enemy);
        }
        return check;
    }
    public int getEnemyNum() {
        return enemies.size();
    }
    public static double getSpeed(){
        return speed;
    }
    public static void setSpeed(double a){
        speed+=a;
    }
    public static void hit(int a){
        if(enemies.get(a).canSplit()){
            enemies.add(new Meteroid(enemies.get(a).getSize()/2.0,enemies.get(a).getX(),enemies.get(a).getY(),enemies.get(a).getX(),enemies.get(a).getY()));
            enemies.add(new Meteroid(enemies.get(a).getSize()/2.0,enemies.get(a).getX(),enemies.get(a).getY(),enemies.get(a).getX(),enemies.get(a).getY()));
        }
           // enemies.remove(a);
           enemies.get(a).kill();

    }
     public void pow(){
         for(int x=0;x<enemies.size();x++){
             enemies.get(x).setSize(enemies.get(x).getSize()/2.0);
         }
     }


}
