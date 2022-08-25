package common

//Adapted from following example:
//https://www.gregbeech.com/2018/06/05/writing-a-ring-buffer-in-scala/

import scala.collection.immutable.Queue

case class RingBuffer[+A] private (capacity: Int, size: Int, queue: Queue[A]) {
  def push[B >: A](b: B): (Option[A], RingBuffer[B]) =
    if (size < capacity) (None, RingBuffer(capacity, size + 1, queue.enqueue(b)))
    else queue.dequeue match {
      case (h, t) => (Some(h), RingBuffer(capacity, size, t.enqueue(b)))
    }

  def pop: (A, RingBuffer[A]) = popOption.getOrElse(throw new NoSuchElementException)

  def popOption: Option[(A, RingBuffer[A])] = queue.dequeueOption.map {
    case (h, t) => (h, RingBuffer(capacity, size - 1, t))
  }
}

object RingBuffer {
//  def empty[A](capacity: Int): RingBuffer[A] = RingBuffer(capacity, 0, Queue.empty)
//
//  def apply[A](capacity: Int)(xs: A*): RingBuffer[A] = {
//    val elems = if (xs.size <= capacity) xs else xs.takeRight(capacity)
//    RingBuffer(capacity, elems.size, Queue(elems: _*))
//  }
  def apply[A](capacity: Int): RingBuffer[A] = RingBuffer(capacity, 0, Queue.empty)
}
