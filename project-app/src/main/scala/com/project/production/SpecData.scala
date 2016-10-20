package com.project.production

import akka.actor.FSM
import akka.actor.FSM._
import akka.actor.Props
import org.json4s._
import org.json4s.Formats._
import org.json4s.native.JsonMethods._
import scala.collection.immutable.Queue

case class SpecData private (val em1: Double, val em2: Double,  val a1: Double,val a2: Double, val b2: Double, val b1: Double, val overallStatus: String, val ts_start: Double, val ts_stop: Double)

object SpecData {
  implicit val formats = DefaultFormats
  
  def apply(input: String): SpecData = {
    parse(input).extract[SpecData]
  }
}

//{"em1":68.57023259955811,"em2":23.00670413496103,"a1":93.71879801932647,"a2":39.361895168117115,"b2":905.5874763242226,"b1":6426.319779136222,"overallStatus":"OK","ts_start":1476939031019,"ts_stop":1476939038025}
