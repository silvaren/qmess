import org.specs2.mutable.Specification

class MessageQueueSpec extends Specification {

  val CONTENT = "a message"

  "MessageQueue" should {

    "correctly add element and retrieve from queue" in {
      val queue = new MessageQueue()
      queue.sendMessage(CONTENT)
      val receivedMessage = queue.receiveMessage()
      receivedMessage.get.content must contain(CONTENT)
    }

    "return no message when attempt to retrieve from empty queue " in {
      val queue = new MessageQueue()
      val receivedMessage = queue.receiveMessage()
      receivedMessage must beNone
    }

    "delete existing message from queue when requested" in {
      val queue = new MessageQueue()
      queue.sendMessage(CONTENT)
      val receivedMessage = queue.receiveMessage()
      queue.deleteMessage(receivedMessage.get.id)
      queue.receiveMessage() must beNone
    }
  }
}
