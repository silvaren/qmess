
case class Message(content: String)

class Queue {

  // SendMessage: Send messages to a specified queue.
  def sendMessage(message: Message): Unit = ???

  // ReceiveMessage: Return one or more messages from a specified queue.
  def receiveMessage(): Message = ???

  // DeleteMessage: Remove a previously received message from a specified queue.
  def deleteMessage(message: Message): Unit = ???

}
