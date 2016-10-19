package com.project.production

import akka.actor.FSM
import akka.actor.FSM._
import akka.actor.Props
import org.json4s._
import org.json4s.Formats._
import org.json4s.native.JsonMethods._
import scala.collection.immutable.Queue

class ItemFSM extends FSM[State, Queue[ProdData]] {
    // Zustandsautomat: Start -> L1 -> Transport1 -> L2 -> Schieber1 -> Fraesen -> Transport2 -> Bohren -> Schieben2 -> L5 -> Ende

    startWith(Start, Queue[ProdData]())

    when(Start) {
        case Event((L1start, x: ProdData), state) => goto(L1) using state.enqueue(x)
    }

    when(L1) {
        case Event((L1ende, x: ProdData), state) => goto(Transport1) using state.enqueue(x)
    }

    when(Transport1) {
        case Event((L2start,x: ProdData), state) => goto(L2) using state.enqueue(x)
    }

    when(L2) {
        case Event((L2ende, x: ProdData), state) => goto(Schieber1) using state.enqueue(x)
    }

    when(Schieber1) {
        case Event((L3start, x: ProdData), state) => goto(Fraesen) using state.enqueue(x)
    }

    when(Fraesen) {
        case Event((L3ende, x: ProdData), state) => goto(Transport2) using state.enqueue(x)
        case Event((Fraese, x: ProdData), state) => stay() using state.enqueue(x)
    }

    when(Transport2) {
        case Event((L4start, x: ProdData), state) => goto(Bohren) using state.enqueue(x)
    }

    when(Bohren) {
        case Event((L4ende, x: ProdData), state) => goto(Schieber2) using state.enqueue(x)
        case Event((Bohre, x: ProdData), state) => stay() using state.enqueue(x)
    }

    when(Schieber2) {
        case Event((L5start, x: ProdData), state) => goto(L5) using state.enqueue(x)
    }

    when(L5) {
        case Event((L5ende, x: ProdData), state) => goto(Ende) using state.enqueue(x)
    }

    when(Ende) {
        case Event(_,_) => stay()
    }

    whenUnhandled {
        case Event(e, d) => {
            println(s"Unhandled Event $e with Data $d")
            stay()
        }
    }

    initialize()
}
