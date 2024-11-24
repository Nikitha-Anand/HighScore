import java.io.*;
import java.util.Scanner;

public class HighScore {
    private String playerName;
    private Integer score;
    public HighScore(String playerName, Integer score){
        this.playerName = playerName;
        this.score = score;
    }
    public static HighScore[] scoresArray = new HighScore[11];

    public static void ReadHighScores(){
        File file = new File("HighScore.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            scoresArray[10] = new HighScore("",0);
            for (int i = 0; i < 10; i++) {
                //Initialization of array elements
                scoresArray[i] = new HighScore("",0);
                scoresArray[i].playerName = reader.readLine();
                scoresArray[i].score = Integer.valueOf(reader.readLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    public static void OutputHighScores(){
        for (int i = 0; i < 11; i++) {
            System.out.println(scoresArray[i].playerName + " " + scoresArray[i].score);
        }
    }


    public static void NewHighScoreList(String nplayerName, int nScore){
        if(scoresArray[9].score < nScore){
            for (int i = 0; i < 10; i++) {
                if(scoresArray[i].score < nScore){
                    HighScore temp = scoresArray[i];
                    scoresArray[i] = new HighScore(nplayerName,nScore);
                    for (int j = 10; j >= i + 1; j--) {
                        scoresArray[j] = scoresArray[j - 1];
                    }
                    scoresArray[i+1] = temp;
                    break;
                }
            }
        }else{
            scoresArray[10] = new HighScore(nplayerName,nScore);
        }
    }

    public static void WriteTopTen() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("NewHighScore.txt"))) {
            scoresArray[10] = new HighScore("", 0);
            for (int i = 0; i < 10; i++) {
                writer.write(scoresArray[i].playerName);
                writer.newLine();
                writer.write(String.valueOf(scoresArray[i].score));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    public static void main(String[] args){
        ReadHighScores();
        OutputHighScores();

        Scanner keyboard = new Scanner(System.in);

        String nplayerName;
        int nScore;
        do {
            System.out.println("Enter a 3-character player name: ");
            nplayerName = keyboard.next();
            if (nplayerName.length() != 3) {
                System.out.println("Not a valid player Name");
            }
        }while (nplayerName.length() != 3);


        do {
            System.out.println("Enter a 3-character player name: ");
            System.out.println("Enter player Score: ");
            nScore = keyboard.nextInt();
            if(nScore < 1 || nScore > 100000){
                System.out.println("Not a valid Score");
            }
        }while (nScore < 1 || nScore > 100000);


        NewHighScoreList(nplayerName, nScore);
        OutputHighScores();
        WriteTopTen();
    }
}
