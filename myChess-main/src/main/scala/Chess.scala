import java.util.Scanner
import scala.language.postfixOps

class Chess(val FEN: String) {

  var board = Array.ofDim[Piece](8,8)
  var whiteTurn = true
  var whitePoints = 0
  var blackPoints = 0

  def this() = {
    this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1") 
  }

  //canKingMove

  //isKingThreatend

  //Kolla pjäser mellan två pjäser (skicka directions??) Den kan heta "in line" eller något som fortsätter tills den träffar slutet eller en pjäs
  //Borde kunna använda det till schack också 

  def range(piece: Piece): Unit = {
    var reachedEnd = false
    piece.directions.foreach{p => 
      var n: Piece = Empty
      while(n == Empty || !reachedEnd){
        if(piece.pos._1 + p._1 == 0 || piece.pos._1 + p._1 == 7 || piece.pos._2 + p._2 == 0 || piece.pos._2 + p._2 == 7) reachedEnd = true
        n = board(piece.pos._1 + p._1)(piece.pos._2 + p._2)
      }
      if(n.isInstanceOf[King] && n.color != piece.color) n.asInstanceOf[King].checked = true
    }
  }

  def load: Unit = whiteTurn = FEN.split(" ")(1) == "w"

  def emptyBetweenPos(start: (Int, Int), dest: (Int, Int)): Boolean = {
    true
  }

  def castle(r: Int, c: Int, toR: Int, toC: Int): Unit = {
    if(!board(r)(c).moved && !board(toR)(toC).moved){
      if(difference((r,c), (toR, toC))._2 > 0){
        moveToDestination(r, c, r, c+2)
        moveToDestination(toR, toC, r, c+1)
      }else if(difference((r,c), (toR, toC))._2 < 0){
        moveToDestination(r, c, r, c-2)
        moveToDestination(toR, toC, r, c-1)
      } else {
        throw new InvalidMoveException("Can't castle")
      }
    }
  }

  def getCommand: Unit = {
    if(whiteTurn) println(s"\n $whitePoints White: ") else println(s"\n $blackPoints Black: ")

    def convertStr(str: String): Int = str match
      case "a" => 0
      case "b" => 1
      case "c" => 2
      case "d" => 3
      case "e" => 4
      case "f" => 5
      case "g" => 6
      case "h" => 7
      case _ => throw new InvalidPositionException("Invalid Position")

    def convertInt(str: String): Int = str match
      case "1" => 7
      case "2" => 6
      case "3" => 5
      case "4" => 4
      case "5" => 3
      case "6" => 2
      case "7" => 1
      case "8" => 0
      case _ => throw new InvalidPositionException("Invalid Position")

    var command = scala.io.StdIn.readLine
    if(command.contains("quit")) System.exit(0)
    if(command.length != 5) println("FEL STORLEK")
    var note = command.split("")

    try{
      val piece = note(0)
      val c = convertStr(note(1))
      val r = convertInt(note(2))
      val toC = convertStr(note(3))
      val toR = convertInt(note(4))
      move(piece, c, r, toC, toR)
    }catch{
      case e: InvalidPositionException => println(e.msg)
      case e: InvalidMoveException => println(e.msg)
      case e: NumberFormatException => println("Wrong position, can't make String to Int")
    }
  }

  def followRule(pos: (Int, Int), dest: (Int, Int)): Boolean = {
    val destination = board(dest._1)(dest._2)
    val start = board(pos._1)(pos._2)
    val diff: (Int, Int) = difference(pos, dest)
    if(start.isInstanceOf[Pawn] && destination != Empty){
      var pawn = start.asInstanceOf[Pawn]
      pawn.specialMove.contains(diff)
    }else{
      if(start.isInstanceOf[King] || start.isInstanceOf[Pawn] || start.isInstanceOf[Knight]) start.directions.contains(diff) else{
       var found = false
       while(!found){
          start.directions.foreach{dir =>
            if(dir._1 != 0 && dir._2 != 0){
              if(diff._1 % dir._1 == 0 && diff._2 % dir._2 == 0) found = true else found = false
            }else if(dir._1 == 0){
              if(diff._2 % dir._2 == 0) found = true else found = false
            }else {
              if(diff._1 % dir._1 == 0) found = true else found = false
            }
          }
        }
        found
      }       
    }
  }

  def difference(pos: (Int, Int), dest: (Int, Int)): (Int, Int) = (dest._1 - pos._1, dest._2 - pos._2)
  

  def moveToDestination(r: Int, c: Int, toR: Int, toC: Int): Unit = {
    if((board(r)(c).isInstanceOf[Pawn] || board(r)(c).isInstanceOf[Rook] || board(r)(c).isInstanceOf[King]) && !board(r)(c).moved) board(r)(c).moved = true
    if(board(toR)(toC) != Empty) if (whiteTurn) whitePoints += board(toR)(toC).value else blackPoints += board(toR)(toC).value
    board(toR)(toC) = board(r)(c) 
    board(toR)(toC).pos = (toR, toC)
    board(r)(c) = Empty
    whiteTurn = !whiteTurn
}
  def move(piece: String, c: Int, r: Int, toC: Int, toR: Int): Unit = {
    val destination = board(toR)(toC)
    val start = board(r)(c)
    if(start.isInstanceOf[King] && destination.isInstanceOf[Rook]){
      castle(r, c, toR, toC)
      printBoard
      return
    }

    if (whiteTurn && start.color != 1 || !whiteTurn && start.color != 0) throw new InvalidPositionException("That isnt your piece") 
    if (piece != board(r)(c).toString.toUpperCase) throw new InvalidPositionException("Wrong piece")
    if(followRule((r,c), (toR, toC))){
      if(destination.toString == Empty.toString) moveToDestination else if(destination.color != start.color) {
        moveToDestination 
      }else{
        throw new InvalidMoveException("Can't move here")
      }
    }else throw new InvalidMoveException("Invalid move")
    printBoard
  }

  def createBoard(): Unit = {
    val positions = FEN.split(" ")(0)
    var scanPos = new Scanner(positions).useDelimiter("")

    var r = 0
    while(r < board.length){
      var c = 0
      while(c < board(r).length){
        if(scanPos.hasNext()){
          val next = scanPos.next()
          if(Character.isDigit(next.charAt(0))){
            val newCol = c + Integer.parseInt(next)
            for(k <- c until newCol){
              board(r)(k) = Empty
            }
            if(newCol == board.length){
              r += 1
            }else{
              c = newCol
            }
          }else if(next == "/"){
            c = -1
        }else{
          val pos = (r,c)
          board(r)(c) = next match{
            case "r" => new Rook(0, pos)
            case "R" => new Rook(1, pos) 
            case "n" => new Knight(0, pos)
            case "N" => new Knight(1, pos)
            case "b" => new Bishop(0, pos)
            case "B" => new Bishop(1, pos)
            case "q" => new Queen(0, pos)
            case "Q" => new Queen(1, pos)
            case "p" => new Pawn(0, pos)
            case "P" => new Pawn(1, pos)
            case "k" => new King(0, pos)
            case "K" => new King(1, pos)
          }
        }
          c += 1
      }
    }
      r += 1
  }
}
  def printBoard: Unit = {
    println("  a b c d e f g h \n")
    for i <- 0 until board.length 
    do 
      print(s"${8-i}")
      for j <- 0 until board(i).length do printf(" " + board(i)(j).toString()) 
      print(s" ${8-i}")
      println("\n")
    print("  a b c d e f g h")
  }

  class InvalidPositionException(val msg: String) extends Exception(msg)
  class InvalidMoveException(val msg: String) extends Exception(msg)
}