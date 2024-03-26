package blockbattle 
import java.awt.{Color=>JColor}



object Game {
    val windowSize = (30, 50)
    val windowTitle = "EPIC BLOCK BATTLE"
    val blockSize = 14
    val skyRange = 0 to 7
    val grassRange = 8 to 9
    val winPoint = 300
    
    object Color { 
    val black = new JColor (0,0,0)
    val moleL = new JColor (51,51,0)
    val moleR = new JColor (51,0,0)
    val soil = new JColor (153,102,51)
    val tunnel = new JColor (204,153,102)
    val grass = new JColor (25,130,35)
    val skyn = new JColor (140,200,220)
    val red = new JColor (255, 0, 0)
    }

     def backgroundColorAtDepth(y:Int):java.awt.Color = {
        if (skyRange contains y) Color.skyn
        else if (grassRange contains y) Color.grass
        else Color.soil
     }
   
}

class Game(
    val leftPlayerName: String = "Mrs. Mullvad",
    val rightPlayerName: String = "Mr. mullvad"
    )  {
    import Game._ 

    val window = new BlockWindow(windowSize, windowTitle, blockSize)
    val leftMole: Mole = new Mole(leftPlayerName, Pos(10, 25), (0, -1), Color.moleL, new KeyControl("a", "d", "w", "s"))
    val rightMole: Mole = new Mole(rightPlayerName, Pos(20, 25), (0, 1), Color.moleR, new KeyControl("Left", "Right", "Up", "Down"))
     

    def drawWorld(): Unit = {
        for (y <- 0 to windowSize._2) {
            for (x <- 0 to windowSize._1) {
                window.setBlock(Pos(x,y), backgroundColorAtDepth(y))
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
            showPoints(leftMole)
            showPoints(rightMole)
            gameOver()


            var elapsedMillis = (System.currentTimeMillis - t0).toInt
            Thread.sleep((delayMillis - elapsedMillis) max 0)   //Thread.sleep gör att den väntar så många millisekunder, vad gör max
            

        }
    }
    

    def showPoints(mole: Mole): Int = {
        if (window.getBlock(mole.nextPos) == Color.soil) mole.points += 1
        if (window.getBlock(mole.nextPos) == Color.grass) mole.points -= 1

         for (y <- 0 to 7) {
            for (x <- 0 to windowSize._1) {
                window.setBlock(Pos(x,y), backgroundColorAtDepth(y))

                window.write(leftPlayerName, Pos(2, 2), Color.black, 16)
                window.write(rightPlayerName, Pos(20, 2), Color.black, 16)

                window.write((leftMole.points).toString, Pos(2, 5), Color.black, 16)
                window.write((rightMole.points).toString, Pos(27, 5), Color.black, 16)
            }   
         } 
     mole.points
 }


    def gameOver(): Unit = {
        if (showPoints(leftMole) >= winPoint || showPoints(rightMole) >= winPoint){
            window.write("GAME OVER", Pos(10, 25), Color.red, 32)
            quit = true

        if (leftMole.points == winPoint){window.write(s"$leftPlayerName has won", Pos(6, 28), Color.black, 24)}
        else window.write(s"$rightPlayerName has won", Pos(6, 28), Color.black, 24)
        }
        
    }

    def start(): Unit = {
        println(s"$leftPlayerName ${leftMole.keyControl}")
        println(s"$rightPlayerName ${rightMole.keyControl}")
        drawWorld()
        gameLoop()
    }

    def handleEvents(): Unit = {
        var e = window.nextEvent()
        while (e != BlockWindow.Event.Undefined) {
            e match {
                case BlockWindow.Event.KeyPressed(key) => leftMole.setDir(key); rightMole.setDir(key) //ändra riktning på resp. mullvad 

                case BlockWindow.Event.WindowClosed => quit = true 
            }
            e=window.nextEvent()
        }
    }

    def update(mole: Mole): Unit = {
        window.setBlock(mole.nextPos, mole.color)
        window.setBlock(mole.pos, Color.tunnel)
        mole.move()
    }

}