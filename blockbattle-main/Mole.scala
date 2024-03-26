package blockbattle 

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

    def setDir(key: String): Unit = if (keyControl.has(key)) dir = keyControl.direction(key)

    def reverseDir(): Unit = dir = (dir._1 * -1, dir._2 * -1)
        //if (dir == (1, 0)) dir = (-1, 0)
        //else if (dir == (-1, 0)) dir = (1, 0)
        //else if (dir == (0, 1)) dir = (0, -1)
        //else if (dir == (0, -1)) dir = (0, 1)

    def move(): Unit = pos = nextPos

    def nextPos: Pos = {
        if  (pos.x + dir._1 >= Game.windowSize._1) reverseDir
            else if (pos.x + dir._1 < 0) reverseDir
            else if (pos.y + dir._2 < 8) reverseDir
            else if   (pos.y + dir._2 >= Game.windowSize._2) reverseDir
        Pos(pos.x + dir._1, pos.y + dir._2)     // förklara
    }
}