
import org.junit.Test
import org.junit.Assert._

var chess = new Chess()
class Test1 {
  @Test def t1(): Unit = {
    assertEquals("r", chess.board(0)(0))
  }
}