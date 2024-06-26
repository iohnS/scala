object Match {
    def main (args: Array[String]): Unit = {
        val favorite = if (args.length > 0) args(0) else "selleri"
        println ("Din favoritgörnska: " + favorite)
        val firstChar = favorite.toLowerCase.charAt(0)
        val meThink = firstChar match {
            case 'g' if math.random() > 0.5 => "gurka är gott!"
            case 't' => "tomat är gott!"
            case 'b' => "brocolli är gott!"
            case _ => s"$favorite är mindre gott..."

        }
        println (s"Jag tycker att $meThink")
    }
}