package com.project

import com.project._
import akka.actor._
import scala.collection.mutable.HashMap

class Production extends Actor {
    val products : HashMap[String, ActorRef] = new HashMap[String, ActorRef]

    def receive = {
        case x : String => {
            if(products.contains(x)) {
                sender() ! products.get(x)
            } else {
                val fsm = context.actorOf(Props[ProduktionsFSM])
                products += (x -> fsm)
                sender() ! fsm
            }
        }
    }
}
