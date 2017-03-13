import org.specs2.mutable.Specification

class QueueSpec extends Specification {

  val CONTENT = "a message"

  "Queue" should {

    "add element and retrieve from queue" in {
      val queue = new Queue()
      queue.sendMessage(Message(CONTENT))
      val receivedMessage = queue.receiveMessage()
      receivedMessage.get.content must contain(CONTENT)
    }

    "retrieve from empty queue returns no message" in {
      val queue = new Queue()
      val receivedMessage = queue.receiveMessage()
      receivedMessage must beNone
    }
  }
}
