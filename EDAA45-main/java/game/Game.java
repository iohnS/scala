package game;

import java.net.URL;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;
import java.lang.*;



public class Game {

    private String playerName;
    private int score;
    private String secret = word();


    public Game(String playerName){
        this.playerName = playerName;
    }

    public String getPlayerName(){
        return playerName;
    }

    public int calculateScore(double t1, double t2){
        double playTime = t2 - t1;
        return (int) Math.round(100-(playTime/1000));
    }

    public int getScore(){
        return score;
    }


    private String[] hangman = new String[] {
        "======   ",
        "|/   |   ",
        "|    0   ",
        "|   -|-  ",
        "|   / \\ ",
        "|        ",
        "|        ",
        "===============   RIP  :("
    };

    private String renderHangman(int n){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++){
            result.append(hangman[i]);
            if(i < n-1) {
                result.append("\n");
            }
        }
        return result.toString();
    }

    private String hideSecret(String secret, Set<Character> found){
        String result = "";
        for (int i = 0; i < secret.length(); i++) {
            if(found.contains(secret.charAt(i))) {
                result += secret.charAt(i);
            }else {
                result += '_';
            }
        }
        return result;
    }

    private boolean foundAll(String secret, Set<Character> found){
        boolean foundMissing = false;
        int i = 0;
        while(i < secret.length() && !foundMissing) {
            foundMissing = !found.contains(secret.charAt(i));
            i++;
        }
        return !foundMissing;
    }

    private char makeGuess(){
        Scanner scan = new Scanner(System.in);
        String guess = "";
        do {
            System.out.println("Gissa ett tecken: ");
            guess = scan.next();
        } while(guess.length() != 1);
        return Character.toLowerCase(guess.charAt(0));
    }

    public String download(String address, String coding){
        String result = "lackalänga";
        try{
            URL url = new URL(address);
            ArrayList<String> words = new ArrayList<String>();
            Scanner scan = new Scanner(url.openStream(), coding);
            while (scan.hasNext()) {
                words.add(scan.next());
            }
            int rnd = (int) (Math.random() * words.size());
            result = words.get(rnd);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return result;
    }

    private String word(){
            String runeberg = 
            "http://runeberg.org/words/ord.ortsnamn.posten";
            return(download(runeberg, "ISO-8859-1"));
            }

    public void run(){
        Set<Character> found = new HashSet<Character>();
        int bad = 0;
        double time0 = java.lang.System.currentTimeMillis();
        boolean won = false;
        while (bad < hangman.length && !won){
            System.out.print("\nFelgissningar: " + bad + "\t");
            System.out.println(hideSecret(secret, found));
            char guess = makeGuess();
            if (secret.indexOf(guess) >= 0) {
                found.add(guess);
            }else {
                bad++; 
                System.out.println(renderHangman(bad));
            }
            won = foundAll(secret, found);
        }
        if (won){
            System.out.println("BRA! :)");
        }else {
            System.out.println("Hängd! :(");
        }
        double endTime = java.lang.System.currentTimeMillis();
        if (won){
            score = calculateScore(time0, endTime);
        }else {
            score = 0;
        }
        System.out.println("Rätt svar: " + secret);
        System.out.println("Antal felgissningar: " + bad);
    }
}
