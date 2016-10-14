package com.projekt

import akka.actor.FSM
import akka.actor.FSM._
import akka.actor.Props

// states
sealed trait State
case object Start                       extends State
case object Lichtschranke1eintritt      extends State
case object Lichtschranke1austritt      extends State
case object Lichtschranke2eintritt      extends State
case object Lichtschranke2austritt      extends State
case object Lichtschranke3eintritt      extends State
case object Lichtschranke3austritt      extends State
case object Lichtschranke4eintritt      extends State
case object Lichtschranke4austritt      extends State
case object Lichtschranke5eintritt      extends State
case object Lichtschranke5austritt      extends State
case object Bohren                      extends State
case object Fraesen                     extends State
case object Schieber1                   extends State
case object Schieber2                   extends State
case object Ende                        extends State

// events 
case object Produktionsbegin 
case object Pruduktionsende
case object WeiterFahren
case object Fraese
case object Bohre

class ProduktionsFSM extends FSM[State, Any] {
    startWith(Start, null)

    when(Start) {
        case Event(Produktionsbegin, _) => goto(Lichtschranke1eintritt)
    }
    
    when(Lichtschranke1eintritt) {
        case Event(WeiterFahren, _) => goto(Lichtschranke1austritt)
    }
      
    when(Lichtschranke1austritt) {
        case Event(WeiterFahren, _) => goto(Lichtschranke1austritt)
    }
  
    when(Lichtschranke2eintritt) {
        case Event(WeiterFahren, _) => goto(Lichtschranke2austritt)
    }

    when(Lichtschranke2austritt) {
        case Event(WeiterFahren, _) => goto(Schieber1)
    }
    
    when(Lichtschranke3eintritt) {
        case Event(WeiterFahren, _) => goto(Fraesen)
    }

    when(Lichtschranke3austritt) {
        case Event(WeiterFahren, _) => goto(Lichtschranke4eintritt)
    }
    
    when(Lichtschranke4eintritt) {
        case Event(WeiterFahren, _) => goto(Bohren)
    }

    when(Lichtschranke4austritt) {
        case Event(WeiterFahren, _) => goto(Schieber2)
    }

    when(Lichtschranke5eintritt) {
        case Event(WeiterFahren, _) => goto(Lichtschranke5austritt)
    }
    
    when(Lichtschranke5austritt) {
        case Event(WeiterFahren, _) => goto(Ende)
    }

    when(Bohren) {
        case Event(WeiterFahren, _) => goto(Lichtschranke4austritt)
    }

    when(Fraesen) {
        case Event(WeiterFahren, _) => goto(Lichtschranke3austritt)
    }

    when(Schieber1) {
        case Event(WeiterFahren, _) => goto(Lichtschranke3eintritt)
    }

    when(Schieber2) {
        case Event(WeiterFahren, _) => goto(Lichtschranke5eintritt)
    }

    when(Lichtschranke2austritt) {
        case Event(WeiterFahren, _) => goto(Schieber1)
    }


    when(Ende) {
        case Event(WeiterFahren, _) => stay()
    }
    
    whenUnhandled {
        case Event(e, _) =>  stay()
    }

    initialize()
}
