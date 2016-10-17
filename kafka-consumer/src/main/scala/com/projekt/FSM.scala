package com.projekt

import akka.actor.FSM
import akka.actor.FSM._
import akka.actor.Props

// states
sealed trait State
case object Start               extends State
case object L1                  extends State
case object L2                  extends State
case object L5                  extends State
case object Transport1          extends State
case object Transport2          extends State
case object Bohren              extends State
case object Fraesen             extends State
case object Schieber1           extends State
case object Schieber2           extends State
case object Ende                extends State

// events 
case object L1start
case object L1ende
case object L2start
case object L2ende
case object L3start
case object L3ende
case object L4start
case object L4ende
case object L5start
case object L5ende
case object Fraese
case object Bohre

class ProduktionsFSM extends FSM[State, Any] {
    startWith(Start, null)

    when(Start) {
        case Event(L1start, _) => goto(L1)
    }
    
    when(L1) {
        case Event(L1ende, _) => goto(Transport1)
    }

    when(Transport1) {
        case Event(L2start,_) => goto(L2)
    }
      
    when(L2) {
        case Event(L2ende, _) => goto(Schieber1)
    }
  
    when(Schieber1) {
        case Event(L3start, _) => goto(Fraesen)
    }

    when(Fraesen) {
        case Event(L3ende, _) => goto(Transport2)
    }
    
    when(Transport2) {
        case Event(L4start, _) => goto(Bohren)
    }

    when(Bohren) {
        case Event(L4ende, _) => goto(Schieber2)
    }
    
    when(Schieber2) {
        case Event(L5start, _) => goto(L5)
    }

    when(L5) {
        case Event(L5ende, _) => stay(Ende)
    }

    when(Ende) {
        case Event(_,_) => stay()
    }
    
    whenUnhandled {
        case Event(e, _) =>  stay()
    }

    initialize()
}

/*
L1start     {"value":false,"status":"GOOD","itemName":"L1","timestamp":1476726173743}
L1ende      {"value":true,"status":"GOOD","itemName":"L1","timestamp":1476726203843}
L2start     {"value":false,"status":"GOOD","itemName":"L2","timestamp":1476726273923}
L2ende      {"value":true,"status":"GOOD","itemName":"L2","timestamp":1476726304023}
L3start     {"value":false,"status":"GOOD","itemName":"L3","timestamp":1476726374083}
Fraese      {"value":true,"status":"GOOD","itemName":"MILLING","timestamp":1476726404193}
Fraese      {"value":12480,"status":"GOOD","itemName":"MILLING_SPEED","timestamp":1476726414223}
Fraese      {"value":143.96,"status":"GOOD","itemName":"MILLING_HEAT","timestamp":1476726464303}
Fraese      {"value":165.554,"status":"GOOD","itemName":"MILLING_HEAT","timestamp":1476726514373}
Fraese      {"value":15000,"status":"GOOD","itemName":"MILLING_SPEED","timestamp":1476726584473}
Fraese      {"value":215.94,"status":"GOOD","itemName":"MILLING_HEAT","timestamp":1476726594523}
Fraese      {"value":201.544,"status":"GOOD","itemName":"MILLING_HEAT","timestamp":1476726664613}
Fraese      {"value":0,"status":"GOOD","itemName":"MILLING_SPEED","timestamp":1476726724713}
Fraese      {"value":146.66,"status":"GOOD","itemName":"MILLING_HEAT","timestamp":1476726734753}
Fraese      {"value":39.769999999999996,"status":"GOOD","itemName":"MILLING_HEAT","timestamp":1476726764843}
Fraese      {"value":false,"status":"GOOD","itemName":"MILLING","timestamp":1476726774873}
L3ende      {"value":true,"status":"GOOD","itemName":"L3","timestamp":1476726814943}
L4start     {"value":false,"status":"GOOD","itemName":"L4","timestamp":1476726865023}
Bohre       {"value":true,"status":"GOOD","itemName":"DRILLING","timestamp":1476726905133}
Bohre       {"value":15500,"status":"GOOD","itemName":"DRILLING_SPEED","timestamp":1476726915153}
Bohre       {"value":212.7,"status":"GOOD","itemName":"DRILLING_HEAT","timestamp":1476726965253}
Bohre       {"value":244.60499999999996,"status":"GOOD","itemName":"DRILLING_HEAT","timestamp":1476727015323}
Bohre       {"value":18500,"status":"GOOD","itemName":"DRILLING_SPEED","timestamp":1476727085393}
Bohre       {"value":319.04999999999995,"status":"GOOD","itemName":"DRILLING_HEAT","timestamp":1476727095433}
Bohre       {"value":297.78,"status":"GOOD","itemName":"DRILLING_HEAT","timestamp":1476727165503}
Bohre       {"value":0,"status":"GOOD","itemName":"DRILLING_SPEED","timestamp":1476727215563}
Bohre       {"value":120.98,"status":"GOOD","itemName":"DRILLING_HEAT","timestamp":1476727225603}
Bohre       {"value":false,"status":"GOOD","itemName":"DRILLING","timestamp":1476727235633}
L4ende      {"value":true,"status":"GOOD","itemName":"L4","timestamp":1476727265673}
L5ende      {"value":false,"status":"GOOD","itemName":"L5","timestamp":1476727365793}
L5ende      {"value":true,"status":"GOOD","itemName":"L5","timestamp":1476727395873}

Zustandsautomat:
Start -> L1 -> Transport1 -> L2 -> Schieber1 -> Fraesen -> Transport2 -> Bohren -> Schieben2 -> L5 -> Ende
*/
