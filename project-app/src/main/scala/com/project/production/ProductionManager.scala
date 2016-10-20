package com.project.production

import com.project.consumer._
import com.project.producer._
import akka.actor._
import scala.collection.mutable.HashMap
import scala.util.{Try, Success, Failure}

class ProductionManager extends Actor {
    val products : HashMap[Int, ActorRef] = new HashMap[Int, ActorRef]
    var kproducer: Option[ActorRef] = None
    var counter : Int = 0;

    override def preStart(): Unit = {
        context.actorOf(KafkaConsumer.props("127.0.0.1:2181", "prod"))
        context.actorOf(AMQConsumer.props("tcp://127.0.0.1:61616", "m_orders"))
<<<<<<< 4ccf4b46971d75484170ad95f628e9330731ba68:project-app/src/main/scala/com/project/production/ProductionManager.scala
        context.actorOf(FileConsumer.props())
        context.actorOf(KafkaProducer.props("localhost:9092", "test"))
=======
        kproducer = Some(context.actorOf(KafkaProducer.props("localhost:9092", "test")))
>>>>>>> ProductionManager kann nun nachrichten an kafka senden:project-app/src/main/scala/com/project/production/ProductionManger.scala
    }

    def receive = {
        case x: ProdData => {
            (x.event(), x) match {
                case (L1NonReceiving, y : ProdData) => {
                    val item = context.actorOf(Props[Item])
                    item ! (L1NonReceiving, x)
                    products += (counter -> item)
                    counter += 1
                }
                case (x : ProdEvent, y: ProdData) => {
                    val item1: Option[ActorRef] = products.get(counter - 1)
                    val item2: Option[ActorRef] = products.get(counter - 2)

                    item1.map(_ ! (x, y))
                    item2.map(_ ! (x, y))
                }
            }
            println(x)
        }
        case x : ERPData => {
            val item: Option[ActorRef] = products.get(counter -1)
            item.map(_ ! x)
            println(x)
        }
        case x: SpecData => {
          val item: Option[ActorRef] = products.get(counter -2)
          item.map(_ ! x)
          println(x)
        }
        case x: String => {
            kproducer.map(_ ! x)
        }

        case x => println(s"unhandled $x")
    }
}
