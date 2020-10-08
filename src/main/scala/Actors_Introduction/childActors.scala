package Actors_Introduction

import Actors_Introduction.childActors.CreditCard.{AttachToAccount, CheckStatus}
import Actors_Introduction.childActors.NaiveBank.InitializedAccount
//import Actors_Introduction.childActors.Parent.{CreateChild, TellChild}
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object childActors extends App{
//  //actors can create other actors
//  object Parent {
//    case class CreateChild(name:String)
//    case class TellChild(message :String)
//  }
//  class Parent extends Actor
//  {
//    import Parent._
//    override def receive : Receive =
//    {
//      case CreateChild(name) => println(s"${self.path} creating child !!!")
//      val childRef = context.actorOf(Props[Child] , name)
//        context.become(withChild(childRef))
//    }
//    def withChild(childRef:ActorRef):Receive =
//    {
//      case TellChild(message) =>
//      if (childRef != null) childRef forward message
//    }
//  }//
//
//
//

//  class Child extends Actor{
//    override def receive : Receive =
//    {
//      case message => println(s"${self.path} I got: $message")
//    }
//  }//

//  import Parent._
//  val system = ActorSystem("test")
//  val parent =  system.actorOf(Props[Parent] , "Parent")
//  parent ! CreateChild("Child1")
//  parent ! TellChild("Hey Kido...")

  /**
  * top level actors :
  * -/system = system Guardian
  * -/user = user level guardian
  * -/= the root guardian system and user are below Guardian
  * */

  /**
   * finding parent by path
   * Actor selection
   *
   * */
  //val childSeletion = system.actorSelection("akka://test/user/Parent/Child1")
  //childSeletion ! "Got you!"
  /**sent to dead letters
   * Never Pass Mutable Actor State, OR the This reference to child Actors
   * never :security Problems will happen really bad Problems
   *
   * */

  object NaiveBank{
    case class Deposit(Amount:Int)
    case class withdraw(amount:Int)
    case object InitializedAccount
  }

  class NaiveBank extends Actor
  {
    import NaiveBank._
    import CreditCard._
    var amount = 0
    override def receive :Receive ={
      case InitializedAccount =>
        val creditCardRef = context.actorOf(Props[CreditCard])
        creditCardRef ! AttachToAccount(this)
      case Deposit(funds) =>
    }
    def deposit(funds:Int) = amount += funds
    def withdraw(funds :Int) = amount -= funds
  }

  object CreditCard{
    case class AttachToAccount(bankAccount:NaiveBank)
    case object CheckStatus
  }
  class CreditCard extends Actor {
    override def receive:Receive = {
      case AttachToAccount(account) => context.become(attchedToAccount(account))
    }
    def attchedToAccount(account: NaiveBank):Receive = {
      case CheckStatus => println(s"${self.path} your message has been Processed..")
      account.withdraw(1)
    }
  }
  val system = ActorSystem("High")
  val bankAcountRef = system.actorOf(Props[NaiveBank])
  bankAcountRef ! InitializedAccount
  //val ccSelection = system.actorSelection("/user/acount/card")
  //ccSelection ! CheckStatus

}
