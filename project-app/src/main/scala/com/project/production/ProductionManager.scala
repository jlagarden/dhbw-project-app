package com.project.production

import com.project.consumer._
import com.project.producer._
import akka.actor._
import scala.collection.mutable.HashMap
import scala.util.{Try, Success, Failure}
import org.json4s._
import org.json4s.Formats._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods

class ProductionManager extends Actor {
    val products : HashMap[Int, ActorRef] = new HashMap[Int, ActorRef]
    var kproducer: Option[ActorRef] = None
    var kproducerlive: Option[ActorRef] = None
    var counter : Int = 0;

    override def preStart(): Unit = {
        context.actorOf(KafkaConsumer.props("kafka:2181", "prod"))
        context.actorOf(AMQConsumer.props("tcp://activemq:61616", "m_orders"))
        context.actorOf(FileConsumer.props("/tmp"))
        kproducer = Some(context.actorOf(KafkaProducer.props("kafka:9092", "test")))
        //kproducerlive = Some(context.actorOf(KafkaProducer.props("kafka:9092", "live")))
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
            liveProdData(x)
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
            println("kproducter:")
            println(x)
            kproducer.map(_ ! x)
        }

        case x => println(s"unhandled $x")
    }



    def liveProdData(inp: ProdData) {
      var current_action          = ""
      var speed_milling           = 0.0
      var speed_drilling          = 0.0
      var temperature_milling     = 0.0
      var temperature_drilling    = 0.0

      // val value:Option[Any] = Some(inp.value)
      // val valueAnyVal = (value match {
      //     case Some(x:Double) => x
      //     case _ => {}
      // })
      // val ar:Array[AnyVal] = Array[AnyVal](valueAnyVal)
      // val nar:Array[Double] = ar.map(_.asInstanceOf[Double])
      //
      // inp match {
      //   case ProdData(false, _, "L1", _) => {
      //     current_action = inp.itemName
      //   }
      //   case ProdData(false, _, "L2", _) => {
      //     current_action = inp.itemName
      //   }
      //   case ProdData(false, _, "L3", _) => {
      //     current_action = inp.itemName
      //   }
      //   case ProdData(false, _, "L4", _) => {
      //     current_action = inp.itemName
      //   }
      //   case ProdData(false, _,"L5",  _) => {
      //     current_action = inp.itemName
      //   }
      //   case ProdData(_, _, "MILLING", _) => {
      //     current_action = inp.itemName
      //   }
      //   case ProdData(_, _, "MILLING_SPEED", _) => {
      //     speed_milling = nar(1)
      //   }
      //   case ProdData(_, _, "MILLING_HEAT", _) => {
      //     temperature_milling = nar(1)
      //   }
      //   case ProdData(_, _, "DRILLING", _) => {
      //     current_action = inp.itemName
      //   }
      //   case ProdData(_, _, "DRILLING_SPEED", _) => {
      //     speed_drilling = nar(1)
      //   }
      //   case ProdData(_, _, "DRILLING_HEAT", _) => {
      //     temperature_drilling = nar(1)
      //   }
      //
      //   case _ => None
      // }

        val json: String = "{\"live_data\":{\"speed_milling\":"+speed_milling+",\"speed_drilling\":"+speed_drilling+",\"temperature_drilling\":"+temperature_drilling+",\"temperature_milling\":"+temperature_milling+",\"current_action\":\""+current_action+"\"}}"
        //   ("live_data" ->
        // ("speed_milling" -> speed_milling) ~
        // ("speed_drilling" -> speed_drilling) ~
        // ("temperature_drilling" -> temperature_drilling) ~
        // ("temperature_milling" -> temperature_milling) ~
        // ("current_action" -> current_action)
        // )

      println("FUCK THIS FUCKING SHITPILE OF GARBAAAAAAAAAGE!!!!!")
      println(json)
      //kproducerlive.map(_ ! json)
    }
}
