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
    L1NonReceiving     {"value":false,"status":"GOOD","itemName":"L1","timestamp":1476726173743}
    L1Receiving      {"value":true,"status":"GOOD","itemName":"L1","timestamp":1476726203843}
    L2NonReceiving     {"value":false,"status":"GOOD","itemName":"L2","timestamp":1476726273923}
    L2Receiving      {"value":true,"status":"GOOD","itemName":"L2","timestamp":1476726304023}
    L3NonReceiving     {"value":false,"status":"GOOD","itemName":"L3","timestamp":1476726374083}
    InMillingStation      {"value":true,"status":"GOOD","itemName":"MILLING","timestamp":1476726404193}
    InMillingStation      {"value":12480,"status":"GOOD","itemName":"MILLING_SPEED","timestamp":1476726414223}
    InMillingStation      {"value":143.96,"status":"GOOD","itemName":"MILLING_HEAT","timestamp":1476726464303}
    InMillingStation      {"value":165.554,"status":"GOOD","itemName":"MILLING_HEAT","timestamp":1476726514373}
    InMillingStation      {"value":15000,"status":"GOOD","itemName":"MILLING_SPEED","timestamp":1476726584473}
    InMillingStation      {"value":215.94,"status":"GOOD","itemName":"MILLING_HEAT","timestamp":1476726594523}
    InMillingStation      {"value":201.544,"status":"GOOD","itemName":"MILLING_HEAT","timestamp":1476726664613}
    InMillingStation      {"value":0,"status":"GOOD","itemName":"MILLING_SPEED","timestamp":1476726724713}
    InMillingStation      {"value":146.66,"status":"GOOD","itemName":"MILLING_HEAT","timestamp":1476726734753}
    InMillingStation      {"value":39.769999999999996,"status":"GOOD","itemName":"MILLING_HEAT","timestamp":1476726764843}
    InMillingStation      {"value":false,"status":"GOOD","itemName":"MILLING","timestamp":1476726774873}
    L3Receiving      {"value":true,"status":"GOOD","itemName":"L3","timestamp":1476726814943}
    L4NonReceiving     {"value":false,"status":"GOOD","itemName":"L4","timestamp":1476726865023}
    InDrillingStation       {"value":true,"status":"GOOD","itemName":"DRILLING","timestamp":1476726905133}
    InDrillingStation       {"value":15500,"status":"GOOD","itemName":"DRILLING_SPEED","timestamp":1476726915153}
    InDrillingStation       {"value":212.7,"status":"GOOD","itemName":"DRILLING_HEAT","timestamp":1476726965253}
    InDrillingStation       {"value":244.60499999999996,"status":"GOOD","itemName":"DRILLING_HEAT","timestamp":1476727015323}
    InDrillingStation       {"value":18500,"status":"GOOD","itemName":"DRILLING_SPEED","timestamp":1476727085393}
    InDrillingStation       {"value":319.04999999999995,"status":"GOOD","itemName":"DRILLING_HEAT","timestamp":1476727095433}
    InDrillingStation       {"value":297.78,"status":"GOOD","itemName":"DRILLING_HEAT","timestamp":1476727165503}
    InDrillingStation       {"value":0,"status":"GOOD","itemName":"DRILLING_SPEED","timestamp":1476727215563}
    InDrillingStation       {"value":120.98,"status":"GOOD","itemName":"DRILLING_HEAT","timestamp":1476727225603}
    InDrillingStation       {"value":false,"status":"GOOD","itemName":"DRILLING","timestamp":1476727235633}
    L4Receiving      {"value":true,"status":"GOOD","itemName":"L4","timestamp":1476727265673}
    L5Receiving      {"value":false,"status":"GOOD","itemName":"L5","timestamp":1476727365793}
    L5Receiving      {"value":true,"status":"GOOD","itemName":"L5","timestamp":1476727395873}
    */

    def event(): ProdEvent = {
        this match {
            case ProdData(false, _, "L1", _) => L1NonReceiving
            case ProdData(true, _, "L1", _) => L1Receiving
            case ProdData(false, _, "L2", _) => L2NonReceiving
            case ProdData(true, _, "L2", _) => L2Receiving
            case ProdData(false, _, "L3", _) => L3NonReceiving
            case ProdData(true, _, "L3", _) => L3Receiving
            case ProdData(false, _, "L4", _) => L4NonReceiving
            case ProdData(true, _, "L4", _) => L4Receiving
            case ProdData(false, _,"L5",  _) => L5NonReceiving
            case ProdData(true, _, "L5", _) => L5Receiving
            case ProdData(_, _, "MILLING", _) => MillingStationActive
            case ProdData(_, _, "MILLING_SPEED", _) => MillingStationActive
            case ProdData(_, _, "MILLING_HEAT", _) => MillingStationActive
            case ProdData(_, _, "DRILLING", _) => DrillingStationActive
            case ProdData(_, _, "DRILLING_SPEED", _) => DrillingStationActive
            case ProdData(_, _, "DRILLING_HEAT", _) => DrillingStationActive

            case ProdData(_,_,_,_) => Undefined
        }
    }
}

object ProdData {

    implicit val formats = DefaultFormats

    def apply(input: String) = parse(input).extract[ProdData]

}
