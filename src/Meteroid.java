import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Meteroid {
    private double x;
    private double y;
    private double deltax;
    private double deltay;
    private double x2;
    private double y2;
    private double size;
    private double velocity=2.0;
    private boolean alive;

    public Meteroid(double size, double x2,double y2){
        x=Math.random()*GameEngine.getWindowSize();
        y=0;
        this.x2=x2;
        this.y2=y2;
        this.size=(int)(Math.random()*size)+8;
        findSlope();
        alive=true;
    }
    public Meteroid(double size, double x, double y, double deltax, double deltay){
        this.x=x;
        this.y=y;
        this.size=size;
        setDestination();
        alive=true;
    }

    public void graphic(Graphics g){
        Graphics2D asteroids = (Graphics2D)g;
        asteroids.setColor(Color.ORANGE);
        asteroids.fillOval((int)Math.round(x),(int)Math.round(y),(int)Math.round(size),(int)Math.round(size));
    }

    public double getSize() {
        return size;
    }
    public void setSize(double a){
        size=a;
    }

    public void move(){
        x+=deltax;
        y+=deltay;
        if(collision(x2,y2,size))
            setDestination();

    }
    public void setDestination(){
         x2=(int)(Math.random()*GameEngine.getWindowSize());
         y2=(int)(Math.random()*GameEngine.getWindowSize());
        findSlope();
    }
    public void findSlope(){
        double slope = (y-y2)/(x-x2);
        deltax=x2<x?-1.0:1.0;
        deltay=deltax*slope;
        double distance = Math.sqrt(Math.pow(deltax,2)+Math.pow(deltay,2));
        deltax=(deltax/distance)*Meteors.getSpeed();
        deltay=(deltay/distance)*Meteors.getSpeed();
    }
    public boolean collision(double x, double y, double diameter){

        Shape Main = new Ellipse2D.Float((float)this.x,(float)this.y,(float)size,(float)size);
        Shape Enemy = new Rectangle2D.Float((float)x,(float)y,(float)diameter,(float)diameter);

        Area mainArea = new Area(Main);
        mainArea.intersect(new Area(Enemy));

        return !mainArea.isEmpty();
    }
    public boolean canSplit(){
        return (size<20)?false:true;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public boolean isAlive(){
        return alive;
    }
    public void kill(){
        alive=false;
    }

}
