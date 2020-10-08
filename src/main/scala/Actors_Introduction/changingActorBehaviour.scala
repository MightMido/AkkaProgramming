package Actors_Introduction

import Actors_Introduction.changingActorBehaviour.FussyKid.{KidAccept, KidReject}
import Actors_Introduction.changingActorBehaviour.Mom.{Food, MomStart}
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object changingActorBehaviour extends App
{


  //here is the Kid class
  object FussyKid
  {
    case object KidAccept
    case object KidReject
    val Happy : String = "happy"
    val Sad : String = "sad"
  }
  class FussyKid extends Actor {
    //internal state of the kid
    import Mom._
    import FussyKid._
    var state = "happy"
    override def receive:Receive = {
      case Food(Veggies) => state = Sad
      case Food(Chocolate) => state = Happy
      case Ask(_) =>
        if(state == Happy) sender() ! KidAccept
        else if (state == Sad) sender() ! KidReject
    }
  }
  //end of kid class
  class statelessFussyKid extends Actor{
    import Mom._
    override def receive:Receive = happyReceive
    def happyReceive:Receive =
    {
      case Food(Veggies) => context.become(sadReceive , false)//change my receive handler to happyReceive
      case Food(Chocolate) => context.unbecome()
      case Ask(_) => sender() ! KidAccept
    }
    def sadReceive :Receive =
    {
      case Food(Chocolate) => context.unbecome()
      case Food(Veggies) => context.become(sadReceive , false)
      case Ask(_) => sender() ! KidReject
    }
  }

  //here is the mom object
  object Mom
  {
    case class MomStart(kidRef:ActorRef)
    case class Food(name: String)
    case class Ask(message:String) // its a Question
    val Veggies = "veggies!"
    val Chocolate = "chocolate!"
  }
  class Mom extends Actor
  {
    import Mom._
    import FussyKid._
    override def receive:Receive =
    {
      case KidAccept => println("Yey My kid is Happy !!!")
      case KidReject => println("Ouch!! He is healthy")
      case MomStart(kidRef) =>
      kidRef ! Food(Veggies)
      kidRef ! Food(Chocolate)
      kidRef ! Food(Chocolate)
      kidRef ! Food(Chocolate)
      kidRef ! Food(Chocolate)
      kidRef ! Ask("Do you want to play?")
      // test Interaction
    }
  }
  val system = ActorSystem("changingActorBehaviorDemo")
  val fussyKid = system.actorOf(Props[FussyKid] , "FussyKid!")
  val mom  = system.actorOf(Props[Mom] , "Mom!")
  val stateLessFussyKid = system.actorOf(Props[statelessFussyKid] , "statelessFussyKid")
  mom ! MomStart(stateLessFussyKid)


  //here is the mom object


  //recreate the counter Actor with context Become Response messages
  // will not import anything Context become


}
