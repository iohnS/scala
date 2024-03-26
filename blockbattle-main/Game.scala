package blockbattle 
import java.awt.{Color=>JColor}



object Game {
    val windowSize = (30, 50)
    val windowTitle = "EPIC BLOCK BATTLE"
    val blockSize = 14
    val skyRange = 0 to 7
    val grassRange = 8 to 8
    
    object Color {???}
    /** used with the different ranges and eraseBlocks */
    def backgroundColorAtDepth(y:Int):java.awt.COlor = black
    val black = new JColor (0,0,0)
    val moleL = new JColor (51,51,0)
    val moleR = new JColor (51,0,0)
    val soil = new JColor (153,102,51)
    val tunnel = new JColor (204,153,102)
    val grass = new JColor (25,130,35)
    val skyn = new JColor (140,200,220)
}

class Game(
    val leftPlayerName: String = "Cool mullvad"
    val rightPlayerName: String = "Keff mullvad"
)  {
    import Game._ //ger direkt tillgng till namn på medlemmar i kompanjon

    val window = new BlockWindow(windowSize, windowTitle, blockSize)
    val leftMole: Mole = new Mole(leftPlayerName, (20, 25), (0, -1), moleL, new KeyControl("Left", "Right", "Up", "Down"))
    val rightMole: Mole = new Mole(rightPlayerName, (10, 25), (0, 1), moleR, new KeyControl("a", "d", "w", "s"))
   
     def drawWorld()(leftTop: Pos)(size: (Int, Int))(color: JColor = JColor.gray){
         for (y <- leftTop._2 to leftTop._2 + size._2) {
          for (x <- leftTop._1 to leftTop._1 + size._1) {
              window.setBlock(Pos(x,y))(color)
             }
         }
      }

    def eraseBlocks(x1: Int, y1: Int, x2: Int, y2: Int): Unit = ???

    var quit = false 
    val delayMillis = 80

    def gameLoop(): Unit = {
        while(!quit){
            val t0 = System.currentTimeMillis
            handleEvents()
            update(leftMole)
            update(rightMole)

            val elapsedMillis = (System.currentTimeMillis - t0).toInt
            Thread.sleep((delayMillis - elapsedMillis) max 0)   //Thread.sleep gör att den väntar så många millisekunder, vad gör max 0
        }
    }

    def start(): Unit = {
        println("Start digging")
        println(s"$leftPlayerName ${leftMole.keyControl}")
        println(s"$rightPlayerName ${rightMole.keyControl}")
        drawWorld()
        gameLoop()
    }

    def handleEvents(): Unit = {
        var e = window.nextEvent()
        while (e != BlockWindow.Event.Undefined) {
            e match {
                case BlockWindow.Event.KeyPressed(key) => ??? //ändra riktning på resp. mullvad 

                case BlockWindow.Event.WindowClosed => quit = true 
            }
        }
    }

}