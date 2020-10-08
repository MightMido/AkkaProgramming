package Actors_Introduction

import Actors_Introduction.ActorsIntro.actorSystem
import Actors_Introduction.actorCapabilities.bankAccount.{currentFund, deposit}
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object actorCapabilities extends App {


//  object simpleActor {
//    def props(name:String) = Props(new simpleActor(name))
//  }
//  //receive handler
//  class simpleActor(name:String) extends Actor
//  {
//    override def receive: Receive =
//    {
//      case "Hi" => context.sender() ! "Hello there"
//      case message: String => println(s"[${context.self.path}] I have received: $message")
//      case number:Int => println(s"[$name] I have received number: $number")
//      case specialMessage: SpecialMessage =>
//        println(s"[$name] I have received SM: ${specialMessage.contents}")
//      case SendMessageToYourself(content) => self ! content
//      case sayHiTo(ref) => ref ! "Hi"
//      case wirelessPhoneMessage(content , ref) => ref forward (content + "s")
//      // keeping original sender of the WPM
//    }
//  }
//
//  case class SpecialMessage(contents:String)
//  case class SendMessageToYourself(contents:String)
//  val system = ActorSystem("actorCapabilities")
//  val simpleA = system.actorOf(simpleActor.props("Masoud"))
//  simpleA ! "Hey Champ !!!"
//  simpleA ! 42
//  simpleA ! SpecialMessage("Masoud!!!")
//  simpleA ! SendMessageToYourself("will Jump High Enough!!!")
//
//  case class sayHiTo(ref: ActorRef)
//  val alice = system.actorOf(simpleActor.props("alice"))
//  val Bob = system.actorOf(simpleActor.props("Bob"))
//  alice ! "Hi"

  //a) messages have to be immutable
  //b) messages must be serializable
  //In practice use case classes and case objects
  //core principles : of customer Services from consumer Point of view
  //2 - context to actor reference
  // context.self === this In OOP
  // dead letters
  //5 - forwarding message
  // sending the message with original Data Structure
  //case class wirelessPhoneMessage(content:String, ref:ActorRef)
  //alice ! wirelessPhoneMessage("Hi",Bob)
  //alice ! sayHiTo(Bob)
  //Premitive types of customer
  //Actor reference

  /*
  * 1) a counter actor
  * increment
  * decrement
  * print
  *
  * 2) a Bank account as an actor
  * receives
  * - deposits amount
  * - withdraw an amount
  * - Statement
  * replies with
  * - Success
  * - Failure
  * interact with some other kind of actor
  * */

  case class increment(x:Int , iterator:Int)
  case class decrement(x:Int , iterator:Int)
  case class p(x:Int)

  object Counter {
   def props(name:String) = Props(new Counter(name))
    case object increment
    case object decrement
    case object print
  }

  class Counter(name:String) extends Actor
  {
    override def receive : Receive  =
    {
      case increment(x , iterator) =>
      {
        if(iterator == 0) self ! p(x)
        else self ! increment(x + 1 , iterator - 1)
      }

      case decrement(x , iterator) =>
      {
        if(iterator == 0) self ! p(x)
        else self ! decrement(x - 1 , iterator - 1)
      }
      case p(x) => println(s"[$name] result is : $x")
    }
  }

  val sys = ActorSystem("Exercise_1")
  val incrementer = sys.actorOf(Counter.props("Increment !!!"))
  incrementer ! increment(150,23)
  incrementer ! decrement(30,20)



  case class deposit(amount:Int,current:Int)
  case class withdraw(amount:Int,current:Int)
  case class showCurrentFunds(amount:Int)
  case class currentFund(amount:Int)

  object bankAccount
  {
    def props(name:String) = Props(new bankAccount(name))
    case object deposit
    case object withdraw
    case object currentFund
    case object showCurrentFunds
  }

  class bankAccount(name:String) extends Actor
  {
    override def receive: Receive =
    {
      case deposit(amount,current) =>
        {
          self ! currentFund(amount + current)
          self ! showCurrentFunds(current + amount)
        }
      case withdraw(amount,current) => if (current < amount) self ! "Not enough cash ...!!!"
                                       else self ! (current - amount)
      case currentFund(amount) => amount
      case showCurrentFunds(amount) => println(amount)
      case "Not enough cash" => println("not enough cash")
      }
    }


}
