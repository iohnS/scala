package game

import java.util.Scanner
import scala.collection.JavaConverters
import scala.jdk.CollectionConverters._

object UserInterface {

    val scan = new Scanner(System.in)

  /** Prints the available choices, then reads an integer from the user */
  def readChoice(choices: Array[String]): Int = {
    for (x <- choices)println(x)
    var userInt: Int = scan.nextInt()
    userInt
  }

  /** Reads a string from the user */
  def readString(): String = {
    var userString: String = scan.next()
       if(userString == "")readString()
         else userString
  }

  /** Prints scores in descending order */
  def showHighScores(scores: java.util.ArrayList[Game]): Unit = {
    scores.asScala.sortBy(_.getScore()).reverse.foreach(x=>
      println(s"Name: ${x.getPlayerName()} Score: ${x.getScore()}"))
  }

  /** Prints scores achieved by a specific player in descending order */
  def showHighScores(scores: java.util.ArrayList[Game], player: String): Unit = {
    val sortedScores = scores.asScala.filter(_.getPlayerName == player).sortBy(_.getScore()).reverse
    for(t <- sortedScores){
      println(s"Score for ${t.getPlayerName()} is: ${t.getScore()}")
    }
  }
}

