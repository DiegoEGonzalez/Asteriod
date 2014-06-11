import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Filmmakernow
 * Date: 3/31/14
 * Time: 8:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Explosions {

    public static ArrayList<Particles> particles = new ArrayList<Particles>();

    public static void explosionGraphic(Graphics g){
        if(particles.size()>0)
        for(int particle=0;particle<particles.size();particle++){
            particles.get(particle).graphic(g);
            if(!particles.get(particle).isalive())
                particles.remove(particle);

        }

    }

    public static int size(){
        return particles.size();
    }



}
