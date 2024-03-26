package life

import life.Life.defaultRule

case class Life(cells: Matrix[Boolean]) {

  /** Ger true om cellen på plats (row, col) är vid liv annars false.
    * Ger false om indexeringen är utanför universums gränser.
    */
  def apply(row: Int, col: Int): Boolean = {
    if (row >= 0 && row < cells.dim._1 && col >= 0 && col < cells.dim._2) {
      cells(row,col)
    } else false
  }

  /** Sätter status på cellen på plats (row, col) till value. */
  def updated(row: Int, col: Int, value: Boolean): Life = Life(cells.updated(row, col)(value))

  /** Växlar status på cellen på plats (row, col). */
  def toggled(row: Int, col: Int): Life = {
    Life(if (cells(row, col))  cells.updated(row, col)(false) else cells.updated(row, col)(true))
  }

  /** Räknar antalet levande grannar till cellen i (row, col). */

  def nbrOfNeighbours(row: Int, col: Int): Int = {
    var n = 0
    val dirs = Vector(-1, 0, 1)
    (for (a <- dirs; b <- dirs) yield (a, b)).foreach(i => if (apply(row + i._1, col + i._2)) n += 1)
    if (apply(row, col)) n - 1 else n

  }

  /** Skapar en ny Life-instans med nästa generation av universum.
    * Detta sker genom att applicera funktionen \code{rule} på cellerna.
    */
  def evolved(rule: (Int, Int, Life) => Boolean = Life.defaultRule):Life = {
    var nextGeneration = Life.empty(cells.dim)
    cells.foreachIndex { (r,c) => nextGeneration = nextGeneration.updated(r,c,rule(r,c,this))


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
  def empty(dim: (Int, Int)): Life = Life(Matrix.fill(dim._1,dim._2)(false))

  /** Skapar ett unviversum med slumpmässigt liv. */
  def random(dim: (Int, Int)): Life = {
    def rand: Boolean = math.random() >= 0.5
    var ny = empty(dim)
    Life(ny.cells.mapIndex{ (r,c) => rand})

  }

  /** Implementerar reglerna enligt Conways Game of Life. */
  def defaultRule(row: Int, col: Int, current: Life): Boolean = {
    if (current(row,col)) {
      if (current.nbrOfNeighbours(row,col) < 2 || current.nbrOfNeighbours(row,col) > 3) false else true
    } else if (current.nbrOfNeighbours(row,col) == 3) true else false
}