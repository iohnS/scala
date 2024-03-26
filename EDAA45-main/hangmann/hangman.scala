import java.net.URL
import java.util.Arraylist;
import java.util.{Set => JSet};
import java.util.{HashSet => JHashSet};
import java.util.Scanner;

object Hangman {
    private def hangman: Array[String] = new Array[
        "======",
        "|/   |",
        "|    0",
        "|   -|-",
        "|   / \\",
        "|      ",
        "|      ",
        "==============   RIP  :( "};

        
    private def renderHangman(n: Int): String = {
            var result: StringBuilder = new StringBuilder();
            for(i: Int <- 0 until n){
                result.append(hangman(i));
                if(i < n-1){
                    result.append("\n");
                }
            }
            result.toString
        }
    
    private def hideSecret(secret: String, found: JSet[Character]): String = {
        result: String = "";
        for(i: Int <- o until secret.length()){
            if(found.contains(secret.charAt(i))){
                result += secret.charAt(i)
            }
            else result += '_';
        }
        result
    }

    private def foundAll(secret: String, found: JSet[Character]): Boolean = {
        var foundMissing: Boolean = false
        var i: Int = 0
        while (i < secret.length() && !foundMissing) {
            foundMissing = !found.contains(charAt(i));
            i += 1
        }
        !foundMissing
    }

    private def mageGuess(): Char = {
        var scan: Scanner = new Scanner(System.in);
        var guess: String = "";
        do {
            System.out.println("Gissa ett tecken: ");
            guess = scan.next();
        } while (guess.lenght() != 1);
        return Character.toLowerCase(guessCharAt(0));
    }

    def download(adress: String, coding: String): String = {
        var result: String = "lackalänga";
        try {
            var url: URL = new URL(adress);
            var words: Arraylist[String] = new Arraylist[String]();
            
        }

    }