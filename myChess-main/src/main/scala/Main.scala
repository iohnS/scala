
@main 
def start: Unit = {
    var quit = false
    var chess = new Chess()
    chess.load
    chess.createBoard()
    chess.printBoard
    
    while(!quit) chess.getCommand

}
