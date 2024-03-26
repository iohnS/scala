package life

import introprog.PixelWindow
import introprog.PixelWindow.Event
import java.awt.{Color}

object LifeWindow {
  val EventMaxWait = 1 // milliseconds
  var NextGenerationDelay = 50 // milliseconds
  val pink = new Color(242,128,161)
  val black = new Color(0, 0, 0)
  val white = new Color(255, 255, 255)
  val underPopulated = new Color(0, 255, 255)
  val overPopulated = new Color(255, 0, 0)
  val willBeBorn = new Color(40, 0, 0)
  val blockSize = 20
  val lineSize = 2
}




class LifeWindow(rows: Int, cols: Int, isMultiColor: Boolean){
  import LifeWindow._ // importera namn från kompanjon

  var life = Life.random(rows, cols)
  val windowSize = (cols*(blockSize+lineSize), (rows*(blockSize+lineSize)))
  val window: PixelWindow = new PixelWindow(windowSize._1, windowSize._2, "Game of Life", black)
  var quit = false
  var play = false


  def pickColor(row: Int, col: Int): java.awt.Color = {
    if(isMultiColor){
      if (life.cells(row, col) && life.nbrOfNeighbours(row, col) < 2){underPopulated}
      else if (life.cells(row, col) && life.nbrOfNeighbours(row, col) > 3){overPopulated}
      else if (!(life.cells(row, col)) && life.nbrOfNeighbours(row, col) == 3){willBeBorn}
      else if(life.cells(row,col)) pink
      else black
    }
    else{
      if (life.cells(row, col)) pink else black
    }
  }

  def drawGrid(): Unit = {
    for (y <- 0 until rows){
      window.line(0, y*(blockSize+lineSize), windowSize._1, y*(blockSize+lineSize), white, lineSize)
    }
    for (x <- 0 until cols){
      window.line(x*(blockSize+lineSize), 0, x*(blockSize+lineSize), windowSize._2, white, lineSize)
  }
}


  //Den ska vara rosa om det finns en life där, annars ska den vara svart. Rita ut de svarta och råsa??
  def drawCell(row: Int, col: Int): Unit = {
    window.fill(col*(blockSize+lineSize), row*(blockSize+lineSize), blockSize, blockSize, pickColor(row, col))
  }


  def update(newLife: Life): Unit = { 
    val oldLife = life
    life = newLife
    life.cells.foreachIndex{ (r, c) => drawCell(r, c)}
      
  }

  def handleKey(key: String): Unit = key match {
    case "Enter"            => update(life.evolved())//den uppdaterar spelet, alltså att den evolvar en generation
    case " "                => play = !play//den fortsätter köra spelet, evolvar flera gånger
    case "r"                => update(Life.random(rows, cols)) //skapar random liv på random ställen i rutmönstret
    case "Backspace"        => update(Life.empty(rows, cols)) //tar bort allt liv, clear
  }

  def handleClick(pos: (Int, Int)): Unit = {
    var row = pos._2/(blockSize+lineSize)
    var col = pos._1/(blockSize+lineSize)
    update(life.toggled(row, col))
    
  }


  def loopUntilQuit(): Unit = while (!quit) {
    val t0 = System.currentTimeMillis
    if (play) update(life.evolved())
    window.awaitEvent(EventMaxWait)
    while (window.lastEventType != PixelWindow.Event.Undefined) {
      window.lastEventType match {
        case Event.KeyPressed  =>  handleKey(window.lastKey)
        case Event.MousePressed => handleClick(window.lastMousePos)
        case Event.WindowClosed => quit = true
        case _ =>
      }
      window.awaitEvent(EventMaxWait)
    }
    val elapsed = System.currentTimeMillis - t0
    Thread.sleep((NextGenerationDelay - elapsed) max 0)
  }

def start(): Unit = {drawGrid(); loopUntilQuit();}
}