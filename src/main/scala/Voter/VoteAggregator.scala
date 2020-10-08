package Voter
import akka.actor.{Actor, ActorRef}


import scala.collection.immutable.Set
object VoteAggregator

class VoteAggregator extends Actor
{
  import Voter._

  final private case class AcceptedVote(candidate:Candidate , s:String)
  def receiveVoteRequest(d:ID, idSet:Set[ID] = Set() , VoteSet:Set[Candidate] = Set()) : Receive =
  {
    case AcceptedVote(candidate , "Your vote could be counted...")  =>
      context.become(receiveVoteRequest(d , idSet , VoteSet + candidate))

    case Vote(candidate , id) =>
      if (idSet.contains(id))
      self ! AcceptedVote(candidate , "Your vote could be counted...")
      else sender() ! "invalid vote"

    case ID =>
    if (idSet.contains(d)){
      sender() ! "Your vote could be counted..."
      //sender() ! id.hashID
    }
    else
    {
      sender() ! "your vote could be counted..."
      context.become(receiveVoteRequest(d , idSet + d , VoteSet))
    }
    case _ =>
    {
      sender() ! "Identification Error !!!"
    }
  }



  override def receive: Receive =
  {
    ??? //TODO
  }
}
