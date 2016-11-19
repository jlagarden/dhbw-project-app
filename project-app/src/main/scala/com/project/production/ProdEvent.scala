package com.project.production

import akka.actor.FSM
import akka.actor.FSM._
import akka.actor.Props
import org.json4s._
import org.json4s.Formats._
import org.json4s.native.JsonMethods._
import scala.collection.immutable.Queue

// events
sealed trait ProdEvent
case object L1NonReceiving              extends ProdEvent
case object L1Receiving                 extends ProdEvent
case object L2NonReceiving              extends ProdEvent
case object L2Receiving                 extends ProdEvent
case object L3NonReceiving              extends ProdEvent
case object L3Receiving                 extends ProdEvent
case object L4NonReceiving              extends ProdEvent
case object L4Receiving                 extends ProdEvent
case object L5NonReceiving              extends ProdEvent
case object L5Receiving                 extends ProdEvent
case object MillingStationActive        extends ProdEvent
case object DrillingStationActive       extends ProdEvent
case object Undefined                   extends ProdEvent
