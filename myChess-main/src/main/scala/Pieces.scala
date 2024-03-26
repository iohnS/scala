sealed trait Piece {
  val color: Int
  val value: Int
  var pos: (Int, Int)
  override def toString: String
  def directions: List[(Int, Int)]
  var list = List[(Int, Int)]()
  var moved: Boolean
}

class Rook(val color: Int, var pos: (Int, Int)) extends Piece{
  var moved = false
  val value = 5

  def directions: List[(Int, Int)] = (1,0)::(-1, 0)::(0, 1)::(0,-1)::list

  override def toString: String = if (color == 1) "R" else "r"
}

class Pawn(val color: Int, var pos: (Int, Int)) extends Piece{
  var moved = false
  val value = 1

  def directions: List[(Int, Int)] = {
    if(color == 1){
      if(!moved) (-2,0)::(-1,0)::list else (-1,0)::list
    }else {
     if(!moved) (2,0)::(1,0)::list else (1,0)::list
   }
  }

  def specialMove: List[(Int, Int)] = {
  if(color == 1){
      (-1,-1)::(-1,1)::list
    }else {
      (1,-1)::(1,1)::list
    }
  }
  override def toString: String = if (color == 1) "P" else "p"  
}

class Bishop(val color: Int, var pos: (Int, Int)) extends Piece{
  var moved = true
  val value = 3

  def directions: List[(Int, Int)] = (-1,-1)::(-1,1)::(1, -1)::(1,1)::list

  override def toString: String = if (color == 1) "B" else "b" 
}

class Knight(val color: Int, var pos: (Int, Int)) extends Piece{
  var moved = true
  val value = 3

  def directions: List[(Int, Int)] = (-2,-1)::(-2,1)::(2,-1)::(2,1)::(-1,2)::(1,2)::(-1,-2)::(1,-2)::list

  override def toString: String = if (color == 1) "N" else "n"
}

class Queen(val color: Int, var pos: (Int, Int)) extends Piece{
  var moved = true
  val value = 9

  def directions: List[(Int, Int)] = (-1,-1)::(-1,1)::(1,-1)::(1,1)::(1,0)::(-1,0)::(0, 1)::(0,-1)::list
  
  override def toString: String = if (color == 1) "Q" else "q"
}

class King(val color: Int, var pos: (Int, Int)) extends Piece{
  var moved = false
  val value = 100
  var checked = false

  def directions: List[(Int, Int)] = (-1,-1)::(-1,1)::(1,-1)::(1,1)::(1,0)::(-1,0)::(0, 1)::(0,-1)::list

  override def toString: String = if (color == 1) "K" else "k"
}

object Empty extends Piece{
  var moved = true
  var pos = (-1,-1)
  val color = 2
  val value = 0

  def directions: List[(Int, Int)] = list

  override def toString: String = "="
}