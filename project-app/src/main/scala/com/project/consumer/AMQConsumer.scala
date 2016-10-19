package com.project.consumer

import com.felstar.jmsScala.JMS._
import com.felstar.jmsScala.JMS.AllImplicits._
import org.apache.activemq.ActiveMQConnectionFactory
import javax.jms._

class AMQConsumer(val broker: String, val topic: String) {
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
     def onMessage(mess:javax.jms.Message)=  {
       println("RECEIVED:  " + mess.asText)
     }
  })
}
