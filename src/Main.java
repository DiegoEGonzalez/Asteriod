import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Filmmakernow
 * Date: 3/8/14
 * Time: 1:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class Main  {
    static GameEngine game;
    public static void main(String[] args) {
                game = new GameEngine();
                Game run = new Game();
                game.add(run);
                game.setVisible(true);
                run.start();

                for (;;){
                    run.update();
                    run.repaint();
                    try {
                        Thread.sleep(30);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }

    }
}
