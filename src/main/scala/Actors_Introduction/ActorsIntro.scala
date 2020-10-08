package Actors_Introduction
import akka.actor.{Actor, ActorSystem, Props}

//happiness Loneliness Apparert
object ActorsIntro extends App
{
  //actor systems
  val actorSystem = ActorSystem("MyFirstOfMany")
  println(actorSystem.name)

  //part2
  // word count actor
  class WordCountActor extends Actor {
    var totalWords = 0
  //part3 - instantiate our actor
    def receive:PartialFunction[Any , Unit] ={
      case message :String => println(s"[wordCounter] I have received: $message")
        totalWords += message.split("").length
      case message :String => println(s"[word counter] I can not understand ${message}")
    }
  }
  val wordCounter = actorSystem.actorOf(Props[WordCountActor] , "wordCounter")
  val AnotherWordCounter = actorSystem.actorOf(Props[WordCountActor] , "AnotherWordCounter")

  wordCounter ! "I am learning Scala along side with akka actor systems"
  AnotherWordCounter ! "A totally different message"
  //asynchronous
  object Person {
    def props(name:String) = Props(new Person(name))
  }


  class Person(name:String) extends Actor {
    override def receive: Receive = {
      case "Hi" => println(s"Hi my name is $name and I will be a scalaPro")
      case _=>
    }
  }


//!means tell in ActorSystems
  val person =
    actorSystem.actorOf(Person.props("Masoud"))
  person ! "Hi"

}
