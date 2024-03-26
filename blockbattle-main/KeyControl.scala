package blockbattle

case class KeyControl (left: String, right: String, up: String, down: String){
    def direction(key: String): (Int, Int) = {
        key match {
            case `left` => (-1, 0)
            case `right` => (1, 0)
            case `up` => (0, -1)
            case `down` => (0, 1)
            case _ => (0,0)
        }
        
        
        //if      (key == left) {(-1, 0)}
        //else if (key == right) {(1, 0)}
        //else if (key == up) {(0, -1)}
        //else if (key == down) {(0, 1)}
        //else (0,0)                   //Det finns ingen knapp så kommer aldrig komma till den här punkten 
    }
    
    def has(key: String): Boolean = List(left, right, up, down).contains(key)

}
