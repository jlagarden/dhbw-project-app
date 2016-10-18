package com.project

import com.felstar.jmsScala.JMS._
import com.felstar.jmsScala.JMS.AllImplicits._
import org.apache.activemq.ActiveMQConnectionFactory
import javax.jms._

class AMQConsumer {
  val connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:32786")
  val connection = connectionFactory.createConnection()
  connection.start
  println("connection started")
  val session = connection.session(false, Session.AUTO_ACKNOWLEDGE)
  println("session created")
  val destination = session.topic("m_orders")
  println("topic created")
  val messageConsumer = destination.consumer
  println("consumer created")

  messageConsumer.setMessageListener(new MessageListener(){
     def onMessage(mess:javax.jms.Message)=  {
       println("message received")
       println("RECEIVED:  " + mess)
       println("RECEIVED:  " + mess.asText)
     }
  })
}
