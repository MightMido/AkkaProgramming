package Voter

//import java.security.MessageDigest
//import java.util.Random
//
//import akka.actor.{Actor, ActorRef}
//
//object VotingSystem extends App
//{
//  //Customer Scoring problems and systems from scoring
//
//
//  object VoteCounter
//  class VoteCounter extends Actor
//  {
//    //Convert To Hash
//   def hashMessage(s:String) =
//   {
//     val random =  new Random
//     val randomPart = random.nextInt()
//     val HashMessage = MessageDigest.getInstance("SHA-256").digest((s + randomPart).getBytes)
//     List(HashMessage , randomPart)
//   }
//
//
//   override def receive:Receive = countVote(0 , 0)
//   def receiveVoteReq : Receive =
//   {
//     case VoteStatusRequest(_) =>
//     {
//       sender() ! hashMessage("VoteKey")
//     }
//     case VoteReq =>
//   }
//
//   def countVote(v1: Int , v2:Int):Receive =
//      {
//        case Vote("Trump") => context.become(countVote(v1 + 1 , v2))
//        case Vote("Biden") => context.become(countVote(v1 , v2 + 1))
//        println("Voted for Biden")
//      }
//  }
////the customer scoring problems and systems from scoring products and futures
//}
//