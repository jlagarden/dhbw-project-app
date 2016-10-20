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

import better.files._
import FileWatcher._
import FileWatcher.{ Message => FileWatcherMessage }
import java.nio.file.{Paths, Files, WatchEvent}
import java.nio.file.{StandardWatchEventKinds => EventType}
import java.io.File._


object App {

    def main(args : Array[String]) {
        val system = ActorSystem()
        val p = system.actorOf(Props[ProductionManager])
        //(new Thread(new KafkaConsumer("127.0.0.1:2181", "prod", p))).start()
        //val amqconsumer = new AMQConsumer("tcp://127.0.0.1:61616","m_orders")

        val watcher: ActorRef = system.actorOf(Props(new FileWatcher(Paths.get("/Users/helgedickel/gitlab/dhbw-projekt-app/project-app/tmp"))))

        // util to create a RegisterCallback message for the actor
        def when(events: Event*)(callback: Callback): FileWatcherMessage = {
          FileWatcherMessage.RegisterCallback(events.distinct, callback)
        }

        // send the register callback message for create/modify events
        watcher ! when(events = EventType.ENTRY_CREATE, EventType.ENTRY_MODIFY) {
          case (EventType.ENTRY_CREATE, file) => println(s"$file got created")
          case (EventType.ENTRY_MODIFY, file) => println(s"$file got modified")
        }
    }
}
