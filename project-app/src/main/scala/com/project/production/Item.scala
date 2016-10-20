package com.project.production

import org.json4s._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.FSM
import akka.actor.FSM._
import akka.actor.Props
import scala.collection.immutable.Queue

case class ItemData(val erpData: ERPData, val specData: SpecData, val prodData: Queue[_])

class Item extends Actor {
    val fsm: ActorRef = context.actorOf(Props[ItemFSM])
    var erpData: Option[ERPData] = None
    var specData: Option[SpecData] = None
    var prodData: Option[Queue[_]] = None

    implicit val formats = Serialization.formats(NoTypeHints)

    def receive = {
        case (x: ProdEvent, y: ProdData) => fsm ! (x, y)
        case x: ERPData => erpData = Some(x)
        case x: SpecData => {
            specData = Some(x)
            trySubmit()
        }
        case x: Queue[_] => prodData = Some(x)
    }

    def itemData: ItemData = {
        val item = for {
            e <- erpData
            s <- specData
            p <- prodData
        } yield ItemData(e, s, p)
        item.get
    }

    def serialize(item: ItemData): String = {
        write(item)
    }

    def trySubmit() {
        erpData.isDefined && specData.isDefined && prodData.isDefined match {
            case true => context.parent ! serialize(itemData)
            case _ => ()
        }
    }
}
