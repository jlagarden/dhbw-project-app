package com.project.consumer

import com.project.production._

import scala.xml._
import akka.actor.Actor
import akka.actor.Props
import com.felstar.jmsScala.JMS._
import com.felstar.jmsScala.JMS.AllImplicits._
import org.apache.activemq.ActiveMQConnectionFactory
import javax.jms._

object AMQConsumer {
  def props(broker: String, topic: String): Props = Props(new AMQConsumer(broker, topic))
}

class AMQConsumer(val broker: String, val topic: String) extends Actor {
  val connectionFactory = new ActiveMQConnectionFactory(broker)
  val connection = connectionFactory.createConnection()
  connection.start
  println("AMQConsumer: Connection established")
  val session = connection.session(false, Session.AUTO_ACKNOWLEDGE)
  println("AMQConsumer: Session created")
  val destination = session.topic(topic)
  println("AMQConsumer: Topic subscribed")
  val messageConsumer = destination.consumer
  println("AMQConsumer: Consumer online")

  messageConsumer.setMessageListener(new MessageListener(){
     def onMessage(mess:Message)=  {
       context.parent ! ERPData(mess.asText)
     }
  })

  // def processMessage(mess:Message) {
  //   val inp = mess.asText
  //   val xml = scala.xml.XML.loadString(inp)
  //   val erpData = ERPData.fromXml(xml)
  //   println("ERPData: " + erpData)
  //    }
  // })

  def receive = {
    case _ => ()
  }
}
