import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Filmmakernow
 * Date: 3/6/14
 * Time: 12:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScoreComparator implements Comparator<Score> {
             public int compare(Score a, Score b){
                   int c = a.getScore();
                   int d = b.getScore();

                 if(c>d){
                     return -1;
                 } else if (c<d){
                     return 1;
                 } else {
                     return 0;
                 }
             }
}
