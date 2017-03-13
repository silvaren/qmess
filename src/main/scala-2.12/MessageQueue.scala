
case class Message(id: Int, content: String)
case class InvisibilityStart(id: Int, startTime: Long)

class MessageQueue(val capacity: Int = 100000, val visibilityTimeout: Int = 60) {

  // need to be mutable to work the requests
  private[this] var receivedMessages = 0
  private[this] var queue = Seq[Message]()
  private[this] var invisibleLog = Set[InvisibilityStart]()

  // SendMessage: Send messages to the queue
  def sendMessage(content: String): Either[String, Int] = {
    synchronized {
      // actor model could be an alternative, but probably overkill
      if (queue.size == capacity) Left("Queue is full!")
      else {
        receivedMessages += 1
        queue = queue :+ Message(receivedMessages, content)
        Right(receivedMessages)
      }
    }
  }

  private def isVisible(msg: Message): Boolean = invisibleLog.find(_.id == msg.id) match {
    case None => true
    case Some(invisibilityStart) =>
      System.currentTimeMillis() - (invisibilityStart.startTime + visibilityTimeout * 1000) > 0
  }

  // ReceiveMessage: Return one or more messages from the queue.
  def receiveMessage(): Option[Message] = {
    synchronized { // actor model could be an alternative, but probably overkill
      val firstVisible = queue.find(isVisible)
      firstVisible match {
        case None => None
        case Some(msg) => {
          invisibleLog = invisibleLog + InvisibilityStart(msg.id, System.currentTimeMillis())
          Some(msg)
        }
      }
    }
  }

  // DeleteMessage: Remove a previously received message from the queue.
  def deleteMessage(id: Int): Unit = {
    synchronized { // actor model could be an alternative, but probably overkill
      invisibleLog = invisibleLog.filter(_ != id)
      queue = queue.filter(_.id != id)
    }
  }

}
