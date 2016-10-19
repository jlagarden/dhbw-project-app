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
