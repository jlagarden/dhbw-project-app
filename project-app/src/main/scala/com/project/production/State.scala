package com.project.production

import akka.actor.FSM
import akka.actor.FSM._
import akka.actor.Props
import org.json4s._
import org.json4s.Formats._
import org.json4s.native.JsonMethods._
import scala.collection.immutable.Queue


// states
sealed trait State
case object Start               extends State
case object L1Interrupted       extends State
case object L2Interrupted       extends State
case object L5Interrupted       extends State
case object Transporting1       extends State
case object Transporting2       extends State
case object InDrillingStation  extends State
case object InMillingStation   extends State
case object SlideFeeding1       extends State
case object SlideFeeding2       extends State
case object End                 extends State
