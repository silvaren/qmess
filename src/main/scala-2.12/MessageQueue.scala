
case class Message(id: Int, content: String)

class MessageQueue {

  private var receivedMessages = 0
  private var queue = Seq[Message]()

  // SendMessage: Send messages to the queue
  def sendMessage(content: String): Unit = {
    receivedMessages += 1
    queue = Message(receivedMessages, content) +: queue
  }

  // ReceiveMessage: Return one or more messages from the queue.
  def receiveMessage(): Option[Message] =
    queue.headOption

  // DeleteMessage: Remove a previously received message from the queue.
  def deleteMessage(id: Int): Unit =
    queue = queue.filter(msg => msg.id != id)

}
