package com.project.production

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.FSM
import akka.actor.FSM._
import akka.actor.Props

class Item extends Actor {
    val fsm: ActorRef = context.actorOf(Props[ItemFSM])
    var erpData: Option[ERPData] = None
    var specData: Option[SpecData] = None

    def receive = {
        case (x: ProdEvent, y: ProdData) => fsm ! (x, y)
        case x : ERPData => erpData = Some(x)
        case x : SpecData => specData = Some(x)
    }
}
