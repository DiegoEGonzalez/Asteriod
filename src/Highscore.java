import java.util.*;
import java.io.*;

public class Highscore{

    private static  ArrayList<Score> scores= new ArrayList <Score>();
    private static final String highscoreFile="scores.dat";

        static ObjectOutputStream out = null;
        static ObjectInputStream in = null;

        public Highscore(){
        }

        public ArrayList<Score> getScores(){
            load();
            sort();
            return scores;
        }

        private void sort(){
            ScoreComparator compare = new ScoreComparator();
            Collections.sort(scores,compare);
        }

        public void addScore(int a, String b){
            if(b.length()==0)
            b="No Name";
            load();
            scores.add(new Score(a,b));
            update();
        }

        public static void clearScore(){
            scores.clear();
            update();
        }

        public  void load(){
            try{
                in = new ObjectInputStream(new FileInputStream(highscoreFile)); // Creates a new filestream from the file
                scores = (ArrayList<Score>) in.readObject();// saves the object inside the .dat file into the ArrayList

            }catch (FileNotFoundException e){
                 e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.flush();
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public static void update(){
            try{
                out = new ObjectOutputStream(new FileOutputStream(highscoreFile));
                out.writeObject(scores);
                out.close();
            } catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }  finally {
                try {
                    if (out != null) {
                        out.flush();
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        }