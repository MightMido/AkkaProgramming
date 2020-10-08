package Voter
import java.security.MessageDigest
import java.util.Random
import scala.io.StdIn.readLine
import akka.actor.{Actor, ActorRef}


object Voter
{
  case class Vote(candidate:Candidate , id:ID)
  case class Candidate(CandidateName:String)
  case class VoteStatusRequest(id:ID)
  case class VoteStatusReply(candidate : Option[String])
  case class ID(n:String , f:String , r:Int)
    {
      def hashID  =
        {
          val hashedID = MessageDigest.getInstance("SHA-256").digest((n + f + r).getBytes)
          hashedID
        }
    }
}

class Voter(VoteAggRef:ActorRef) extends Actor
{
  import Voter._
  val random = new Random
  val name = "Voucher"
  val family = "voucher"
  final private val rnd = random.nextInt(1000) //It Will Become PrivateKey...
  val i = ID(name , family , rnd)

  def createVote(candidateName:String):Vote =
  {
    val candidate = Candidate(candidateName)
    val vote = Vote(candidate  , i)
    vote
  }
  private val voteKey = receiveVoteAccess
  def sendVote(voteString:String):Unit =
  {
    case this.voteKey == 1 =>
    {
      val V = createVote(voteString)
      VoteAggRef ! V
      println("your vote has been sent")
    }
    case this.voteKey == 0 => println("You are not allowed to vote !!!")
  }

  def sendVoteRequest =
  {
    VoteAggRef ! ID
    println("Just Sent my voting Request !!!")
  }

  def receiveVoteAccess:Receive =
  {
     case i.hashID =>
      {
        println("You Can Vote ...")
        1
      }
      case  _ => 0
  }
  override def receive : Receive =
  {
    case ID => sendVote("Masoud")
    case "Your registration have been successful" => println("Your registration have been successful")
    case "Your vote registered ..." => println("Just Voted")
  }
}