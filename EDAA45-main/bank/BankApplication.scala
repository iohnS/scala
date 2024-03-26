package bank

import scala.io.StdIn._
import java.nio.file.attribute.UserPrincipalNotFoundException
import bank.time.Date

object BankApplication{

  val myBank = new Bank
  var quit = false

        def main(args: Array[String]): Unit = {
        myBank.load()
        println("---------------------------------------\n" + 
            "1. Hitta konton för given kund \n" +
          "2. Sök efter kunder på (del av) namn \n" +
          "3. Sätt in pengar \n" +
          "4. Ta ut pengar \n" +
          "5. Överför pengar mellan konton \n" +
          "6. Skapa nytt konto \n" +
          "7. Radera existerande konto \n" +
          "8. Skriv ut alla konton i banken \n" +
          "9. Skriv ut ändringshistoriken \n" +
          "10. Återställ banken till ett tidigare datum \n" +
          "11. Avsluta")
              
        while(!quit){
            pickOption()
        }
    }

    def pickOption(): Unit = {
        print("----------------------------\n" + "Välj ett alternativ: ")
        readInt() match{
            case 1 => {
                myBank.findAccountsForHolder(UI.readID()).foreach(x=>println(x))
                UI.currentDate()
            }
            case 2 => {
                println(myBank.findByName(UI.readName)(0))
                UI.currentDate()  
            }
            case 3 => {
                print("Kontonummer: ")
                println(myBank.doEvent(Deposit(UI.readAccountNr(), UI.readAmount()), false))
                UI.currentDate()
            }
            case 4 => {
                print("Kontonummer: ")
                println(myBank.doEvent(Withdraw(UI.readAccountNr(), UI.readAmount()), false))
                UI.currentDate()
            }
            case 5 => {
                println(myBank.doEvent(Transfer(UI.readAccountNrFrom(), UI.readAccountNrTo(), UI.readAmount()), false))
                UI.currentDate()
            }
            case 6 => {
                println(myBank.doEvent(NewAccount(UI.readName(), UI.readID()), false))
                UI.currentDate()
            }
            case 7 => {
                print("Ange konto att radera: ")
                println(myBank.doEvent(DeleteAccount(UI.readAccountNr()), false))
                UI.currentDate()
            }
            case 8 => {
                println(myBank.allAccountsByNbr().mkString("\n"))
                UI.currentDate()
            }
            case 9 => {
                println(myBank.allHistory().map(_.toNaturalFormat).mkString("\n"))
                UI.currentDate()
            }
            case 10 => {
                println("Vilket datum vill du återställa banken till?")
                println(myBank.returnToState(UI.readDate()))
                UI.currentDate()
            }
            case 11 => quit = true
        }
        // if (readInt() < 1 || readInt() > 11){
        //     println("Välj mellan 1 till 11: ")
        //     pickOption()
        // }
        // else pickOption()
    }
}