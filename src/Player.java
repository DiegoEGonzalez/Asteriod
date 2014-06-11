import java.awt.*;
import java.util.ArrayList;

public class Player {
    private static double x;
    private static double y;
    private static double deltaX;
    private static double deltaY;
    private static boolean up,down,left,right=false;
    private static double speed=2.5;
    private static int score;
    private static String name;
    private static boolean alive;

    private static long time;

    final static double laserSpeed=.1;



    public Player(){
        x=100;
        y=100;
        score=0;
        alive=true;
    }

    public static void up(){
        deltaY=-speed;
        up=true;
    }
    public static void stopUp(){
        deltaY=0;
        up=false;
        if(down==true)
            down();
    }
    public static void down(){
        deltaY=speed;
        down=true;

    }
    public static void stopDown(){
        deltaY=0;
        down=false;
        if(up==true)
            up();
    }
    public static void left(){
        deltaX=-speed;
        left=true;
    }
    public static void stopLeft(){
        deltaX=0;
        left=false;
        if(right==true)
           right();
    }
    public static void right(){
        deltaX=speed;
        right=true;
    }
    public static void stopRight(){
        deltaX=0;
        right=false;
        if(left==true)
            left();
    }
    public static void move(){
        x+=deltaX*GameEngine.getZoomSize();
        y+=deltaY*GameEngine.getZoomSize();

        if(x>400){
            x=-10;
        } else if(x<-10){
            x=400;
        }
        if(y>400){
            y=-10;
        } else if(y<-10){
            y=400;
        }


    }
    public static void reset(){
        x=100;
        y=100;
        deltaX=0;
        deltaY=0;
        score=0;
        time=0;
        alive=true;

    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public void setScore(int a){
        if(time==0){
        score+=a;
        time = System.currentTimeMillis();
        } else {
            int timeTook=(int)(Math.abs(time-System.currentTimeMillis())/10);
            System.out.println(timeTook);
            if(timeTook<1000){
                score+=(a*1000)/timeTook;
                System.out.println("Added: "+(a*1000)/timeTook);
            } else {
                score+=a;
            }
            time=System.currentTimeMillis();


        }
    }
    public int getScore(){
        return score;
    }
    public void setSpeed(double a){
        speed+=a;
    }
    public void setName(String a){
        name=a;
    }
    public String getName(){
        return name;
    }
    public void kill(){
        alive=false;
    }
    public boolean isAlive(){
        return alive;
    }


}
