package com.project.production

import akka.actor.FSM
import akka.actor.FSM._
import akka.actor.Props
import org.json4s._
import org.json4s.Formats._
import org.json4s.native.JsonMethods._
import scala.collection.immutable.Queue

class ItemFSM extends FSM[State, Queue[ProdData]] {
    // Zustandsautomat: Start -> L1 -> Transporting1 -> L2 -> SlideFeeding1 -> InMillingStationn -> Transporting2 -> InDrillingStationn -> Schieben2 -> L5 -> Ende

    startWith(Start, Queue[ProdData]())

    when(Start) {
        case Event((L1NonReceiving, x: ProdData), state) => {
            println("Start")
            goto(L1Interrupted) using state.enqueue(x)
        }
    }

    when(L1Interrupted) {
        case Event((L1Receiving, x: ProdData), state) => goto(Transporting1) using state.enqueue(x)
    }

    when(Transporting1) {
        case Event((L2NonReceiving,x: ProdData), state) => goto(L2Interrupted) using state.enqueue(x)
    }

    when(L2Interrupted) {
        case Event((L2Receiving, x: ProdData), state) => goto(SlideFeeding1) using state.enqueue(x)
    }

    when(SlideFeeding1) {
        case Event((L3NonReceiving, x: ProdData), state) => goto(InMillingStationn) using state.enqueue(x)
    }

    when(InMillingStationn) {
        case Event((L3Receiving, x: ProdData), state) => goto(Transporting2) using state.enqueue(x)
        case Event((InMillingStation, x: ProdData), state) => stay() using state.enqueue(x)
    }

    when(Transporting2) {
        case Event((L4NonReceiving, x: ProdData), state) => goto(InDrillingStationn) using state.enqueue(x)
    }

    when(InDrillingStationn) {
        case Event((L4Receiving, x: ProdData), state) => goto(SlideFeeding2) using state.enqueue(x)
        case Event((InDrillingStation, x: ProdData), state) => stay() using state.enqueue(x)
    }

    when(SlideFeeding2) {
        case Event((L5NonReceiving, x: ProdData), state) => goto(L5Interrupted) using state.enqueue(x)
    }

    when(L5Interrupted) {
        case Event((L5Receiving, x: ProdData), state) => {
            println("Ende")
            stop(FSM.Normal, state.enqueue(x))
        }
    }

    when(Ende) {
        case Event(_,_) => stay()
    }


    onTermination {
        case StopEvent(_, state, data) => context.parent ! data
    }

    whenUnhandled {
        case Event(e, d) => {
            println(s"Unhandled Event $e with Data $d")
            stay()
        }
    }

    initialize()
}
