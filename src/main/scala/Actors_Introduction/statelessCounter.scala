package Actors_Introduction

import Actors_Introduction.statelessCounter.StatelessCounter.{DDecrement, IIncrement, PP}
import Actors_Introduction.statelessCounter.statelessCounterc.{Decrement, Increment, Print}
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object statelessCounter extends App
{

  object statelessCounterc
  {
    case object Increment
    case object Decrement
    case object Print
  }
  class statelessCounterc extends Actor
  {
    import statelessCounterc._
    def receive:Receive = counterReceive(0)
    def counterReceive(start:Int) :Receive =
    {
      case Decrement =>
      {
        context.become(counterReceive(start - 1))
        println(s"decrementing ===> : $start")
      }

      case Increment =>
      {
        context.become(counterReceive(start + 1))
        println(s"incrementing ===> : $start")
      }

      case Print => println(s"Current Count calc = $start" )
    }
  }

  object StatelessCounter {
    object IIncrement
    object DDecrement
    object PP
  }
  class StatelessCounter  extends Actor {
    override def receive:Receive = counterRec(0)
    def counterRec(start:Int):Receive = {
      case IIncrement => context.become(counterRec(start  + 1))
      case DDecrement => context.become(counterRec(start  - 1))
      case PP => println(start)
    }
  }

  val system = ActorSystem("StatelessCounter")
  val MasoudActor = system.actorOf(Props[statelessCounterc] , "MasoudCounter!!!")
  val MahdiCounter = system.actorOf(Props[statelessCounterc] , "MahdiCounter!!!")
  for(i <- 1 to 15) if (i%4 == 0) MasoudActor ! Increment else MahdiCounter ! Increment

  MasoudActor  ! Print
  MahdiCounter ! Print

}