package com.project.production

import akka.actor.FSM
import akka.actor.FSM._
import akka.actor.Props
import org.json4s._
import org.json4s.Formats._
import org.json4s.native.JsonMethods._
import scala.collection.immutable.Queue

case class ProdData private (val value: Any, val status: String, val itemName : String, val timestamp: Int) {

    /*
    Events:
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
    */

    def event(): ProdEvent = {
        this match {
            case ProdData(false, _, "L1", _) => L1start
            case ProdData(true, _, "L1", _) => L1ende
            case ProdData(false, _, "L2", _) => L2start
            case ProdData(true, _, "L2", _) => L2ende
            case ProdData(false, _, "L3", _) => L3start
            case ProdData(true, _, "L3", _) => L3ende
            case ProdData(false, _, "L4", _) => L4start
            case ProdData(true, _, "L4", _) => L4ende
            case ProdData(false, _,"L5",  _) => L5start
            case ProdData(true, _, "L5", _) => L5ende
            case ProdData(_, _, "MILLING", _) => Fraese
            case ProdData(_, _, "MILLING_SPEED", _) => Fraese
            case ProdData(_, _, "MILLING_HEAT", _) => Fraese
            case ProdData(_, _, "DRILLING", _) => Bohre
            case ProdData(_, _, "DRILLING_SPEED", _) => Bohre
            case ProdData(_, _, "DRILLING_HEAT", _) => Bohre

            case ProdData(_,_,_,_) => Undefined
        }
    }

}

object ProdData {

    implicit val formats = DefaultFormats

    def apply(input: String) = parse(input).extract[ProdData]

}
