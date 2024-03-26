package life

case class Matrix[T](data: Vector[Vector[T]]) {
  require(data.forall(t => t.length == data(0).length))

  val dim: (Int, Int) = (data.length, data(0).length)

  def apply(row: Int, col: Int): T = data(row)(col)

  def updated(row: Int, col: Int)(value: T): Matrix[T] = {
    Matrix(data.updated(row, data(row).updated(col, value)))
  }

  def foreach(f: T => Unit): Unit = data.foreach(_.foreach(f))

  def foreachIndex(f: (Int, Int) => Unit): Unit = {
    for (i <- data.indices) {
      for (j <- data(i).indices) {
        f(i, j)
      }}}

  override def toString = {
    s"""Matrix of dim $dim:\n${data.map(_.mkString(" ")).mkString("\n")}"""
  }

  def map[U](f: T => U): Matrix[U] = Matrix(data.map(_.map(f)))

  def mapIndex[U](f: (Int, Int) => U): Matrix[U] = {
    var result = Matrix.fill(dim)(f(0,0))
    for(r <- data.indices; c <- data(r).indices) {
      result = result.updated(r, c)(f(r,c))
    }
    result
  }

  def test: String = s"""Matrix of dim $dim:\n${data.map(_.mkString(" ")).mkString("\n")}"""
}
object Matrix {
  def fill[T](dim: (Int, Int))(value: T): Matrix[T] = {
    Matrix(Vector.fill(dim._1, dim._2)(value))
  }
}