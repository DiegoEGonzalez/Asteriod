import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;


public class Game extends JPanel implements MouseListener{
    final static String version = "© 2014 Diego Gonzalez version 1.9.6 - Lasers back with Particles! :)";
    static Highscore score = new Highscore();
    final static int windowsizeX=400;
    static double spawn=50;
    //private static ArrayList <Particles> explosion = new ArrayList<Particles>();
    public static boolean classic = false;
    static boolean end =false;
    static JTextField enterName = new JTextField("");
    public boolean gameover;


    final static int windowsizeY=400;

    //final static GameEngi=ne g = new GameEngine();
    final static Meteors m = new Meteors();
    final static Player user = new Player();
    final static Lasers l = new Lasers();

    static int state = 0;
    static int menu=0;


    public static int z = 0;
    public static int XF=150;
    public static int YF=250;
    public static int total=0;

    public ArrayList<Integer> starX = new ArrayList<Integer>();
    public ArrayList<Integer> starY = new ArrayList<Integer>();


    public Game(){
        gameover=false;
        setLayout(null);
        setSize(windowsizeX, windowsizeY);

        enterName.setLocation(125,200);
        enterName.setSize(150, 25);
        enterName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.setName(enterName.getText().length()>0?enterName.getText():"No Name");
                System.out.println(user.getName());
                state=0;
                enterName.setVisible(false);
                enterName.setFocusable(false);
                Main.game.setFocusable(true);
                if(user.getName().length()>15)
                    user.setName(user.getName().substring(0,12)+"...");
                score.addScore(user.getScore(),user.getName());
                reset();
                enterName.setText("");

            }
        });
        enterName.setVisible(false);
        enterName.setFocusable(false);
        add(enterName);
        setBackground(Color.BLACK);
        //addKeyListener(this);
        addMouseListener(this);
        //setFocusable(true);
        setVisible(true);

        Action up = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if(Game.getState()==0){
                    if(Game.getMenu()==0){
                        Game.setMenu(2);
                    } else {
                        Game.setMenu(Game.getMenu() - 1);
                    }
                } else if(Game.getState()==1){
                    Player.up();
                }
            }
        };
        getInputMap().put(KeyStroke.getKeyStroke("W"),
                "up");
        getActionMap().put("up",
                up);

        Action upReleased = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Player.stopUp();
            }
        };
        getInputMap().put(KeyStroke.getKeyStroke("released W"),
                "upReleased");
        getActionMap().put("upReleased",
                upReleased);

        Action down = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if(Game.getState()==0){

                    if(Game.getMenu()==2){
                        Game.setMenu(0);
                    } else {
                        Game.setMenu(Game.getMenu()+1);
                    }
                }else if(Game.getState()==1){
                    Player.down();
                }
            }
        };



        getInputMap().put(KeyStroke.getKeyStroke("S"),
                "down");
        getActionMap().put("down",
                down);

        Action downReleased = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Player.stopDown();
            }
        };
        getInputMap().put(KeyStroke.getKeyStroke("released S"),
                "downReleased");
        getActionMap().put("downReleased",
                downReleased);

        Action left = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Player.left();
            }
        };
        getInputMap().put(KeyStroke.getKeyStroke("A"),
                "left");
        getActionMap().put("left",
                left);

        Action leftReleased = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Player.stopLeft();
            }
        };
        getInputMap().put(KeyStroke.getKeyStroke("released A"),
                "leftReleased");
        getActionMap().put("leftReleased",
                leftReleased);

        Action right = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Player.right();
            }
        };
        getInputMap().put(KeyStroke.getKeyStroke("D"),
                "right");
        getActionMap().put("right",
                right);

        Action rightReleased = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Player.stopRight();
            }
        };
        getInputMap().put(KeyStroke.getKeyStroke("released D"),
                "rightReleased");
        getActionMap().put("rightReleased",
                rightReleased);

        Action enter = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if(Game.getState()==0){
                    switch (Game.getMenu()){
                        case 0:
                            Game.setState(1);
                            gameover=false;
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
            }
        };
        getInputMap().put(KeyStroke.getKeyStroke("ENTER"),
                "enter");
        getActionMap().put("enter",
                enter);

        Action escape = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if(Game.getState()==0&&Game.getTotal()>0){
                    Game.setState(1);

                } else {
                    Game.setState(0);
                }
            }
        };
        getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"),
                "escape");
        getActionMap().put("escape",
                escape);

    }

    public static int getMenu() {
        return menu;
    }

    public static void setMenu(int menu) {
        Game.menu = menu;
    }

    public static int getState() {
        return state;
    }

    public static void setState(int state) {
        Game.state = state;
    }

    public static int getTotal() {
        return total;
    }

    public Font getFont(String name, int size){
        Font font = null;
        try{
        InputStream is = getClass().getResourceAsStream(name);
        font = Font.createFont(Font.TRUETYPE_FONT,is);
        font = font.deriveFont(Font.PLAIN,size);
        } catch (FontFormatException e){
        System.out.println("Font format exception");
        } catch (IOException e){
        System.out.println("IO Exception from font");
        }
        return font;
    }

    public void start(){
        for(int x=0;x<100;x++){
            starX.add((int)(Math.random()*windowsizeX));
            starY.add((int)(Math.random()*windowsizeY));
        }
    }

    public void stars(Graphics g){
        Graphics2D star = (Graphics2D)g;
        star.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        star.setColor(Color.GRAY);
        for(int x=0;x<starY.size();x++){
            g.fillOval(starX.get(x),starY.get(x),1,1);
        }
    }

    public void intro(Graphics g){
        Graphics2D text = (Graphics2D)g;
        text.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Font titlesFont = getFont("DIMIS___.TTF",40);
        Font choicesFont = getFont("DIMITRI_.TTF",24);
        Font copyrightFont = getFont("DIMITRI_.TTF",10);
        FontMetrics fm;
        Rectangle2D rect;
        text.setFont(titlesFont);
        text.setColor(Color.WHITE);
        fm=text.getFontMetrics();
        rect=fm.getStringBounds("METEOROIDS",text);
        text.drawString("METEOROIDS",(int) ((windowsizeX - rect.getWidth())/2),90);
        text.setFont(choicesFont);
        fm=text.getFontMetrics();
        if(menu==0)
            text.setColor(Color.GRAY);
        if (total==0){
            rect=fm.getStringBounds("Start",text);
        text.drawString("Start", (int) ((windowsizeX - rect.getWidth())/2), 180);
        } else {
            rect=fm.getStringBounds("Continue",text);
            text.drawString("Continue",(int) ((windowsizeX - rect.getWidth())/2),180);
        }
        text.setColor(Color.WHITE);
        if(menu==1)
            text.setColor(Color.GRAY);
        rect=fm.getStringBounds("Highscore",text);
        text.drawString("Highscore", (int) ((windowsizeX - rect.getWidth())/2), 230);
        text.setColor(Color.WHITE);
        if(menu==2)
            text.setColor(Color.GRAY);
        rect=fm.getStringBounds("Options",text);
        text.drawString("Options",(int) ((windowsizeX - rect.getWidth())/2),280);
        text.setColor(Color.WHITE);
        text.setFont(copyrightFont);
        fm=text.getFontMetrics();
        rect=fm.getStringBounds(version,text);
        text.drawString(version,(int) ((windowsizeX - rect.getWidth())/2),360);
    }

    public void objectiveGraphic(Graphics g){
        Graphics2D text = (Graphics2D)g;
        Font font = getFont("DIMITRI_.TTF",16);
        text.setFont(font);
        text.setColor(Color.GRAY);
        if(total==0){
        text.drawString("Collect these",XF+20,YF+20);
        }
        text.setColor(Color.blue);
        text.drawRect(XF,YF,10,10);
    }

    public void characterGraphic(Graphics g){

        if(total==1){
        Graphics2D directions = (Graphics2D)g;
        Font font = getFont("DIMITRI_.TTF",16);
        directions.setFont(font);
        directions.setColor(Color.GRAY);
        directions.drawString("Use W,A,S,D",150,100);
        directions.drawString("to move.",150,120);
        }

        Graphics2D character =(Graphics2D)g;
        double zoom = GameEngine.getZoomSize();
        double offsetX = GameEngine.getOffsetX();
        double offsetY = GameEngine.getOffsetY();
        int x = (int)Math.round(user.getX()+offsetX);
        int y = (int)Math.round(user.getY()+offsetY);
        character.setColor(Color.GREEN);
        if(user.isAlive()){
        character.fillOval(x,y,(int)(10*zoom),(int)(10*zoom));
        }
        if(!classic)
        l.draw(g);

    }

    public void gameoverGraphic(Graphics g){
        Graphics2D text = (Graphics2D)g;
        Font font = getFont("DIMITRI_.TTF",40);
        Font font2 = getFont("DIMITRI_.TTF",15);
        Font font3 = getFont("DIMITRI_.TTF",20);
        text.setFont(font);
        text.setColor(Color.BLACK);
        text.fillRect(75,100,250,200);
        text.setColor(Color.WHITE);
        text.drawRect(75,100,250,200);
        text.drawString("Gameover",110,150);
        text.setFont(font2);
        text.drawString("Final Score = "+user.getScore(),150,175);
        text.drawRect(150,245,100,35);
        text.setFont(font3);
        text.drawString("Enter",174,267);
    }

    public void HUDGraphic(Graphics g){
        Graphics2D text = (Graphics2D)g;
        Font font = getFont("DIMITRI_.TTF",16);
        text.setFont(font);
        text.setColor(Color.WHITE);
        text.drawString(("Score =  "+user.getScore()),30,40);
        if(!classic)
        text.drawString(("Ammo =  "+l.getAmmo()),210,40);
        //text.drawString(("User = ( "+user.getX()+" , "+user.getY()+" )"),30,60);
        //text.drawString(("Crashed?= "+end),30,80);
    }

    public void highscoreGraphic(Graphics g){
        Graphics2D text = (Graphics2D)g;
        Font font = getFont("DIMITRI_.TTF",20);
        Font font2 = getFont("DIMITRI_.TTF",14);
        Font font3 = getFont("DIMITRI_.TTF",32);
        Font font4 = getFont("DIMITRI_.TTF",12);
        FontMetrics fm;
        Rectangle2D rect;
        //**** HIGH SCORE MENU ****
        text.setFont(font3);
        text.setColor(Color.WHITE);
        fm=text.getFontMetrics();
        rect=fm.getStringBounds("High scores:",text);
        text.drawString("High scores:",(int) ((windowsizeX - rect.getWidth())/2),110);
        //**** RETURN TO MAIN MENU ****
        text.setFont(font2); // applies the smaller font size font.
        text.setColor(Color.DARK_GRAY); // changes the font to a less visible grey color.
        text.drawRect(25,25,105,22); // draws the rectangle around the text.
        text.drawString("Esc for Menu",30,40); // draws the text.
        //**** HIGH SCORE LIST ****
        text.setFont(font);
        text.setColor(Color.LIGHT_GRAY);
        if(score.getScores().size()>=1){
        text.drawString("1. "+score.getScores().get(0).getName()+" "+score.getScores().get(0).getScore(),100,170);
        } else {
        text.drawString("No HighScores Yet! ",100,170);
        text.drawString("Play the Game! :)",100,200);
        }
        if(score.getScores().size()>=2)
        text.drawString("2. "+score.getScores().get(1).getName()+" "+score.getScores().get(1).getScore(),100,200);
        if(score.getScores().size()>=3)
        text.drawString("3. "+score.getScores().get(2).getName()+" "+score.getScores().get(2).getScore(),100,230);
        if(score.getScores().size()>=4)
        text.drawString("4. "+score.getScores().get(3).getName()+" "+score.getScores().get(3).getScore(),100,260);
        if(score.getScores().size()>=5)
        text.drawString("5. "+score.getScores().get(4).getName()+" "+score.getScores().get(4).getScore(),100,290);

        text.setFont(font4);
        fm=text.getFontMetrics();
        rect=fm.getStringBounds("Press Control+Y to clear High Scores",text);
        text.drawString("Press Control+Y to clear High Scores",(int) ((windowsizeX - rect.getWidth())/2),360);
    }

    public void optionGraphic(Graphics g){
        Graphics2D text = (Graphics2D)g;
        Font font = getFont("DIMITRI_.TTF",20);
        Font font2 = getFont("DIMITRI_.TTF",14);
        Font font3 = getFont("DIMITRI_.TTF",32);
        FontMetrics fm;
        Rectangle2D rect;
        //**** OPTIONS MENU ****
        text.setFont(font3);
        text.setColor(Color.WHITE);
        fm=text.getFontMetrics();
        rect=fm.getStringBounds("Options",text);
        text.drawString("Options",(int) ((windowsizeX - rect.getWidth())/2),110);
        //**** RETURN TO MAIN MENU ****
        text.setFont(font2); // applies the smaller font size font.
        text.setColor(Color.DARK_GRAY); // changes the font to a less visible grey color.
        text.drawRect(25, 25, 105, 22); // draws the rectangle around the text.
        text.drawString("Esc for Menu",30,40); // draws the text.
        //**** COMING SOON DESC. ****
        text.setFont(font);
        text.setColor(Color.LIGHT_GRAY);
        fm=text.getFontMetrics();
        rect=fm.getStringBounds("Switch between versions of the game!",text);
        text.drawString("Switch between versions of the game!",(int) ((windowsizeX - rect.getWidth())/2),170);
        String enable="enable";
        if(!classic)
            enable="disable";

        rect=fm.getStringBounds("Press enter to "+enable+" lasers.",text);
        text.drawString("Press enter to "+enable+" lasers.",(int) ((windowsizeX - rect.getWidth())/2),200);


    }

    public void objective(){
        if (GameEngine.collision(XF, YF, 15, 15, (int)Math.round(user.getX()), (int)Math.round(user.getY()), 10, 10)){
            total++;
            user.setScore(total);
            if(!classic){
            l.setAmmo(10);
            m.setSpeed(Math.log(total)/100.0);
            spawn-=Math.log(total);
            }
            while(GameEngine.collision(XF, YF, 15, 15, (int)Math.round(user.getX()), (int)Math.round(user.getY()), 10, 10)){
            XF=(int)(Math.random()*380);
            YF=(int)(Math.random()*310)+50;
            }

            /**
            for(int all=0;all<m.enemies.size();all++){
             for(int x=0;x<15;x++)
             explosion.add(new Particles(Color.RED,3.0,m.enemies.get(all).getX(),m.enemies.get(all).getY(),5.0));
             m.hit(all);
            }
             **/

            //m.pow();

        }
    }

    public void difficulty() {
        z++;
        if(z>=spawn){
            if(total>0&&!end) {

                m.add(user.getX(),user.getY());

            }
            z=0;

        }
    }

    public void reset(){
        state =2;
        menu=0;
        z = 0;
        XF=150;
        YF=250;
        total=0;
        user.reset();
        m.reset();
        l.reset();
        end=false;
        spawn=100;

    }

    public void update(){
       if(state==1){

       difficulty();
       objective();
       user.move();

       m.update();


           l.update();


       if (m.crash((int)Math.round(user.getX()),(int)Math.round(user.getY()),10,10)>-1&&user.isAlive()){// Checks whether the user hit any of the Meteroids
        user.kill();
       for(int x=0;x<50;x++)
               Explosions.particles.add(new Particles(Color.GREEN,2.0,user.getX(),user.getY(),15.0));
       }

           if(end==true&&Explosions.size()==0&&!gameover){

               enterName.setVisible(true);
               enterName.setFocusable(true);
               enterName.requestFocus();
               gameover=true;

           }

        }



        try {
            Thread.sleep(30);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void paintComponent(Graphics g){
     super.paintComponent(g);
     //setBackground(Color.black);
     stars(g);

     switch (state){
         case 0:
             intro(g);
             break;
         case 1:
             objectiveGraphic(g);
             characterGraphic(g);
             m.graphic(g);
             Explosions.explosionGraphic(g);
             //user.graphic(g);
             HUDGraphic(g);
             if(gameover){
             gameoverGraphic(g);
             }
             break;
         case 2:
             highscoreGraphic(g);
             break;
         case 3:
             optionGraphic(g);
             break;

     }

    }

    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e){

    }
    public void mouseClicked(MouseEvent e){

    }
    public void mouseReleased(MouseEvent e){

    }
    public void mousePressed(MouseEvent e){


         if(!classic&&user.isAlive())
         l.shoot(user.getX(),user.getY(),e.getX(),e.getY());
    }



}
