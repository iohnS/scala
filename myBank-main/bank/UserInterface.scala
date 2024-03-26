package bank

import bank.time.Date
import java.util.Scanner
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object UI {

val scan = new Scanner(System.in)

  def readID(): Long = {
    print("ID: ")
    val inputID = scan.next()
      if(inputID.toString.length != 10){
        println("ID måste vara 10 siffror: ")
        readID()}
      else if(!inputID.forall(_.isDigit)){
        print("Bara siffror i ")
        readID()
      }
      else inputID.toLong
  }

  def readDate(): Date = {
    val inputYear = {
      print("År: ") 
      scan.nextInt()
    }
    val inputMonth = {
      print("Månad: ") 
      scan.nextInt()
    }
    val inputDay = {
      print("Datum (dag): ") 
      scan.nextInt()
    }
    val inputHour = {
      print("Timme: ") 
      scan.nextInt()
    }
    val inputMinute = {
      print("Minut: ") 
      scan.nextInt()
    }
    val inputDate = Date(inputYear, inputMonth, inputDay, inputHour, inputMinute)
    inputDate
  }

  def readAccountNr(): Int = {
    val inputAccNr = scan.next()
    if(inputAccNr.toString.length != 4){
      println("Kontonummer måste vara 4 siffor: ")
      readAccountNr()
    }
    else if(!inputAccNr.forall(_.isDigit)){
      print("Bara siffror i kontonummret: ")
      readAccountNr()
    }
    else inputAccNr.toInt
  }

    def readAccountNrTo(): Int = {
      print("Kontonummer att överföra till: ")
    val inputAccNr = scan.next()
    if(inputAccNr.toString.length != 4){
      println("Kontonummer måste vara 4 siffror: ")
      readAccountNrTo()
    }
    else if(!inputAccNr.forall(_.isDigit)){
      print("Bara siffror i kontonummret: ")
      readAccountNrTo()
    }
    else inputAccNr.toInt
  }

    def readAccountNrFrom(): Int = {
      print("Kontonummer att överföra från: ")
    val inputAccNr = scan.next()
    if(inputAccNr.toString.length != 4){
      println("Kontonummer måste vara 4 siffror: ")
      readAccountNrFrom()
    }
    else if(!inputAccNr.forall(_.isDigit)){
      print("Bara siffror i kontonummret: ")
      readAccountNrFrom()
    }
    else inputAccNr.toInt
  }

  def readAmount(): BigInt = {
    print("Summa: ")
    val inputAmount = scan.next()
    if(!inputAmount.forall(_.isDigit)){
      print("Bara siffror i summan: ")
      readAmount()
    }
    BigInt(inputAmount)
    }

  def readName(): String = {
    print("Namn: ")
    val inputName = scan.next()
    if(inputName == ""){
        println("Var vänlig och skriv ditt namn: ")
        readName()
      }
    else if(inputName.exists(_.isDigit)){
      println("Inga siffror i namnet: ")
      readName()
    }
    else inputName
  }

  def currentDate(): Unit = {
    println(Date.now.toNaturalFormat)
  }

}

