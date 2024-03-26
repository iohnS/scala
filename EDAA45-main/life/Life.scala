package life

case class Life(cells: Matrix[Boolean]){

  /** Ger true om cellen på plats (row, col) är vid liv annars false. Alltså: om färgen är rosa på platsen så true.
    * Ger false om indexeringen är utanför universums gränser.
    */
  def apply(row: Int, col: Int): Boolean = {
    cells(row, col)
  }

  /** Sätter status på cellen på plats (row, col) till value. */
  def updated(row: Int, col: Int, value: Boolean): Life = {
        Life(cells.updated(row, col)(value))
  }

  /** Växlar status på cellen på plats (row, col). */
  def toggled(row: Int, col: Int): Life = {
    Life(cells.updated(row, col)(!cells(row, col)))    // om cells(row)(col) redan har värdet value så är det true (vid liv), annars false
  }
            
  /** Räknar antalet levande grannar till cellen i (row, col). Om de bredvid row col är true så är det en neighbour*/
  def nbrOfNeighbours(row: Int, col: Int): Int = {
    var neighbours = 0
      for (a <- row -(if(row>0) 1 else 0) to row +(if(row == cells.dim._1-1)0 else 1)){
        for (b <- col -(if(col>0) 1 else 0) to col +(if(col == cells.dim._2-1)0 else 1)){
          if (cells(a, b) && (row, col)!=(a,b))neighbours += 1
        }
      }
    neighbours
  }


  /** Skapar en ny Life-instans med nästa generation av universum.
    * Detta sker genom att applicera funktionen \code{rule} på cellerna.
    */
  def evolved(rule: (Int, Int, Life) => Boolean = Life.defaultRule):Life = {
    var nextGeneration = Life.empty(cells.dim)
    cells.foreachIndex { (r,c) => nextGeneration = nextGeneration.updated(r, c, rule(r, c, this))
    }
    nextGeneration
  }

  /** Radseparerad text där 0 är levande cell och - är död cell. */
  override def toString = {
    cells.data.map(_.map(if(_)"0" else "-").mkString).mkString("\n")
  }

} 

object Life {
  /** Skapar ett universum med döda celler. */
  def empty(dim: (Int, Int)): Life = Life(Matrix.fill(dim)(false))

  /** Skapar ett unviversum med slumpmässigt liv. */
  def random(dim: (Int, Int)): Life = {
    val randomLife = Matrix.fill(dim)(false)
    Life(Matrix(Vector.tabulate(dim._1, dim._2)((x,y)=>scala.util.Random.nextBoolean())))
     
  }

  /** Implementerar reglerna enligt Conways Game of Life. */
  def defaultRule(row: Int, col: Int, current: Life): Boolean = {
    (((current(row, col) && (current.nbrOfNeighbours(row, col) == 2) || current.nbrOfNeighbours(row, col) == 3)) || 
    (!current(row, col) && (current.nbrOfNeighbours(row, col) == 3)))
  }
}