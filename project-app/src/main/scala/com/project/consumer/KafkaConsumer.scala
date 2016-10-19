package com.project.consumer

import com.project.production._

import kafka.consumer._
import kafka.message._
import kafka.serializer._
import kafka.utils._
import java.util.Properties
import kafka.utils.Logging
import kafka.serializer.StringDecoder
import scala.collection.JavaConversions._

import akka.actor._
import akka.util.Timeout
import scala.concurrent.duration._
import akka.pattern.ask
import scala.concurrent.ExecutionContext.Implicits.global

import org.apache.log4j.Logger

import scala.collection.immutable.HashMap

object KafkaConsumer {

    def props(broker: String, topic: String): Props = Props(new KafkaConsumer(broker, topic))
}

class KafkaConsumer(val broker: String, val topic: String) extends Actor{

    val logger : Logger = Logger.getLogger(this.getClass.getName);

    val conf = createMyConf(broker)
    val connector = Consumer.create(conf)
    val stream = createStream(connector,topic)
    val iterator = stream.iterator()

    while(iterator.hasNext()) {
        //implicit val timeout = Timeout(10 seconds)
        val data = ProdData(iterator.next().message())
        val event = data.event()
        context.parent ! (event, data)
        println(s"$event : $data")
    }

    def createStream(connector: ConsumerConnector, topic: String) = {
        val topicCountMap = Map(topic -> 1)
        val stream = connector.createMessageStreams(topicCountMap, new StringDecoder(), new StringDecoder()).get(topic).get(0)
        println(stream)
        stream
    }

    def createMyConf(broker : String) = {
        val props = new Properties()
        props.put("zookeeper.connect", broker);
        props.put("advertised.host.name", "localhost");
        props.put("group.id", "bla");
        props.put("auto.offset.reset", "largest");
        props.put("zookeeper.session.timeout.ms", "4000");
        props.put("zookeeper.sync.time.ms", "2000");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
        val config : ConsumerConfig = new ConsumerConfig(props)
        config
    }


    def receive = {
        case _ => ()
    }

}
