package blockmole
import java.awt.{Color=>JColor}


object Color {
val black = new JColor (0,0,0)
val mole = new JColor (51,51,0)
val soil = new JColor (153,102,51)
val tunnel = new JColor (204,153,102)
val grass = new JColor (25,130,35)
val skyn = new JColor (140,200,220)
}



object BlockWindow {

import introprog.PixelWindow
val windowSize = (30,50)
val blocksize = 10
type Pos = (Int, Int)
val window = new PixelWindow(windowSize._1*blocksize, windowSize._2*blocksize, "Digging Blockmole")
val maxWaitMillis = 10
val delayMillis = 200


    def block(pos: Pos)(color:JColor = JColor.gray): Unit = {
        val x = pos._1*blocksize
        val y = pos._2*blocksize
        window.fill(x, y, blocksize, blocksize, color)
    }

    def rectangle(leftTop: Pos)(size: (Int, Int))(color: JColor = JColor.gray){
         for (y <- leftTop._2 to leftTop._2 + size._2) {
          for (x <- leftTop._1 to leftTop._1 + size._1) {
              block(x,y)(color)
             }
         }
      }

    def waitForKey(): String = {
        window.awaitEvent(maxWaitMillis)
        while (window.lastEventType != PixelWindow.Event.KeyPressed){
            window.awaitEvent(maxWaitMillis)
        }
        println(s"KeyPressed: " + window.lastKey)
        window.lastKey
    }

}



object Mole {

    def dig (): Unit = println("Här ska det grävas!")
        var x = BlockWindow.windowSize._1 / 2 
        var y = BlockWindow.windowSize._2 / 2
        var quit = false
        while (!quit){
            if (x >= BlockWindow.windowSize._1) x -= 1
            else if (x <= 0) x += 1
            else if (y <= 2) y += 1
            else if (y >= BlockWindow.windowSize._2) y -= 1
            BlockWindow.block(x,y)(Color.mole)
            val key = BlockWindow.waitForKey
            BlockWindow.block(x,y)(Color.tunnel)
            if      (key == "w") y -=1
            else if (key == "a") x -= 1
            else if (key == "s") y += 1
            else if (key == "d") x += 1
            else if (key == "q") quit = true
        }

}



object Main{
    def drawWorld(): Unit = {
        BlockWindow.rectangle(0, 2)(size = (30, 4))(Color.grass)
        BlockWindow.rectangle(0, 0)(size = (30, 2))(Color.skyn)
       BlockWindow.rectangle(0, 4)(size = (30, 44))(Color.soil)
        }



    def main(args: Array[String]): Unit = {
        drawWorld()
        Mole.dig()

    }
}    
