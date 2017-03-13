
case class Message(id: Int, content: String)

class MessageQueue {

  private var receivedMessages = 0
  private var queue = Seq[Message]()

  // SendMessage: Send messages to a specified queue.
  def sendMessage(content: String): Unit = {
    receivedMessages += 1
    queue = Message(receivedMessages, content) +: queue
  }

  // ReceiveMessage: Return one or more messages from a specified queue.
  def receiveMessage(): Option[Message] =
    queue.headOption

  // DeleteMessage: Remove a previously received message from a specified queue.
  def deleteMessage(id: Int): Unit = ???

}
