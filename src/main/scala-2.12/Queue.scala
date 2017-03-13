
case class Message(content: String)

class Queue {

  private var queue = Seq[Message]()

  // SendMessage: Send messages to a specified queue.
  def sendMessage(message: Message): Unit =
    queue = message +: queue

  // ReceiveMessage: Return one or more messages from a specified queue.
  def receiveMessage(): Option[Message] =
    queue.headOption

  // DeleteMessage: Remove a previously received message from a specified queue.
  def deleteMessage(message: Message): Unit = ???

}
