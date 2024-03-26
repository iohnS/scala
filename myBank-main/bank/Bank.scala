package bank

import java.util.Scanner
import java.time.LocalDateTime
import bank.time.Date
import java.io._

//creates a new bank with no account or history

class Bank() {  

    val scan = new Scanner(System.in)
    var accounts: Vector[BankAccount] = Vector.empty[BankAccount]
    var history: Vector[HistoryEntry] = Vector.empty[HistoryEntry]
    

    // returns a list of every bankaccount in the bank, The returned list is sorted in an alphabetical order according to the customers name. 
    def allAccounts(): Vector[BankAccount] = {
        accounts.sortBy(_.holder.name).toVector
    }

    def allAccountsByNbr(): Vector[BankAccount] = {
        accounts.sortBy(_.accountNumber).toVector
    }

    //Returns the account holding the provided account number. 
    def findByNumber(accountNbr: Int): Option[BankAccount] = {
        allAccounts().find(_.accountNumber == accountNbr)
    }
    //Returns a list of every account belonging to the customer with the provided ID
    def findAccountsForHolder(id: Long): Vector[BankAccount] = {
        accounts.filter(_.holder.id == id).toVector
    }

    //returns a list of all customers whose name match the provided name pattern 
    def findByName(namePattern: String): Vector[Customer] = {
        accounts.map(_.holder).filter(_.name.toLowerCase.contains(namePattern.toLowerCase))
    }

 
    def load(): Unit = {
        for(x <- 0 to str.length-1){
            history = history :+ HistoryEntry.fromLogFormat(str(x))
        }
            history.map(_.event).foreach(x=> 
            doEvent(x, true))
    }

    val str = scala.io.Source.fromFile("bank_log.txt").mkString("").split('\n')


    //Executes an event in the bank. Returns a string describing whether the event succeded or failed. 
    //Den kör antingen deposit, withdraw osv. 
    def doEvent(event: BankEvent, load: Boolean): String = {
        event match{
            case Deposit(account, amount) => {
                if(findByNumber(account).isDefined){
                    findByNumber(account).get.deposit(amount)
                    if(load == false){
                        newHistoryEntry(event)
                        addToHistoryFile(event)
                }
                Strings.success
            }
            else Strings.fail
        }
            case Withdraw(account, amount) => {
                if(findByNumber(account).isDefined && findByNumber(account).get.withdraw(amount)){
                    if(load == false){
                        newHistoryEntry(event)
                        addToHistoryFile(event)
                    }
                    Strings.success
                }
                    else Strings.fail
            
     }
            case Transfer(accFrom, accTo, amount) =>{
            if(findByNumber(accFrom).isDefined && findByNumber(accTo).isDefined){
                if(findByNumber(accFrom).get.withdraw(amount)){
                    findByNumber(accTo).get.deposit(amount)
                    if(load == false){
                        newHistoryEntry(event)
                        addToHistoryFile(event)
                }
                Strings.success
                }
                else Strings.fail
                }
                else Strings.fail
            }


            case NewAccount(name, id) => {
                val acc = new BankAccount(Customer(name, id))
                accounts = accounts :+ acc
                if(load == false){
                    newHistoryEntry(event)
                    addToHistoryFile(event)
                }
                Strings.accountCreated + s"${acc.accountNumber}"
            }
            case DeleteAccount(account) => {
                accounts = accounts.filter(_.accountNumber != account)
                if(!accounts.contains(account) && !load){
                    newHistoryEntry(event)
                    addToHistoryFile(event)
                    BankAccount.nbrOfAccounts -= 1
                    Strings.success
                }
                else Strings.fail
            }
        }
    }

    def newHistoryEntry(event: BankEvent): Vector[HistoryEntry] = {
        val newEntry = new HistoryEntry(Date.now(), event)
        history = history :+ newEntry
        history
    }

    val file = new File("bank_log.txt")


    def addToHistoryFile(event: BankEvent): Unit =  {
        val fw = new FileWriter(file, true)
        val entry = new HistoryEntry(Date.now(), event)
        fw.write(s"${entry.toLogFormat}\n")
        fw.close()
    }

    //returns a log of all changes to the bank state
    def allHistory(): Vector[HistoryEntry] = history

    //resets the bank to the state it had at the provided date.
    //returns a string describing whether the event was succesful or not
    def returnToState(returnDate: Date): String = {
        val newStr = str.slice(0, (history.map(_.date).indexOf(returnDate)+1))
        val ow = new FileWriter(file, false)
        ow.write(s"${newStr.mkString("\n")}\n")
        ow.close()
        history = history.filter(_.date.compare(returnDate) <= 0)
        accounts = Vector.empty[BankAccount]
        BankAccount.nbrOfAccounts = 0
        history.map(_.event).foreach(x=> 
                    doEvent(x, true))
        Strings.success
    }
}