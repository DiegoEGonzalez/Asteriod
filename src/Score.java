import java.io.Serializable;

public class Score implements Serializable{
    private int score;
    private String name;

    public Score(int a, String b){
        score=a;
        name=b;
    }

    public int getScore(){
        return score;
    }

    public String getName(){
        return name;
    }


}
