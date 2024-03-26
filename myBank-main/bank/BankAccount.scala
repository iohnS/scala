package bank

//Creates a new bankaccount for the customer provided. The bankacount is given a unique account number and a initial balance of 0
class BankAccount(val holder: Customer) {
    val accountNumber: Int = 1000 + BankAccount.nextAccountNr()

    private var balance: BigInt = 0

    //Deposits provided amount in this account
    def deposit(amount: BigInt): Unit = {
        if(amount < 0)Strings.failNegativeNumber
        balance = balance + amount
    }

    //Returns the balance of this account
    def getBalance: BigInt = balance

    //withdraws the amount of money from this account if there is enough. Returns true if transaction succesful otherwise false. 
    def withdraw(amount: BigInt): Boolean = {
        if(amount <= balance && amount>0){
            balance = balance - amount
            true
        }
        else false
}

    override def toString(): String = s"Konto $accountNumber ($holder) $balance kr"
}

object BankAccount{
    var nbrOfAccounts = 0
    def nextAccountNr(): Int = {
        nbrOfAccounts += 1 
        nbrOfAccounts
    }
}