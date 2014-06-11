import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Filmmakernow
 * Date: 3/17/14
 * Time: 7:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class Lasers {
    ArrayList<Laser> lasers = new ArrayList<Laser>();
    private int ammo;
    public Lasers(){
        ammo=0;

    }
    public void draw(Graphics g){
        for(int x=0;x<lasers.size();x++)
            lasers.get(x).graphic(g);
    }
    public void shoot(double x, double y, double x2, double y2){
        if(ammo>0){
        lasers.add(new Laser(x,y,x2,y2));
        ammo--;
        }
    }
    public void update(){
        for(int x=0;x<lasers.size();x++){
            lasers.get(x).move();
            if(!lasers.get(x).isAlive()){
                lasers.remove(x);
                //x--;
            }
        }

    }
    public void reset(){
        lasers.clear();
        ammo=0;

    }
    public ArrayList<Laser> getLasers(){
        return lasers;
    }
    public int getAmmo(){
        return ammo;
    }
    public void setAmmo(int ammo){
        this.ammo+=ammo;
    }
}
