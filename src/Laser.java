import java.awt.*;

public class Laser{
    private double x;
    private double y;
    private double deltax;
    private double deltay;
    final private static int size = 4;
    final private static double speed = 1;
    private boolean alive;

    public Laser(double x,double y,double x2,double y2){
        this.x=x;
        this.y=y;
        double slope = (y-y2)/(x-x2);
        deltax=x2<x?-1.0:1.0;
        deltay=deltax*slope;
        double distance = Math.sqrt(Math.pow(deltax,2)+Math.pow(deltay,2));
        deltax=deltax/distance;
        deltay=deltay/distance;
        alive=true;
    }

    public void graphic(Graphics g){
        Graphics2D lasers = (Graphics2D)g;
        lasers.setColor(Color.RED);
                for(int l=0;l<15;l++){
                move();
                lasers.fillOval((int)Math.round(this.x), (int)Math.round(y), size, size);
                }
        //}
    }

    public void move(){
        x+=deltax;
        y+=deltay;
        int num = (Meteors.crash((int)Math.round(x),(int)Math.round(y),4,4));
        if(num!=-1){ // Checks whether the laser hit any of- the Meteroids
            for(int l=0;l<15;l++)
                Explosions.particles.add(new Particles(Color.ORANGE,3.0,Meteors.enemies.get(num).getX(),Meteors.enemies.get(num).getY(),5.0));
            Meteors.hit(num);
        }
        if(x>410||x<-10||y>410||y<-10){
            alive=false;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDeltax() {
        return deltax;
    }

    public double getDeltay() {
        return deltay;
    }

    public static int getSize() {
        return size;
    }

    public boolean isAlive(){
        return alive;
    }


}
