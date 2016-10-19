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
case object L1start             extends ProdEvent
case object L1ende              extends ProdEvent
case object L2start             extends ProdEvent
case object L2ende              extends ProdEvent
case object L3start             extends ProdEvent
case object L3ende              extends ProdEvent
case object L4start             extends ProdEvent
case object L4ende              extends ProdEvent
case object L5start             extends ProdEvent
case object L5ende              extends ProdEvent
case object Fraese              extends ProdEvent
case object Bohre               extends ProdEvent
case object Undefined           extends ProdEvent
