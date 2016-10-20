package com.project.producer

import akka.actor.Actor
import akka.actor.Props

import java.util.Properties
import kafka.producer.ProducerConfig
import kafka.producer.Producer
import kafka.producer.KeyedMessage

class KafkaProducer(val broker: String, val topic: String) extends Actor {

    val props = new Properties()
    props.put("metadata.broker.list", broker)
    props.put("serializer.class", "kafka.serializer.StringEncoder")
    props.put("producer.type", "async")

    val config = new ProducerConfig(props)
    val producer = new Producer[String, String](config)
    //producer.send(new KeyedMessage[String, String](topic, broker, "test nachricht test"))

    def receive = {
        case x : String => producer.send(new KeyedMessage[String, String](topic, broker, x))
        case _ => ()
    }
}

object KafkaProducer {
    def props(broker: String, topic: String): Props = Props(new KafkaProducer(broker, topic))
}
