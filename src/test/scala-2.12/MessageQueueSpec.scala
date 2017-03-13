import org.specs2.mutable.Specification

class MessageQueueSpec extends Specification {

  val CONTENT = "a message"
  val ANOTHER_CONTENT = "another message"

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

    "return first visible message from queue" in {
      val queue = new MessageQueue()
      queue.sendMessage(CONTENT)
      queue.sendMessage(ANOTHER_CONTENT)
      queue.receiveMessage()
      val secondReceivedMessage = queue.receiveMessage()
      secondReceivedMessage.get.content must contain(ANOTHER_CONTENT)
    }

    "not allow sending more messages than capacity" in {
      val queue = new MessageQueue(2)
      queue.sendMessage(CONTENT) must beRight(1)
      queue.sendMessage(CONTENT) must beRight(2)
      queue.sendMessage(ANOTHER_CONTENT) must beLeft
    }
  }
}
