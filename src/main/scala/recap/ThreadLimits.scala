package recap

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
object ThreadLimits extends App{
  /*
  */

  class BankAcount(private var amount:Int){
    override def toString:String = "" + amount
    def withdraw(money :Int):Unit = this.synchronized(amount -= money)
    def deposit(money:Int):Unit = this.synchronized(amount += money)
    def getAmount:Int = amount
  }

  val account = new BankAcount(2000)
  for (_ <- 1 to 1000) {
    new Thread(() => account.withdraw(1)).start()
  }

  for (_ <- 1 to 1000) {
    new Thread(() => account.deposit(1)).start()
  }
  println(account.getAmount)


  /*
  * DR #2 : delegating something to thread is a pain
  * */
  //you have a running thread and want to pass a runnable to that thread

  var task: Runnable = null
  val runningThread:Thread = new Thread(() => {
    while(true){
      while (task == null)
      {
        runningThread.synchronized
        {
          println("[backGround] waiting for a task ...")
          runningThread.wait()
        }
      }

      task.synchronized{
        println("[background] I have task!")
        task.run()
        task = null
      }
    }
  })

  def delegateToBackGroundThread(r:Runnable) =
  {
    if (task == null) task = r
    runningThread.synchronized
    {
      runningThread.notify()
    }
  }

  runningThread.start()
  Thread.sleep(1000)
  delegateToBackGroundThread(() => println(42))
  Thread.sleep(1000)
  delegateToBackGroundThread(() => println("this should run in the Background"))


  /**tracing and dealing with
   * 1M numbers in between for every future computers ranges 100000
   * if range contains the number of solutions this will take a moment from customer
   */
  val futures = (0 to 9)
    .map(i => 1000 until 10000 * (i+1))
    .map(range => Future {
    if(range.contains(567891))
      throw new RuntimeException("invalidNumber")
      range.sum
    })
  val sumFutures = Future.reduceLeft(futures)(_+_)
}
