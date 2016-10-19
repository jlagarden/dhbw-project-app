package com.project.app

import com.project.production._
import com.project.consumer._

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

object App {

    def main(args : Array[String]) {
        val system = ActorSystem()
        val p = system.actorOf(Props[ProductionManager])
        (new Thread(new KafkaConsumer("127.0.0.1:2181", "prod", p))).start()
        val amqconsumer = new AMQConsumer("127.0.0.1:61616","m_orders")
    }


}
