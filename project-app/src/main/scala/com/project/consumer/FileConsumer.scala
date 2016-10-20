package com.project.consumer

import com.project.production._

import akka.actor._
import better.files._
import FileWatcher._
import FileWatcher.{ Message => FileWatcherMessage }
import java.nio.file.{Paths, Files, WatchEvent}
import java.nio.file.{StandardWatchEventKinds => EventType}
import java.io.File._

object FileConsumer {
  def props(path: String): Props = Props(new FileConsumer(path))
}

class FileConsumer(val path: String) extends Actor {
  val system = ActorSystem()
  val watcher: ActorRef = system.actorOf(Props(new FileWatcher(Paths.get(path))))

  // util to create a RegisterCallback message for the actor
  def when(events: Event*)(callback: Callback): FileWatcherMessage = {
    FileWatcherMessage.RegisterCallback(events.distinct, callback)
  }

  // send the register callback message for create/modify events
  watcher ! when(events = EventType.ENTRY_CREATE, EventType.ENTRY_MODIFY) {
    case (EventType.ENTRY_CREATE, file) => file.lines.map(x => context.parent ! SpecData(x))
    case (EventType.ENTRY_MODIFY, file) => println(s"$file got modified")
  }

  def receive = {
    case _ => ()
  }
}
