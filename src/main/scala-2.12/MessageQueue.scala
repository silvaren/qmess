
case class Message(id: Int, content: String)

class MessageQueue {

  // need to be mutable to work the requests
  private[this] var receivedMessages = 0
  private[this] var queue = Seq[Message]()
  private[this] var invisibleLog = Set[Int]()

  // SendMessage: Send messages to the queue
  def sendMessage(content: String): Unit = {
    receivedMessages += 1
    queue = queue :+ Message(receivedMessages, content)
  }

  // ReceiveMessage: Return one or more messages from the queue.
  def receiveMessage(): Option[Message] = {
    val firstVisible = queue.find(msg => !invisibleLog.contains(msg.id))
    firstVisible match {
      case None => None
      case Some(msg) => {
        invisibleLog = invisibleLog + msg.id
        Some(msg)
      }
    }
  }

  // DeleteMessage: Remove a previously received message from the queue.
  def deleteMessage(id: Int): Unit = {
    invisibleLog = invisibleLog.filter(_ != id)
    queue = queue.filter(_.id != id)
  }

}
