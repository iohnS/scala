package blockbattle
import java.awt.Color
    
class Game 

object start(args: Arrays: [String]) Unit = 

















/* case class Pos(x: Int, y: Int){
    def moved (delta: (Int, Int)): Pos = ???
}

case class KeyControl (left: String, right: String, up: String, down: String){
    def direction(key: String): (Int, Int) = ???

    def has (key: String): Bolean = ???

}

class Mole(
    val name: String,
    var pos: Pos,
    var dir: (Int, Int),
    val color: java.awt.Color,
    val keyControl: KeyControl
){
    var points = 0

    override def toString = 
    s"Mole[name=$name, pos=$pos, dir=$dir, points=$points]"

    def setDir(key: String): Unit = if (keyControl.has(key)) dir = keyControl

    def reverseDir(): Unit = dir = dir._1 * -1 && dir._ 2 * -1 
        //if (dir == (1, 0)) dir = (-1, 0)
        //else if (dir == (-1, 0)) dir = (1, 0)
        //else if (dir == (0, 1)) dir = (0, -1)
        //else if (dir == (0, -1)) dir = (0, 1)

    def move(): Unit = pos = nextPos

    def nextPos: Pos = (pos.moved(dir))

}

class BlockWindow(
    val windowsize = (Int,Int),
    val title: String = "BLOCK WINDOW",
    val blockSize: Int = 14
){
    import introprog.PixelWindow

    val pixelWindow = new PixelWindow(windowSize._1*blocksize, windowSize._2*blocksize, title)

    def setBlock(pos: Pos, color: java.awt.Color): Unit = ???

    def getBlock(pos: Pos): java.awt.Color = ???

    def write (
        text: String,
        pos: Pos, 
        color: java.awt.Color,
        textsize: Int = blockSize
    ): Unit = pixelWindow.drawText(
        text, pos.x * blockSize, pos.y * blockSize, color, textSize)

   def nextEvent(maxWaitMillis: Int = 10): BlockWindow.Event.EventType = {
       import BlockWindow.Event._
       pixelWindow.awaitEvent(maxWaitMillis)
       pixelWindow.lastEventType match {
           case PixelWindow.Event.KeyPressed    => KeyPressed(pixelWindow.lastKey)
           case PixelWindow.Event.KeyPressed    => WindowClosed
           case _                               => Undefined
       }
   }

}

object BlockWindow {
    def delay (millis: Int): Unit = Thread.sleep(millis)

    object Event {
        trait EventType
        case class KeyPressed (key: String) extends EventType
        case object WindowClosed            extends EventType
        case object Undefined               extends EventType
        
    }
}

